package com.thoughtworks.springbootemployee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springbootemployee.model.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.model.entity.Gender;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    private EmployeeResponse employeeResponse;

    @BeforeEach
    public void setUp() {
        employeeResponse = new EmployeeResponse(1L, "Test", 18, Gender.Male, 1000, 1L);
    }

    @Test
    public void should_getEmployees_when_requestEmployeesApi() throws Exception {
        //given
        List<EmployeeResponse> employees = Arrays.asList(employeeResponse);
        when(service.getEmployees()).thenReturn(employees);
        String expected = objectMapper.writeValueAsString(employees);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/employees")).andReturn().getResponse();

        //then
        assertEquals(200, response.getStatus());
        assertEquals(expected, response.getContentAsString());
    }

    @Test
    public void should_getEmployee_when_requestEmployeeIdApi() throws Exception {
        //given
        when(service.getEmployeeById(employeeResponse.getId())).thenReturn(employeeResponse);
        String expected = objectMapper.writeValueAsString(employeeResponse);

        //when
        MockHttpServletResponse response = mockMvc.perform(get(String.format("/employees/%d", employeeResponse.getId()))).andReturn().getResponse();

        //then
        assertEquals(200, response.getStatus());
        assertEquals(expected, response.getContentAsString());
    }

    @Test
    public void should_getCreatedEmployee_when_requestCreateEmployeeApi() throws Exception {
        //given
        when(service.create(Mockito.any())).thenReturn(employeeResponse);

        //when
        MockHttpServletResponse response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(new EmployeeRequest()))).andReturn().getResponse();

        //then
        assertEquals(201, response.getStatus());
        assertEquals(objectMapper.writeValueAsString(employeeResponse), response.getContentAsString());
    }

    @Test
    public void should_getDeleteEmployee_when_requestDeleteEmployeeByIdApi() throws Exception {
        //given
        Long employeeId = 1L;
        doNothing().when(service).delete(employeeId);

        //when
        MockHttpServletResponse response = mockMvc.perform(delete(String.format("/employees/%d", employeeId))).andReturn().getResponse();

        //then
        assertEquals(200, response.getStatus());
    }
}
