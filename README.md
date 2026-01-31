# Hệ thống Quản lý Nhân sự và Tồn kho (HR Inventory System)

Hệ thống quản lý nhân sự toàn diện với giao diện web hiện đại được xây dựng bằng **Node.js/Express.js** và **JavaScript thuần**.

## Yêu cầu hệ thống

### Backend
- **Node.js**: v18 trở lên
- **npm**: 9+ (hoặc yarn/pnpm)
- **PostgreSQL**: Database đã được cấu hình sẵn (Neon.tech)

### Frontend
- **HTML/CSS/JavaScript thuần**
- **Tailwind CSS** (sử dụng CDN, không cần cài đặt)
- **Font Awesome** (sử dụng CDN, không cần cài đặt)

### IDE
- VS Code (khuyến nghị), hoặc bất kỳ editor nào hỗ trợ JavaScript

## Cài đặt và chạy dự án

### Bước 1: Kiểm tra Node.js version

```bash
node --version
npm --version
```

Đảm bảo bạn đang sử dụng Node.js v18 hoặc cao hơn.

### Bước 2: Cài đặt dependencies

```bash
npm install
```

### Bước 3: Cấu hình Database

Tạo file `.env` trong thư mục gốc với nội dung:

```env
DB_HOST=ep-broad-king-a1yq7ezn-pooler.ap-southeast-1.aws.neon.tech
DB_PORT=5432
DB_NAME=neondb
DB_USER=neondb_owner
DB_PASSWORD=npg_oQsxIY18TLcq
DB_SCHEMA=hrm
DB_SSL=true

PORT=8082
NODE_ENV=development

JWT_SECRET=your-secret-key-change-in-production
SESSION_SECRET=your-session-secret-change-in-production
```

### Bước 4: Chạy ứng dụng

#### Development mode (với auto-reload)

```bash
npm run dev
```

#### Production mode

```bash
npm start
```

Ứng dụng sẽ chạy tại: `http://localhost:8082`

## Cấu trúc dự án

```
hr-inventory-system/
├── server.js                 # Entry point
├── package.json              # Dependencies
├── .env                      # Environment variables (tạo mới)
├── src/
│   ├── config/
│   │   └── database.js       # Sequelize configuration
│   ├── models/               # Sequelize models
│   ├── controllers/          # Request handlers
│   ├── services/             # Business logic
│   ├── routes/               # Express routes
│   ├── middleware/           # Custom middleware
│   └── main/
│       └── resources/
│           └── static/        # Frontend files (HTML, JS, CSS)
└── README.md
```

## API Endpoints

### Employees
- `GET /api/employees` - Lấy danh sách nhân viên (có phân trang và filter)
- `GET /api/employees/:id` - Lấy thông tin nhân viên theo ID
- `POST /api/employees` - Tạo nhân viên mới
- `PUT /api/employees/:id` - Cập nhật nhân viên
- `DELETE /api/employees/:id` - Xóa nhân viên
- `GET /api/employees/search?keyword=...` - Tìm kiếm nhân viên
- `GET /api/employees/:employeeId/appointments` - Lịch sử bổ nhiệm
- `GET /api/employees/:employeeId/appointments/active` - Bổ nhiệm đang active

### Departments
- `GET /api/v1/departments` - Lấy danh sách phòng ban
- `GET /api/v1/departments/:id` - Lấy thông tin phòng ban

### Projects
- `GET /api/v1/projects/summary` - Lấy tóm tắt dự án
- `GET /api/v1/projects/:id` - Lấy thông tin dự án

### Decisions
- `GET /api/decisions` - Lấy danh sách quyết định
- `GET /api/decisions/:id` - Lấy thông tin quyết định

### Appointments
- `GET /api/appointments` - Lấy danh sách bổ nhiệm
- `GET /api/appointments/:id` - Lấy thông tin bổ nhiệm

### Contracts
- `GET /api/v1/contracts` - Lấy danh sách hợp đồng
- `GET /api/v1/contracts/:id` - Lấy thông tin hợp đồng

### Dashboard
- `GET /api/v1/dashboard/stats` - Thống kê tổng quan

## Frontend

Frontend được phục vụ từ thư mục `src/main/resources/static/`:
- **HTML files**: `dashboard.html`, `employees.html`, `departments.html`, etc.
- **JavaScript**: 
  - `js/api.js` - API service để gọi backend
  - `js/app.js` - Logic render và routing client-side
- **CSS**: Sử dụng Tailwind CSS qua CDN

## Database

- **PostgreSQL** với schema `hrm`
- **Sequelize ORM** để quản lý database
- Database schema giữ nguyên từ phiên bản Java

## Tính năng

- ✅ Quản lý nhân viên (CRUD đầy đủ)
- ✅ Tìm kiếm và filter nhân viên
- ✅ Validation endpoints
- ✅ Phân trang
- ✅ Session-based authentication
- ✅ RESTful API
- ✅ Frontend với JavaScript thuần (không framework)

## Lưu ý

- Tất cả file Java đã được xóa
- Backend hiện tại chỉ có **Employee module** được implement đầy đủ
- Các modules khác (departments, projects, etc.) đang có routes stub và cần được implement tiếp
- Database schema giữ nguyên từ phiên bản Java

## Troubleshooting

### Lỗi kết nối database
- Kiểm tra file `.env` có đúng thông tin database không
- Kiểm tra kết nối internet (nếu dùng Neon.tech)
- Kiểm tra SSL settings

### Port đã được sử dụng
- Thay đổi `PORT` trong file `.env`
- Hoặc dừng process đang sử dụng port 8082

### Module not found
- Chạy lại `npm install`
- Xóa `node_modules` và `package-lock.json`, sau đó chạy `npm install` lại

## Phát triển tiếp

Để implement các modules còn lại:
1. Tạo Sequelize models trong `src/models/`
2. Tạo services trong `src/services/`
3. Tạo controllers trong `src/controllers/`
4. Cập nhật routes trong `src/routes/`

Xem `src/services/employee.service.js` và `src/controllers/employee.controller.js` để tham khảo.
