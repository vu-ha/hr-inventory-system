import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database.js';
import Employee from './Employee.js';

const Decision = sequelize.define('Decision', {
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    decisionNumber: {
        type: DataTypes.STRING,
        allowNull: false,
        unique: true,
        field: 'decision_number'
    },
    decisionType: {
        type: DataTypes.ENUM('APPOINTMENT', 'PROMOTION', 'TRANSFER', 'TERMINATION', 'SALARY_ADJUSTMENT', 'OTHER'),
        allowNull: false,
        field: 'decision_type'
    },
    signedDate: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'signed_date'
    },
    effectiveDate: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'effective_date'
    },
    expiredDate: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'expired_date'
    },
    decisionUrl: {
        type: DataTypes.STRING,
        allowNull: true,
        field: 'decision_url'
    },
    content: {
        type: DataTypes.TEXT,
        allowNull: false
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
    signerId: {
        type: DataTypes.UUID,
        allowNull: true,
        field: 'signer_id',
        references: {
            model: 'employee',
            key: 'id'
        }
    },
    createdAt: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'created_at'
    },
    createdBy: {
        type: DataTypes.UUID,
        allowNull: true,
        field: 'created_by'
    },
    updatedAt: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'updated_at'
    },
    updatedBy: {
        type: DataTypes.UUID,
        allowNull: true,
        field: 'updated_by'
    }
}, {
    tableName: 'decision',
    schema: 'hrm',
    timestamps: false
});

Decision.belongsTo(Employee, { foreignKey: 'employeeId', as: 'employee' });
Decision.belongsTo(Employee, { foreignKey: 'signerId', as: 'signer' });

export default Decision;
