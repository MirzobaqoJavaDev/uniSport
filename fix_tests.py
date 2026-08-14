import os
import re

def fix_test_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # Replace .setId(UUID) with .setUuid(UUID)
    content = re.sub(r'\.setId\((.*?Id.*?)\);', r'.setUuid(\1);', content)
    # also for wrongId
    content = re.sub(r'\.setId\((.*?wrong.*?)\);', r'.setUuid(\1);', content)
    
    # Replace .findById(UUID) with .findByUuid(UUID)
    content = re.sub(r'\.findById\((.*?Id.*?)\)', r'.findByUuid(\1)', content)
    content = re.sub(r'\.findById\((.*?wrong.*?)\)', r'.findByUuid(\1)', content)
    
    # Replace existsById(UUID) with existsByUuid(UUID) if any
    content = re.sub(r'\.existsById\((.*?Id.*?)\)', r'.existsByUuid(\1)', content)
    
    # Check for BookingReminderServiceTest etc where they do booking.setId(bookingId)
    # The regex .setId\((.*?Id.*?)\); handles it.

    with open(filepath, 'w') as f:
        f.write(content)

test_files = [
    "src/test/java/uz/uniSport/uni_sport/service/gym/FacilityServiceImplTest.java",
    "src/test/java/uz/uniSport/uni_sport/service/auth/AuthServiceImplTest.java",
    "src/test/java/uz/uniSport/uni_sport/service/notification/BookingReminderServiceTest.java",
    "src/test/java/uz/uniSport/uni_sport/service/notification/BookingReminderSchedulerTest.java"
]

for f in test_files:
    fix_test_file(f)
    print(f"Fixed {f}")

