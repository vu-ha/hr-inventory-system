import Decision from '../models/Decision.js';
import Employee from '../models/Employee.js';
import Appointment from '../models/Appointment.js';
import { Op } from 'sequelize';

export const getDecisions = async (page = 0, size = 10, filters = {}) => {
    const where = {};
    if (filters.employeeId) where.employeeId = filters.employeeId;
    if (filters.decisionType) where.decisionType = filters.decisionType;
    if (filters.status) where.status = filters.status;
    
    const offset = page * size;
    const { count, rows } = await Decision.findAndCountAll({
        where,
        include: [{
            model: Employee,
            as: 'employee',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }, {
            model: Employee,
            as: 'signer',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }],
        limit: size,
        offset,
        order: [['signedDate', 'DESC']]
    });
    
    return {
        content: rows.map(formatDecisionResponse),
        pageNumber: page,
        pageSize: size,
        totalElements: count,
        totalPages: Math.ceil(count / size)
    };
};

export const getDecisionById = async (id) => {
    const decision = await Decision.findByPk(id, {
        include: [{
            model: Employee,
            as: 'employee',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }, {
            model: Employee,
            as: 'signer',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }]
    });
    
    if (!decision) return null;
    
    // Get related appointments
    const appointments = await Appointment.findAll({
        where: { decisionId: id },
        include: [{
            model: Employee,
            as: 'employee',
            attributes: ['id', 'firstName', 'lastName']
        }]
    });
    
    const result = formatDecisionResponse(decision);
    result.appointments = appointments.map(a => ({
        id: a.id,
        employeeName: `${a.employee.firstName} ${a.employee.lastName}`,
        startDate: a.startDate,
        status: a.status
    }));
    
    return result;
};

export const createDecision = async (dto) => {
    const decision = await Decision.create({
        decisionNumber: dto.decisionNumber,
        decisionType: dto.decisionType,
        signedDate: dto.signedDate || dto.decisionDate,
        effectiveDate: dto.effectiveDate,
        expiredDate: dto.expiredDate,
        content: dto.content,
        employeeId: dto.employeeId,
        signerId: dto.signerId,
        decisionUrl: dto.decisionUrl
    });
    return formatDecisionResponse(decision);
};

function formatDecisionResponse(decision) {
    return {
        id: decision.id,
        decisionNumber: decision.decisionNumber,
        decisionType: decision.decisionType,
        decisionDate: decision.signedDate, // Alias for compatibility
        signedDate: decision.signedDate,
        effectiveDate: decision.effectiveDate,
        expiredDate: decision.expiredDate,
        content: decision.content,
        employeeId: decision.employeeId,
        employee: decision.employee ? {
            id: decision.employee.id,
            name: `${decision.employee.firstName} ${decision.employee.lastName}`,
            email: decision.employee.email
        } : null,
        signerId: decision.signerId,
        signer: decision.signer ? {
            id: decision.signer.id,
            name: `${decision.signer.firstName} ${decision.signer.lastName}`,
            email: decision.signer.email
        } : null,
        decisionUrl: decision.decisionUrl
    };
}
