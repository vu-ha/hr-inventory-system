import express from 'express';
const router = express.Router();

router.get('/departments/:departmentId/members', (req, res) => {
    res.json({});
});

router.get('/positions/:positionId/holders', (req, res) => {
    res.json({});
});

export default router;
