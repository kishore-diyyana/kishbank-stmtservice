package com.kishbank.test.stmtservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kishbank.stmtservice.StatementServiceApplication;
import com.kishbank.stmtservice.model.Statement;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Integration tests for Statement service application.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = StatementServiceApplication.class)
@TestPropertySource(properties = {"management.port=0"})
public class StmtIntegrationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    /**
     * Test Case: DUPLICATE_REFERENCE without mirror input
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_DupRef() throws Exception {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt2 = new Statement(11L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);
        StringBuffer expected = new StringBuffer("{result=DUPLICATE_REFERENCE, ").append(
                "errorRecords=[{reference=11, accountNumber=1}, ").append(
                "{reference=11, accountNumber=2}]}");
        testRestService(stmtList, expected);
    }

    /**
     * Test Case: DUPLICATE_REFERENCE with mirror input
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_DupRefMirror() throws Exception {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt2 = new Statement(11L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        //*******Mirror Input of transactionRef 11 (Exact Same of all attributes) ******************
        Statement stmt3 = new Statement(11L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);
        stmtList.add(stmt3);

        StringBuffer expected = new StringBuffer("{result=DUPLICATE_REFERENCE, ").append(
                "errorRecords=[{reference=11, accountNumber=1}, ").append(
                "{reference=11, accountNumber=2}]}");

         testRestService(stmtList, expected);
    }

    /**
     * Test Case: INCORRECT_END_BALANCE - Mutation Plus
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_InvalidAmtPlus() throws Exception {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("20"),new BigDecimal("10"), new BigDecimal("40"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        StringBuffer expected = new StringBuffer("{result=INCORRECT_END_BALANCE, ").append(
                "errorRecords=[{reference=11, accountNumber=1}]}");

        testRestService(stmtList, expected);
    }

    /**
     * Test Case: INCORRECT_END_BALANCE - Mutation Minus
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_InvalidAmtMinus() throws Exception {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("20"),new BigDecimal("10"), new BigDecimal("5"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);

        final StringBuffer expected = new StringBuffer("{result=INCORRECT_END_BALANCE, ").append(
                "errorRecords=[{reference=11, accountNumber=1}]}");
        testRestService(stmtList, expected);
    }

    /**
     * Test Case: SUCCESSFUL - Valid Amt Mutation Minus
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_ValidAmtMinus() throws Exception {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("20"),new BigDecimal("10"), new BigDecimal("10"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);

        final StringBuffer expected = new StringBuffer("{result=SUCCESSFUL, ").append(
                "errorRecords=[]}");
        testRestService(stmtList, expected);
    }

    /**
     * Test Case: SUCCESSFUL - Valid Amt Mutation Plus
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_ValidAmtPlus() throws Exception {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("20"),new BigDecimal("10"), new BigDecimal("30"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);

        final StringBuffer expected = new StringBuffer("{result=SUCCESSFUL, ").append(
                "errorRecords=[]}");
        testRestService(stmtList, expected);
    }

    /**
     * Test Case: DUPLICATE_REFERENCE_INCORRECT_END_BALANCE
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_DupsAndInvalidAmt() throws Exception {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("15"));
        Statement stmt2 = new Statement(12L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt3 = new Statement(12L, "3" ,
                new BigDecimal("7"),new BigDecimal("7"), new BigDecimal("7"));
        Statement stmt4 = new Statement(18L, "6" ,
                new BigDecimal("40"),new BigDecimal("10"), new BigDecimal("100"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);
        stmtList.add(stmt3);
        stmtList.add(stmt4);

        final StringBuffer expected = new StringBuffer("{result=DUPLICATE_REFERENCE_INCORRECT_END_BALANCE, ").append(
                "errorRecords=[{reference=12, accountNumber=2, errType=DUPLICATE_REFERENCE}, {reference=12, accountNumber=3, errType=DUPLICATE_REFERENCE}, " +
                        "{reference=12, accountNumber=3, errType=INCORRECT_END_BALANCE}, {reference=18, accountNumber=6, errType=INCORRECT_END_BALANCE}]}");
        testRestService(stmtList, expected);
    }

    /**
     * Test Case: SUCCESSFUL - Valid Amt and No Dups
     */
    @Test
    public void shouldReturn200WhenSendingProcessRequestToController_ValidAmtAndNoDups() throws Exception {

        Statement stmt1 = new Statement(12L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt2 = new Statement(13L, "3" ,
                new BigDecimal("17"),new BigDecimal("7"), new BigDecimal("10"));
        Statement stmt3 = new Statement(14L, "3" ,
                new BigDecimal("17"),new BigDecimal("7"), new BigDecimal("24"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);
        stmtList.add(stmt3);

        final StringBuffer expected = new StringBuffer("{result=SUCCESSFUL, ").append(
                "errorRecords=[]}");
        testRestService(stmtList, expected);
    }

    /**
     * This is common test method to call Rest Service.
     * @param stmtList
     * @param expected
     * @throws Exception
     */
    private void testRestService(List<Statement> stmtList, StringBuffer expected) throws Exception {
        ResponseEntity<Object> entity = this.testRestTemplate.postForEntity(
                "/api/statement-service/validate", stmtList, Object.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals(expected.toString(), entity.getBody().toString(), false);
    }

    @Test
    public void shouldReturn200WhenSendingStatusRequestToController() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<String> entity = this.testRestTemplate.getForEntity(
                "/api/statement-service/status", String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}