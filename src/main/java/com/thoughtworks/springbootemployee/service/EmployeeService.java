package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.extension.EmployeeMerger;
import com.thoughtworks.springbootemployee.model.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.entity.Company;
import com.thoughtworks.springbootemployee.model.entity.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeResponse> getEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);

        return employeeResponse;
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        Employee employee = modelMapper.map(employeeRequest, Employee.class);

        setEmployeeCompanyId(employeeRequest.getCompanyId(), employee);

        Employee employeeCreated = employeeRepository.save(employee);
        EmployeeResponse employeeResponse = modelMapper.map(employeeCreated, EmployeeResponse.class);
        return employeeResponse;
    }

    public void delete(Long employeeId) {
        employeeRepository.findById(employeeId).ifPresent(employeeRepository::delete);
    }

    public Page<EmployeeResponse> getAll(int page, int pageSize) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id")));
        return employees.map(employee -> modelMapper.map(employee, EmployeeResponse.class));
    }

    public EmployeeResponse update(Long employeeId, EmployeeRequest employeeRequest) {
        Employee targetEmployee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        Employee sourceEmployee = modelMapper.map(employeeRequest, Employee.class);
        EmployeeMerger.merge(sourceEmployee, targetEmployee);

        setEmployeeCompanyId(employeeRequest.getCompanyId(), targetEmployee);

        Employee updatedEmployee = employeeRepository.save(targetEmployee);
        EmployeeResponse employeeResponse = modelMapper.map(updatedEmployee, EmployeeResponse.class);
        return employeeResponse;
    }

    private void setEmployeeCompanyId(Long companyId, Employee targetEmployee) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(companyId));
        targetEmployee.setCompanyId(company.getId());
    }
}
