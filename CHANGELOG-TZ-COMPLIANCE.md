# TZ Compliance Changelog

## Phase A: Core Domain Additions

**Requirement:** AUTH-01 (Missing `Permission` entity), GYM-01 (Missing `Quota` entity)

**Old State:** 
- RBAC existed only with `Role`, but specific system permissions (`Permission`) were missing.
- Gym bookings lacked quota definitions (`Quota`).

**New State:** 
- Added `Permission` entity with Many-To-Many relationship to `Role`.
- Added `Quota` entity to restrict bookings per role.

**Files Changed:**
- `src/main/java/uz/uniSport/uni_sport/domain/auth/Role.java` (Modified)
- `src/main/java/uz/uniSport/uni_sport/domain/auth/Permission.java` (Created)
- `src/main/java/uz/uniSport/uni_sport/domain/gym/Quota.java` (Created)
- `src/test/java/uz/uniSport/uni_sport/UniSportApplicationTests.java` (Modified - added `@ActiveProfiles("test")`)

**Database Changes:**
- Created `permissions` table.
- Created `role_permissions` join table.
- Created `quotas` table.
- Mapped in `04-add-permissions.yaml` and `05-add-quotas.yaml`.

**API Changes:**
- None yet. (Underlying domain modified).

**Tests Added/Changed:**
- Fixed context profile for `UniSportApplicationTests.java`.

**Potential Breaking Changes:**
- New constraints added, but existing logic isn't tied to these tables yet. No breaking changes for existing features.

**Verification Status:**
- `mvn clean test` successfully executed and verified database context loading via Liquibase.
