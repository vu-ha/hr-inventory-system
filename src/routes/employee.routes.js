import express from 'express';
import * as employeeController from '../controllers/employee.controller.js';
import { validateEmployee } from '../middleware/validation.js';

const router = express.Router();

// Create employee
router.post('/', validateEmployee, employeeController.createEmployee);

// Get employee by ID
router.get('/:id', employeeController.getEmployeeById);

// Update employee
router.put('/:id', validateEmployee, employeeController.updateEmployee);

// Delete employee
router.delete('/:id', employeeController.deleteEmployee);

// Get employees with filters and pagination
router.get('/', employeeController.getEmployees);

// Search employees
router.get('/search', employeeController.searchEmployees);

// Get employees by department
router.get('/departments/:departmentId', employeeController.getEmployeesByDepartment);

// Validation endpoints
router.get('/check-email', employeeController.checkEmailExists);
router.get('/check-phone', employeeController.checkPhoneNumberExists);
router.get('/check-tax-code', employeeController.checkTaxCodeExists);
router.get('/check-insurance-number', employeeController.checkInsuranceNumberExists);

router.get('/:id/check-email', employeeController.checkEmailExistsForUpdate);
router.get('/:id/check-phone', employeeController.checkPhoneNumberExistsForUpdate);
router.get('/:id/check-tax-code', employeeController.checkTaxCodeExistsForUpdate);
router.get('/:id/check-insurance-number', employeeController.checkInsuranceNumberExistsForUpdate);

export default router;
