# UniSport - University Sports and Campus Wellness Ecosystem

UniSport - bu universitet talabalari va jamoasi uchun sport zallarini bron qilish, mashg'ulotlarga yozilish, sport anjomlarini ijaraga olish, sun'iy intellekt orqali jismoniy holat bo'yicha maslahatlar olish va jismoniy tarbiya darslari davomatini yuritish imkonini beruvchi yagona ekotizimdir.

## 🚀 Loyiha Arxitekturasi

Ushbu loyiha **Modular Monolith** arxitekturasida yozilgan bo'lib, kelajakda Microservices arxitekturasiga osongina o'tish imkoniyatiga ega. Loyiha qat'iy "Clean Architecture" va "SOLID" prinsiplariga amal qilib ishlab chiqilgan.

### Texnologik Stack
* **Backend:** Java 21, Spring Boot 3.2, Spring Security (JWT), Spring Data JPA
* **Database:** PostgreSQL (Asosiy DB), Redis (Kesh, JWT Invalidatsiya, Rate Limiting)
* **Migrations:** Liquibase
* **Mappers:** MapStruct
* **Build Tool:** Maven
* **Documentation:** Swagger/OpenAPI 3.0
* **Testing:** JUnit 5, Mockito

## 📁 Loyiha Tuzilishi

Loyiha mantiqiy jihatdan quyidagi modullarga bo'lingan:

* **Auth Module (`uz.uniSport.uni_sport.domain.auth`)**: Foydalanuvchilarni ro'yxatdan o'tkazish, tizimga kirish (JWT), Rol va Huquqlar (Role & Permission) tizimini boshqarish.
* **Gym Module (`uz.uniSport.uni_sport.domain.gym`)**: Sport inshootlari (Facility), maydonchalar (Court), jihozlar (Equipment), ularni bron qilish (Booking) hamda jihozlarni ijaraga olish (Rental) jarayonlari. Shuningdek, xodimlar uchun kvotalar (Quota) va ombor hisob-kitobi (InventoryItem).
* **Wellness & AI Module (`uz.uniSport.uni_sport.domain.wellness`, `uz.uniSport.uni_sport.domain.ai`)**: Mashqlar katalogi (Exercise), shaxsiy mashg'ulot rejalari (WorkoutPlan), AI (LLM) orqali yozilgan so'rovlar (AIRequestLog, PromptTemplate, TokenUsageLog) integratsiyasi.
* **Payment Module (`uz.uniSport.uni_sport.domain.payment`)**: Obunalar (SubscriptionPlan, Subscription) va to'lov tranzaksiyalari (PaymentTransaction).
* **Integration Module (`uz.uniSport.uni_sport.domain.integration`)**: Tashqi tizimlar bilan ishlash, jumladan, Dinamik QR Kodlar (DynamicQRCode).

### Arxitektura qatlamlari
Har bir modul ichida qat'iy qatlamlar mavjud:
1. **Controller (`/controller`)**: REST API endpointlari (Spring Web MVC).
2. **Service (`/service`)**: Biznes mantiqi (Business Logic) va tranzaksiyalarni boshqarish (`@Transactional`).
3. **Repository (`/repository`)**: Ma'lumotlar bazasi bilan ishlash (Spring Data JPA).
4. **DTO (`/dto`)**: Tarmoq orqali uzatiladigan obyektlar (Request/Response modellari).
5. **Mapper (`/mapper`)**: Entity va DTO'lar o'rtasida konvertatsiya qiluvchi MapStruct interfeyslari.
6. **Domain (`/domain`)**: Ma'lumotlar bazasi jadvallariga mos keluvchi JPA Entity'lar.

## 🛠️ O'rnatish va Ishga tushirish (Local)

Loyihani o'z kompyuteringizda ishga tushirish uchun quyidagi dasturlar o'rnatilgan bo'lishi kerak:
- Java 21
- Maven
- PostgreSQL 15+
- Redis (ixtiyoriy, xotira uchun)

### Manual Ishga tushirish

1. **Ma'lumotlar bazasini sozlash (`application.yml`):**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/unisport
    username: postgres
    password: password
```

2. **Migratsiyalarni bajarish va Backendni ishga tushirish:**
Loyihada **Liquibase** yoqilgan. Ilova birinchi marta ishga tushganda barcha jadvallar (`changelog.xml` fayllari orqali) avtomatik tarzda yaratiladi.
```bash
mvn clean package -DskipTests
mvn spring-boot:run
```
Yoki yig'ilgan jar faylni ishga tushirish:
```bash
java -jar target/uni-sport-0.0.1-SNAPSHOT.jar
```

3. **API Dokumentatsiya:**
Ilova muvaffaqiyatli ishga tushgach, Swagger UI orqali barcha API'larni ko'rishingiz mumkin:
* Swagger UI: `http://localhost:8080/swagger-ui.html`
* OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🛡 Xavfsizlik va Ma'lumotlar Butunligi
* **Optimistic Locking:** Parallellik muammolarining oldini olish maqsadida barcha muhim jadvallarda `@Version` (optimistic locking) maydoni qat'iy joriy qilingan.
* **Audit Fields:** Barcha Entity'lar `BaseEntity` dan voris oladi (`createdAt`, `updatedAt`, `deleted` maydonlari) - "Soft Delete" (mantiqiy o'chirish) imkoniyati uchun.
* **UUID as PK:** Xavfsizlikni ta'minlash va bashorat qilishni qiyinlashtirish maqsadida ko'pchilik jadvallarning Asosiy Kalitlari (Primary Key) `UUID` (v4) sifatida tanlangan.

## 📝 Konventsiyalar (Conventions)
* **Til:** Loyihadagi barcha kod izohlari (Commit xabarlari, Javadoc, Swagger tavsiflari) to'liq o'zbek tilida yozilgan.
* **Lombok:** Boilerplate kodni kamaytirish uchun Lombok annotation'lari (`@Getter`, `@Setter`) ishlatilgan. Ammo Hibernate Entity'larida unumdorlik va `LazyInitializationException` muammolari tufayli `@Data` va `@EqualsAndHashCode` dan umuman FОYDALANILMAGAN.
* **API Versioning:** Barcha API endpointlari versiyalanadi, masalan: `/api/v1/...`.

---
*Ushbu tizim O'zbekistondagi universitetlarning jismoniy madaniyat, sport va sog'lomlashtirish komplekslari uchun maxsus yechim sifatida yaratilgan.*
