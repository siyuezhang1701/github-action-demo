package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.extension.CompanyMerger;
import com.thoughtworks.springbootemployee.model.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.model.dto.CompanyResponse;
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
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CompanyService(CompanyRepository CompanyRepository) {
        this.companyRepository = CompanyRepository;
    }

    public List<CompanyResponse> getCompanies() {
        return companyRepository.findAll().stream()
                .map(Company -> modelMapper.map(Company, CompanyResponse.class))
                .collect(Collectors.toList());
    }

    public CompanyResponse getCompanyById(Long companyId) {
        Company Company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(companyId));

        CompanyResponse companyResponse = modelMapper.map(Company, CompanyResponse.class);

        return companyResponse;
    }

    public CompanyResponse create(CompanyRequest companyRequest) {
        Company company = modelMapper.map(companyRequest, Company.class);
        Company companyCreated = companyRepository.save(company);
        CompanyResponse companyResponse = modelMapper.map(companyCreated, CompanyResponse.class);
        return companyResponse;
    }

    public void delete(Long companyId) {
        companyRepository.findById(companyId).ifPresent(companyRepository::delete);
    }

    public Page<CompanyResponse> getAll(int page, int pageSize) {
        Page<Company> companies = companyRepository.findAll(PageRequest.of(page, pageSize, Sort.by("id")));
        return companies.map(Company -> modelMapper.map(Company, CompanyResponse.class));
    }

    public CompanyResponse update(Long companyId, CompanyRequest companyRequest) {
        Company targetCompany = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(companyId));
        Company sourceCompany = modelMapper.map(companyRequest, Company.class);
        CompanyMerger.merge(sourceCompany, targetCompany);
        Company updatedCompany = companyRepository.save(targetCompany);
        CompanyResponse CompanyResponse = modelMapper.map(updatedCompany, CompanyResponse.class);
        return CompanyResponse;
    }

    public List<EmployeeResponse> getEmployeesByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CompanyNotFoundException(companyId));

        List<Employee> employees = company.getEmployees();

        List<EmployeeResponse> employeeResponseList = employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponse.class))
                .collect(Collectors.toList());

        return employeeResponseList;
    }
}
