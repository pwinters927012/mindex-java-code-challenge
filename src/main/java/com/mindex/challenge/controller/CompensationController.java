package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Compensation controller.
 */
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /**
     * Create compensation.
     *
     * @param compensation the compensation
     * @return the compensation
     */
    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);
        try {
            return compensationService.create(compensation);
        } catch (RuntimeException error) {
            // this is a broad exception indicating something unexpected occurred
            // log and rethrow
            LOG.error("Create Compensation General Exception={0}", error);
            throw error;
        }
    }

    /**
     * Read list.
     *
     * @param id the id
     * @return the list
     */
    @GetMapping("/compensation/{id}")
    public List<Compensation> read(@PathVariable String id) {
        LOG.debug("Received read compensation request for employee id [{}]", id);
        try {
            return compensationService.read(id);
        } catch (RuntimeException error) {
            // this is a broad exception indicating something unexpected occurred
            // log and rethrow
            LOG.error("Read Compensation General Exception={0}", error);
            throw error;
        }
    }
}
