package com.mindex.challenge.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Compensation repository.
 */
@Repository
public interface CompensationRepository extends MongoRepository<CompensationDao, String> {
    /**
     * Find by employee id list.
     *
     * @param employeeId the employee id
     * @return the list
     */
    List<CompensationDao> findByEmployeeId(String employeeId);

    /**
     * Find by compensation id compensation dao.
     *
     * @param compensationId the compensation id
     * @return the compensation dao
     */
    CompensationDao findByCompensationId(String compensationId);
}
