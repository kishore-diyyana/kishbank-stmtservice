package com.kishbank.stmtservice.domain;

import java.util.List;

public class StmtResponse {

    String result;

    List<StmtResponsePayload> errorRecords;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<StmtResponsePayload> getErrorRecords() {
        return errorRecords;
    }

    public void setErrorRecords(List<StmtResponsePayload> errorRecords) {
        this.errorRecords = errorRecords;
    }
}
