package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.entity.Employee;
import com.thoughtworks.springbootemployee.model.entity.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Test
    public void should_returnEmployee_when_getEmployeeById(){
        //given
        Employee employee =  new Employee(1L, "Angie", 18, Gender.Female, 10000);

        employeeRepository.save(employee);

        //when
        Employee actual = employeeRepository.findById(employee.getId()).get();

        //then
        assertEquals(employee.getId(), actual.getId());
    }
}
