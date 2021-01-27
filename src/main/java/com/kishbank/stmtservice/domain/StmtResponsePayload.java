package com.kishbank.stmtservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kishbank.stmtservice.constants.StmtConstant;

/**
 * This class is to hold Error Records
 *
 * @author Kishore Diyyana
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StmtResponsePayload {
    Long reference;
    String accountNumber;
    StmtConstant errType;

    public StmtResponsePayload(Long reference, String accountNumber, StmtConstant errType) {
        this.reference = reference;
        this.accountNumber = accountNumber;
        this.errType = errType;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StmtConstant getErrType() {
        return errType;
    }

}
