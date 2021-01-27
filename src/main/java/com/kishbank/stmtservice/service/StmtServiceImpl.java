package com.kishbank.stmtservice.service;

import com.kishbank.stmtservice.constants.StmtConstant;
import com.kishbank.stmtservice.domain.StmtResponse;
import com.kishbank.stmtservice.model.Statement;
import com.kishbank.stmtservice.util.StmtBuilder;
import com.kishbank.stmtservice.validator.StmtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements processing of business operations .
 *
 * @author Kishore Diyyana
 */
@Service("StatementService")
public class StmtServiceImpl implements StmtService {

	@Autowired
	StmtValidator validator;

	public ResponseEntity<StmtResponse> process(List<Statement>  statements) {
		List<Statement> dupRefs = validator.validateStmtTransRef(statements);
		List<Statement> invalidAmts = validator.validateStmtBalance(statements);
		//When there are duplicate reference and correct balance
		if(!CollectionUtils.isEmpty(dupRefs) && CollectionUtils.isEmpty(invalidAmts)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(StmtBuilder.buildStmtResponseBody(dupRefs, null,
							StmtConstant.DUPLICATE_REFERENCE));
		}
		//When there are no duplicate reference and In correct balance
		else if(CollectionUtils.isEmpty(dupRefs) && !CollectionUtils.isEmpty(invalidAmts)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(StmtBuilder.buildStmtResponseBody(null, invalidAmts,
							StmtConstant.INCORRECT_END_BALANCE));
		}
		//When there are duplicate reference and In correct balance
		else if(!CollectionUtils.isEmpty(dupRefs) && !CollectionUtils.isEmpty(invalidAmts)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(StmtBuilder.buildStmtResponseBody(dupRefs, invalidAmts,
							StmtConstant.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE));

		}
		//When there are no duplicate reference and correct end balance
		return ResponseEntity.status(HttpStatus.OK)
				.body(StmtBuilder.buildStmtResponseBody(new ArrayList<>(), new ArrayList<>(),
						StmtConstant.SUCCESSFUL));
	}

}
