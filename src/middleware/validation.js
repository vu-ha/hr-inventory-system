import { body, validationResult } from 'express-validator';

export const validateEmployee = [
    body('firstName').notEmpty().withMessage('First name is required'),
    body('lastName').notEmpty().withMessage('Last name is required'),
    body('email').isEmail().withMessage('Valid email is required'),
    body('phoneNumber').notEmpty().withMessage('Phone number is required'),
    body('gender').isIn(['MALE', 'FEMALE', 'OTHER']).withMessage('Invalid gender'),
    body('maritalStatus').isIn(['SINGLE', 'MARRIED', 'DIVORCED', 'WIDOWED']).withMessage('Invalid marital status'),
    body('permanentAddress').notEmpty().withMessage('Permanent address is required'),
    body('bankAccount').notEmpty().withMessage('Bank account is required'),
    body('bankName').notEmpty().withMessage('Bank name is required'),
    body('taxCode').notEmpty().withMessage('Tax code is required'),
    body('socialInsuranceNumber').notEmpty().withMessage('Social insurance number is required'),
    (req, res, next) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }
        next();
    }
];
