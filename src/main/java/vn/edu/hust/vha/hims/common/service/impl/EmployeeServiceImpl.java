package vn.edu.hust.vha.hims.common.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.hust.vha.hims.common.entity.Employee;
import vn.edu.hust.vha.hims.common.mapper.EmployeeMapper;
import vn.edu.hust.vha.hims.common.mapper.dto.request.EmployeeCreateDTO;
import vn.edu.hust.vha.hims.common.mapper.dto.response.EmployeeResponseDTO;
import vn.edu.hust.vha.hims.common.repository.EmployeeRepository;
import vn.edu.hust.vha.hims.common.service.EmployeeService;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeCreateDTO dto) {
    	Employee employee = Employee.builder()
    			.firstName(dto.getFirstName())
    			.lastName(dto.getLastName())
    			.gender(dto.getGender())
    			.email(dto.getEmail())
    			.phoneNumber(dto.getPhoneNumber())
    			.maritalStatus(dto.getMaritalStatus())
    			.permanentAddress(dto.getPermanentAddress())
    			.bankAccount(dto.getBankAccount())
    			.bankName(dto.getBankName())
    			.taxCode(dto.getTaxCode())
    			.socialInsuranceNumber(dto.getSocialInsuranceNumber())
    			.hometown(dto.getHometown())
    			.yearJoining(dto.getYearJoining())
    			.build();
    	
    	Employee saved = employeeRepository.save(employee);
    	return employeeMapper.toResponse(saved);
    }
}
