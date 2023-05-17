package com.kontekapp.housingloan.service;

import com.kontekapp.housingloan.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanPaymentCalcFactory {
    public static final String HOUSING = "HOUSING";


    public CalculationStrategy createStrategy(Loan loan) {
        if (HOUSING.equalsIgnoreCase(loan.loanType().name())) {
            return new HousingLoanPaymentService();
        }
        return new CarLoanPaymentService();
    }
}
