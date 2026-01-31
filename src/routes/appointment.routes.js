import express from 'express';
import * as appointmentController from '../controllers/appointment.controller.js';

const router = express.Router();

router.get('/', appointmentController.getAppointments);
router.get('/:id', appointmentController.getAppointmentById);
router.post('/', appointmentController.createAppointment);

export default router;
