package com.kishbank.stmtservice.util;

import com.kishbank.stmtservice.constants.StmtConstant;
import com.kishbank.stmtservice.domain.StmtResponsePayload;
import com.kishbank.stmtservice.domain.StmtResponse;
import com.kishbank.stmtservice.model.Statement;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is to build response service
 * @author Kishore Diyyana
 */
public final class StmtBuilder {

    public static StmtResponse buildResponseBody(final String requestType) {
        final StmtResponse response = new StmtResponse();
        response.setResult(requestType.toString());
        response.setErrorRecords(new ArrayList<>());
        return response;
    }

    public static StmtResponse buildStmtResponseBody(List<Statement> dupRefs,
                                                            List<Statement> invalidAmts,
                                                            final StmtConstant requestType) {
        final StmtResponse response = buildResponseBody(String.valueOf(requestType));
        List<StmtResponsePayload> errRecords = new ArrayList<>();

        if(StmtConstant.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.equals(requestType) &&
                !CollectionUtils.isEmpty(dupRefs) && !CollectionUtils.isEmpty(invalidAmts)) {
           dupRefs.forEach(stmt -> errRecords.add(new StmtResponsePayload(stmt.getTransactionRef(),
                    stmt.getAccountNumber(), StmtConstant.DUPLICATE_REFERENCE)));
            invalidAmts.forEach(stmt -> errRecords.add(new StmtResponsePayload(stmt.getTransactionRef(),
                    stmt.getAccountNumber(), StmtConstant.INCORRECT_END_BALANCE)));
        } else if(StmtConstant.DUPLICATE_REFERENCE.equals(requestType) &&
                    !CollectionUtils.isEmpty(dupRefs)) {
            dupRefs.forEach(stmt -> errRecords.add(new StmtResponsePayload(stmt.getTransactionRef(),
                    stmt.getAccountNumber(), null)));
        } else if(StmtConstant.INCORRECT_END_BALANCE.equals(requestType) &&
                    !CollectionUtils.isEmpty(invalidAmts)) {
            invalidAmts.forEach(stmt -> errRecords.add(new StmtResponsePayload(stmt.getTransactionRef(),
                    stmt.getAccountNumber(), null)));
        } else if (invalidAmts != null && dupRefs != null) {
            Stream.concat(invalidAmts.stream(), dupRefs.stream())
                    .collect(Collectors.toList()).forEach(
                            stmt -> errRecords.add(new StmtResponsePayload(stmt.getTransactionRef(),
                    stmt.getAccountNumber(), null)));
        }
        response.setErrorRecords(errRecords);
        return response;
    }
}
