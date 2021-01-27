package com.kishbank.stmtservice.service;

import com.kishbank.stmtservice.domain.StmtResponse;
import com.kishbank.stmtservice.model.Statement;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * This interface serve and process business operations.
 *
 * @author Kishore Diyyana
 */
public interface StmtService {
    public ResponseEntity<StmtResponse> process(List<Statement> statements);
}
