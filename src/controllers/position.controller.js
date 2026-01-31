import * as positionService from '../services/position.service.js';

export const getAllPositions = async (req, res, next) => {
    try {
        const positions = await positionService.getAllPositions();
        res.json(positions);
    } catch (error) {
        next(error);
    }
};

export const getPositionById = async (req, res, next) => {
    try {
        const position = await positionService.getPositionById(req.params.id);
        if (!position) {
            return res.status(404).json({ error: 'Position not found' });
        }
        res.json(position);
    } catch (error) {
        next(error);
    }
};
