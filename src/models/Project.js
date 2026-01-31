import { DataTypes } from 'sequelize';
import { sequelize } from '../config/database.js';
import Employee from './Employee.js';

const Project = sequelize.define('Project', {
    id: {
        type: DataTypes.UUID,
        defaultValue: DataTypes.UUIDV4,
        primaryKey: true
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false
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
    startDate: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'start_date'
    },
    expectedEndDate: {
        type: DataTypes.DATE,
        allowNull: true,
        field: 'expected_end_date'
    },
    status: {
        type: DataTypes.STRING,
        allowNull: true
    }
}, {
    tableName: 'project',
    schema: 'hrm',
    timestamps: false
});

Project.belongsTo(Employee, { foreignKey: 'managerId', as: 'manager' });

export default Project;
