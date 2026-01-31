import Contract from '../models/Contract.js';
import Employee from '../models/Employee.js';
import { Op } from 'sequelize';

export const getAllContracts = async () => {
    const contracts = await Contract.findAll({
        // Select known columns to avoid missing column errors
        attributes: ['id', 'contractNumber', 'employeeId', 'contractType', 'startDate', 'endDate', 'salary', 'status', 'notes'],
        include: [{
            model: Employee,
            as: 'employee',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }],
        order: [['startDate', 'DESC']]
    });
    return contracts.map(formatContractResponse);
};

export const getContractById = async (id) => {
    const contract = await Contract.findByPk(id, {
        attributes: ['id', 'contractNumber', 'employeeId', 'contractType', 'startDate', 'endDate', 'salary', 'status', 'notes'],
        include: [{
            model: Employee,
            as: 'employee',
            attributes: ['id', 'firstName', 'lastName', 'email']
        }]
    });
    return contract ? formatContractResponse(contract) : null;
};

export const createContract = async (dto) => {
    const contract = await Contract.create({
        contractNumber: dto.contractNumber,
        employeeId: dto.employeeId,
        contractType: dto.contractType,
        startDate: dto.startDate,
        endDate: dto.endDate,
        salary: dto.salary,
        status: dto.status || 'DRAFT',
        notes: dto.notes
    });
    return formatContractResponse(contract);
};

export const updateContract = async (id, dto) => {
    const contract = await Contract.findByPk(id);
    if (!contract) return null;
    
    await contract.update({
        contractNumber: dto.contractNumber,
        contractType: dto.contractType,
        startDate: dto.startDate,
        endDate: dto.endDate,
        salary: dto.salary,
        status: dto.status,
        notes: dto.notes
    });
    
    return formatContractResponse(contract);
};

function formatContractResponse(contract) {
    // contractNumber might be stored under different column names in DB
    const rawContractNumber = contract.get ? (contract.get('contract_code') || contract.get('contract_number') || contract.contractNumber) : contract.contractNumber;
    return {
        id: contract.id,
        contractNumber: rawContractNumber,
        employeeId: contract.employeeId,
        employee: contract.employee ? {
            id: contract.employee.id,
            name: `${contract.employee.firstName} ${contract.employee.lastName}`,
            email: contract.employee.email
        } : null,
        contractType: contract.contractType,
        startDate: contract.startDate,
        endDate: contract.endDate,
        salary: contract.salary ? parseFloat(contract.salary) : null,
        status: contract.status,
        notes: contract.notes
    };
}
