import Appointment from '../models/Appointment.js';
import Employee from '../models/Employee.js';
import Position from '../models/Position.js';
import Department from '../models/Department.js';
import { Op } from 'sequelize';

export const getAppointments = async (page = 0, size = 10, filters = {}) => {
    const where = {};
    if (filters.employeeId) where.employeeId = filters.employeeId;
    if (filters.status) where.status = filters.status;
    
    const offset = page * size;
    const { count, rows } = await Appointment.findAndCountAll({
        where,
        include: [{
            model: Employee,
            as: 'employee',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }, {
            model: Position,
            as: 'position',
            attributes: ['id', 'name']
        }, {
            model: Department,
            as: 'department',
            attributes: ['id', 'name', 'roomCode']
        }],
        limit: size,
        offset,
        order: [['startDate', 'DESC']]
    });
    
    return {
        content: rows.map(formatAppointmentResponse),
        pageNumber: page,
        pageSize: size,
        totalElements: count,
        totalPages: Math.ceil(count / size)
    };
};

export const getAppointmentById = async (id) => {
    const appointment = await Appointment.findByPk(id, {
        include: [{
            model: Employee,
            as: 'employee',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }, {
            model: Position,
            as: 'position',
            attributes: ['id', 'name']
        }, {
            model: Department,
            as: 'department',
            attributes: ['id', 'name', 'roomCode']
        }]
    });
    return appointment ? formatAppointmentResponse(appointment) : null;
};

export const getEmployeeAppointments = async (employeeId) => {
    const appointments = await Appointment.findAll({
        where: { employeeId },
        include: [{
            model: Position,
            as: 'position',
            attributes: ['id', 'name']
        }, {
            model: Department,
            as: 'department',
            attributes: ['id', 'name', 'roomCode']
        }],
        order: [['startDate', 'DESC']]
    });
    return appointments.map(formatAppointmentResponse);
};

export const getEmployeeActiveAppointments = async (employeeId) => {
    const appointments = await Appointment.findAll({
        where: {
            employeeId,
            status: 'ACTIVE',
            [Op.or]: [
                { endDate: null },
                { endDate: { [Op.gte]: new Date() } }
            ]
        },
        include: [{
            model: Position,
            as: 'position',
            attributes: ['id', 'name']
        }, {
            model: Department,
            as: 'department',
            attributes: ['id', 'name', 'roomCode']
        }],
        order: [['startDate', 'DESC']]
    });
    return appointments.map(formatAppointmentResponse);
};

export const createAppointment = async (dto) => {
    const appointment = await Appointment.create({
        employeeId: dto.employeeId,
        positionId: dto.positionId,
        departmentId: dto.departmentId,
        decisionId: dto.decisionId,
        startDate: dto.startDate,
        endDate: dto.endDate,
        status: dto.status || 'PENDING'
    });
    return formatAppointmentResponse(appointment);
};

function formatAppointmentResponse(appointment) {
    return {
        id: appointment.id,
        employeeId: appointment.employeeId,
        employee: appointment.employee ? {
            id: appointment.employee.id,
            name: `${appointment.employee.firstName} ${appointment.employee.lastName}`,
            email: appointment.employee.email
        } : null,
        positionId: appointment.positionId,
        position: appointment.position ? {
            id: appointment.position.id,
            name: appointment.position.name,
            code: appointment.position.code || null,
            level: appointment.position.level || null
        } : null,
        departmentId: appointment.departmentId,
        department: appointment.department ? {
            id: appointment.department.id,
            name: appointment.department.name,
            code: appointment.department.roomCode || null
        } : null,
        decisionId: appointment.decisionId,
        startDate: appointment.startDate,
        endDate: appointment.endDate,
        status: appointment.status
    };
}
