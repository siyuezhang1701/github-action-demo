package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.model.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public List<CompanyResponse> getCompanies() {
        return companyService.getCompanies();
    }

    @GetMapping("/{companyId}")
    public CompanyResponse getCompanyById(@PathVariable Long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody CompanyRequest companyRequest) {
        return companyService.create(companyRequest);
    }

    @DeleteMapping("/{companyId}")
    public void delete(@PathVariable Long companyId) {
        companyService.delete(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public Page<CompanyResponse> getPaginatedAll(@RequestParam(required = false) int page, @RequestParam(required = false) int pageSize) {
        Page<CompanyResponse> companyResponseList = companyService.getAll(page, pageSize);
        return companyResponseList;
    }

    @PutMapping("/{companyId}")
    public CompanyResponse update(@PathVariable Long companyId, @RequestBody CompanyRequest companyRequest) {
        return companyService.update(companyId, companyRequest);
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyId(@PathVariable Long companyId) {
        return companyService.getEmployeesByCompanyId(companyId);
    }
}
