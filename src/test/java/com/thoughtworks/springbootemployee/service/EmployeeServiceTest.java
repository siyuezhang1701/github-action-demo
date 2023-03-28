package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.entity.Employee;
import com.thoughtworks.springbootemployee.model.entity.Gender;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private ModelMapper modelMapper;

    private EmployeeResponse employeeResponse;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeResponse = new EmployeeResponse(1L, "Test", 18, Gender.Male, 1000, 1L);
        employee = new Employee(1L, "Test", 18, Gender.Male, 1000);
        when(modelMapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(employeeResponse);
    }

    @Test
    public void should_returnEmployees_when_getEmployees() {
        //given
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        //when
        List<EmployeeResponse> actual = employeeService.getEmployees();

        //then
        assertEquals(employeeResponse, actual.stream().findFirst().get());
    }

    @Test
    public void should_returnEmployee_when_getEmployeeById() {
        //given
        when(employeeRepository.findById(employeeResponse.getId())).thenReturn(Optional.ofNullable(employee));

        //when
        EmployeeResponse actual = employeeService.getEmployeeById(employeeResponse.getId());

        //then
        assertEquals(employeeResponse, actual);
    }
}
