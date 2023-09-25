package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeUrl;
    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreate() {
        Date today = new Date();
        // Full Compensation object
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        testEmployee.setEmployeeId("empId-1111");

        Compensation testCompensation = new Compensation("test-id", testEmployee, 10005.2f, today);
        assertEquals(testCompensation.getCompensationId(), "test-id");
        assertEquals(testCompensation.getEmployee().getEmployeeId(), "empId-1111");
        assertEquals(testCompensation.getSalary(), 10005.2f, 0.0001);
        assertEquals(today, testCompensation.getEffectiveDate());

        // Null compensationId
        Compensation testCompensation1 = new Compensation(null, testEmployee, 20005.2f, today);
        assertNull(testCompensation1.getCompensationId());
        assertEquals(testCompensation1.getEmployee().getEmployeeId(), "empId-1111");
        assertEquals(testCompensation1.getSalary(), 20005.2f, 0.0001);
        assertEquals(today, testCompensation1.getEffectiveDate());

        // Null employee
        assertThrows(IllegalArgumentException.class, () -> new Compensation(null, null, 20005.2f, today));
    }

    @Test
    public void testRead() {
        // Test from data loaded from file
        List<Compensation> result = compensationService.read("b7839309-3348-463b-a7e3-5de1c168beb3");  //employeeId
        assertEquals(result.size(), 1);

        result = compensationService.read("16a596ae-edd3-4847-99fe-c4518e82c86f");  //employeeId
        assertEquals(result.size(), 2);

        result = compensationService.read("bad-id");  //employeeId
        assertEquals(result.size(), 0);
    }

    @Test
    public void testCreateRead() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        // Create employee
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assert createdEmployee != null;
        assertNotNull(createdEmployee.getEmployeeId());

        Date today = new Date();
        Compensation testCompensation = new Compensation(null, createdEmployee, 30005.2f, today);
        assertNull(testCompensation.getCompensationId());   // does not create a UUID with this constructor

        // Create compensation
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assert createdCompensation != null;
        assertNotNull(createdCompensation.getCompensationId());
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), createdEmployee.getEmployeeId());
        assertEquals(createdCompensation.getSalary(), 30005.2f, 0.0001);
        assertNotEquals(createdCompensation.getCompensationId(), createdCompensation.getEmployee().getEmployeeId());  // make sure the ids are different

        // Read compensation
        Compensation[] readCompensations = restTemplate.getForEntity(compensationIdUrl, Compensation[].class,
                createdCompensation.getEmployee().getEmployeeId()).getBody();
        assert readCompensations != null;
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), readCompensations[0].getEmployee().getEmployeeId());
        assertEquals(createdCompensation.getSalary(), readCompensations[0].getSalary(), 0.0001);
    }
}