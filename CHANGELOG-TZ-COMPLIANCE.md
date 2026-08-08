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

## Phase B: Payments & Subscriptions Module

**Requirement:** PAY-01 (Missing `Payments` module including Subscriptions and Transactions).

**Old State:** 
- The system had no representation of subscriptions or financial transactions.

**New State:** 
- Added `SubscriptionPlan` to define pricing packages.
- Added `Subscription` to track user access duration.
- Added `PaymentTransaction` to log payments and enforce Idempotency.

**Files Changed:**
- `src/main/java/uz/uniSport/uni_sport/domain/payment/SubscriptionPlan.java` (Created)
- `src/main/java/uz/uniSport/uni_sport/domain/payment/Subscription.java` (Created)
- `src/main/java/uz/uniSport/uni_sport/domain/payment/PaymentTransaction.java` (Created)

**Database Changes:**
- Created `subscription_plans`, `subscriptions`, and `payment_transactions` tables.
- Mapped in `06-add-payments-module.yaml`.

**API Changes:**
- Service and API layers not yet implemented, but domain foundation is complete.

**Tests Added/Changed:**
- None, but context tests passed.

**Potential Breaking Changes:**
- None, isolated module.

**Verification Status:**
- `mvn clean test` successfully executed. Tables created correctly.
