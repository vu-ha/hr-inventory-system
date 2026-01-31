import express from 'express';
import * as projectController from '../controllers/project.controller.js';

const router = express.Router();

router.get('/summary', projectController.getAllProjects);
router.get('/:id', projectController.getProjectById);
router.post('/', projectController.createProject);
router.put('/:id', projectController.updateProject);

export default router;
