package com.mindex.challenge.dao;

import java.util.Date;

/**
 * The type Compensation dao.
 */
public class CompensationDao {
    private final String compensationId;
    private final String employeeId;
    private final float salary;
    private final Date effectiveDate;

    /**
     * Instantiates a new Compensation dao.
     *
     * @param compensationId the compensation id
     * @param employeeId     the employee id
     * @param salary         the salary
     * @param effectiveDate  the effective date
     * @throws IllegalArgumentException the illegal argument exception
     */
    public CompensationDao(String compensationId, String employeeId, float salary, Date effectiveDate) throws IllegalArgumentException {
        if (employeeId == null) {
            throw new IllegalArgumentException("employeeId cannot be null");
        }
        this.compensationId = compensationId;
        this.employeeId = employeeId;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    /**
     * Gets compensation id.
     *
     * @return the compensation id
     */
    public String getCompensationId() { return compensationId; }

    /**
     * Gets salary.
     *
     * @return the salary
     */
    public float getSalary() { return salary; }

    /**
     * Gets employee id.
     *
     * @return the employee id
     */
    public String getEmployeeId() { return employeeId; }

    /**
     * Gets effective date.
     *
     * @return the effective date
     */
    public Date getEffectiveDate() { return effectiveDate; }
}
