package com.kishbank.stmtservice.validator;

import com.kishbank.stmtservice.filter.StmtFilter;
import com.kishbank.stmtservice.model.Statement;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class references to business validations to find duplicates and invalid amounts.
 *
 * @author Kishore Diyyana
 */
@Component
public class StmtValidator {

    public List<Statement> validateStmtTransRef(List<Statement> stmts) {
        List<Statement> dupRefs = StmtFilter.removeDuplicateTransRefList(stmts);
        return dupRefs;
    }

    public List<Statement> validateStmtBalance(List<Statement> stmts) {
        return StmtFilter.extractInvalidAmountList(stmts);
    }

}
