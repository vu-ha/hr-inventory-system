import Department from '../models/Department.js';
import Employee from '../models/Employee.js';
import { Op } from 'sequelize';

export const getAllDepartments = async () => {
    const departments = await Department.findAll({
        include: [{
            model: Employee,
            as: 'manager',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }],
        order: [['name', 'ASC']]
    });
    return departments.map(formatDepartmentResponse);
};

export const getDepartmentById = async (id) => {
    const department = await Department.findByPk(id, {
        include: [{
            model: Employee,
            as: 'manager',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }]
    });
    return department ? formatDepartmentResponse(department) : null;
};

export const createDepartment = async (dto) => {
    const department = await Department.create({
        name: dto.name,
        roomCode: dto.roomCode || dto.code,
        description: dto.description,
        managerId: dto.managerId,
        parentId: dto.parentId || dto.parentDepartmentId
    });
    return formatDepartmentResponse(department);
};

export const updateDepartment = async (id, dto) => {
    const department = await Department.findByPk(id);
    if (!department) return null;
    
    await department.update({
        name: dto.name,
        roomCode: dto.roomCode || dto.code,
        description: dto.description,
        managerId: dto.managerId,
        parentId: dto.parentId || dto.parentDepartmentId
    });
    
    return formatDepartmentResponse(department);
};

export const deleteDepartment = async (id) => {
    const department = await Department.findByPk(id);
    if (department) {
        await department.destroy();
    }
};

function formatDepartmentResponse(department) {
    return {
        id: department.id,
        name: department.name,
        code: department.roomCode, // Alias for compatibility
        roomCode: department.roomCode,
        description: department.description,
        managerId: department.managerId,
        manager: department.manager ? {
            id: department.manager.id,
            name: `${department.manager.firstName} ${department.manager.lastName}`,
            email: department.manager.email
        } : null,
        parentDepartmentId: department.parentId, // Alias for compatibility
        parentId: department.parentId,
        status: 'ACTIVE' // Default status since column doesn't exist
    };
}
