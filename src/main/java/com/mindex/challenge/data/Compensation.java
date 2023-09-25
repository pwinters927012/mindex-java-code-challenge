package com.mindex.challenge.data;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * The type Compensation.
 */
public final class Compensation {
    private final String compensationId;
    private final float salary;
    private final Date effectiveDate;
    private final Employee employee;

    /**
     * Instantiates a new Compensation.
     *
     * @param compensationId the compensation id
     * @param employee       the employee
     * @param salary         the salary
     * @param effectiveDate  the effective date
     * @throws IllegalArgumentException the illegal argument exception
     */
    @Autowired
    public Compensation(String compensationId, Employee employee, float salary, Date effectiveDate) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("employee cannot be null");
        }
        if (employee.getEmployeeId() == null) {
            throw new IllegalArgumentException("employeeId cannot be null");
        }
        this.compensationId = compensationId;
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets compensation id.
     *
     * @return the compensation id
     */
    public String getCompensationId() { return this.compensationId; }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public float getSalary() { return this.salary; }

    /**
     * Gets effective date.
     *
     * @return the effective date
     */
    public Date getEffectiveDate() { return this.effectiveDate; }

    /**
     * Gets employee.
     *
     * @return the employee
     */
    public Employee getEmployee() { return this.employee; }
}
