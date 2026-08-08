# UniSport Amalga Oshirish Rejasi (Implementation Plan)

## 1. Loyihaga Umumiy Ta'rif
UniSport — bu oliy o'quv yurtlari uchun maxsus ishlab chiqilgan sport va kampus sog'lomlashtirish ekotizimi bo'lib, o'zida sport inshootlarini band qilish, inventarlarni boshqarish, jismoniy tarbiya davomatini hisobga olish, mashg'ulotlar/ovqatlanishni rejalashtirish, AI yordamchisi va to'lovlarni bitta yagona platformaga birlashtiradi.

## 2. Arxitekturaga Umumiy Ta'rif
Tizim **Clean Architecture** (Toza arxitektura) dan foydalangan holda **Modulli Monolit** (Modular Monolith) sifatida ishlab chiqilgan.
MVP bosqichida mikroservislar, Kafka va API Gateway ishlatish qat'iyan taqiqlanadi.

**Clean Architecture Qatlamlari:**
1. **Presentation (Taqdimot):** REST kontrollerlar, Request/Response DTOlar, Avtorizatsiya konteksti. Kontrollerlar yupqa bo'lishi va biznes mantiqdan xoli bo'lishi kerak.
2. **Application (Ilova):** Use caselar, ilova xizmatlari (application services), tranzaksiyalarni orkestratsiya qilish, ishlash oqimini muvofiqlashtirish.
3. **Domain (Domen):** Entitilar, Value Objectlar, domen qoidalari/invariantlari (masalan, band qilish kvotalari, minimal xavfsiz kaloriyalar).
4. **Infrastructure (Infratuzilma):** JPA repozitoriylari, Redis, MinIO, AI integratsiyalari, tashqi xizmatlar.

## 3. Modulli Monolit Chegaralari va Modullar
Tizim 7 ta asosiy chegaralangan kontekstlarga (Bounded Contexts) bo'lingan:
1. **Authentication & Identity:** SSO, JWT, Foydalanuvchi profillari, Auth/AuthZ.
2. **Gym & Booking:** Sport inshootlari, Kortlar, Band qilish, Mavjudlik, Kvotalar, Raqobat (Concurrency).
3. **Equipment Logistics:** Inventar holati, Mavjudlik, Ijaraga berish, Qaytarish.
4. **PE Academic:** Jismoniy tarbiya darslari, Jadvallar, Davomat, Dinamik QR davomat.
5. **Workout & Nutrition:** Mashqlar bazasi, Mashg'ulotlarni rejalashtirish, Oziqlanish/Kaloriyalar, Ovqatlanish jurnali.
6. **AI Intelligence:** AI provayderlar, Promptlar, Versiyalash, Ko'rish tahlili (Vision analysis), Tokenlarni kuzatish.
7. **Payments & Subscriptions:** To'lovlar, Obuna muddati (lifecycle), To'lovlarni qayta ishlash, Idempotentlik.

## 4. Modullar O'rtasidagi Bog'liqliklar
*Bog'liqliklar Clean Architecture va modullar chegaralariga hurmat bilan yondashishi kerak.*
- **Authentication & Identity** asosiy poydevor modul hisoblanadi.
- **Gym & Booking**, **Equipment Logistics**, **PE Academic**, **Workout & Nutrition** modullari Authentication & Identity moduliga bog'liq.
- **Payments & Subscriptions** moduli Authentication & Identity hamda Gym & Booking modullariga bog'liq.
- **AI Intelligence** moduli Authentication & Identity moduliga bog'liq bo'lib, global miqyosda ishlaydi, lekin provayder abstraksiyalari orqali izolyatsiya qilingan holda qoladi.
- Ma'lumotlar bazasi sxemalari iloji boricha boshqa chegaralangan kontekstlar bilan to'g'ridan-to'g'ri JOIN qilishni cheklash orqali izolyatsiyani ta'minlaydi.

## 5. Domen Entitilari
*Entitilarda Lombok `@Data` va `@EqualsAndHashCode` annotatsiyalaridan foydalanish qat'iyan man etiladi. Entity ↔ DTO konvertatsiyasi uchun MapStruct ishlatiladi.*
- **Authentication & Identity:** User, Role, Permission, RefreshToken.
- **Gym & Booking:** Facility, Court, Booking (har bir foydalanuvchi uchun maksimal 2 ta faol bandlik), Quota.
- **Equipment Logistics:** Equipment, InventoryItem, RentalAgreement.
- **PE Academic:** PEClass, ClassSchedule, AttendanceRecord, DynamicQRCode.
- **Workout & Nutrition:** Exercise, WorkoutPlan, MealLog (Ayollar uchun min 1,200 kcal, Erkaklar uchun min 1,500 kcal cheklovi).
- **AI Intelligence:** PromptTemplate, PromptVersion, AIRequestLog, TokenUsageLog.
- **Payments & Subscriptions:** PaymentTransaction, Subscription, SubscriptionPlan.

## 6. Ma'lumotlar Bazasi Strategiyasi
**RDBMS:** PostgreSQL 16
**Migratsiya Vositasi:** Liquibase (Hibernate tomonidan bazani avtomatik yaratish taqiqlanadi).
**Qoidalar:**
- **Soft Delete (Yumshoq o'chirish):** Tranzaksion obyektlar hech qachon jismonan o'chirib tashlanmaydi (`deleted = true`).
- **Concurrency (Raqobat):** Cheklangan resurslar (masalan, band qilish, inventar) uchun `@Version` orqali Optimistic locking (Optimizm blokirovka) majburiy hisoblanadi. `OptimisticLockException` yuzaga kelganda 409 Conflict xatosi qaytarilishi shart.
- **Yaxlitlik (Integrity):** To'g'ri PK, FK, unique (noyob) cheklovlar, check (tekshirish) cheklovlari va relyatsion yaxlitlik.

**Mantiqiy Sxemalar (Schemas):**
- `public`: Foydalanuvchilar, inshootlar, bandliklar, inventarlar.
- `ai`: AI prompt shablonlari, versiyalari, token sarfi, jurnallar (logs).
- `files`: MinIO fayl metama'lumotlari.
- `audit`: O'zgartirib bo'lmaydigan (immutable) tizim audit va xavfsizlik jurnallari.
- `analytics`: Hisobotlar uchun yig'ilgan ma'lumotlar.

## 7. API Amalga Oshirish Strategiyasi
**Standart:** JSON formatidan foydalangan holda RESTful API.
**Xatolar:** RFC 7807 Problem Details (400, 401, 403, 404, 409, 422, 500).
**Sahifalash (Pagination):** Standartlashtirilgan `page`, `size`, `sort` parametrlari orqali, javob tarzida `PageResponseDTO` qaytariladi.
**Idempotentlik:** Himoyalangan amaliyotlar (to'lov/band qilish) sarlavhada (header) `Idempotency-Key` bo'lishini talab qiladi. Dublikatlarning oldini olish uchun 24 soatlik Redis keshiga nisbatan tekshiriladi.

## 8. Xavfsizlik Strategiyasi
- **Autentifikatsiya:** Qisqa muddatli JWT tokenlar (15 daqiqa).
- **Sessiyani Boshqarish:** Redis tomonidan boshqariladigan Refresh tokenlar (7 kun).
- **Avtorizatsiya:** `@PreAuthorize` orqali RBAC va PBAC. IDOR hujumlarini oldini olish uchun resurs egaligini tekshirish.
- **Shifrlash (Encryption):** Tranzit uchun TLS 1.3. Ma'lumotlar bazasidagi nozik shaxsiy ma'lumotlar (PII) uchun AES-256-GCM. Maxfiy kalitlar (secrets) faqat muhit sozlamalari (environment configuration) orqali kiritiladi.
- **Rate Limiting (Redis):** Oddiy API lar uchun daqiqasiga 60 ta so'rov, Autentifikatsiya API lari uchun daqiqasiga 5 ta so'rov.

## 9. AI Amalga Oshirish Strategiyasi
- **Roli:** AI qat'iyan faqat yordamchi hisoblanadi va oxirgi biznes qarorlarini qabul qilmaydi. Barcha AI natijalari backend sxemasi va biznes qoidalari validatsiyasidan o'tishi shart.
- **Provayderlar:** Abstraksiya qatlami bilan **OpenAI** asosiy va **Gemini** zaxira (fallback) sifatida.
- **Promptlarni Boshqarish:** Promptlar bazada saqlanadi va versiyalanadi (kod ichiga qattiq kiritilmaydi - hardcoded).
- **Maxfiylik (Vision AI):** Qomat/tana tahlili uchun yuklangan rasmlar AI ga yuborilishidan *oldin* yuzlari xiralashtirilgan (blurred) va PII niqoblangan bo'lishi kerak.
- **Kesh (Caching):** Token xarajatlarini kamaytirish uchun AI promptlari (SHA-256 xesh qilingan) Redis-da 24 soat davomida keshlanadi.

## 10. Redis Strategiyasi
Redis 7 butun tizim bo'ylab quyidagilar uchun ishlatiladi:
1. Qisqa muddatli JWT Refresh Tokenlarni saqlash va bekor qilish.
2. AI javoblarini keshlash (24 soatlik TTL, SHA-256 prompt xeshlash).
3. Rate Limiting (DoS hujumidan himoya).
4. Moliyaviy/band qilish operatsiyalari uchun Idempotentlik kalitlari (24 soatlik TTL).

## 11. MinIO Strategiyasi
- **Saqlash:** Media va hujjatlar uchun S3-mos keluvchi obyektlar ombori.
- **Xavfsizlik:** Hech qachon asl fayl nomiga ishonmang. Fayl turi va o'lchamini tekshiring. Hech qachon ixtiyoriy bajariladigan (executable) kontentga ruxsat bermang.
- **Metama'lumotlar:** PostgreSQL `files` sxemasida kuzatib boriladi.

## 12. Frontend Strategiyasi
**Stek:** React 19, TypeScript, Vite, Tailwind CSS.
- **Server Holati (State):** TanStack Query v5.
- **Global/Mahalliy Holat:** Redux Toolkit (masalan, Auth, Theme).
- **Formalar va Validatsiya:** React Hook Form + Zod.
- **Qoida:** Frontendda asosiy biznes mantiq (masalan, kvota hisoblash, jarimalar, avtorizatsiya) BO'LMASLIGI SHART. Frontend faqat taqdimot va foydalanuvchi ishtiroki uchun mo'ljallangan. AI muloqotlari striming bo'lishi va "Tibbiy maslahat emas" degan ogohlantirishni o'z ichiga olishi kerak.

## 13. Mobil Ilova Strategiyasi
**Stek:** Flutter (iOS/Android), Riverpod, Clean Architecture.
- **Offline-First:** Mahalliy ma'lumotlar bazasi uchun Isar DB. Operatsiyalar `IsarOfflineQueue` orqali o'tadi va tarmoq tiklanganda sinxronlanadi.
- **Xavfsizlik:** JWT tokenlar `Flutter Secure Storage` (Keychain/Keystore) da saqlanadi.
- **QR Kod Xavfsizligi:** Davomat uchun dinamik QR kodlar har 15 soniyada yangilanadi. QR kodlari bor ekranlarda skrinshot olinishini taqiqlash uchun `FLAG_SECURE` dan foydalaniladi.

## 14. Testlash Strategiyasi
**Piramida:** Unit (50%), Integration (30%), UI/Component (15%), E2E (5%).
**Vositalar:** JUnit 5, Mockito, Testcontainers (PostgreSQL, Redis), React Testing Library, Flutter Widget Test, Playwright, Flutter Integration Test.
**Sifat Darvozasi (Quality Gate):** Main tarmog'iga qo'shish uchun kamida 80% test qamrovi (coverage), nol ArchUnit qoidabuzarliklari, nol jiddiy xavfsizlik zaifliklari va qabul qilish mezonlaridan (Acceptance Criteria) o'tish talab etiladi.

## 15. DevOps Strategiyasi
- **Infratuzilma:** Docker va Docker Compose (bitta VPS da).
- **CI/CD:** GitHub Actions (testlash, linting, SonarQube, Docker qurish).
- **Kuzatuvchanlik (Observability):** Spring Boot Actuator, Prometheus. JSON formatidagi strukturaviy loglar (SLF4J/Logback) stdout ga uzatiladi.
- **Zaxira (Backup):** Har kuni soat 02:00 da shifrlangan zaxira nushalari olinadi (DB uchun `pg_dumpall`, MinIO uchun `mc mirror`) va xavfsiz tashqi xotiraga (masalan, AWS S3) jo'natiladi. Zaxira nusxalarini qayta tiklash (restoration) orqali tekshirilishi shart.

## 16. Amalga Oshirish Bosqichlari
1. **1-bosqich — Loyiha Poydevori:** Spring Boot sozlamalari, paket (package) tuzilishi, Docker, DB/Redis/MinIO ulanishlari.
2. **2-bosqich — Ma'lumotlar Bazasi:** Liquibase sxemalari, jadvallar, cheklovlar, indekslar.
3. **3-bosqich — Domen:** Entitilar va domen modullari.
4. **4-bosqich — Xavfsizlik:** JWT, RBAC/PBAC, rate limiting.
5. **5-bosqich — Asosiy Biznes Modullari:** Gym & Booking, Equipment, PE Academic, Workout, Payments.
6. **6-bosqich — REST API:** API kelishuvlari (contracts), Problem Details, Pagination.
7. **7-bosqich — Redis:** Keshlash, idempotentlik, refresh tokenlar.
8. **8-bosqich — MinIO:** Xavfsiz media saqlash.
9. **9-bosqich — AI:** Provayder abstraksiyasi, promptlarni boshqarish, ko'rish (vision) tahlili, PII niqoblash.
10. **10-bosqich — Frontend:** React ilovasi.
11. **11-bosqich — Mobil Ilova:** Flutter ilovasi.
12. **12-bosqich — Testlash:** Barcha testlardan o'tishini ta'minlash.
13. **13-bosqich — Kuzatuvchanlik (Observability):** Loglar, metrikalar, audit.
14. **14-bosqich — Joylashtirish (Deployment):** Docker Compose, CI/CD, zaxira nusxalar.

## 17. Xavflar (Risks)
- **Raqobat tiqilinchlari (Concurrency Bottlenecks):** Dars boshlanishi (Dinamik QR) va band qilish paytida 2,000 RPS gacha bo'lgan pik yuklamalar yuzaga keladi. `@Version` optimistik blokirovkasi juda samarali ishlashi kerak.
- **AI kechikishi va ishonchliligi:** AI matnli javobi (< 1,500ms) va Ko'rish (Vision) tahlili (< 2,500ms) kabi NFR lari (nofunksional talablar) asosan tashqi provayder ishiga bog'liq. To'g'ri keshlash va zaxira (fallback) mexanizmlari o'ta muhim.
- **Mobil Oflayn Sinxronizatsiya:** Tarmoq qayta tiklangan vaqtda `IsarOfflineQueue` dagi ma'lumotlarning masofaviy (remote) holat bilan ziddiyatlarga uchrashi.

## 18. TBD / Aniq bo'lmagan talablar
- **Oflayn Sinxronizatsiya Ziddiyatlari:** Mobil oflayn sinxronizatsiya ziddiyatlarini aniq hal qilish strategiyalari (masalan, foydalanuvchi oflayn rejimda onlaynda band qilib bo'lingan resursni band qilsa) TZ da aniq belgilanmagan.
- **PBAC Ta'riflari:** PBAC qoidalarini batafsil ta'riflash (aynan qaysi resursga kim egalik qiladi) dasturlash jarayonida qat'iy belgilab olinishini talab qiladi.
- **Jarimalar:** TZ da inventar jarimalari faqat "aniq belgilangan holatlarda" deb aytilgan, ya'ni yangi jarima mantiqlarini o'ylab topmaslik kerak. Jarimalarning batafsil qoidalarini tasdiqlash zarur.
