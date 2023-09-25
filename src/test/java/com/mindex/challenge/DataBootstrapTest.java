package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationDao;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataBootstrapTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Test
    public void test() {
        Employee employee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("Lennon", employee.getLastName());
        assertEquals("Development Manager", employee.getPosition());
        assertEquals("Engineering", employee.getDepartment());

        CompensationDao compensation = compensationRepository.findByCompensationId("0da342ea-c61b-4b34-bb9f-c638e06db868");
        assertNotNull(compensation);
        assertEquals(compensation.getEmployeeId(), "16a596ae-edd3-4847-99fe-c4518e82c86f");
    }
}