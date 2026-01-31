import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database.js';
import Employee from './Employee.js';

const Contract = sequelize.define('Contract', {
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    contractNumber: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
        field: 'contract_code' // Changed from contract_number to contract_code
    },
    employeeId: {
        type: DataTypes.UUID,
        allowNull: false,
        field: 'employee_id',
        references: {
            model: 'employee',
            key: 'id'
        }
    },
    contractType: {
        type: DataTypes.ENUM('PERMANENT', 'TEMPORARY', 'INTERN', 'PART_TIME', 'CONSULTANT'),
        allowNull: false,
        field: 'contract_type'
    },
    startDate: {
        type: DataTypes.DATE,
        allowNull: false,
        field: 'start_date'
    },
    endDate: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'end_date'
    },
    salary: {
        type: DataTypes.DECIMAL(15, 2),
        allowNull: false
    },
    status: {
        type: DataTypes.ENUM('DRAFT', 'ACTIVE', 'EXPIRED', 'TERMINATED', 'CANCELLED'),
        defaultValue: 'DRAFT'
    },
    notes: {
        type: DataTypes.TEXT,
        allowNull: true
    }
}, {
    tableName: 'contract',
    schema: 'hrm',
    timestamps: false
});

Contract.belongsTo(Employee, { foreignKey: 'employeeId', as: 'employee' });

export default Contract;
