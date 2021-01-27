package com.kishbank.stmtservice.validator;

import com.kishbank.stmtservice.model.Statement;
import com.kishbank.stmtservice.props.StmtProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * This class is to validate input Statement list cannot contain
 * more than specified size defined application.properties file (inputFeedSize).
 *
 * @author Kishore Diyyana
 */
public class StmtMaxSizeConstraintValidator implements ConstraintValidator<StmtMaxSizeConstraint, List<Statement>> {

    @Autowired
    StmtProperties props;

    @Override
    public boolean isValid(List<Statement> statements, ConstraintValidatorContext constraintValidatorContext) {
        return statements.size() <= props.getInputFeedSize();
    }
}