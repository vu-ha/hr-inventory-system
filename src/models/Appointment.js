import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database.js';
import Employee from './Employee.js';
import Position from './Position.js';
import Department from './Department.js';
import Decision from './Decision.js';

const Appointment = sequelize.define('Appointment', {
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
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
    positionId: {
        type: DataTypes.UUID,
        allowNull: false,
        field: 'position_id',
        references: {
            model: 'position',
            key: 'id'
        }
    },
    departmentId: {
        type: DataTypes.UUID,
        allowNull: false,
        field: 'department_id',
        references: {
            model: 'department',
            key: 'id'
        }
    },
    decisionId: {
        type: DataTypes.UUID,
        allowNull: true,
        field: 'decision_id',
        references: {
            model: 'decision',
            key: 'id'
        }
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
    status: {
        type: DataTypes.ENUM('ACTIVE', 'INACTIVE', 'PENDING', 'TERMINATED'),
        defaultValue: 'PENDING'
    }
}, {
    tableName: 'appointment',
    schema: 'hrm',
    timestamps: false
});

Appointment.belongsTo(Employee, { foreignKey: 'employeeId', as: 'employee' });
Appointment.belongsTo(Position, { foreignKey: 'positionId', as: 'position' });
Appointment.belongsTo(Department, { foreignKey: 'departmentId', as: 'department' });
Appointment.belongsTo(Decision, { foreignKey: 'decisionId', as: 'decision' });

export default Appointment;
