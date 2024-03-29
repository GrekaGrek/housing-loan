package com.kontekapp.housingloan.model;

import com.kontekapp.housingloan.enums.LoanTypeEnum;

import java.math.BigDecimal;

public record Loan(
        int termsInYears,
        BigDecimal loanAmount,
        LoanTypeEnum loanType
) {
}
