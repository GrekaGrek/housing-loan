package com.kontekapp.housingloan.model;

import java.math.BigDecimal;

public record MonthlyPayment(
        int monthNumber,
        BigDecimal loanAmount,
        BigDecimal principalAmount,
        BigDecimal paymentAmount,
        BigDecimal interestAmount
) {
}
