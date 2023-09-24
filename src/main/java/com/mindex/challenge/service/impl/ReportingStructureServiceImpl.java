package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * The type Reporting structure service.
 */
@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;  // todo investigate thread safety

    @Autowired
    private EmployeeService employeeService;    // todo investigate thread safety

    @Override
    public ReportingStructure compute(String id) throws IllegalArgumentException {
        LOG.debug("Reporting structure compute for employee with id [{}]", id);
        if (id == null) {
            LOG.debug("reporting structure compute for employee with id [{}] was null", id);
            throw new IllegalArgumentException("id passed was null");
        } else {
            try {
                Employee employee = employeeService.read(id);
                return new ReportingStructure(employee, getDirectReportCount(employee.getEmployeeId()));
            }
            catch (RuntimeException error) {
                LOG.debug("reporting structure compute for employee with id [{}] was not found", id);
                throw new IllegalArgumentException("id passed returned no result");
            }
        }
    }

    private int getDirectReportCount(String id) throws IllegalArgumentException {
        Employee employee = employeeService.read(id);
        if (employee == null) {
            throw new IllegalArgumentException(String.format("id [%s] was not found", id));
        }
        if (employee.getDirectReports().isEmpty()) {
            return 0;
        }
        else {
            int directReportSum = 0;
            for (Employee item : employee.getDirectReports()) {
                directReportSum += getDirectReportCount(item.getEmployeeId());
            }
            return employee.getDirectReports().size() + directReportSum;
        }
    }
}
