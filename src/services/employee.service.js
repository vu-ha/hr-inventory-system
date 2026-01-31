import Employee from '../models/Employee.js';
import { Op } from 'sequelize';
import { sequelize } from '../config/database.js';

export const createEmployee = async (dto) => {
    const employee = await Employee.create({
        firstName: dto.firstName,
        lastName: dto.lastName,
        gender: dto.gender,
        email: dto.email,
        phoneNumber: dto.phoneNumber,
        maritalStatus: dto.maritalStatus,
        permanentAddress: dto.permanentAddress,
        bankAccount: dto.bankAccount,
        bankName: dto.bankName,
        taxCode: dto.taxCode,
        socialInsuranceNumber: dto.socialInsuranceNumber,
        hometown: dto.hometown,
        yearJoining: dto.yearJoining
    });
    
    return formatEmployeeResponse(employee);
};

export const getEmployeeById = async (id) => {
    const employee = await Employee.findByPk(id);
    return employee ? formatEmployeeResponse(employee) : null;
};

export const updateEmployee = async (id, dto) => {
    const employee = await Employee.findByPk(id);
    if (!employee) return null;
    
    await employee.update({
        firstName: dto.firstName,
        lastName: dto.lastName,
        gender: dto.gender,
        email: dto.email,
        phoneNumber: dto.phoneNumber,
        maritalStatus: dto.maritalStatus,
        permanentAddress: dto.permanentAddress,
        bankAccount: dto.bankAccount,
        bankName: dto.bankName,
        taxCode: dto.taxCode,
        socialInsuranceNumber: dto.socialInsuranceNumber,
        hometown: dto.hometown,
        yearJoining: dto.yearJoining
    });
    
    return formatEmployeeResponse(employee);
};

export const deleteEmployee = async (id) => {
    const employee = await Employee.findByPk(id);
    if (employee) {
        await employee.destroy();
    }
};

export const getEmployeesWithFilters = async (
    name, email, gender, maritalStatus, departmentId, positionId, yearJoining,
    page, size, sortBy, sortDir
) => {
    const where = {};
    
    if (name) {
        where[Op.or] = [
            { firstName: { [Op.iLike]: `%${name}%` } },
            { lastName: { [Op.iLike]: `%${name}%` } }
        ];
    }
    if (email) where.email = { [Op.iLike]: `%${email}%` };
    if (gender) where.gender = gender;
    if (maritalStatus) where.maritalStatus = maritalStatus;
    if (yearJoining) where.yearJoining = yearJoining;
    
    const offset = page * size;
    const order = [[sortBy, sortDir.toUpperCase()]];
    
    const { count, rows } = await Employee.findAndCountAll({
        where,
        limit: size,
        offset,
        order
    });
    
    return {
        content: rows.map(formatEmployeeResponse),
        pageNumber: page,
        pageSize: size,
        totalElements: count,
        totalPages: Math.ceil(count / size)
    };
};

export const searchEmployees = async (keyword, page, size, sortBy, sortDir) => {
    const where = {
        [Op.or]: [
            { firstName: { [Op.iLike]: `%${keyword}%` } },
            { lastName: { [Op.iLike]: `%${keyword}%` } },
            { email: { [Op.iLike]: `%${keyword}%` } },
            { phoneNumber: { [Op.iLike]: `%${keyword}%` } }
        ]
    };
    
    const offset = page * size;
    const order = [[sortBy, sortDir.toUpperCase()]];
    
    const { count, rows } = await Employee.findAndCountAll({
        where,
        limit: size,
        offset,
        order
    });
    
    return {
        content: rows.map(formatEmployeeResponse),
        pageNumber: page,
        pageSize: size,
        totalElements: count,
        totalPages: Math.ceil(count / size)
    };
};

export const getEmployeesByDepartment = async (departmentId) => {
    // This would require joining with Appointment table
    // For now, return empty array
    return [];
};

export const checkEmailExists = async (email) => {
    const employee = await Employee.findOne({ where: { email } });
    return !!employee;
};

export const checkPhoneNumberExists = async (phoneNumber) => {
    const employee = await Employee.findOne({ where: { phoneNumber } });
    return !!employee;
};

export const checkTaxCodeExists = async (taxCode) => {
    const employee = await Employee.findOne({ where: { taxCode } });
    return !!employee;
};

export const checkInsuranceNumberExists = async (insuranceNumber) => {
    const employee = await Employee.findOne({ where: { socialInsuranceNumber: insuranceNumber } });
    return !!employee;
};

export const checkEmailExistsForUpdate = async (email, id) => {
    const employee = await Employee.findOne({ where: { email, id: { [Op.ne]: id } } });
    return !!employee;
};

export const checkPhoneNumberExistsForUpdate = async (phoneNumber, id) => {
    const employee = await Employee.findOne({ where: { phoneNumber, id: { [Op.ne]: id } } });
    return !!employee;
};

export const checkTaxCodeExistsForUpdate = async (taxCode, id) => {
    const employee = await Employee.findOne({ where: { taxCode, id: { [Op.ne]: id } } });
    return !!employee;
};

export const checkInsuranceNumberExistsForUpdate = async (insuranceNumber, id) => {
    const employee = await Employee.findOne({ where: { socialInsuranceNumber: insuranceNumber, id: { [Op.ne]: id } } });
    return !!employee;
};

function formatEmployeeResponse(employee) {
    return {
        id: employee.id,
        firstName: employee.firstName,
        lastName: employee.lastName,
        fullName: `${employee.firstName} ${employee.lastName}`,
        gender: employee.gender,
        email: employee.email,
        phoneNumber: employee.phoneNumber,
        maritalStatus: employee.maritalStatus,
        permanentAddress: employee.permanentAddress,
        bankAccount: employee.bankAccount,
        bankName: employee.bankName,
        taxCode: employee.taxCode,
        socialInsuranceNumber: employee.socialInsuranceNumber,
        hometown: employee.hometown,
        yearJoining: employee.yearJoining
    };
}
