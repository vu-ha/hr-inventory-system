// Main App JavaScript
document.addEventListener('DOMContentLoaded', () => {
    const currentPage = window.location.pathname;
    
    // Check for form pages first (new/edit)
    if (currentPage.includes('/employees/new') || currentPage.includes('/employees/new.html')) {
        loadEmployeeForm(null);
        return;
    }
    
    // Check for edit pages - handle both /employees/{id}/edit.html and /employees/{id}/edit
    if (currentPage.includes('/employees/') && (currentPage.includes('/edit') || currentPage.match(/\/employees\/[^\/]+\/edit/))) {
        const uuidPattern = /([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})/i;
        const match = currentPage.match(uuidPattern);
        if (match) {
            loadEmployeeForm(match[1]);
            return;
        }
    }
    
    // Check for other form pages (departments, projects, etc.)
    if (currentPage.includes('/departments/new') || currentPage.includes('/departments/new.html')) {
        loadDepartmentForm(null);
        return;
    }
    
    // Check for department edit pages
    if (currentPage.includes('/departments/') && (currentPage.includes('/edit') || currentPage.match(/\/departments\/[^\/]+\/edit/))) {
        const uuidPattern = /([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})/i;
        const match = currentPage.match(uuidPattern);
        if (match) {
            loadDepartmentForm(match[1]);
            return;
        }
    }
    
    if (currentPage.includes('/projects/new') || currentPage.includes('/projects/new.html')) {
        // TODO: Implement loadProjectForm
        document.getElementById('content').innerHTML = '<p class="text-gray-600">Form tạo dự án - Đang phát triển</p>';
        return;
    }
    
    if (currentPage.includes('/decisions/new') || currentPage.includes('/decisions/new.html')) {
        // TODO: Implement loadDecisionForm
        document.getElementById('content').innerHTML = '<p class="text-gray-600">Form tạo quyết định - Đang phát triển</p>';
        return;
    }
    
    if (currentPage.includes('/appointments/new') || currentPage.includes('/appointments/new.html')) {
        // TODO: Implement loadAppointmentForm
        document.getElementById('content').innerHTML = '<p class="text-gray-600">Form tạo bổ nhiệm - Đang phát triển</p>';
        return;
    }
    
    if (currentPage.includes('/contracts/new') || currentPage.includes('/contracts/new.html')) {
        // TODO: Implement loadContractForm
        document.getElementById('content').innerHTML = '<p class="text-gray-600">Form tạo hợp đồng - Đang phát triển</p>';
        return;
    }
    
    // Check for detail pages (UUID pattern)
    const uuidPattern = /([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})/i;
    const match = currentPage.match(uuidPattern);
    
    if (match) {
        const id = match[1];
        if (currentPage.includes('/employees/')) {
            loadEmployeeDetail(id);
        } else if (currentPage.includes('/departments/')) {
            loadDepartmentDetail(id);
        } else if (currentPage.includes('/projects/')) {
            loadProjectDetail(id);
        } else if (currentPage.includes('/decisions/')) {
            loadDecisionDetail(id);
        } else if (currentPage.includes('/appointments/')) {
            loadAppointmentDetail(id);
        }
        return;
    }
    
    // List pages
    if (currentPage.includes('dashboard')) {
        loadDashboard();
    } else if (currentPage.includes('employees')) {
        loadEmployees();
    } else if (currentPage.includes('departments')) {
        loadDepartments();
    } else if (currentPage.includes('positions')) {
        loadPositions();
    } else if (currentPage.includes('projects')) {
        loadProjects();
    } else if (currentPage.includes('decisions')) {
        loadDecisions();
    } else if (currentPage.includes('appointments')) {
        loadAppointments();
    } else if (currentPage.includes('contracts')) {
        loadContracts();
    }
});

async function loadDashboard() {
    try {
        const stats = await apiService.getDashboardStats();
        console.log('Dashboard Stats API Response:', stats);
        const content = document.getElementById('content');
        
        if (!stats) {
            throw new Error('Invalid response from server');
        }
        
        content.innerHTML = `
            <h1 class="text-3xl font-bold text-gray-900 mb-6">Dashboard</h1>
            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
                <div class="bg-white rounded-lg shadow p-6">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-sm text-gray-600">Tổng nhân viên</p>
                            <p class="text-3xl font-bold text-gray-900">${stats.totalEmployees || 0}</p>
                        </div>
                        <div class="bg-blue-100 p-3 rounded-lg">
                            <i class="fas fa-users text-blue-600 text-2xl"></i>
                        </div>
                    </div>
                </div>
                <div class="bg-white rounded-lg shadow p-6">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-sm text-gray-600">Phòng ban</p>
                            <p class="text-3xl font-bold text-gray-900">${stats.totalDepartments || 0}</p>
                        </div>
                        <div class="bg-green-100 p-3 rounded-lg">
                            <i class="fas fa-building text-green-600 text-2xl"></i>
                        </div>
                    </div>
                </div>
                <div class="bg-white rounded-lg shadow p-6">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-sm text-gray-600">Dự án</p>
                            <p class="text-3xl font-bold text-gray-900">${stats.totalProjects || 0}</p>
                        </div>
                        <div class="bg-purple-100 p-3 rounded-lg">
                            <i class="fas fa-project-diagram text-purple-600 text-2xl"></i>
                        </div>
                    </div>
                </div>
                <div class="bg-white rounded-lg shadow p-6">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-sm text-gray-600">Vị trí</p>
                            <p class="text-3xl font-bold text-gray-900">${stats.totalPositions || 0}</p>
                        </div>
                        <div class="bg-orange-100 p-3 rounded-lg">
                            <i class="fas fa-briefcase text-orange-600 text-2xl"></i>
                        </div>
                    </div>
                </div>
            </div>
        `;
    } catch (error) {
        console.error('Error loading dashboard:', error);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="bg-red-50 border border-red-200 rounded-lg p-4">
                <h2 class="text-red-800 font-semibold mb-2">Lỗi khi tải dashboard</h2>
                <p class="text-red-600 text-sm">${error.message || 'Không thể kết nối đến server'}</p>
                <p class="text-red-500 text-xs mt-2">Vui lòng kiểm tra console để xem chi tiết lỗi</p>
            </div>
        `;
    }
}

async function loadEmployees() {
    try {
        const urlParams = new URLSearchParams(window.location.search);
        const page = parseInt(urlParams.get('page')) || 0;
        const keyword = urlParams.get('keyword') || '';
        
        const response = await apiService.getEmployees(page, 10, keyword);
        console.log('Employees API Response:', response);
        
        // Handle both PageResponseDTO format and direct array
        const employees = response.content || response.data || response || [];
        const totalElements = response.totalElements || employees.length || 0;
        const totalPages = response.totalPages || Math.ceil(totalElements / 10) || 0;
        
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="flex items-center justify-between mb-6">
                <div>
                    <h1 class="text-3xl font-bold text-gray-900">Quản lý Nhân viên</h1>
                    <p class="text-gray-600 mt-2">Danh sách nhân viên trong hệ thống</p>
                </div>
                <a href="/employees/new.html" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                    <i class="fas fa-plus mr-2"></i>Thêm nhân viên
                </a>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-4 mb-6">
                <form method="get" action="/employees.html" class="flex gap-4">
                    <input type="text" name="keyword" value="${keyword}" placeholder="Tìm kiếm nhân viên..."
                        class="flex-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                    <button type="submit" class="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                        <i class="fas fa-search mr-2"></i>Tìm kiếm
                    </button>
                </form>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
                <table class="w-full">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nhân viên</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Email</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Số điện thoại</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Phòng ban</th>
                            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        ${employees.length === 0 ? `
                            <tr>
                                <td colspan="5" class="px-6 py-8 text-center text-gray-500">
                                    <i class="fas fa-inbox text-4xl mb-2 block"></i>
                                    <p>Không có nhân viên nào</p>
                                </td>
                            </tr>
                        ` : employees.map(emp => `
                            <tr class="hover:bg-gray-50">
                                <td class="px-6 py-4">
                                    <div class="flex items-center">
                                        <div class="flex-shrink-0 h-10 w-10 rounded-full bg-blue-100 flex items-center justify-center">
                                            <i class="fas fa-user text-blue-600"></i>
                                        </div>
                                        <div class="ml-4">
                                            <div class="text-sm font-medium text-gray-900">${emp.fullName || 'N/A'}</div>
                                            <div class="text-sm text-gray-500">${emp.employeeCode || ''}</div>
                                        </div>
                                    </div>
                                </td>
                                <td class="px-6 py-4 text-sm text-gray-900">${emp.email || 'N/A'}</td>
                                <td class="px-6 py-4 text-sm text-gray-900">${emp.phoneNumber || 'N/A'}</td>
                                <td class="px-6 py-4 text-sm text-gray-900">${emp.departmentName || 'Chưa phân công'}</td>
                                <td class="px-6 py-4 text-right text-sm font-medium">
                                    <a href="/employees/${emp.id}.html" class="text-blue-600 hover:text-blue-900 mr-3">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                    <a href="/employees/${emp.id}/edit.html" class="text-green-600 hover:text-green-900">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
                ${totalPages > 1 ? `
                    <div class="bg-gray-50 px-6 py-4 flex items-center justify-between border-t">
                        <div class="text-sm text-gray-700">
                            Hiển thị ${page * 10 + 1} - ${Math.min((page + 1) * 10, totalElements)} trong tổng số ${totalElements} nhân viên
                        </div>
                        <div class="flex space-x-2">
                            ${page > 0 ? `<a href="/employees.html?page=${page - 1}&keyword=${keyword}" class="px-4 py-2 border border-gray-300 rounded-lg text-sm font-medium text-gray-700 hover:bg-gray-50">Trước</a>` : ''}
                            ${page < totalPages - 1 ? `<a href="/employees.html?page=${page + 1}&keyword=${keyword}" class="px-4 py-2 border border-gray-300 rounded-lg text-sm font-medium text-gray-700 hover:bg-gray-50">Sau</a>` : ''}
                        </div>
                    </div>
                ` : ''}
            </div>
        `;
    } catch (error) {
        console.error('Error loading employees:', error);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="bg-red-50 border border-red-200 rounded-lg p-4">
                <h2 class="text-red-800 font-semibold mb-2">Lỗi khi tải danh sách nhân viên</h2>
                <p class="text-red-600 text-sm">${error.message || 'Không thể kết nối đến server'}</p>
                <p class="text-red-500 text-xs mt-2">Vui lòng kiểm tra console để xem chi tiết lỗi</p>
            </div>
        `;
    }
}

async function loadDepartments() {
    try {
        const departments = await apiService.getDepartments();
        console.log('Departments API Response:', departments);
        const content = document.getElementById('content');
        
        if (!Array.isArray(departments)) {
            throw new Error('Invalid response format');
        }
        
        content.innerHTML = `
            <div class="flex items-center justify-between mb-6">
                <h1 class="text-3xl font-bold text-gray-900">Quản lý Phòng ban</h1>
                <a href="/departments/new.html" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                    <i class="fas fa-plus mr-2"></i>Thêm phòng ban
                </a>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                ${departments.length === 0 ? `
                    <div class="col-span-3 bg-white rounded-lg shadow-sm border border-gray-200 p-8 text-center">
                        <i class="fas fa-inbox text-4xl text-gray-400 mb-4"></i>
                        <p class="text-gray-500">Không có phòng ban nào</p>
                    </div>
                ` : departments.map(dept => `
                    <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition">
                        <div class="flex items-start justify-between mb-4">
                            <div class="flex items-center">
                                <div class="p-3 bg-blue-100 rounded-lg">
                                    <i class="fas fa-building text-blue-600 text-2xl"></i>
                                </div>
                                <div class="ml-4">
                                    <h3 class="text-lg font-semibold text-gray-900">${dept.name || 'N/A'}</h3>
                                    <p class="text-sm text-gray-500">${dept.code || dept.roomCode || ''}</p>
                                </div>
                            </div>
                        </div>
                        <p class="text-sm text-gray-600 mb-4">${dept.description || ''}</p>
                        ${dept.manager ? `
                            <div class="text-sm text-gray-600 mb-4">
                                <i class="fas fa-user-tie mr-2"></i>
                                <span>Trưởng phòng: ${dept.manager.name}</span>
                            </div>
                        ` : ''}
                        <div class="flex items-center justify-between pt-4 border-t">
                            <div class="flex items-center text-sm text-gray-600">
                                <i class="fas fa-building mr-2"></i>
                                <span>Phòng ban</span>
                            </div>
                            <a href="/departments/${dept.id}.html" class="text-blue-600 hover:text-blue-800 text-sm font-medium">
                                Xem chi tiết <i class="fas fa-arrow-right ml-1"></i>
                            </a>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;
    } catch (error) {
        console.error('Error loading departments:', error);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="bg-red-50 border border-red-200 rounded-lg p-4">
                <h2 class="text-red-800 font-semibold mb-2">Lỗi khi tải danh sách phòng ban</h2>
                <p class="text-red-600 text-sm">${error.message || 'Không thể kết nối đến server'}</p>
                <p class="text-red-500 text-xs mt-2">Vui lòng kiểm tra console để xem chi tiết lỗi</p>
            </div>
        `;
    }
}

async function loadPositions() {
    try {
        const response = await apiService.getPositions();
        console.log('Positions API Response:', response);
        const positions = Array.isArray(response) ? response : (response.content || response.data || []);
        const content = document.getElementById('content');
        content.innerHTML = `
            <h1 class="text-3xl font-bold text-gray-900 mb-6">Quản lý Vị trí</h1>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                ${positions.length === 0 ? `
                    <div class="col-span-3 bg-white rounded-lg shadow-sm border border-gray-200 p-8 text-center">
                        <i class="fas fa-briefcase text-gray-400 text-4xl mb-4"></i>
                        <p class="text-gray-600">Chưa có vị trí nào trong hệ thống</p>
                    </div>
                ` : positions.map(pos => `
                    <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition">
                        <div class="flex items-start justify-between mb-4">
                            <div class="flex items-center">
                                <div class="p-3 bg-orange-100 rounded-lg">
                                    <i class="fas fa-briefcase text-orange-600 text-2xl"></i>
                                </div>
                                <div class="ml-4">
                                    <h3 class="text-lg font-semibold text-gray-900">${pos.name || 'N/A'}</h3>
                                    <p class="text-sm text-gray-500">${pos.jobGradeName || ''}</p>
                                </div>
                            </div>
                        </div>
                        <p class="text-sm text-gray-600 mb-4">${pos.description || ''}</p>
                        <div class="pt-4 border-t">
                            <a href="/positions/${pos.id}/holders.html" class="text-blue-600 hover:text-blue-800 text-sm font-medium">
                                Xem người giữ vị trí <i class="fas fa-arrow-right ml-1"></i>
                            </a>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;
    } catch (error) {
        console.error('Error loading positions:', error);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="bg-red-50 border border-red-200 rounded-lg p-4">
                <h2 class="text-red-800 font-semibold mb-2">Lỗi khi tải danh sách vị trí</h2>
                <p class="text-red-600 text-sm">${error.message || 'Không thể kết nối đến server'}</p>
                <details class="mt-2">
                    <summary class="text-red-600 text-xs cursor-pointer">Chi tiết lỗi</summary>
                    <pre class="text-xs mt-2 text-red-700">${JSON.stringify(error, null, 2)}</pre>
                </details>
            </div>
        `;
    }
}

async function loadProjects() {
    try {
        const projects = await apiService.getProjects();
        console.log('Projects API Response:', projects);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="flex items-center justify-between mb-6">
                <h1 class="text-3xl font-bold text-gray-900">Quản lý Dự án</h1>
                <button onclick="showCreateModal()" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                    <i class="fas fa-plus mr-2"></i>Tạo dự án
                </button>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                ${projects.length === 0 ? `
                    <div class="col-span-3 bg-white rounded-lg shadow-sm border border-gray-200 p-8 text-center">
                        <i class="fas fa-inbox text-4xl text-gray-400 mb-4"></i>
                        <p class="text-gray-500">Không có dự án nào</p>
                    </div>
                ` : projects.map(project => `
                    <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 hover:shadow-md transition">
                        <div class="flex items-start justify-between mb-4">
                            <div class="flex items-center">
                                <div class="p-3 bg-purple-100 rounded-lg">
                                    <i class="fas fa-project-diagram text-purple-600 text-2xl"></i>
                                </div>
                                <div class="ml-4">
                                    <h3 class="text-lg font-semibold text-gray-900">${project.name || project.projectName || 'N/A'}</h3>
                                    <p class="text-sm text-gray-500">${project.code || project.projectCode || ''}</p>
                                </div>
                            </div>
                        </div>
                        <p class="text-sm text-gray-600 mb-4">${project.description || ''}</p>
                        <div class="flex items-center justify-between pt-4 border-t">
                            <div class="flex items-center text-sm text-gray-600">
                                <i class="fas fa-users mr-2"></i>
                                <span>${project.memberCount || project.numberOfMembers || 0} thành viên</span>
                            </div>
                            <a href="/projects/${project.id || project.projectId}.html" class="text-blue-600 hover:text-blue-800 text-sm font-medium">
                                Xem chi tiết <i class="fas fa-arrow-right ml-1"></i>
                            </a>
                        </div>
                    </div>
                `).join('')}
            </div>
        `;
    } catch (error) {
        console.error('Error loading projects:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải danh sách dự án</p>';
    }
}

async function loadDecisions() {
    try {
        const urlParams = new URLSearchParams(window.location.search);
        const page = parseInt(urlParams.get('page')) || 0;
        const response = await apiService.getDecisions(page, 10);
        console.log('Decisions API Response:', response);
        const decisions = response.content || response.data || response || [];
        
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="flex items-center justify-between mb-6">
                <h1 class="text-3xl font-bold text-gray-900">Quản lý Quyết định</h1>
                <a href="/decisions/new.html" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                    <i class="fas fa-plus mr-2"></i>Tạo quyết định
                </a>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
                <table class="w-full">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Số quyết định</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Loại</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ngày ký</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ngày có hiệu lực</th>
                            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        ${decisions.length === 0 ? `
                            <tr>
                                <td colspan="5" class="px-6 py-8 text-center text-gray-500">Không có quyết định nào</td>
                            </tr>
                        ` : decisions.map(decision => `
                            <tr class="hover:bg-gray-50">
                                <td class="px-6 py-4 text-sm font-medium">${decision.decisionNumber || 'N/A'}</td>
                                <td class="px-6 py-4 text-sm">${decision.decisionType || 'N/A'}</td>
                                <td class="px-6 py-4 text-sm">${decision.decisionDate ? new Date(decision.decisionDate).toLocaleDateString('vi-VN') : 'N/A'}</td>
                                <td class="px-6 py-4 text-sm">${decision.effectiveDate ? new Date(decision.effectiveDate).toLocaleDateString('vi-VN') : 'N/A'}</td>
                                <td class="px-6 py-4 text-right">
                                    <a href="/decisions/${decision.id}.html" class="text-blue-600 hover:text-blue-800">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
            </div>
        `;
    } catch (error) {
        console.error('Error loading decisions:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải danh sách quyết định</p>';
    }
}

async function loadAppointments() {
    try {
        const urlParams = new URLSearchParams(window.location.search);
        const page = parseInt(urlParams.get('page')) || 0;
        const response = await apiService.getAppointments(page, 10);
        console.log('Appointments API Response:', response);
        const appointments = response.content || response.data || response || [];
        
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="flex items-center justify-between mb-6">
                <h1 class="text-3xl font-bold text-gray-900">Quản lý Bổ nhiệm</h1>
                <a href="/appointments/new.html" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                    <i class="fas fa-plus mr-2"></i>Tạo bổ nhiệm
                </a>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
                <table class="w-full">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nhân viên</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Vị trí</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Phòng ban</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ngày bắt đầu</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Trạng thái</th>
                            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        ${appointments.length === 0 ? `
                            <tr>
                                <td colspan="6" class="px-6 py-8 text-center text-gray-500">Không có bổ nhiệm nào</td>
                            </tr>
                        ` : appointments.map(apt => `
                            <tr class="hover:bg-gray-50">
                                <td class="px-6 py-4">
                                    <a href="/employees/${apt.employeeId || apt.employee?.id}.html" class="text-blue-600 hover:text-blue-800">
                                        ${apt.employee?.name || apt.employeeName || 'N/A'}
                                    </a>
                                </td>
                                <td class="px-6 py-4 text-sm">${apt.position?.name || apt.positionName || 'N/A'}</td>
                                <td class="px-6 py-4 text-sm">${apt.department?.name || apt.departmentName || 'N/A'}</td>
                                <td class="px-6 py-4 text-sm">${apt.startDate ? new Date(apt.startDate).toLocaleDateString('vi-VN') : 'N/A'}</td>
                                <td class="px-6 py-4">
                                    <span class="px-2 py-1 text-xs rounded-full ${apt.status === 'ACTIVE' ? 'bg-green-100 text-green-800' : apt.status === 'PENDING' ? 'bg-yellow-100 text-yellow-800' : 'bg-red-100 text-red-800'}">
                                        ${apt.status === 'ACTIVE' ? 'Đang làm việc' : apt.status === 'PENDING' ? 'Chờ duyệt' : 'Đã kết thúc'}
                                    </span>
                                </td>
                                <td class="px-6 py-4 text-right">
                                    <a href="/appointments/${apt.id}.html" class="text-blue-600 hover:text-blue-800">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
            </div>
        `;
    } catch (error) {
        console.error('Error loading appointments:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải danh sách bổ nhiệm</p>';
    }
}

async function loadContracts() {
    try {
        const response = await apiService.getContracts();
        console.log('Contracts API Response:', response);
        const contracts = Array.isArray(response) ? response : (response.content || response.data || []);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="flex items-center justify-between mb-6">
                <h1 class="text-3xl font-bold text-gray-900">Quản lý Hợp đồng</h1>
                <a href="/contracts/new.html" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                    <i class="fas fa-plus mr-2"></i>Tạo hợp đồng
                </a>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
                <table class="w-full">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Số hợp đồng</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nhân viên</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Loại</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ngày bắt đầu</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ngày kết thúc</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Trạng thái</th>
                            <th class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase">Thao tác</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-200">
                        ${contracts.length === 0 ? `
                            <tr>
                                <td colspan="7" class="px-6 py-8 text-center text-gray-500">Không có hợp đồng nào</td>
                            </tr>
                        ` : contracts.map(contract => `
                            <tr class="hover:bg-gray-50">
                                <td class="px-6 py-4 text-sm font-medium">${contract.contractNumber || 'N/A'}</td>
                                <td class="px-6 py-4">
                                    <a href="/employees/${contract.employeeId || contract.employee?.id}.html" class="text-blue-600 hover:text-blue-800">
                                        ${contract.employee?.name || 'N/A'}
                                    </a>
                                </td>
                                <td class="px-6 py-4 text-sm">${contract.contractType || 'N/A'}</td>
                                <td class="px-6 py-4 text-sm">${contract.startDate ? new Date(contract.startDate).toLocaleDateString('vi-VN') : 'N/A'}</td>
                                <td class="px-6 py-4 text-sm">${contract.endDate ? new Date(contract.endDate).toLocaleDateString('vi-VN') : 'Không xác định'}</td>
                                <td class="px-6 py-4">
                                    <span class="px-2 py-1 text-xs rounded-full ${contract.status === 'ACTIVE' ? 'bg-green-100 text-green-800' : contract.status === 'DRAFT' ? 'bg-gray-100 text-gray-800' : 'bg-red-100 text-red-800'}">
                                        ${contract.status === 'ACTIVE' ? 'Đang hiệu lực' : contract.status === 'DRAFT' ? 'Nháp' : contract.status || 'N/A'}
                                    </span>
                                </td>
                                <td class="px-6 py-4 text-right">
                                    <a href="/contracts/${contract.id}.html" class="text-blue-600 hover:text-blue-800">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
            </div>
        `;
    } catch (error) {
        console.error('Error loading contracts:', error);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="bg-red-50 border border-red-200 rounded-lg p-4">
                <h2 class="text-red-800 font-semibold mb-2">Lỗi khi tải danh sách hợp đồng</h2>
                <p class="text-red-600 text-sm">${error.message || 'Không thể kết nối đến server'}</p>
                <details class="mt-2">
                    <summary class="text-red-600 text-xs cursor-pointer">Chi tiết lỗi</summary>
                    <pre class="text-xs mt-2 text-red-700">${JSON.stringify(error, null, 2)}</pre>
                </details>
            </div>
        `;
    }
}

// Detail Pages
async function loadEmployeeDetail(id) {
    try {
        const [employee, appointments, activeAppointments] = await Promise.all([
            apiService.getEmployee(id),
            apiService.getEmployeeAppointments(id),
            apiService.getEmployeeActiveAppointments(id)
        ]);
        
        const content = document.getElementById('content');
        const fullName = employee.firstName && employee.lastName 
            ? `${employee.firstName} ${employee.lastName}` 
            : employee.fullName || 'Chi tiết Nhân viên';
        
        content.innerHTML = `
            <div class="mb-6">
                <a href="/employees.html" class="text-blue-600 hover:text-blue-800 mb-4 inline-block">
                    <i class="fas fa-arrow-left mr-2"></i>Quay lại
                </a>
                <div class="flex items-center justify-between">
                    <h1 class="text-3xl font-bold text-gray-900">${fullName}</h1>
                    <a href="/employees/${id}/edit.html" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                        <i class="fas fa-edit mr-2"></i>Chỉnh sửa
                    </a>
                </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                    <h2 class="text-xl font-semibold mb-4">Thông tin cơ bản</h2>
                    <dl class="space-y-3">
                        <div>
                            <dt class="text-sm font-medium text-gray-500">Mã nhân viên</dt>
                            <dd class="mt-1 text-sm text-gray-900">${employee.employeeCode || 'N/A'}</dd>
                        </div>
                        <div>
                            <dt class="text-sm font-medium text-gray-500">Email</dt>
                            <dd class="mt-1 text-sm text-gray-900">${employee.email || 'N/A'}</dd>
                        </div>
                        <div>
                            <dt class="text-sm font-medium text-gray-500">Số điện thoại</dt>
                            <dd class="mt-1 text-sm text-gray-900">${employee.phoneNumber || 'N/A'}</dd>
                        </div>
                        <div>
                            <dt class="text-sm font-medium text-gray-500">Giới tính</dt>
                            <dd class="mt-1 text-sm text-gray-900">${employee.gender === 'MALE' ? 'Nam' : employee.gender === 'FEMALE' ? 'Nữ' : 'Khác'}</dd>
                        </div>
                        <div>
                            <dt class="text-sm font-medium text-gray-500">Phòng ban</dt>
                            <dd class="mt-1 text-sm text-gray-900">${employee.departmentName || 'Chưa phân công'}</dd>
                        </div>
                    </dl>
                </div>

                <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                    <h2 class="text-xl font-semibold mb-4">Bổ nhiệm hiện tại</h2>
                    ${activeAppointments && activeAppointments.length > 0 ? `
                        <div class="space-y-3">
                            ${activeAppointments.map(apt => `
                                <div class="border-l-4 border-blue-500 pl-4">
                                    <p class="font-medium">${apt.positionName || 'N/A'}</p>
                                    <p class="text-sm text-gray-600">${apt.departmentName || 'N/A'}</p>
                                    <p class="text-xs text-gray-500">Từ: ${apt.startDate || 'N/A'}</p>
                                </div>
                            `).join('')}
                        </div>
                    ` : '<p class="text-gray-500">Chưa có bổ nhiệm</p>'}
                </div>
            </div>

            <div class="mt-6 bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h2 class="text-xl font-semibold mb-4">Lịch sử bổ nhiệm</h2>
                ${appointments && appointments.length > 0 ? `
                    <table class="w-full">
                        <thead class="bg-gray-50">
                            <tr>
                                <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Vị trí</th>
                                <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Phòng ban</th>
                                <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ngày bắt đầu</th>
                                <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ngày kết thúc</th>
                                <th class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Trạng thái</th>
                            </tr>
                        </thead>
                        <tbody class="divide-y divide-gray-200">
                            ${appointments.map(apt => `
                                <tr>
                                    <td class="px-4 py-3 text-sm">${apt.positionName || 'N/A'}</td>
                                    <td class="px-4 py-3 text-sm">${apt.departmentName || 'N/A'}</td>
                                    <td class="px-4 py-3 text-sm">${apt.startDate || 'N/A'}</td>
                                    <td class="px-4 py-3 text-sm">${apt.endDate || 'Đang làm việc'}</td>
                                    <td class="px-4 py-3">
                                        <span class="px-2 py-1 text-xs rounded-full ${apt.status === 'ACTIVE' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}">
                                            ${apt.status === 'ACTIVE' ? 'Đang làm việc' : 'Đã kết thúc'}
                                        </span>
                                    </td>
                                </tr>
                            `).join('')}
                        </tbody>
                    </table>
                ` : '<p class="text-gray-500">Chưa có lịch sử bổ nhiệm</p>'}
            </div>
        `;
    } catch (error) {
        console.error('Error loading employee detail:', error);
        document.getElementById('content').innerHTML = `
            <div class="bg-red-50 border border-red-200 rounded-lg p-4">
                <h2 class="text-red-800 font-semibold mb-2">Lỗi khi tải chi tiết nhân viên</h2>
                <p class="text-red-600 text-sm">${error.message || 'Không thể kết nối đến server'}</p>
            </div>
        `;
    }
}

async function loadDepartmentDetail(id) {
    try {
        const department = await apiService.getDepartment(id);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="mb-6">
                <a href="/departments.html" class="text-blue-600 hover:text-blue-800 mb-4 inline-block">
                    <i class="fas fa-arrow-left mr-2"></i>Quay lại
                </a>
                <h1 class="text-3xl font-bold text-gray-900">${department.name || 'Chi tiết Phòng ban'}</h1>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h2 class="text-xl font-semibold mb-4">Thông tin phòng ban</h2>
                <dl class="space-y-3">
                    <div>
                        <dt class="text-sm font-medium text-gray-500">Tên phòng ban</dt>
                        <dd class="mt-1 text-sm text-gray-900">${department.name || 'N/A'}</dd>
                    </div>
                    <div>
                        <dt class="text-sm font-medium text-gray-500">Mô tả</dt>
                        <dd class="mt-1 text-sm text-gray-900">${department.description || 'N/A'}</dd>
                    </div>
                </dl>
            </div>
        `;
    } catch (error) {
        console.error('Error loading department detail:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải chi tiết phòng ban</p>';
    }
}

async function loadProjectDetail(id) {
    try {
        const project = await apiService.getProject(id);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="mb-6">
                <a href="/projects.html" class="text-blue-600 hover:text-blue-800 mb-4 inline-block">
                    <i class="fas fa-arrow-left mr-2"></i>Quay lại
                </a>
                <h1 class="text-3xl font-bold text-gray-900">${project.name || 'Chi tiết Dự án'}</h1>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h2 class="text-xl font-semibold mb-4">Thông tin dự án</h2>
                <dl class="space-y-3">
                    <div>
                        <dt class="text-sm font-medium text-gray-500">Tên dự án</dt>
                        <dd class="mt-1 text-sm text-gray-900">${project.name || 'N/A'}</dd>
                    </div>
                    <div>
                        <dt class="text-sm font-medium text-gray-500">Mô tả</dt>
                        <dd class="mt-1 text-sm text-gray-900">${project.description || 'N/A'}</dd>
                    </div>
                </dl>
            </div>
        `;
    } catch (error) {
        console.error('Error loading project detail:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải chi tiết dự án</p>';
    }
}

async function loadDecisionDetail(id) {
    try {
        const decision = await apiService.getDecision(id);
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="mb-6">
                <a href="/decisions.html" class="text-blue-600 hover:text-blue-800 mb-4 inline-block">
                    <i class="fas fa-arrow-left mr-2"></i>Quay lại
                </a>
                <h1 class="text-3xl font-bold text-gray-900">Chi tiết Quyết định</h1>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h2 class="text-xl font-semibold mb-4">Thông tin quyết định</h2>
                <dl class="space-y-3">
                    <div>
                        <dt class="text-sm font-medium text-gray-500">Số quyết định</dt>
                        <dd class="mt-1 text-sm text-gray-900">${decision.decisionNumber || 'N/A'}</dd>
                    </div>
                </dl>
            </div>
        `;
    } catch (error) {
        console.error('Error loading decision detail:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải chi tiết quyết định</p>';
    }
}

async function loadAppointmentDetail(id) {
    try {
        // TODO: Add getAppointment API method
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="mb-6">
                <a href="/appointments.html" class="text-blue-600 hover:text-blue-800 mb-4 inline-block">
                    <i class="fas fa-arrow-left mr-2"></i>Quay lại
                </a>
                <h1 class="text-3xl font-bold text-gray-900">Chi tiết Bổ nhiệm</h1>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <p class="text-gray-600">Chi tiết bổ nhiệm sẽ được hiển thị ở đây</p>
            </div>
        `;
    } catch (error) {
        console.error('Error loading appointment detail:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải chi tiết bổ nhiệm</p>';
    }
}

async function loadEmployeeForm(id) {
    try {
        const departments = await apiService.getDepartments();
        let employee = {};
        
        if (id) {
            employee = await apiService.getEmployee(id);
        }
        
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="mb-6">
                <a href="${id ? `/employees/${id}.html` : '/employees.html'}" class="text-blue-600 hover:text-blue-800 mb-4 inline-block">
                    <i class="fas fa-arrow-left mr-2"></i>Quay lại
                </a>
                <h1 class="text-3xl font-bold text-gray-900">${id ? 'Chỉnh sửa' : 'Tạo mới'} Nhân viên</h1>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <form id="employeeForm">
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Họ</label>
                            <input type="text" name="firstName" value="${employee.firstName || ''}" required
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Tên</label>
                            <input type="text" name="lastName" value="${employee.lastName || ''}" required
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Email</label>
                            <input type="email" name="email" value="${employee.email || ''}" required
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Số điện thoại</label>
                            <input type="tel" name="phoneNumber" value="${employee.phoneNumber || ''}" required
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Phòng ban</label>
                            <select name="departmentId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                                <option value="">Chọn phòng ban</option>
                                ${departments.map(dept => `
                                    <option value="${dept.id}" ${employee.departmentId === dept.id ? 'selected' : ''}>${dept.name}</option>
                                `).join('')}
                            </select>
                        </div>
                    </div>
                    <div class="mt-6 flex justify-end gap-4">
                        <a href="${id ? `/employees/${id}.html` : '/employees.html'}" 
                            class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">Hủy</a>
                        <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                            ${id ? 'Cập nhật' : 'Tạo mới'}
                        </button>
                    </div>
                </form>
            </div>
        `;
        
        // Add event listener for form submission
        const form = document.getElementById('employeeForm');
        if (form) {
            form.addEventListener('submit', async (e) => {
                e.preventDefault();
                const formData = new FormData(e.target);
                const data = Object.fromEntries(formData);
                
                try {
                    if (id) {
                        await apiService.put(`/api/employees/${id}`, data);
                    } else {
                        await apiService.post('/api/employees', data);
                    }
                    window.location.href = id ? `/employees/${id}.html` : '/employees.html';
                } catch (error) {
                    console.error('Error saving employee:', error);
                    alert('Lỗi khi lưu nhân viên: ' + (error.message || 'Unknown error'));
                }
            });
        }
    } catch (error) {
        console.error('Error loading employee form:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải form</p>';
    }
}

async function loadDepartmentForm(id) {
    try {
        const departmentsResponse = await apiService.getDepartments();
        const departments = Array.isArray(departmentsResponse) ? departmentsResponse : (departmentsResponse.content || departmentsResponse.data || []);
        
        const employeesResponse = await apiService.getEmployees(0, 1000); // Get all employees for dropdown
        const employees = employeesResponse.content || employeesResponse.data || employeesResponse || [];
        
        let department = {};
        
        if (id) {
            department = await apiService.getDepartment(id);
        }
        
        const content = document.getElementById('content');
        content.innerHTML = `
            <div class="mb-6">
                <a href="${id ? `/departments/${id}.html` : '/departments.html'}" class="text-blue-600 hover:text-blue-800 mb-4 inline-block">
                    <i class="fas fa-arrow-left mr-2"></i>Quay lại
                </a>
                <h1 class="text-3xl font-bold text-gray-900">${id ? 'Chỉnh sửa' : 'Tạo mới'} Phòng ban</h1>
            </div>
            <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <form id="departmentForm">
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Tên phòng ban <span class="text-red-500">*</span></label>
                            <input type="text" name="name" value="${department.name || ''}" required
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Mã phòng</label>
                            <input type="text" name="roomCode" value="${department.roomCode || department.code || ''}"
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                        </div>
                        <div class="md:col-span-2">
                            <label class="block text-sm font-medium text-gray-700 mb-2">Mô tả</label>
                            <textarea name="description" rows="3"
                                class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">${department.description || ''}</textarea>
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Trưởng phòng</label>
                            <select name="managerId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                                <option value="">Chọn trưởng phòng</option>
                                ${(Array.isArray(employees) ? employees : []).map(emp => `
                                    <option value="${emp.id}" ${department.managerId === emp.id ? 'selected' : ''}>
                                        ${emp.fullName || `${emp.firstName || ''} ${emp.lastName || ''}`.trim() || 'N/A'}
                                    </option>
                                `).join('')}
                            </select>
                        </div>
                        <div>
                            <label class="block text-sm font-medium text-gray-700 mb-2">Phòng ban cha</label>
                            <select name="parentId" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500">
                                <option value="">Không có (phòng ban gốc)</option>
                                ${(Array.isArray(departments) ? departments : []).filter(d => d.id !== id).map(dept => `
                                    <option value="${dept.id}" ${department.parentId === dept.id || department.parentDepartmentId === dept.id ? 'selected' : ''}>${dept.name || 'N/A'}</option>
                                `).join('')}
                            </select>
                        </div>
                    </div>
                    <div class="mt-6 flex justify-end gap-4">
                        <a href="${id ? `/departments/${id}.html` : '/departments.html'}" 
                            class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">Hủy</a>
                        <button type="submit" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                            ${id ? 'Cập nhật' : 'Tạo mới'}
                        </button>
                    </div>
                </form>
            </div>
        `;
        
        // Add event listener for form submission
        const form = document.getElementById('departmentForm');
        if (form) {
            form.addEventListener('submit', async (e) => {
                e.preventDefault();
                const formData = new FormData(e.target);
                const data = Object.fromEntries(formData);
                
                // Convert empty strings to null for optional fields
                if (!data.managerId) data.managerId = null;
                if (!data.parentId) data.parentId = null;
                if (!data.roomCode) data.roomCode = null;
                
                try {
                    if (id) {
                        await apiService.updateDepartment(id, data);
                    } else {
                        await apiService.createDepartment(data);
                    }
                    window.location.href = id ? `/departments/${id}.html` : '/departments.html';
                } catch (error) {
                    console.error('Error saving department:', error);
                    alert('Lỗi khi lưu phòng ban: ' + (error.message || 'Unknown error'));
                }
            });
        }
    } catch (error) {
        console.error('Error loading department form:', error);
        document.getElementById('content').innerHTML = '<p class="text-red-600">Lỗi khi tải form</p>';
    }
}
