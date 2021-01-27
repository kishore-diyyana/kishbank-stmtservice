package com.kishbank.test.stmtservice;

import com.kishbank.stmtservice.constants.StmtConstant;
import com.kishbank.stmtservice.domain.StmtResponse;
import com.kishbank.stmtservice.model.Statement;
import com.kishbank.stmtservice.util.StmtBuilder;
import org.assertj.core.api.BDDSoftAssertions;
import org.junit.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class StmtBuilderUnitTest {

    BDDSoftAssertions softly = new BDDSoftAssertions();

    @Test
    public void testBuildResponseBody(){
        final StmtResponse response = StmtBuilder.buildResponseBody(StmtConstant.SUCCESSFUL.toString());
        Assert.notNull(response, "Response must not be null");
        Assert.isInstanceOf(StmtResponse.class, response);
        then(StmtConstant.SUCCESSFUL.toString()).isEqualTo(response.getResult());
        Assert.notNull(response.getErrorRecords(), "Response Err Records must not be null");
        then(response.getErrorRecords().size()).isZero();
   }

    @Test
    public void testBuildStmtResponseBody_DupsRefAndIncorrectBalace() {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt2 = new Statement(11L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt3 = new Statement(12L, "3" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("40"));
        Statement stmt4 = new Statement(13L, "4" ,
                new BigDecimal("10"),new BigDecimal("5"),new BigDecimal("10"));

        List<Statement> dupList = new ArrayList<>();
        List<Statement> amountInvalidList = new ArrayList<>();

        dupList.add(stmt1);
        dupList.add(stmt2);
        amountInvalidList.add(stmt3);
        amountInvalidList.add(stmt4);

        final StmtResponse response = StmtBuilder.buildStmtResponseBody(dupList,
                            amountInvalidList, StmtConstant.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
        Assert.notNull(response, "Response must not be null");
        Assert.isInstanceOf(StmtResponse.class, response);
        then(StmtConstant.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString()).isEqualTo(response.getResult());
        Assert.notNull(response.getErrorRecords(), "Response Err Records must not be null");
        then(response.getErrorRecords().size()).isGreaterThan(0);

        response.getErrorRecords().forEach(err -> {
            Assert.notNull(err.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(err.getReference(), "Reference must not be null");
            Assert.notNull(err.getErrType(), "Error Type must not be null");
            softly.then(err.getReference()).isEqualTo(11L).isEqualTo(StmtConstant.DUPLICATE_REFERENCE);
            softly.then(err.getReference()).isEqualTo(12L).isEqualTo(StmtConstant.INCORRECT_END_BALANCE);
        });
    }

    @Test
    public void testBuildStmtResponseBody_IncorrectBalanceOnly(){
        Statement stmt1 = new Statement(12L, "3" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("40"));
        Statement stmt2 = new Statement(13L, "4" ,
                new BigDecimal("10"),new BigDecimal("5"),new BigDecimal("10"));
        List<Statement> amountInvalidList = new ArrayList<>();
        amountInvalidList.add(stmt1);
        amountInvalidList.add(stmt2);

        final StmtResponse response = StmtBuilder.buildStmtResponseBody(amountInvalidList,
                amountInvalidList, StmtConstant.INCORRECT_END_BALANCE);
        Assert.notNull(response, "Response must not be null");
        Assert.isInstanceOf(StmtResponse.class, response);
        then(StmtConstant.INCORRECT_END_BALANCE.toString()).isEqualTo(response.getResult());
        Assert.notNull(response.getErrorRecords(), "Response Err Records must not be null");
        then(response.getErrorRecords().size()).isGreaterThan(0);
        response.getErrorRecords().forEach(err -> {
            Assert.notNull(err.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(err.getReference(), "Reference must not be null");
            softly.then(err.getReference()).isEqualTo(11L);
            softly.then(err.getReference()).isEqualTo(13L);
        });
    }

    @Test
    public void testBuildStmtResponseBody_DupRefOnly(){
        Statement stmt1 = new Statement(12L, "3" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("40"));
        Statement stmt2 = new Statement(12L, "4" ,
                new BigDecimal("10"),new BigDecimal("5"),new BigDecimal("10"));
        Statement stmt3 = new Statement(13L, "4" ,
                new BigDecimal("10"),new BigDecimal("5"),new BigDecimal("10"));
        List<Statement> amountInvalidList = new ArrayList<>();
        amountInvalidList.add(stmt1);
        amountInvalidList.add(stmt2);
        amountInvalidList.add(stmt3);

        final StmtResponse response = StmtBuilder.buildStmtResponseBody(amountInvalidList,
                amountInvalidList, StmtConstant.DUPLICATE_REFERENCE);
        Assert.notNull(response, "Response must not be null");
        Assert.isInstanceOf(StmtResponse.class, response);
        then(StmtConstant.DUPLICATE_REFERENCE.toString()).isEqualTo(response.getResult());
        Assert.notNull(response.getErrorRecords(), "Response Err Records must not be null");
        then(response.getErrorRecords().size()).isGreaterThan(0);
        response.getErrorRecords().forEach(err -> {
            Assert.notNull(err.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(err.getReference(), "Reference must not be null");
            softly.then(err.getReference()).isEqualTo(11L);
            softly.then(err.getReference()).isEqualTo(12L);
            softly.then(err.getReference()).isEqualTo(13L);
        });
    }

}