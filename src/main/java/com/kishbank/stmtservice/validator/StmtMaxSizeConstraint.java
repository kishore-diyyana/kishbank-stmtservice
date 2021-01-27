package com.kishbank.stmtservice.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This class is to validate input Statement list cannot contain
 * more than specified size defined application.properties file (app.inputfeedSize).
 *
 * @author Kishore Diyyana
 */
@Constraint(validatedBy = StmtMaxSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StmtMaxSizeConstraint {

    String message() default "The input Statement list cannot contain more than configured size in app";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
