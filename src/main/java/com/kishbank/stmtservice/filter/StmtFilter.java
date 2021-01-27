package com.kishbank.stmtservice.filter;

import com.kishbank.stmtservice.model.Statement;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * This class is to filter input Statement list
 * of duplicates and extract Amounts (Start/Mutation/End Balances in Euros) from input Statement list.
 *
 * @author Kishore Diyyana
 */
public final class StmtFilter {

    /**
     * This method used to extract Invalid Amounts after
     *  verifying end balance needs to be validated ( Start Balance +/- Mutation = End Balance)
     * @param stmts
     * @return List<Statement>
     */
    public static List<Statement> extractInvalidAmountList(List<Statement> stmts) {
        return stmts.stream().filter(stmt -> stmt.getStartBalance().add(stmt.getMutation()).compareTo(stmt.getEndBalance()) != 0)
                .filter(stmt -> stmt.getStartBalance().subtract(stmt.getMutation()).compareTo(stmt.getEndBalance()) != 0)
                .collect(Collectors.toList());
    }

    /**
     * This method used to removes the duplicates of all Transaction References.
     * @param stmts
     * @return
     */
    public static List<Statement> removeDuplicateTransRefList(List<Statement> stmts) {
        return distictDuplicates(extractDuplicateList(stmts));
    }

    /**
     * This method filters the duplicates by grouping the all Transaction References.
     * @param stmts
     * @return
     */
    private static Map<Long, List<Statement>> filterDuplicateTransRefList(List<Statement> stmts) {
        return stmts.stream().collect(groupingBy(Statement::getTransactionRef));
    }

    /**
     * This method used to extract Duplicate to make
     * it all transaction references should be unique
     * @param stmts
     * @return
     */
    private static List<Statement> extractDuplicateList(List<Statement> stmts) {
        return filterDuplicateTransRefList(stmts).values().stream()
                .filter(duplicates -> duplicates.size() > 1)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    /**
     * This method does distinc among duplicates and
     * make Transaction Reference as sorting order.
     * @param stmts
     * @return List<Statement>
     */
    private static List<Statement> distictDuplicates(List<Statement> stmts) {
        return stmts.stream().distinct().sorted(
                Comparator.comparing(Statement::getTransactionRef)).collect(toList());
    }
}
