import * as appointmentService from '../services/appointment.service.js';

export const getAppointments = async (req, res, next) => {
    try {
        const { page = 0, size = 10, employeeId, status } = req.query;
        const result = await appointmentService.getAppointments(
            parseInt(page),
            parseInt(size),
            { employeeId, status }
        );
        res.json(result);
    } catch (error) {
        next(error);
    }
};

export const getAppointmentById = async (req, res, next) => {
    try {
        const appointment = await appointmentService.getAppointmentById(req.params.id);
        if (!appointment) {
            return res.status(404).json({ error: 'Appointment not found' });
        }
        res.json(appointment);
    } catch (error) {
        next(error);
    }
};

export const getEmployeeAppointments = async (req, res, next) => {
    try {
        const appointments = await appointmentService.getEmployeeAppointments(req.params.employeeId);
        res.json(appointments);
    } catch (error) {
        next(error);
    }
};

export const getEmployeeActiveAppointments = async (req, res, next) => {
    try {
        const appointments = await appointmentService.getEmployeeActiveAppointments(req.params.employeeId);
        res.json(appointments);
    } catch (error) {
        next(error);
    }
};

export const createAppointment = async (req, res, next) => {
    try {
        const appointment = await appointmentService.createAppointment(req.body);
        res.status(201).json(appointment);
    } catch (error) {
        next(error);
    }
};
