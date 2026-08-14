import os
import re

changesets_dir = "src/main/resources/db/changelog/changesets"

def process_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # 1. Replace multiline `id` primary key from UUID to BIGINT + autoIncrement
    content = re.sub(
        r'(name:\s*id\s*\n\s*type:\s*)UUID',
        r'\1BIGINT\n                  autoIncrement: true',
        content
    )

    # 2. Replace all `type: UUID` with `type: BIGINT`
    # We will add actual UUID columns manually later.
    content = re.sub(r'type:\s*UUID', r'type: BIGINT', content)
    
    # 3. Add `uuid` and `deleted` if they don't exist in the `createTable` blocks.
    parts = content.split('createTable:')
    new_parts = [parts[0]]
    
    for part in parts[1:]:
        if 'columns:' in part:
            has_uuid = 'name: uuid' in part
            has_deleted = 'name: deleted' in part
            
            insert_str = ""
            if not has_uuid:
                insert_str += """
              - column:
                  name: uuid
                  type: UUID
                  constraints:
                    nullable: false
                    unique: true"""
            
            if not has_deleted:
                insert_str += """
              - column:
                  name: deleted
                  type: BOOLEAN
                  defaultValueBoolean: false
                  constraints:
                    nullable: false"""
            
            if insert_str:
                part = part.replace('            columns:\n', f'            columns:\n{insert_str}\n', 1)
        
        new_parts.append(part)
        
    content = 'createTable:'.join(new_parts)
    
    with open(filepath, 'w') as f:
        f.write(content)

for filename in sorted(os.listdir(changesets_dir)):
    if not filename.endswith(".yaml"): continue
    filepath = os.path.join(changesets_dir, filename)
    process_file(filepath)
    print(f"Processed {filename}")
