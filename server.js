import express from 'express';
import cors from 'cors';
import dotenv from 'dotenv';
import session from 'express-session';
import connectPgSimple from 'connect-pg-simple';
import { sequelize } from './src/config/database.js';
import path from 'path';
import { fileURLToPath } from 'url';

// Import routes
import authRoutes from './src/routes/auth.routes.js';
import employeeRoutes from './src/routes/employee.routes.js';
import departmentRoutes from './src/routes/department.routes.js';
import positionRoutes from './src/routes/position.routes.js';
import projectRoutes from './src/routes/project.routes.js';
import decisionRoutes from './src/routes/decision.routes.js';
import appointmentRoutes from './src/routes/appointment.routes.js';
import contractRoutes from './src/routes/contract.routes.js';
import dashboardRoutes from './src/routes/dashboard.routes.js';
import workflowRoutes from './src/routes/workflow.routes.js';
import attendanceRoutes from './src/routes/attendance.routes.js';

dotenv.config();

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
const PORT = process.env.PORT || 8082;

// Middleware
app.use(cors({
    origin: true,
    credentials: true
}));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Session configuration
const PgSession = connectPgSimple(session);
app.use(session({
    store: new PgSession({
        conString: `postgresql://${process.env.DB_USER}:${process.env.DB_PASSWORD}@${process.env.DB_HOST}/${process.env.DB_NAME}?sslmode=require`
    }),
    secret: process.env.SESSION_SECRET || 'your-session-secret',
    resave: false,
    saveUninitialized: false,
    cookie: {
        secure: process.env.NODE_ENV === 'production',
        httpOnly: true,
        maxAge: 24 * 60 * 60 * 1000 // 24 hours
    }
}));

// Serve static files
app.use(express.static(path.join(__dirname, 'src', 'main', 'resources', 'static')));

// API Routes
app.use('/api/auth', authRoutes);
app.use('/api/employees', employeeRoutes);
app.use('/api/v1/departments', departmentRoutes);
app.use('/api/positions', positionRoutes);
app.use('/api/v1/projects', projectRoutes);
app.use('/api/decisions', decisionRoutes);
app.use('/api/appointments', appointmentRoutes);
app.use('/api/v1/contracts', contractRoutes);
app.use('/api/v1/dashboard', dashboardRoutes);
app.use('/api/workflows', workflowRoutes);
app.use('/api/attendance', attendanceRoutes);

// Employee appointments routes
app.get('/api/employees/:employeeId/appointments', async (req, res, next) => {
    try {
        const { getEmployeeAppointments } = await import('./src/controllers/appointment.controller.js');
        await getEmployeeAppointments(req, res, next);
    } catch (error) {
        next(error);
    }
});

app.get('/api/employees/:employeeId/appointments/active', async (req, res, next) => {
    try {
        const { getEmployeeActiveAppointments } = await import('./src/controllers/appointment.controller.js');
        await getEmployeeActiveAppointments(req, res, next);
    } catch (error) {
        next(error);
    }
});

// Serve HTML files - handle nested paths and dynamic routes
app.get('*.html', async (req, res, next) => {
    try {
        const fs = await import('fs');
        // Remove leading slash and get the relative path
        let filePath = req.path.startsWith('/') ? req.path.slice(1) : req.path;
        
        // Build full path
        const fullPath = path.join(__dirname, 'src', 'main', 'resources', 'static', filePath);
        
        // Check if exact file exists
        if (fs.existsSync(fullPath)) {
            res.sendFile(fullPath);
            return;
        }
        
        // For nested paths like /employees/new.html or /employees/{id}/edit.html
        // Extract the base module name (employees, departments, etc.)
        const pathParts = filePath.split('/');
        const baseModule = pathParts[0]; // e.g., 'employees'
        
        // Try to serve the base module HTML file
        const basePath = path.join(__dirname, 'src', 'main', 'resources', 'static', `${baseModule}.html`);
        if (fs.existsSync(basePath)) {
            res.sendFile(basePath);
            return;
        }
        
        // Fallback to index.html
        const indexPath = path.join(__dirname, 'src', 'main', 'resources', 'static', 'index.html');
        if (fs.existsSync(indexPath)) {
            res.sendFile(indexPath);
            return;
        }
        
        // If nothing found, let 404 handler catch it
        next();
    } catch (error) {
        console.error('Error serving HTML file:', error);
        next(error);
    }
});

// Error handling middleware
app.use((err, req, res, next) => {
    console.error('Error:', err);
    res.status(err.status || 500).json({
        timestamp: new Date().toISOString(),
        status: err.status || 500,
        error: err.message || 'Internal Server Error',
        path: req.path
    });
});

// 404 handler
app.use((req, res) => {
    res.status(404).json({
        timestamp: new Date().toISOString(),
        status: 404,
        error: 'Not Found',
        path: req.path
    });
});

// Database connection and server start
async function startServer() {
    try {
        await sequelize.authenticate();
        console.log('Database connection established successfully.');
        
        // Sync database (use with caution in production)
        if (process.env.NODE_ENV === 'development') {
            // await sequelize.sync({ alter: true });
        }
        
        app.listen(PORT, () => {
            console.log(`Server is running on http://localhost:${PORT}`);
        });
    } catch (error) {
        console.error('Unable to connect to the database:', error);
        process.exit(1);
    }
}

startServer();
