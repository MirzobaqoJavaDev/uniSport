import os
import re

# Repo larda: findBy[Name]Id(Long ...Id) -> findBy[Name]Uuid(UUID ...Id)
# Service larda: findBy[Name]Id( -> findBy[Name]Uuid(

replacements = [
    ("UserId", "UserUuid"),
    ("FacilityId", "FacilityUuid"),
    ("CourtId", "CourtUuid"),
    ("EquipmentId", "EquipmentUuid"),
    ("ScheduleId", "ScheduleUuid"),
    ("SubscriptionPlanId", "SubscriptionPlanUuid"),
    ("PlanId", "PlanUuid") # (for workoutplan etc, better handled manually if conflict)
]

def process_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    new_content = content
    
    for old, new in replacements:
        if old == "PlanId":
            # Ehtiyot bo'lamiz, SubscriptionPlanId bilan ustma-ust tushmasligi uchun oldin SubscriptionPlanId ni o'zgartirib bo'lganmiz
            new_content = new_content.replace('findByPlanId', 'findByPlanUuid')
        else:
            new_content = new_content.replace(f'findBy{old}', f'findBy{new}')
            
    # Repozitoriylarda (Long userId) ni (UUID userId) ga aylantirish kerak bo'ladi, chunki findByUserUuid endi Long emas UUID oladi.
    if 'repository' in filepath.lower():
        for old, new in replacements:
            # masalan: findByUserUuid(Long userId) -> findByUserUuid(UUID userId)
            new_content = re.sub(fr'findBy{new}\(Long\s+([^)]+)\)', fr'findBy{new}(UUID \1)', new_content)

    # PermissionService xatosi
    if 'PermissionService.java' in filepath:
        new_content = new_content.replace('UUID id', 'Long id')
        new_content = new_content.replace('findById(id)', 'findByIdTemp(id)') # ehtiyot uchun
        new_content = new_content.replace('findByUuid(id)', 'findById(id)')

    # findAllById(ids) -> findByUuidIn(ids) for WorkoutPlanService
    if 'WorkoutPlanService.java' in filepath:
        new_content = new_content.replace('.findAllById(', '.findByUuidIn(')
        
    if 'ExerciseRepository.java' in filepath:
        if 'findByUuidIn' not in new_content:
            new_content = new_content.replace('}', '    java.util.List<uz.uniSport.uni_sport.domain.wellness.Exercise> findByUuidIn(java.util.Collection<java.util.UUID> uuids);\n}')

    if content != new_content:
        with open(filepath, 'w') as f:
            f.write(new_content)
        print(f"Updated: {filepath}")

for root, dirs, files in os.walk('src/main/java/uz/uniSport/uni_sport'):
    if 'repository' in root or 'service' in root:
        for file in files:
            if file.endswith('.java'):
                process_file(os.path.join(root, file))
