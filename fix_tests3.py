import os
import re

def fix_test_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    # Replace .setId(...) with .setUuid(...) for anything that is not a DTO
    # But this is hard to distinguish with regex.
    # Let's just find "setId(UUID.randomUUID())" and replace with setUuid(UUID.randomUUID())
    content = re.sub(r'\.setId\(([^)]*UUID[^)]*)\)', r'.setUuid(\1)', content)
    
    # Also some might use a variable that is a UUID, like userId
    content = re.sub(r'\.setId\(userId\)', r'.setUuid(userId)', content)
    content = re.sub(r'\.setId\(roleId\)', r'.setUuid(roleId)', content)
    content = re.sub(r'\.setId\(bookingId\)', r'.setUuid(bookingId)', content)
    content = re.sub(r'\.setId\(facilityId\)', r'.setUuid(facilityId)', content)

    with open(filepath, 'w') as f:
        f.write(content)

test_files = [
    "src/test/java/uz/uniSport/uni_sport/service/auth/AuthServiceImplTest.java",
    "src/test/java/uz/uniSport/uni_sport/service/notification/BookingReminderServiceTest.java",
    "src/test/java/uz/uniSport/uni_sport/service/notification/BookingReminderSchedulerTest.java"
]

for f in test_files:
    fix_test_file(f)
