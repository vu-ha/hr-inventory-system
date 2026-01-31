import express from 'express';
import * as decisionController from '../controllers/decision.controller.js';

const router = express.Router();

router.get('/', decisionController.getDecisions);
router.get('/:id', decisionController.getDecisionById);
router.post('/', decisionController.createDecision);

export default router;
