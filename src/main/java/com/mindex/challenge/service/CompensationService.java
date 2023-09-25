package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.util.List;

/**
 * The interface Compensation service.
 */
public interface CompensationService {
    /**
     * Create compensation.
     *
     * @param compensation the compensation
     * @return the compensation
     */
    Compensation create(Compensation compensation);

    /**
     * Read list.
     *
     * @param id the id
     * @return the list
     */
    List<Compensation> read(String id);
}
