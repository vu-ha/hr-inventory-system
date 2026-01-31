import * as employeeService from '../services/employee.service.js';
import { Op } from 'sequelize';

export const createEmployee = async (req, res, next) => {
    try {
        const employee = await employeeService.createEmployee(req.body);
        res.status(201).json(employee);
    } catch (error) {
        next(error);
    }
};

export const getEmployeeById = async (req, res, next) => {
    try {
        const employee = await employeeService.getEmployeeById(req.params.id);
        if (!employee) {
            return res.status(404).json({ error: 'Employee not found' });
        }
        res.json(employee);
    } catch (error) {
        next(error);
    }
};

export const updateEmployee = async (req, res, next) => {
    try {
        const employee = await employeeService.updateEmployee(req.params.id, req.body);
        if (!employee) {
            return res.status(404).json({ error: 'Employee not found' });
        }
        res.json(employee);
    } catch (error) {
        next(error);
    }
};

export const deleteEmployee = async (req, res, next) => {
    try {
        await employeeService.deleteEmployee(req.params.id);
        res.status(204).send();
    } catch (error) {
        next(error);
    }
};

export const getEmployees = async (req, res, next) => {
    try {
        const {
            name,
            email,
            gender,
            maritalStatus,
            departmentId,
            positionId,
            yearJoining,
            page = 0,
            size = 10,
            sortBy = 'id',
            sortDir = 'asc',
            keyword
        } = req.query;

        let result;
        if (keyword) {
            result = await employeeService.searchEmployees(keyword, parseInt(page), parseInt(size), sortBy, sortDir);
        } else {
            result = await employeeService.getEmployeesWithFilters(
                name, email, gender, maritalStatus, departmentId, positionId, yearJoining,
                parseInt(page), parseInt(size), sortBy, sortDir
            );
        }
        res.json(result);
    } catch (error) {
        next(error);
    }
};

export const searchEmployees = async (req, res, next) => {
    try {
        const { keyword, page = 0, size = 10, sortBy = 'id', sortDir = 'asc' } = req.query;
        const result = await employeeService.searchEmployees(keyword, parseInt(page), parseInt(size), sortBy, sortDir);
        res.json(result);
    } catch (error) {
        next(error);
    }
};

export const getEmployeesByDepartment = async (req, res, next) => {
    try {
        const employees = await employeeService.getEmployeesByDepartment(req.params.departmentId);
        res.json(employees);
    } catch (error) {
        next(error);
    }
};

export const checkEmailExists = async (req, res, next) => {
    try {
        const exists = await employeeService.checkEmailExists(req.query.email);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};

export const checkPhoneNumberExists = async (req, res, next) => {
    try {
        const exists = await employeeService.checkPhoneNumberExists(req.query.phoneNumber);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};

export const checkTaxCodeExists = async (req, res, next) => {
    try {
        const exists = await employeeService.checkTaxCodeExists(req.query.taxCode);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};

export const checkInsuranceNumberExists = async (req, res, next) => {
    try {
        const exists = await employeeService.checkInsuranceNumberExists(req.query.insuranceNumber);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};

export const checkEmailExistsForUpdate = async (req, res, next) => {
    try {
        const exists = await employeeService.checkEmailExistsForUpdate(req.query.email, req.params.id);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};

export const checkPhoneNumberExistsForUpdate = async (req, res, next) => {
    try {
        const exists = await employeeService.checkPhoneNumberExistsForUpdate(req.query.phoneNumber, req.params.id);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};

export const checkTaxCodeExistsForUpdate = async (req, res, next) => {
    try {
        const exists = await employeeService.checkTaxCodeExistsForUpdate(req.query.taxCode, req.params.id);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};

export const checkInsuranceNumberExistsForUpdate = async (req, res, next) => {
    try {
        const exists = await employeeService.checkInsuranceNumberExistsForUpdate(req.query.insuranceNumber, req.params.id);
        res.json({ exists });
    } catch (error) {
        next(error);
    }
};
