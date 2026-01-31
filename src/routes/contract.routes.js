import express from 'express';
import * as contractController from '../controllers/contract.controller.js';

const router = express.Router();

router.get('/', contractController.getAllContracts);
router.get('/:id', contractController.getContractById);
router.post('/', contractController.createContract);
router.put('/:id', contractController.updateContract);

export default router;
