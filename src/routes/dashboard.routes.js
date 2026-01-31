import express from 'express';
import * as dashboardService from '../services/dashboard.service.js';

const router = express.Router();

router.get('/stats', async (req, res, next) => {
    try {
        const stats = await dashboardService.getDashboardStats();
        res.json(stats);
    } catch (error) {
        next(error);
    }
});

export default router;
