# UniSport - Test Users & Credentials

The following test users are automatically seeded into the database and have specific roles and permissions assigned to them. You can use these credentials to log in and test different parts of the application.

## Credentials Table

| Role | Username (Email) | Password | Description |
|---|---|---|---|
| **USER** | `test.user` | `Password123!` | Regular user. Can view own bookings, create bookings, read facility info. |
| **COACH** | `test.coach` | `Password123!` | PE Coach. Can manage attendance, view schedules, manage classes. |
| **DESK_STAFF** | `test.desk` | `Password123!` | Front desk staff. Can manage bookings, view facilities, scan equipment/attendance QR codes. |
| **FACILITY_PARTNER_ADMIN** | `test.facility.admin` | `Password123!` | Facility admin. Can create/manage their own facilities, manage their equipment and inventory. |
| **SPORT_DIRECTOR** | `test.sport.director` | `Password123!` | Sport Director (UniSport Admin). Can manage all facilities, classes, schedules, and view reports. |
| **MINISTRY_OBSERVER** | `test.ministry.observer` | `Password123!` | Ministry Observer. Has read-only access to all dashboards and reports. |

## Usage
Send a `POST` request to `/api/v1/auth/login` with the following JSON payload:

```json
{
  "email": "test.sport.director",
  "password": "Password123!"
}
```

This will return a JWT `accessToken` which should be included in the `Authorization` header of subsequent requests:

```
Authorization: Bearer <your-access-token>
```
