package com.mindex.challenge.data;

/**
 * The type Reporting structure.
 */
public class ReportingStructure {
    private final Employee employee;  // todo investigate for thread safety.  could deep copy employee
    private final int numberOfReports;

    /**
     * Instantiates a new Reporting structure.
     *
     * @param employee        the employee
     * @param numberOfReports the number of reports
     * @throws IllegalArgumentException the illegal argument exception
     */
    public ReportingStructure(Employee employee, int numberOfReports) throws IllegalArgumentException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee passed was null");
        }
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    /**
     * Gets employee.
     *
     * @return the employee
     */
    public Employee getEmployee() {
        return this.employee;
    }

    /**
     * Gets number of reports.
     *
     * @return the number of reports
     */
    public int getNumberOfReports() {
        return this.numberOfReports;
    }
}
