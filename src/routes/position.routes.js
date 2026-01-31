import express from 'express';
import * as positionController from '../controllers/position.controller.js';

const router = express.Router();

router.get('/', positionController.getAllPositions);
router.get('/:id', positionController.getPositionById);

export default router;
