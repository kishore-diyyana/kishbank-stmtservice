package com.kishbank.stmtservice.controllers;

import com.kishbank.stmtservice.domain.StmtResponse;
import com.kishbank.stmtservice.validator.StmtMaxSizeConstraint;
import com.kishbank.stmtservice.model.Statement;
import com.kishbank.stmtservice.service.StmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.*;

/***
 * Implement a REST service which receives the customer statement JSON as a POST data.
 * Perform the below validations :
 * 1. All transaction references should be unique
 * 2. The end balance needs to be validated ( Start Balance +/- Mutation = End Balance)
 *
 * @author Kishore Diyyana
 */
@RestController
@Validated
@RequestMapping(value = "/api/statement-service", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StmtController {

	@Autowired
	private StmtService service;

	/**
	 * Test Statement Service status is Healthy or not
	 * @return String
	 */
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public String status() {
		return "Statement Service is up and healthy..."
				+ LocalDate.now();
	}

	/**
	 * Receives the customer statement JSON as a POST data, Perform the below validations.
	 * | Http Status Code  | Condition
	 * |---                |---
	 * | 200               | When there are no duplicate reference and correct end balance
	 * | 200               |When there are duplicate reference and correct balance
	 * | 200               |When there are no duplicate reference and In correct balance
	 * | 200               |When there are duplicate reference and In correct balance
	 * | 400               |Error during parsing JSON
	 * | 500               |Any other situation
	 * @param stmtsRequest
	 * @return ResponseEntity of stmtResponse
	 */
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public ResponseEntity<StmtResponse> validate(@RequestBody @NotEmpty(message = "No Statement(s) data found in request body.")
													 @StmtMaxSizeConstraint List<@Valid Statement> stmtsRequest) {
		return service.process(stmtsRequest);
	}
}
