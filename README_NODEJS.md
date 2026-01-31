# HR Inventory System - Node.js Version

Hệ thống quản lý nhân sự và kho đã được chuyển đổi hoàn toàn sang Node.js/Express.js.

## Cấu trúc dự án

```
hr-inventory-system/
├── server.js                 # Entry point
├── package.json              # Dependencies
├── .env                      # Environment variables
├── src/
│   ├── config/
│   │   └── database.js       # Sequelize configuration
│   ├── models/               # Sequelize models
│   │   └── Employee.js
│   ├── controllers/          # Request handlers
│   │   └── employee.controller.js
│   ├── services/             # Business logic
│   │   └── employee.service.js
│   ├── routes/               # Express routes
│   │   ├── employee.routes.js
│   │   ├── department.routes.js
│   │   └── ...
│   ├── middleware/           # Custom middleware
│   │   └── validation.js
│   └── main/
│       └── resources/
│           └── static/      # Frontend files (HTML, JS, CSS)
└── README_NODEJS.md
```

## Cài đặt

```bash
npm install
```

## Cấu hình

Tạo file `.env` với các biến môi trường:

```env
DB_HOST=your-db-host
DB_PORT=5432
DB_NAME=your-db-name
DB_USER=your-db-user
DB_PASSWORD=your-db-password
DB_SCHEMA=hrm
DB_SSL=true

PORT=8082
NODE_ENV=development

JWT_SECRET=your-secret-key
SESSION_SECRET=your-session-secret
```

## Chạy ứng dụng

```bash
# Development mode
npm run dev

# Production mode
npm start
```

## API Endpoints

### Employees
- `GET /api/employees` - Lấy danh sách nhân viên (có phân trang và filter)
- `GET /api/employees/:id` - Lấy thông tin nhân viên theo ID
- `POST /api/employees` - Tạo nhân viên mới
- `PUT /api/employees/:id` - Cập nhật nhân viên
- `DELETE /api/employees/:id` - Xóa nhân viên
- `GET /api/employees/search?keyword=...` - Tìm kiếm nhân viên

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
- `GET /api/employees/:employeeId/appointments` - Lịch sử bổ nhiệm của nhân viên
- `GET /api/employees/:employeeId/appointments/active` - Bổ nhiệm đang active

### Contracts
- `GET /api/v1/contracts` - Lấy danh sách hợp đồng
- `GET /api/v1/contracts/:id` - Lấy thông tin hợp đồng

### Dashboard
- `GET /api/v1/dashboard/stats` - Thống kê tổng quan

## Database

Ứng dụng sử dụng PostgreSQL với Sequelize ORM. Schema mặc định là `hrm`.

## Frontend

Frontend được phục vụ từ thư mục `src/main/resources/static/` với:
- HTML files
- JavaScript files (api.js, app.js)
- CSS (Tailwind CSS via CDN)

## Lưu ý

- Tất cả file Java đã được xóa
- Backend hiện tại chỉ có Employee module được implement đầy đủ
- Các modules khác (departments, projects, etc.) đang có routes stub và cần được implement
- Database schema giữ nguyên từ phiên bản Java
