package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationDao;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type Compensation service.
 */
@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        // todo get an id on insert, but for now this will work
        String id = (compensation.getCompensationId() == null) ? UUID.randomUUID().toString() : compensation.getCompensationId();
        CompensationDao compensationDao = compensationRepository.insert(new CompensationDao(id, compensation.getEmployee().getEmployeeId(),
                compensation.getSalary(), compensation.getEffectiveDate()));
        return new Compensation(compensationDao.getCompensationId(), compensation.getEmployee(), compensationDao.getSalary(),
                compensationDao.getEffectiveDate());
    }

    @Override
    public List<Compensation> read(String id) {
        LOG.debug("Compensation read with id [{}]", id);
        List<Compensation> resultCompensations = new ArrayList<>();
        List<CompensationDao> compensations = compensationRepository.findByEmployeeId(id);
        for (CompensationDao compensation : compensations) {
            Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployeeId());
            if (employee == null) {
                throw new IllegalArgumentException("employee was not found");
            }
            resultCompensations.add(new Compensation(compensation.getCompensationId(), employee,
                    compensation.getSalary(), compensation.getEffectiveDate()));
        }
        return resultCompensations;
    }
}
