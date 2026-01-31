import Employee from '../models/Employee.js';
import Department from '../models/Department.js';
import Project from '../models/Project.js';
import Position from '../models/Position.js';
import { sequelize } from '../config/database.js';

export const getDashboardStats = async () => {
    const [employeeCount] = await sequelize.query(
        'SELECT COUNT(*) as count FROM hrm.employee',
        { type: sequelize.QueryTypes.SELECT }
    );
    
    const [departmentCount] = await sequelize.query(
        'SELECT COUNT(*) as count FROM hrm.department',
        { type: sequelize.QueryTypes.SELECT }
    );
    
    const [projectCount] = await sequelize.query(
        'SELECT COUNT(*) as count FROM hrm.project',
        { type: sequelize.QueryTypes.SELECT }
    );
    
    const [positionCount] = await sequelize.query(
        'SELECT COUNT(*) as count FROM hrm.position',
        { type: sequelize.QueryTypes.SELECT }
    );
    
    return {
        totalEmployees: parseInt(employeeCount?.count || 0),
        totalDepartments: parseInt(departmentCount?.count || 0),
        totalProjects: parseInt(projectCount?.count || 0),
        totalPositions: parseInt(positionCount?.count || 0)
    };
};
