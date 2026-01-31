import * as decisionService from '../services/decision.service.js';

export const getDecisions = async (req, res, next) => {
    try {
        const { page = 0, size = 10, employeeId, decisionType, status } = req.query;
        const result = await decisionService.getDecisions(
            parseInt(page),
            parseInt(size),
            { employeeId, decisionType, status }
        );
        res.json(result);
    } catch (error) {
        next(error);
    }
};

export const getDecisionById = async (req, res, next) => {
    try {
        const decision = await decisionService.getDecisionById(req.params.id);
        if (!decision) {
            return res.status(404).json({ error: 'Decision not found' });
        }
        res.json(decision);
    } catch (error) {
        next(error);
    }
};

export const createDecision = async (req, res, next) => {
    try {
        const decision = await decisionService.createDecision(req.body);
        res.status(201).json(decision);
    } catch (error) {
        next(error);
    }
};
