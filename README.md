# UniSport - University Sports and Campus Wellness Ecosystem

UniSport - bu universitet talabalari va jamoasi uchun sport zallarini bron qilish, mashg'ulotlarga yozilish, sport anjomlarini ijaraga olish, sun'iy intellekt orqali jismoniy holat bo'yicha maslahatlar olish va jismoniy tarbiya darslari davomatini yuritish imkonini beruvchi yagona ekotizimdir.

## 🚀 Loyiha Arxitekturasi
Ushbu loyiha **Modular Monolith** arxitekturasida yozilgan bo'lib, kelajakda Microservices arxitekturasiga osongina o'tish imkoniyatiga ega.

### Texnologik Stack
* **Backend:** Java 21, Spring Boot 3.2, Spring Security (JWT), Spring Data JPA
* **Database:** PostgreSQL (Asosiy DB), Redis (Kesh va Rate Limiting), H2 (Testlar uchun)
* **File Storage:** MinIO (AWS S3 alternative) - Rasmlar va fayllar saqlash uchun
* **Frontend:** React 19, Vite, TailwindCSS, Shadcn UI
* **Mobile:** Flutter (Mobile ilova asosi)
* **Testing:** JUnit 5, Mockito, Testcontainers
* **DevOps & Deployment:** Docker, Docker Compose, GitHub Actions (CI/CD)
* **Monitoring:** Spring Boot Actuator, Prometheus, Micrometer Tracing (Brave)

## 📁 Loyiha Tuzilishi

Loyiha mantiqiy jihatdan quyidagi modullarga bo'lingan:
* **Auth Module:** Foydalanuvchilarni ro'yxatdan o'tkazish, JWT token generatsiyasi va avtorizatsiya.
* **Gym Module:** Sport inshootlari (Facility), maydonchalar (Court) va ularni bron qilish (Booking).
* **Equipment Module:** Sport anjomlari (Equipment) va ularni ijaraga olish (Rental).
* **PE (Physical Education) Module:** Jismoniy tarbiya darslari, dars jadvallari va davomat tizimi.
* **Workout Module:** Mashg'ulotlar dasturi, AI orqali ovqatlanish tahlillari va jismoniy rivojlanish kabi sog'liqni saqlash komponentlari.
* **Files Module:** Barcha media fayllarni S3/MinIO da xavfsiz boshqarish.
* **AI Module:** Gemini AI yordamida foydalanuvchiga personal maslahatlar va chat xizmati.

## 🛠️ O'rnatish va Ishga tushirish (Local)

Loyihani o'z kompyuteringizda ishga tushirish uchun quyidagi dasturlar o'rnatilgan bo'lishi kerak:
- Docker va Docker Compose
- Java 21 va Maven (Agar Docker ishlatmasangiz)
- Node.js (Frontend uchun)

### 1-usul: Docker Compose orqali (Tavsiya etiladi)

Loyihani to'liq bitta buyruq bilan ko'tarish:
```bash
docker-compose up -d --build
```
Ushbu buyruq PostgreSQL, Redis, MinIO, Backend va Frontend larni avtomatik tarzda ishga tushiradi.
* **Backend API:** `http://localhost:8080`
* **Frontend:** `http://localhost:5173`
* **MinIO Console:** `http://localhost:9001` (login: minioadmin, pass: minioadmin)

### 2-usul: Manual Ishga tushirish

1. **Ma'lumotlar bazasi va yordamchi xizmatlarni (Postgres, Redis, MinIO) Docker da yoqish:**
```bash
docker run --name unisport-db -e POSTGRES_USER=unisport_user -e POSTGRES_PASSWORD=unisport_pass -e POSTGRES_DB=unisport -p 5432:5432 -d postgres:15-alpine
docker run --name unisport-redis -p 6379:6379 -d redis:7-alpine
docker run --name unisport-minio -p 9000:9000 -p 9001:9001 -e MINIO_ROOT_USER=minioadmin -e MINIO_ROOT_PASSWORD=minioadmin -d minio/minio server /data --console-address ":9001"
```

2. **Backendni ishga tushirish:**
```bash
mvn clean install -DskipTests
mvn spring-boot:run
```

3. **Frontendni ishga tushirish:**
```bash
cd frontend
npm install
npm run dev
```

## 🧪 Testlarni ishga tushirish
Loyihada Testcontainers va In-Memory H2 bazasi yordamida Integration va Unit testlar yo'lga qo'yilgan.
```bash
mvn clean test -Dnet.bytebuddy.experimental=true
```

## 🛡 Xavfsizlik (Security & Rate Limiting)
* Barcha REST API lar **JWT** (JSON Web Token) orqali himoyalangan.
* Takroriy so'rovlarni oldini olish uchun (Idempotency) va serverni ortiqcha yuklanishidan asrash uchun (Rate Limiting) **Redis** interseptorlari sozlangan.
* Xatoliklar global miqyosda ushlanib mijozga tushunarli JSON formatda (`GlobalExceptionHandler`) taqdim etiladi.

## 📊 Monitoring
* Spring Boot Actuator va Prometheus loyihada faol. Metrikalarni ko'rish uchun:
`GET http://localhost:8080/actuator/prometheus`

---
*Loyiha "Software Engineering" standartlari (Clean Code, SOLID, DRY) hamda modern DevOps amaliyotlari asosida yaratilgan.*
