package com.kontekapp.housingloan.service;

import com.kontekapp.housingloan.model.Loan;
import com.kontekapp.housingloan.model.PaymentSchedule;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CarLoanPaymentService implements CalculationStrategy {
    public static final String CAR = "CAR";

    @Override
    public PaymentSchedule calculateMonthlyPayments(Loan loan) {
        if(CAR.equalsIgnoreCase(loan.loanType().name())) {
            throw new IllegalStateException("Instantiation of this factory currently not allowed");
        }
        return new PaymentSchedule(Collections.emptyList());
    }
}
