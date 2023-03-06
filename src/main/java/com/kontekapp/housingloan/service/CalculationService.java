package com.kontekapp.housingloan.service;

import com.kontekapp.housingloan.model.Loan;
import com.kontekapp.housingloan.model.PaymentSchedule;

public interface CalculationService {
    PaymentSchedule calculateMonthlyPayments(Loan loan);
}
