# Hướng dẫn cấu hình Database

## 📍 Tình trạng hiện tại

**Dự án đang sử dụng Database trên Cloud (Neon.tech)**

Database hiện tại:
- **Host**: `ep-broad-king-a1yq7ezn-pooler.ap-southeast-1.aws.neon.tech`
- **Provider**: Neon.tech (PostgreSQL cloud service)
- **Vị trí**: AWS Singapore (ap-southeast-1)
- **SSL**: Bắt buộc (DB_SSL=true)

## 🔄 So sánh Cloud vs Local

### Cloud Database (Neon.tech) - Hiện tại

**Ưu điểm:**
- ✅ Không cần cài đặt PostgreSQL trên máy
- ✅ Truy cập từ bất kỳ đâu (có internet)
- ✅ Tự động backup
- ✅ Dễ deploy lên production
- ✅ Miễn phí tier (có giới hạn)

**Nhược điểm:**
- ❌ Cần internet để kết nối
- ❌ Có thể chậm hơn nếu server xa
- ❌ Phụ thuộc vào nhà cung cấp

### Local Database (PostgreSQL trên PC)

**Ưu điểm:**
- ✅ Nhanh hơn (không qua internet)
- ✅ Không cần internet
- ✅ Toàn quyền kiểm soát
- ✅ Phù hợp phát triển offline

**Nhược điểm:**
- ❌ Phải cài đặt PostgreSQL
- ❌ Phải tự backup
- ❌ Chỉ truy cập từ máy local

## 🛠️ Cấu hình Database

### Option 1: Giữ nguyên Cloud Database (Khuyến nghị)

File `.env`:
```env
DB_HOST=ep-broad-king-a1yq7ezn-pooler.ap-southeast-1.aws.neon.tech
DB_PORT=5432
DB_NAME=neondb
DB_USER=neondb_owner
DB_PASSWORD=npg_oQsxIY18TLcq
DB_SCHEMA=hrm
DB_SSL=true
```

**Không cần làm gì thêm** - chỉ cần tạo file `.env` với thông tin trên.

### Option 2: Chuyển sang Local Database

#### Bước 1: Cài đặt PostgreSQL trên PC

**Windows:**
1. Tải PostgreSQL từ: https://www.postgresql.org/download/windows/
2. Cài đặt và ghi nhớ password cho user `postgres`
3. Khởi động PostgreSQL service

**Mac:**
```bash
brew install postgresql
brew services start postgresql
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
```

#### Bước 2: Tạo Database và Schema

Mở terminal/command prompt và chạy:

```bash
# Kết nối PostgreSQL
psql -U postgres

# Tạo database
CREATE DATABASE hr_inventory;

# Tạo schema
\c hr_inventory
CREATE SCHEMA hrm;

# Tạo user (tùy chọn)
CREATE USER hr_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE hr_inventory TO hr_user;
GRANT ALL ON SCHEMA hrm TO hr_user;
```

#### Bước 3: Cập nhật file `.env`

```env
# Local Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=hr_inventory
DB_USER=postgres
DB_PASSWORD=your_postgres_password
DB_SCHEMA=hrm
DB_SSL=false

PORT=8082
NODE_ENV=development

JWT_SECRET=your-secret-key-change-in-production
SESSION_SECRET=your-session-secret-change-in-production
```

**Lưu ý quan trọng:**
- `DB_HOST=localhost` (thay vì địa chỉ cloud)
- `DB_SSL=false` (local không cần SSL)
- `DB_NAME`, `DB_USER`, `DB_PASSWORD` = thông tin bạn vừa tạo

#### Bước 4: Import dữ liệu (nếu có)

Nếu bạn đã có dữ liệu trên cloud và muốn copy sang local:

```bash
# Export từ cloud (nếu có quyền)
pg_dump -h ep-broad-king-a1yq7ezn-pooler.ap-southeast-1.aws.neon.tech \
        -U neondb_owner \
        -d neondb \
        -n hrm \
        > backup.sql

# Import vào local
psql -U postgres -d hr_inventory < backup.sql
```

## 🔍 Kiểm tra kết nối

Sau khi cấu hình, chạy ứng dụng:

```bash
npm run dev
```

Nếu kết nối thành công, bạn sẽ thấy:
```
Database connection established successfully.
Server is running on http://localhost:8082
```

Nếu lỗi, kiểm tra:
1. PostgreSQL đã chạy chưa? (local)
2. Thông tin trong `.env` đúng chưa?
3. Firewall có chặn port 5432 không? (local)
4. Internet có kết nối không? (cloud)

## 📝 Tóm tắt

| Loại | DB_HOST | DB_SSL | Cần cài đặt |
|------|---------|--------|-------------|
| **Cloud (hiện tại)** | `ep-broad-king-...neon.tech` | `true` | ❌ Không |
| **Local** | `localhost` | `false` | ✅ PostgreSQL |

**Khuyến nghị:**
- **Development**: Dùng local nếu muốn nhanh và offline
- **Production/Testing**: Dùng cloud để dễ chia sẻ và deploy

## 🚀 Quick Start

**Nếu muốn dùng Cloud (hiện tại):**
1. Tạo file `.env` với thông tin cloud ở trên
2. Chạy `npm run dev`
3. Xong!

**Nếu muốn chuyển sang Local:**
1. Cài PostgreSQL
2. Tạo database và schema
3. Cập nhật `.env` với thông tin local
4. Chạy `npm run dev`
