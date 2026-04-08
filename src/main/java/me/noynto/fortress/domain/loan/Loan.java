package me.noynto.fortress.domain.loan;

import me.noynto.fortress.domain.shared.LoanId;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Loan {
    private LoanId id;
    private String name;
    private LoanType type;
    private BigDecimal amountPerMonth;
    private BigDecimal total;
    private LoanState state;
    private Integer withdrawalMonthDay;
    private Integer remainingDeadlines;
    private LocalDate beginDate;

}
