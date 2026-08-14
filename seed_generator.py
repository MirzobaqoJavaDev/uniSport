import uuid
import yaml
from collections import OrderedDict

# Required Permissions
permissions = [
    "USER_PROFILE_READ_SELF",
    "USER_PROFILE_UPDATE_SELF",
    "FACILITY_READ",
    "FACILITY_CREATE",
    "FACILITY_UPDATE",
    "FACILITY_DELETE",
    "BOOKING_CREATE",
    "BOOKING_READ_SELF",
    "BOOKING_READ",
    "BOOKING_UPDATE",
    "BOOKING_CANCEL",
    "BOOKING_MANAGE",
    "EQUIPMENT_READ",
    "EQUIPMENT_CREATE",
    "EQUIPMENT_UPDATE",
    "EQUIPMENT_DELETE",
    "EQUIPMENT_ISSUE",
    "EQUIPMENT_RETURN",
    "EQUIPMENT_SCAN",
    "EQUIPMENT_MANAGE",
    "CLASS_READ",
    "CLASS_CREATE",
    "CLASS_UPDATE",
    "CLASS_DELETE",
    "TRAINING_READ",
    "TRAINING_CREATE",
    "TRAINING_UPDATE",
    "TRAINING_DELETE",
    "ATTENDANCE_READ",
    "ATTENDANCE_CREATE",
    "ATTENDANCE_MANAGE",
    "ATTENDANCE_QR_SCAN",
    "AI_RECOMMENDATION_USE",
    "AI_IMAGE_ANALYSIS_USE",
    "NOTIFICATION_READ_SELF",
    "NOTIFICATION_MANAGE",
    "QUOTA_READ",
    "QUOTA_MANAGE",
    "FINANCIAL_REPORT_READ",
    "FINANCIAL_REPORT_MANAGE",
    "SYSTEM_SETTINGS_READ",
    "SYSTEM_SETTINGS_MANAGE",
    "USER_READ",
    "USER_CREATE",
    "USER_UPDATE",
    "USER_DELETE",
    "USER_ROLE_ASSIGN",
    "ROLE_READ",
    "ROLE_MANAGE",
    "PERMISSION_READ",
    "PERMISSION_MANAGE",
    "MONITORING_DASHBOARD_READ",
    "MONITORING_AGGREGATED_REPORT_READ"
]

roles = [
    "USER",
    "COACH",
    "DESK_STAFF",
    "FACILITY_PARTNER_ADMIN",
    "SPORT_DIRECTOR",
    "MINISTRY_OBSERVER"
]

role_mappings = {
    "USER": [
        "USER_PROFILE_READ_SELF", "USER_PROFILE_UPDATE_SELF", "FACILITY_READ",
        "BOOKING_CREATE", "BOOKING_READ_SELF", "BOOKING_UPDATE", "BOOKING_CANCEL",
        "EQUIPMENT_READ", "CLASS_READ", "TRAINING_READ", "AI_RECOMMENDATION_USE",
        "AI_IMAGE_ANALYSIS_USE", "NOTIFICATION_READ_SELF"
    ],
    "COACH": [
        "USER_PROFILE_READ_SELF", "USER_PROFILE_UPDATE_SELF", "FACILITY_READ",
        "CLASS_READ", "CLASS_CREATE", "CLASS_UPDATE", "TRAINING_READ", "TRAINING_CREATE",
        "TRAINING_UPDATE", "ATTENDANCE_READ", "ATTENDANCE_CREATE", "ATTENDANCE_MANAGE",
        "ATTENDANCE_QR_SCAN", "AI_RECOMMENDATION_USE", "NOTIFICATION_READ_SELF"
    ],
    "DESK_STAFF": [
        "FACILITY_READ", "BOOKING_READ", "EQUIPMENT_READ", "EQUIPMENT_ISSUE",
        "EQUIPMENT_RETURN", "EQUIPMENT_SCAN", "ATTENDANCE_QR_SCAN", "NOTIFICATION_READ_SELF"
    ],
    "FACILITY_PARTNER_ADMIN": [
        "FACILITY_READ", "FACILITY_CREATE", "FACILITY_UPDATE", "BOOKING_READ", "BOOKING_MANAGE",
        "EQUIPMENT_READ", "EQUIPMENT_MANAGE", "SYSTEM_SETTINGS_READ"
    ],
    "SPORT_DIRECTOR": [
        "USER_READ", "USER_CREATE", "USER_UPDATE", "USER_DELETE", "USER_ROLE_ASSIGN",
        "ROLE_READ", "ROLE_MANAGE", "PERMISSION_READ", "PERMISSION_MANAGE", "FACILITY_READ",
        "FACILITY_CREATE", "FACILITY_UPDATE", "FACILITY_DELETE", "BOOKING_READ", "BOOKING_MANAGE",
        "EQUIPMENT_READ", "EQUIPMENT_CREATE", "EQUIPMENT_UPDATE", "EQUIPMENT_DELETE",
        "EQUIPMENT_ISSUE", "EQUIPMENT_RETURN", "EQUIPMENT_SCAN", "CLASS_READ", "CLASS_CREATE",
        "CLASS_UPDATE", "CLASS_DELETE", "TRAINING_READ", "TRAINING_CREATE", "TRAINING_UPDATE",
        "TRAINING_DELETE", "ATTENDANCE_READ", "ATTENDANCE_MANAGE", "QUOTA_READ", "QUOTA_MANAGE",
        "FINANCIAL_REPORT_READ", "FINANCIAL_REPORT_MANAGE", "SYSTEM_SETTINGS_READ",
        "SYSTEM_SETTINGS_MANAGE", "NOTIFICATION_MANAGE"
    ],
    "MINISTRY_OBSERVER": [
        "MONITORING_DASHBOARD_READ", "MONITORING_AGGREGATED_REPORT_READ", "FACILITY_READ"
    ]
}

test_users = [
    {"email": "test.user", "first": "Test", "last": "User", "role": "USER", "hash": "$2b$12$1p19FrWD4rHZ7ssVneJQDOn/9oXhso1v.NA1iMbCQIPdIo7FAasJO"},
    {"email": "test.coach", "first": "Test", "last": "Coach", "role": "COACH", "hash": "$2b$12$Z9uwlBZ4U/D4WSAz3J4z.e7sgsw5E49dgA9cH.q6G7KKynx.9BibS"},
    {"email": "test.desk", "first": "Test", "last": "Desk", "role": "DESK_STAFF", "hash": "$2b$12$E2sNR/5DzsBl5tmQ3qvUgOVt5gwjiqQYIvSdgl3xHO9BAyQ.8UYxe"},
    {"email": "test.facility.admin", "first": "Test", "last": "Facility", "role": "FACILITY_PARTNER_ADMIN", "hash": "$2b$12$svX1iEM0gWkfWZY0mxfQqOr1qjbpMsKt19vAJIZQkj/Ba2aaeDX0K"},
    {"email": "test.sport.director", "first": "Test", "last": "Director", "role": "SPORT_DIRECTOR", "hash": "$2b$12$kyIHt9R/7Jy7jL6l11wneOI4hIouTbAu3WOJcDWlgZREyqGbUDXOS"},
    {"email": "test.ministry.observer", "first": "Test", "last": "Observer", "role": "MINISTRY_OBSERVER", "hash": "$2b$12$wYD/s/TUoZJ4KHPXDjU1YePtk7/juCObT3k4LdhTWl0E7Wfz1qgqi"}
]

sql_statements = []

for p in permissions:
    sql = f"INSERT INTO permissions (uuid, name, description, deleted, created_at, updated_at) VALUES ('{str(uuid.uuid4())}', '{p}', '{p}', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT (name) DO NOTHING;"
    sql_statements.append(sql)

for r in roles:
    sql = f"INSERT INTO roles (uuid, name, deleted, created_at, updated_at) VALUES ('{str(uuid.uuid4())}', '{r}', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ON CONFLICT (name) DO NOTHING;"
    sql_statements.append(sql)

for r, perms in role_mappings.items():
    for p in perms:
        sql = f"INSERT INTO role_permissions (uuid, role_id, permission_id, deleted) SELECT '{str(uuid.uuid4())}', r.id, p.id, false FROM roles r, permissions p WHERE r.name = '{r}' AND p.name = '{p}' ON CONFLICT DO NOTHING;"
        sql_statements.append(sql)

for u in test_users:
    sql = f"INSERT INTO users (uuid, email, password_hash, first_name, last_name, role_id, deleted, created_at, updated_at) SELECT '{str(uuid.uuid4())}', '{u['email']}', '{u['hash']}', '{u['first']}', '{u['last']}', r.id, false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP FROM roles r WHERE r.name = '{u['role']}' ON CONFLICT (email) DO NOTHING;"
    sql_statements.append(sql)

yaml_content = {
    "databaseChangeLog": [
        {
            "changeSet": {
                "id": "14-seed-roles-permissions-test-users",
                "author": "antigravity",
                "changes": [
                    {
                        "sql": {
                            "sql": "\n".join(sql_statements)
                        }
                    }
                ]
            }
        }
    ]
}

def represent_ordereddict(dumper, data):
    value = []
    for item_key, item_value in data.items():
        node_key = dumper.represent_data(item_key)
        node_value = dumper.represent_data(item_value)
        value.append((node_key, node_value))
    return yaml.nodes.MappingNode(u'tag:yaml.org,2002:map', value)

yaml.add_representer(OrderedDict, represent_ordereddict)

with open("src/main/resources/db/changelog/changesets/14-seed-roles-and-permissions.yaml", "w") as f:
    yaml.dump(yaml_content, f, sort_keys=False)
