import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database.js';
import Department from './Department.js';

const Position = sequelize.define('Position', {
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false
    },
    code: {
        type: DataTypes.STRING,
        allowNull: true,
        unique: true
    },
    description: {
        type: DataTypes.TEXT,
        allowNull: true
    },
    // departmentId removed as column doesn't exist in database
    level: {
        type: DataTypes.ENUM('JUNIOR', 'MIDDLE', 'SENIOR', 'LEAD', 'MANAGER', 'DIRECTOR'),
        allowNull: true
    },
    jobGrade: {
        type: DataTypes.STRING,
        allowNull: true,
        field: 'job_grade'
    },
    status: {
        type: DataTypes.ENUM('ACTIVE', 'INACTIVE'),
        defaultValue: 'ACTIVE'
    }
}, {
    tableName: 'position',
    schema: 'hrm',
    timestamps: false
});

// Department association removed as department_id column doesn't exist

export default Position;
