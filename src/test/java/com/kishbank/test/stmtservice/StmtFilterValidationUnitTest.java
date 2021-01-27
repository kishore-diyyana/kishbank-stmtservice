package com.kishbank.test.stmtservice;

import com.kishbank.stmtservice.filter.StmtFilter;
import com.kishbank.stmtservice.model.Statement;
import org.junit.Test;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

public class StmtFilterValidationUnitTest {

    /*** Test Duplicate Invalid Amount Unit Test Begins ***/

    @Test
    public void testFilterStatementInvalidAmt(){

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("15"));
        Statement stmt2 = new Statement(12L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt3 = new Statement(13L, "3" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("50"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);
        stmtList.add(stmt3);

        final List<Statement> invalidList = StmtFilter.extractInvalidAmountList(stmtList);
        Assert.notNull(invalidList, "List of stmts must not be null");
        Assert.isInstanceOf(List.class, invalidList);
        then(stmtList.size()).isNotEqualTo(invalidList.size());
        invalidList.forEach(stmt -> {
            Assert.notNull(stmt.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(stmt.getTransactionRef(), "Reference must not be null");
            then(stmt.getTransactionRef()).isEqualTo(13L);
            then(stmt.getAccountNumber()).isEqualTo("3");
        });
    }

    @Test
    public void testFilterStatementValidAmt(){

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("15"));
        Statement stmt2 = new Statement(12L, "2" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));

        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);

        final List<Statement> validEmptyList = StmtFilter.extractInvalidAmountList(stmtList);
        Assert.notNull(validEmptyList, "List of stmts must not be null");
        Assert.isInstanceOf(List.class, validEmptyList);
        //When Balances are correct it returns empty list.
        then(stmtList.size()).isGreaterThan(validEmptyList.size());
 }

    /*** Test Duplicate Transaction Reference Unit Test Begins ***/

    /**
     * This method is used to test Transaction References with Mirror Duplicates
     */
    @Test
    public void testFilterStatementDupTransRefWithMirrorDups() {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"), new BigDecimal("5"),new BigDecimal("10"));
        Statement stmt2 = new Statement(13L, "2" , new BigDecimal("5"),
                new BigDecimal("5"),new BigDecimal("10"));
        /*** Mirror Duplicates - Exactly same of transactionRef 13L **/
        Statement stmt3 = new Statement(13L, "2" , new BigDecimal(5),
                new BigDecimal(5),new BigDecimal(10));
        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);
        stmtList.add(stmt3);
        List<Statement> duplicateList = StmtFilter.removeDuplicateTransRefList(stmtList);

        Assert.notNull(duplicateList, "List of stmts must not be null");
        Assert.isInstanceOf(List.class, duplicateList);
        then(stmtList.size()).isGreaterThan(duplicateList.size());
        duplicateList.forEach(stmt -> {
            Assert.notNull(stmt.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(stmt.getTransactionRef(), "Reference must not be null");
            then(stmt.getTransactionRef()).isEqualTo(13L);
            then(stmt.getAccountNumber()).isEqualTo("2");
        });
    }

    /**
     * This method is used to test Transaction References without Mirror Duplicates
     */
    @Test
    public void testFilterStatementDupTransRefWithoutMirrorDups() {

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"), new BigDecimal("5"),new BigDecimal("10"));
        Statement stmt2 = new Statement(13L, "2" , new BigDecimal("5"),
                new BigDecimal("5"),new BigDecimal("10"));
        /*** Without Mirror Duplicates - accountNumber is different **/
        Statement stmt3 = new Statement(13L, "3" , new BigDecimal(5),
                new BigDecimal(5),new BigDecimal(10));
        List<Statement> stmtList = new ArrayList<>();
        stmtList.add(stmt1);
        stmtList.add(stmt2);
        stmtList.add(stmt3);
        List<Statement> duplicateList = StmtFilter.removeDuplicateTransRefList(stmtList);

        Assert.notNull(duplicateList, "List of stmts must not be null");
        Assert.isInstanceOf(List.class, duplicateList);
        then(stmtList.size()).isGreaterThan(duplicateList.size());
        duplicateList.forEach(stmt -> {
            Assert.notNull(stmt.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(stmt.getTransactionRef(), "Reference must not be null");
            then(stmt.getTransactionRef()).isEqualTo(13L);
        });
    }

    @Test
    public void testFilterStatementNoDupTransRef(){

        Statement stmt1 = new Statement(11L, "1" ,
                new BigDecimal("10"),new BigDecimal("5"), new BigDecimal("5"));
        Statement stmt2 = new Statement(12L, "3" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("5"));

        List<Statement> stmtList = new ArrayList<>();

        stmtList.add(stmt1);
        stmtList.add(stmt2);
        final List<Statement> duplicateList = StmtFilter.removeDuplicateTransRefList(stmtList);
        Assert.notNull(duplicateList, "List of stmts must not be null");
        Assert.isInstanceOf(List.class, duplicateList);
        then(stmtList.size()).isGreaterThan(duplicateList.size());
        // When No Dups list is empt
        then(duplicateList.size()).isEqualTo(0);
    }
}
