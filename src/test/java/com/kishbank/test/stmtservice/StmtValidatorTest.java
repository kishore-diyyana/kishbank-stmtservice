package com.kishbank.test.stmtservice;

import com.kishbank.stmtservice.StatementServiceApplication;
import com.kishbank.stmtservice.model.Statement;
import com.kishbank.stmtservice.validator.StmtValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StatementServiceApplication.class)
public class StmtValidatorTest {

    @Autowired
    StmtValidator validator;

    @Test
    public void testValidatorValidateStmtTransRef(){
        final List<Statement> stmts = validator.validateStmtTransRef(getExpectedList());
        Assert.notNull(stmts, "List of stmts must not be null");
        Assert.isInstanceOf(List.class, stmts);
        then(stmts.size()).isGreaterThan(0);
        stmts.forEach(stmt -> {
            Assert.notNull(stmt.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(stmt.getTransactionRef(), "Reference must not be null");
            then(stmt.getTransactionRef()).isEqualTo(12l);
        });
    }

    @Test
    public void testValidatorValidateStmtBalance(){
        final List<Statement> stmts = validator.validateStmtBalance(getExpectedList());
        Assert.notNull(stmts, "List of stmts must not be null");
        Assert.isInstanceOf(List.class, stmts);
        then(stmts.size()).isGreaterThan(0);
        stmts.forEach(stmt -> {
            Assert.notNull(stmt.getAccountNumber(), "AccountNumber must not be null");
            Assert.notNull(stmt.getTransactionRef(), "Reference must not be null");
            then(stmt.getTransactionRef()).isEqualTo(12l);
        });
    }

    private List<Statement> getExpectedList() {
        Statement stmt1 = new Statement(12L, "3" ,
                new BigDecimal("10"), new BigDecimal("5"), new BigDecimal("40"));
        Statement stmt2 = new Statement(12L, "4" ,
                new BigDecimal("10"),new BigDecimal("5"),new BigDecimal("10"));
        List<Statement> dupRefList = new ArrayList<>();
        dupRefList.add(stmt1);
        dupRefList.add(stmt2);
        return dupRefList;
    }
}
