import Position from '../models/Position.js';
import Department from '../models/Department.js';

export const getAllPositions = async () => {
    const positions = await Position.findAll({
        // Select only known columns to avoid referencing missing department_id
        attributes: ['id', 'name', 'code', 'description', 'level', 'jobGrade', 'status'],
        order: [['name', 'ASC']]
    });
    return positions.map(formatPositionResponse);
};

export const getPositionById = async (id) => {
    const position = await Position.findByPk(id, {
        attributes: ['id', 'name', 'code', 'description', 'level', 'jobGrade', 'status']
    });
    // Removed Department include as department_id column doesn't exist
    return position ? formatPositionResponse(position) : null;
};

function formatPositionResponse(position) {
    return {
        id: position.id,
        name: position.name,
        code: position.code,
        description: position.description,
        departmentId: position.departmentId || null,
        department: null, // Department relationship removed as department_id doesn't exist
        level: position.level,
        jobGrade: position.jobGrade,
        jobGradeName: position.jobGrade || position.level || '',
        status: position.status
    };
}
