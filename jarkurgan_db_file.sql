-- ============================================================
--  jarkurganMMTB.uz  —  MySQL Ma'lumotlar Bazasi
--  Versiya: 1.0
--  Encoding: UTF-8
-- ============================================================
 
-- Bazani yaratish
CREATE DATABASE IF NOT EXISTS jarkurgan_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
 
USE jarkurgan_db;
 
-- ============================================================
--  JADVALLAR
-- ============================================================
 
-- Adminlar jadvali
CREATE TABLE IF NOT EXISTS admins (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    full_name  VARCHAR(200),
    email      VARCHAR(200),
    active     TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 
-- Sayt sozlamalari jadvali (faqat 1 ta yozuv bo'ladi)
CREATE TABLE IF NOT EXISTS site_settings (
    id               BIGINT PRIMARY KEY DEFAULT 1,
    site_name        VARCHAR(200),
    site_title       VARCHAR(500),
    site_description VARCHAR(1000),
    about_text       LONGTEXT,
    address          VARCHAR(500),
    phone            VARCHAR(50),
    email            VARCHAR(200),
    work_hours       VARCHAR(200),
    leader_name      VARCHAR(200),
    leader_position  VARCHAR(200),
    leader_phone     VARCHAR(50),
    deputy_name      VARCHAR(200),
    deputy_position  VARCHAR(200),
    deputy_phone     VARCHAR(50),
    school_count     INT DEFAULT 0,
    mtm_count        INT DEFAULT 0,
    student_count    INT DEFAULT 0,
    teacher_count    INT DEFAULT 0,
    telegram         VARCHAR(200),
    facebook         VARCHAR(200),
    instagram        VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 
-- Yangiliklar jadvali
CREATE TABLE IF NOT EXISTS news (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(500) NOT NULL,
    content       LONGTEXT NOT NULL,
    short_desc    VARCHAR(1000),
    image_url     VARCHAR(500),
    category      ENUM('YANGILIK','ELON','OLIMPIADA','TADBIR') DEFAULT 'YANGILIK',
    published     TINYINT(1) DEFAULT 0,
    created_at    DATETIME,
    updated_at    DATETIME,
    INDEX idx_news_published (published),
    INDEX idx_news_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 
-- Maktablar jadvali
CREATE TABLE IF NOT EXISTS schools (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    number        INT,
    name          VARCHAR(300) NOT NULL,
    address       VARCHAR(500),
    phone         VARCHAR(50),
    director_name VARCHAR(200),
    student_count INT,
    teacher_count INT,
    type          ENUM('MAKTAB','MTM','IXTISOSLASHGAN') DEFAULT 'MAKTAB',
    latitude      DOUBLE,
    longitude     DOUBLE,
    active        TINYINT(1) DEFAULT 1,
    INDEX idx_schools_active (active),
    INDEX idx_schools_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 
-- Murojaatlar jadvali
CREATE TABLE IF NOT EXISTS contacts (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name  VARCHAR(200) NOT NULL,
    phone      VARCHAR(50),
    email      VARCHAR(200),
    message    TEXT NOT NULL,
    status     ENUM('YANGI','KORIB_CHIQILDI','YOPILDI') DEFAULT 'YANGI',
    created_at DATETIME,
    INDEX idx_contacts_status (status),
    INDEX idx_contacts_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 
-- Hujjatlar jadvali
CREATE TABLE IF NOT EXISTS documents (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(300) NOT NULL,
    description VARCHAR(500),
    file_name   VARCHAR(300),
    file_path   VARCHAR(500),
    file_type   VARCHAR(100),
    file_size   BIGINT,
    category    ENUM('ISH_REJASI','HISOBOT','QOIDA','ARIZA_NAMUNA','BOSHQA') DEFAULT 'BOSHQA',
    active      TINYINT(1) DEFAULT 1,
    uploaded_at DATETIME,
    INDEX idx_documents_active (active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
 
 
-- ============================================================
--  BOSHLANG'ICH MA'LUMOTLAR
-- ============================================================
 
-- Default admin (parol: Admin@2025)
-- BCrypt hash: $2a$10$... — Spring Boot o'zi yaratadi, bu yerga qo'yilmaydi
-- DataInitializer.java avtomatik yaratadi
 
-- Default sayt sozlamalari
INSERT INTO site_settings (
    id, site_name, site_title, site_description,
    about_text, address, phone, email, work_hours,
    leader_name, leader_position, leader_phone,
    deputy_name, deputy_position, deputy_phone,
    school_count, mtm_count, student_count, teacher_count
) VALUES (
    1,
    'jarkurganMMTB.uz',
    'Jarqo''rg''on tumani maktabgacha va maktab ta''limi bo''limi',
    'Surxondaryo viloyati, Jarqo''rg''on tumani ta''lim bo''limi rasmiy veb-sayti',
    'Jarqo''rg''on tumani maktabgacha va maktab ta''limi bo''limi 2000-yildan faoliyat yuritib kelmoqda. Bo''lim tuman miqyosidagi barcha ta''lim muassasalarini boshqaradi va nazorat qiladi.',
    'Surxondaryo viloyati, Jarqo''rg''on tumani, Mustaqillik ko''chasi, 1',
    '+998 91 900-07-41',
    'jarqorgon.mmtb@edu.uz',
    'Dushanba–Juma: 8:00–17:00',
    'F.I.O',
    'Bo''lim boshlig''i',
    '+998 77 000-00-00',
    'F.I.O',
    'Bo''lim boshlig''i o''rinbosari',
    '+998 77 000-00-00',
    42, 18, 14300, 1200
) ON DUPLICATE KEY UPDATE id = 1;
 
 
-- ============================================================
--  FOYDALI SO'ROVLAR (kerak bo'lganda ishlating)
-- ============================================================
 
-- Barcha yangiliklar
-- SELECT * FROM news ORDER BY created_at DESC;
 
-- Faol maktablar
-- SELECT * FROM schools WHERE active = 1 ORDER BY number;
 
-- Yangi murojaatlar
-- SELECT * FROM contacts WHERE status = 'YANGI' ORDER BY created_at DESC;
 
-- Admin parolini ko'rish
-- SELECT username, full_name FROM admins;
 
-- Statistika
-- SELECT
--   (SELECT COUNT(*) FROM schools WHERE active = 1) AS maktablar,
--   (SELECT COUNT(*) FROM news WHERE published = 1) AS yangiliklar,
--   (SELECT COUNT(*) FROM contacts WHERE status = 'YANGI') AS yangi_murojaatlar,
--   (SELECT COUNT(*) FROM documents WHERE active = 1) AS hujjatlar;
 