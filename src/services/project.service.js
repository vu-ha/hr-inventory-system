import Project from '../models/Project.js';
import Employee from '../models/Employee.js';
import { Op } from 'sequelize';

export const getAllProjects = async () => {
    const projects = await Project.findAll({
        include: [{
            model: Employee,
            as: 'manager',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }],
        order: [['startDate', 'DESC']]
    });
    return projects.map(formatProjectResponse);
};

export const getProjectById = async (id) => {
    const project = await Project.findByPk(id, {
        include: [{
            model: Employee,
            as: 'manager',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }]
    });
    return project ? formatProjectResponse(project) : null;
};

export const createProject = async (dto) => {
    const project = await Project.create({
        name: dto.name,
        managerId: dto.managerId,
        startDate: dto.startDate,
        expectedEndDate: dto.expectedEndDate,
        status: dto.status
    });
    return formatProjectResponse(project);
};

export const updateProject = async (id, dto) => {
    const project = await Project.findByPk(id);
    if (!project) return null;
    
    await project.update({
        name: dto.name,
        managerId: dto.managerId,
        startDate: dto.startDate,
        expectedEndDate: dto.expectedEndDate,
        status: dto.status
    });
    
    return formatProjectResponse(project);
};

function formatProjectResponse(project) {
    return {
        id: project.id,
        name: project.name,
        managerId: project.managerId,
        manager: project.manager ? {
            id: project.manager.id,
            name: `${project.manager.firstName} ${project.manager.lastName}`,
            email: project.manager.email
        } : null,
        startDate: project.startDate,
        expectedEndDate: project.expectedEndDate,
        endDate: project.expectedEndDate, // Alias for compatibility
        status: project.status
    };
}
