import os
import re

changesets_dir = "src/main/resources/db/changelog/changesets"

for filename in sorted(os.listdir(changesets_dir)):
    if not filename.endswith(".yaml"): continue
    
    filepath = os.path.join(changesets_dir, filename)
    with open(filepath, 'r') as f:
        content = f.read()

    new_content = content

    # 1. replace primary key UUID to BIGINT + autoIncrement
    # Pattern: 
    #   name: id
    #   type: UUID
    # Replacement:
    #   name: id
    #   type: BIGINT
    #   autoIncrement: true
    new_content = re.sub(
        r'(name:\s*id\s*\n\s*type:\s*)UUID',
        r'\1BIGINT\n                  autoIncrement: true',
        new_content
    )

    # 2. replace foreign key UUID to BIGINT
    # Pattern: type: UUID (in other places)
    new_content = re.sub(
        r'(type:\s*)UUID',
        r'\1BIGINT',
        new_content
    )

    # 3. Inject `uuid` and `deleted` column just after `name: id` column block.
    # Note: Some tables (like databaseChangeLog) might not have it, but we are only modifying our own tables created in these files.
    # Pattern:
    #               - column:
    #                   name: id
    #                   type: BIGINT
    #                   autoIncrement: true
    #                   constraints:
    #                     primaryKey: true
    #
    # We will search for `name: id` block and append our columns.
    
    def inject_columns(match):
        block = match.group(0)
        # Check if uuid already in this table's content (very rough check, but safe for our files)
        uuid_col = """
              - column:
                  name: uuid
                  type: UUID
                  constraints:
                    nullable: false
                    unique: true"""
        
        deleted_col = """
              - column:
                  name: deleted
                  type: BOOLEAN
                  defaultValueBoolean: false
                  constraints:
                    nullable: false"""
        
        # we append these columns after the id block finishes (we match until the next `- column:` or the end of the block)
        return block + uuid_col + deleted_col

    # Since regex for finding a whole YAML block is tricky, we can just replace the `tableName: ...` and inject columns after it.
    # Actually, a safer way to inject is to put them at the very end of `columns:` block? No, just after `id` column.
    
    # Let's just find `primaryKey: true` and inject after it.
    # Actually, the UUID to BIGINT conversion might affect our `uuid_col` above if we run it before! So we run (2) before (3).
    
    # We will write a simple python parser for the lines to safely insert `uuid` and `deleted` for each `createTable`.
    lines = new_content.split('\n')
    output_lines = []
    
    inside_create_table = False
    added_uuid = False
    
    for i, line in enumerate(lines):
        output_lines.append(line)
        
        if 'createTable:' in line:
            inside_create_table = True
            added_uuid = False
        elif 'dropTable:' in line or 'addColumn:' in line:
            inside_create_table = False
            
        if inside_create_table and not added_uuid and 'primaryKey: true' in line:
            # We found the end of the id column block
            indent = line[:len(line) - len(line.lstrip())]
            base_indent = indent[:-14] # back off from constraints: primaryKey: true
            # This is fragile, let's just use a hardcoded indent of 14 spaces for `- column:`
            c_indent = "              "
            output_lines.append(c_indent + "- column:")
            output_lines.append(c_indent + "    name: uuid")
            output_lines.append(c_indent + "    type: UUID")
            output_lines.append(c_indent + "    constraints:")
            output_lines.append(c_indent + "      nullable: false")
            output_lines.append(c_indent + "      unique: true")
            
            output_lines.append(c_indent + "- column:")
            output_lines.append(c_indent + "    name: deleted")
            output_lines.append(c_indent + "    type: BOOLEAN")
            output_lines.append(c_indent + "    defaultValueBoolean: false")
            output_lines.append(c_indent + "    constraints:")
            output_lines.append(c_indent + "      nullable: false")
            
            added_uuid = True

    new_content = '\n'.join(output_lines)
    
    # 02-init-tables.yaml fix for `users` and `roles` which might have had BIGINT before
    # if `uuid` is added twice, let's fix it later.

    if content != new_content:
        with open(filepath, 'w') as f:
            f.write(new_content)
        print(f"Updated {filename}")
