
# TurboAZ Backend

Bu layihə **Java Spring Boot** istifadə edilərək hazırlanmış TurboAZ tipli avtomobil elan platformasının backend hissəsidir.  
İstifadəçilər elan yarada, filtrləyə, silə və admin panel vasitəsilə idarə edə bilərlər.

## 📌 Texnologiyalar

- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA**
- **PostgreSQL**
- **Docker** (opsional)
- **Lombok**

---

## ⚙ Tələblər

Layihəni işlətmək üçün aşağıdakılar lazımdır:

- **Java 17 və ya daha yeni versiya**
- **Maven 3.9+**
- **PostgreSQL 15+** (lokalda və ya Docker üzərindən)

---

## 🛠 Quraşdırma

### 1. Layihəni klonlayın
```bash
git clone https://github.com/sizin-repo/turboaz-backend.git
cd turboaz-backend
````

### 2. PostgreSQL quraşdırılması

#### **Docker ilə**

```bash
docker run --name turbo-db \
  -e POSTGRES_DB=turbo \
  -e POSTGRES_USER=test \
  -e POSTGRES_PASSWORD=pass \
  -p 5432:5432 \
  -d postgres:latest
```

#### **Manual quraşdırma**

PostgreSQL quraşdırıldıqdan sonra:

```bash
psql -U postgres
```

və aşağıdakı SQL əmrlərini icra edin:

```sql
CREATE DATABASE turbo;
CREATE USER test WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON DATABASE turbo TO test;
```

---

## 📄 Konfiqurasiya

`src/main/resources/application.properties` faylında aşağıdakı parametrlər var:

```properties
spring.application.name=turbaz

# PostgreSQL verilənlər bazası konfiqurasiyası
spring.datasource.url=jdbc:postgresql://localhost:5432/turbo
spring.datasource.username=test
spring.datasource.password=pass
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate ayarları
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=create-drop

# Admin istifadəçi məlumatları (proqramda istifadə üçün)
application.admin.email=testuser@gmail.com
application.admin.password=1234
application.admin.username=user

# Email konfiqurasiyası (SMTP)
spring.mail.host= # SMTP server ünvanı (məsələn: smtp.gmail.com)
spring.mail.port=587 # SMTP port (adətən 587 və ya 465)
spring.mail.username= # SMTP istifadəçi adı (email)
spring.mail.password= # SMTP şifrə
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


```

---

## 🚀 Tətbiqi işə salmaq

```bash
mvn spring-boot:run
```

Server **[http://localhost:8080](http://localhost:8080)** ünvanında açılacaq.

---

## 📌 API Endpoint-lər

### **AuthController**

* `POST /api/auth/register` → Yeni istifadəçi qeydiyyatı
* `POST /api/auth/confirm` → OTP təsdiqi

### **CarAdController**

* `POST /api/cars/create` → Yeni elan yaratmaq
* `POST /api/cars/uploadImage/{carAdId}` → Elana şəkil yükləmək
* `GET /api/cars/getall` → Bütün elanları göstərmək
* `GET /api/cars/get/{id}` → Elanı ID ilə göstərmək
* `PUT /api/cars/update/{id}` → Elanı yeniləmək
* `DELETE /api/cars/delete/{id}` → Elanı silmək
* `GET /api/cars/filter` → Parametrlərə görə elanları filtrləmək

### **AdminController**

* `DELETE /api/admin/delete/car/{id}` → Elanı admin tərəfindən silmək



---

## 📜 Lisenziya

Bu layihə açıq mənbəlidir. İstədiyiniz kimi istifadə edə bilərsiniz.[mit lisans](LICENSE)

---
## 🤝 Contributors
- **ElmirNihad** - [Github profil](https://github.com/ElmirNihad)

- **range79** - [GitHub profil](https://github.com/range79)
