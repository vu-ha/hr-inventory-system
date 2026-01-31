import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database.js';

const Employee = sequelize.define('Employee', {
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    firstName: {
        type: DataTypes.STRING,
        allowNull: false,
        field: 'first_name'
    },
    lastName: {
        type: DataTypes.STRING,
        allowNull: false,
        field: 'last_name'
    },
    gender: {
        type: DataTypes.ENUM('MALE', 'FEMALE', 'OTHER'),
        allowNull: false
    },
    email: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true
    },
    phoneNumber: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
        field: 'phone_number'
    },
    maritalStatus: {
        type: DataTypes.ENUM('SINGLE', 'MARRIED', 'DIVORCED', 'WIDOWED'),
        allowNull: false,
        field: 'marital_status'
    },
    permanentAddress: {
        type: DataTypes.TEXT,
        allowNull: false,
        field: 'permanent_address'
    },
    bankAccount: {
        type: DataTypes.STRING,
        allowNull: false,
        field: 'bank_account'
    },
    bankName: {
        type: DataTypes.STRING,
        allowNull: false,
        field: 'bank_name'
    },
    taxCode: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
        field: 'tax_code'
    },
    socialInsuranceNumber: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
        field: 'social_insurance_number'
    },
    hometown: {
        type: DataTypes.STRING,
        allowNull: true
    },
    yearJoining: {
        type: DataTypes.SMALLINT,
        allowNull: true,
        field: 'year_of_joining'
    }
}, {
    tableName: 'employee',
    schema: 'hrm',
    timestamps: false
});

export default Employee;
