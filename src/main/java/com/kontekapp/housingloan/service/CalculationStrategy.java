package com.kontekapp.housingloan.service;

import com.kontekapp.housingloan.model.Loan;
import com.kontekapp.housingloan.model.PaymentSchedule;

public interface CalculationStrategy {
    PaymentSchedule calculateMonthlyPayments(Loan loan);
}
