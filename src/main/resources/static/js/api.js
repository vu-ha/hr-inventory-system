// API Service - Gọi API backend
class ApiService {
    async request(fullUrl, options = {}) {
        const config = {
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include', // Include cookies for session authentication
            ...options
        };

        try {
            const response = await fetch(fullUrl, config);
            
            if (!response.ok) {
                const errorText = await response.text();
                console.error('API Error Response:', errorText);
                throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
            }
            
            return await response.json();
        } catch (error) {
            console.error('API Error:', error);
            throw error;
        }
    }

    async get(endpoint, params = {}) {
        const queryString = new URLSearchParams(params).toString();
        const url = queryString ? `${endpoint}?${queryString}` : endpoint;
        return this.request(url, { method: 'GET' });
    }

    async post(endpoint, data) {
        return this.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    }

    async put(endpoint, data) {
        return this.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    }

    async delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    }

    // Employees - /api/employees (không có v1)
    async getEmployees(page = 0, size = 10, keyword = null) {
        const params = { page, size };
        if (keyword) params.keyword = keyword;
        return this.get('/api/employees', params);
    }

    async getEmployee(id) {
        return this.get(`/api/employees/${id}`);
    }

    async getEmployeeAppointments(employeeId) {
        return this.get(`/api/employees/${employeeId}/appointments`);
    }

    async getEmployeeActiveAppointments(employeeId) {
        return this.get(`/api/employees/${employeeId}/appointments/active`);
    }

    // Departments - /api/v1/departments
    async getDepartments() {
        return this.get('/api/v1/departments');
    }

    async getDepartment(id) {
        return this.get(`/api/v1/departments/${id}`);
    }

    async createDepartment(data) {
        return this.post('/api/v1/departments', data);
    }

    async updateDepartment(id, data) {
        return this.put(`/api/v1/departments/${id}`, data);
    }

    async deleteDepartment(id) {
        return this.delete(`/api/v1/departments/${id}`);
    }

    // Positions - /api/positions (không có v1)
    async getPositions() {
        return this.get('/api/positions');
    }

    // Projects - /api/v1/projects
    async getProjects() {
        return this.get('/api/v1/projects/summary');
    }

    async getProject(id) {
        return this.get(`/api/v1/projects/${id}`);
    }

    async createProject(data) {
        return this.post('/api/v1/projects', data);
    }

    async updateProject(id, data) {
        return this.put(`/api/v1/projects/${id}`, data);
    }

    // Decisions - /api/decisions (không có v1)
    async getDecisions(page = 0, size = 10) {
        return this.get('/api/decisions', { page, size });
    }

    async getDecision(id) {
        return this.get(`/api/decisions/${id}`);
    }

    async createDecision(data) {
        return this.post('/api/decisions', data);
    }

    // Appointments - /api/appointments (không có v1)
    async getAppointments(page = 0, size = 10) {
        return this.get('/api/appointments', { page, size });
    }

    async getAppointment(id) {
        return this.get(`/api/appointments/${id}`);
    }

    async createAppointment(data) {
        return this.post('/api/appointments', data);
    }

    // Contracts - /api/v1/contracts
    async getContracts() {
        return this.get('/api/v1/contracts');
    }

    async getContract(id) {
        return this.get(`/api/v1/contracts/${id}`);
    }

    async createContract(data) {
        return this.post('/api/v1/contracts', data);
    }

    async updateContract(id, data) {
        return this.put(`/api/v1/contracts/${id}`, data);
    }

    // Dashboard stats - /api/v1/dashboard/stats
    async getDashboardStats() {
        return this.get('/api/v1/dashboard/stats');
    }
}

const apiService = new ApiService();
