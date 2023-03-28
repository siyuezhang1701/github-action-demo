package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public List<EmployeeResponse> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse getEmployeeById(@PathVariable Long employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.create(employeeRequest);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Long employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<EmployeeResponse> getPaginatedAll( @RequestParam(required = false) int page, @RequestParam(required = false) int pageSize
    ) {
        Page<EmployeeResponse> employeeResponseList = employeeService.getAll(page, pageSize);
        return employeeResponseList;
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable Long employeeId, @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.update(employeeId, employeeRequest);
    }
}
