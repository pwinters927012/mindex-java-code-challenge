package com.mindex.challenge.service.impl;

import com.mindex.challenge.DataBootstrap;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

	private String reportingStructureIdUrl;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ReportingStructureService reportingStructureService;

	@Before
	public void setup() {
		reportingStructureIdUrl = "http://localhost:" + port + "/reporting-structure/{id}";
	}

	@Test
	public void testCompute() {
		assertEquals(reportingStructureService.compute("16a596ae-edd3-4847-99fe-c4518e82c86f").getNumberOfReports(), 4);  // JohnL
		assertEquals(reportingStructureService.compute("b7839309-3348-463b-a7e3-5de1c168beb3").getNumberOfReports(), 0);  // PaulM
	}

	@Test
	public void testRead() {
		String employeeId;
		ReportingStructure reportingStructure;

		employeeId = "b7839309-3348-463b-a7e3-5de1c168beb3";  //Paul M
		reportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, employeeId).getBody();
        assert reportingStructure != null;
        assertEquals(reportingStructure.getNumberOfReports(), 0);

		employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";  //John L
		reportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, employeeId).getBody();
		assert reportingStructure != null;
		assertEquals(reportingStructure.getNumberOfReports(), 4);

		employeeId = "03aa1462-ffa9-4978-901b-7c001562cf6f";  //Ringo S
		reportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, employeeId).getBody();
		assert reportingStructure != null;
		assertEquals(reportingStructure.getNumberOfReports(), 2);

		employeeId = "62c1084e-6e34-4630-93fd-9153afb65309";  //Pete B
		reportingStructure = restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, employeeId).getBody();
		assert reportingStructure != null;
		assertEquals(reportingStructure.getNumberOfReports(), 0);

		// empty employeeId
		assertThrows(RestClientException.class, () -> restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, ""));

		// Bad employeeId
		assertThrows(RestClientException.class, () -> restTemplate.getForEntity(reportingStructureIdUrl, ReportingStructure.class, "12939-BAD-NEWS-NO_THANKS-000"));
	}
}

