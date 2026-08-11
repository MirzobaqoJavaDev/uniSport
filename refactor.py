import os
import re

def process_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    new_content = content
    
    new_content = new_content.replace('roleRepository.findById', 'roleRepository.FIND_BY_ID_TEMP')
    new_content = new_content.replace('permissionRepository.findById', 'permissionRepository.FIND_BY_ID_TEMP')
    
    new_content = new_content.replace('.findById(', '.findByUuid(')
    
    new_content = re.sub(r'([a-zA-Z0-9_]+)\.existsById\(([^)]+)\)', r'\1.findByUuid(\2).isPresent()', new_content)

    new_content = re.sub(r'([a-zA-Z0-9_]+)\.deleteById\(([^)]+)\);', r'\1.findByUuid(\2).ifPresent(\1::delete);', new_content)

    new_content = new_content.replace('roleRepository.FIND_BY_ID_TEMP', 'roleRepository.findById')
    new_content = new_content.replace('permissionRepository.FIND_BY_ID_TEMP', 'permissionRepository.findById')

    if content != new_content:
        with open(filepath, 'w') as f:
            f.write(new_content)
        print(f"Updated: {filepath}")

for root, dirs, files in os.walk('src/main/java/uz/uniSport/uni_sport/service'):
    for file in files:
        if file.endswith('.java'):
            process_file(os.path.join(root, file))
