package com.kontekapp.housingloan.model;

import java.util.List;

public record PaymentSchedule(
        List<MonthlyPayment> monthlyPayments
) {
}
