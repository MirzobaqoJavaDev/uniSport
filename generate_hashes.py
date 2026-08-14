import bcrypt

passwords = {
    'test.user': 'User123!',
    'test.coach': 'Coach123!',
    'test.desk': 'Desk123!',
    'test.facility.admin': 'Facility123!',
    'test.sport.director': 'Admin123!',
    'test.ministry.observer': 'Observer123!'
}

for username, pw in passwords.items():
    hashed = bcrypt.hashpw(pw.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')
    print(f"{username}: {hashed}")
