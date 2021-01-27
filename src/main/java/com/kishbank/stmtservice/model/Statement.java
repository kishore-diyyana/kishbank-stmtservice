package com.kishbank.stmtservice.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * This class holds the Domain attribute of the following:
 * | Field                  | Description                                      |
 * |------------------------|------------------------------------------------- |
 * | Transaction reference  |A numeric value                                   |
 * | Account number         |  An IBAN                                         |
 * | Start Balance          |  The starting balance in Euros                   |
 * | Mutation               |  Either an addition (+) or a deduction (-)       |
 * | Description            | Free text                                        |
 * | End Balance            | The end balance in Euros                         |
 *
 * @author Kishore Diyyana
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Statement {

	/**
	 * This holds Bank Account Number.
	 */
	@NotEmpty(message = "Please provide valid Account Number")
	@EqualsAndHashCode.Include
	private String accountNumber;

	@NotNull(message = "Please provide valid End Balance in Euros")
	private BigDecimal endBalance;

	@NotNull(message = "Please provide valid Mutation +/- (Deposit/Withdraw Amount) in Euros")
	private BigDecimal mutation;

	@NotNull(message = "Please provide valid Start Balance in Euros")
	private BigDecimal startBalance;

	@NotNull(message = "Please provide valid Transaction Reference")
	@EqualsAndHashCode.Include
	private Long transactionRef;

	public Statement(Long transactionRef, String accountNumber,
					 BigDecimal startBalance, BigDecimal mutation,
					 BigDecimal endBalance) {
		this.accountNumber = accountNumber;
		this.transactionRef = transactionRef;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}

	public BigDecimal getMutation() { return mutation; }

	public void setMutation(BigDecimal mutation) { this.mutation = mutation; }

	public BigDecimal getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}

	public Long getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(Long transactionRef) {
		this.transactionRef = transactionRef;
	}

}
