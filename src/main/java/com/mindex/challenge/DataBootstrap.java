package com.mindex.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationDao;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Component
public class DataBootstrap {
    private static final String DATASTORE_LOCATION = "/static/employee_database.json";
    private static final String COMPENSATION_DATASTORE_LOCATION = "/static/compensation_database.json";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(DATASTORE_LOCATION);

        Employee[] employees = null;

        try {
            employees = objectMapper.readValue(inputStream, Employee[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Employee employee : employees) {
            employeeRepository.insert(employee);
        }

        loadCompensation();
    }

    private void loadCompensation() {
        InputStream inputStream = this.getClass().getResourceAsStream(COMPENSATION_DATASTORE_LOCATION);

        CompensationDao[] compensations = null;

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            objectMapper.setDateFormat(df);
            compensations = objectMapper.readValue(inputStream, CompensationDao[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (CompensationDao compensation : compensations) {
            Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployeeId());
            if (employee == null) {
                throw new IllegalArgumentException("employeeId loaded was not found: " + compensation.getEmployeeId());
            }
            compensationRepository.insert(new CompensationDao(compensation.getCompensationId(), compensation.getEmployeeId(),
                    compensation.getSalary(), compensation.getEffectiveDate()));
        }
    }
}
