package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

/**
 * The interface Reporting structure service.
 */
public interface ReportingStructureService {
    /**
     * Compute reporting structure.
     *
     * @param id the id
     * @return the reporting structure
     * @throws IllegalArgumentException the illegal argument exception
     */
    ReportingStructure compute(String id) throws IllegalArgumentException;
}
