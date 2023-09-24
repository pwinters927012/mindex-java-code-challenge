package com.mindex.challenge.controller;

import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Reporting structure controller.
 */
@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    /**
     * Read response entity.
     *
     * @param id the id
     * @return the response entity
     * @throws EmployeeNotFoundException the employee notfound exception
     */
    @GetMapping("/reporting-structure/{id}")
    public ResponseEntity<Object> read(@PathVariable String id) throws EmployeeNotFoundException {
        LOG.debug("Reporting structure get request for id [{}]", id);
        try {
            return new ResponseEntity<>(reportingStructureService.compute(id), HttpStatus.OK);
        } catch (IllegalArgumentException error) {
            // this error is expected when the user enters an id that is not found
            LOG.debug("Reporting structure error= {0}", error);
            throw new EmployeeNotFoundException();
        } catch (RuntimeException error) {
            // this is a broad exception indicating something unexpected occurred
            LOG.error("Reporting structure General Exception={0}", error);
            throw new EmployeeNotFoundException();
        }
    }

    /**
     * Exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<Object> exception(EmployeeNotFoundException exception) {
        return new ResponseEntity<>("Employee not found", HttpStatus.NOT_FOUND);
    }
}
