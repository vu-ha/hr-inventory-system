import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database.js';
import Employee from './Employee.js';

const Department = sequelize.define('Department', {
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true
    },
    description: {
        type: DataTypes.TEXT,
        allowNull: true
    },
    roomCode: {
        type: DataTypes.STRING,
        allowNull: true,
        field: 'room_code'
    },
    managerId: {
        type: DataTypes.UUID,
        allowNull: true,
        field: 'manager_id',
        references: {
            model: 'employee',
            key: 'id'
        }
    },
    parentId: {
        type: DataTypes.UUID,
        allowNull: true,
        field: 'parent_id',
        references: {
            model: 'department',
            key: 'id'
        }
    }
}, {
    tableName: 'department',
    schema: 'hrm',
    timestamps: false
});

// Associations
Department.belongsTo(Employee, { foreignKey: 'managerId', as: 'manager' });
Department.belongsTo(Department, { foreignKey: 'parentId', as: 'parentDepartment' });

export default Department;
