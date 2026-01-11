package vn.edu.hust.vha.hims.modules.organization.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.modules.organization.mapper.dto.response.DepartmentDashboardDTO;
import vn.edu.hust.vha.hims.modules.organization.service.DepartmentService;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{id}/dashboard")
    public ResponseEntity<DepartmentDashboardDTO> getDepartmentDashboard(@PathVariable UUID id) {
        DepartmentDashboardDTO dashboard = departmentService.getDepartmentDashboard(id);
        return ResponseEntity.ok(dashboard);
    }
}