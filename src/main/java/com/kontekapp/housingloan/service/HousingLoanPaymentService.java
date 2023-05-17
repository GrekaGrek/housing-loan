package com.kontekapp.housingloan.service;

import com.kontekapp.housingloan.model.Loan;
import com.kontekapp.housingloan.model.MonthlyPayment;
import com.kontekapp.housingloan.model.PaymentSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Component
public class HousingLoanPaymentService implements CalculationStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(HousingLoanPaymentService.class);

    @Override
    public PaymentSchedule calculateMonthlyPayments(Loan loan) {

        BigDecimal loanAmount = loan.loanAmount();
        final BigDecimal monthlyInterestRate = getMonthlyInterestRate(loan.loanType().getRate());
        final int termsInMonths = loan.termsInYears() * 12;

        BigDecimal monthlyPaymentAmount = getMonthlyPaymentAmount(loanAmount, monthlyInterestRate, termsInMonths);

        List<MonthlyPayment> payments = new ArrayList<>();
        for (int month = 0; month < termsInMonths; month++) {
            BigDecimal principalAmount;
            BigDecimal paymentAmount;

            final BigDecimal interestAmount = calculateInterestAmount(loan, loanAmount, monthlyInterestRate);

            if (month + 1 == termsInMonths) {
                principalAmount = loanAmount;
            } else {
                principalAmount = (monthlyPaymentAmount.subtract(interestAmount)).setScale(2, HALF_UP);
            }
            paymentAmount = interestAmount.add(principalAmount);

            payments.add(
                    new MonthlyPayment(month, loanAmount, principalAmount, paymentAmount, interestAmount)
            );
            loanAmount = loanAmount.subtract(principalAmount);
        }

        PaymentSchedule result = new PaymentSchedule(Collections.unmodifiableList(payments));
        LOGGER.debug("Calculation result: {}", result);

        return result;
    }

    private BigDecimal getMonthlyInterestRate(BigDecimal rate) {
        final BigDecimal monthlyInterestRate = rate
                .divide(valueOf(100), 8, HALF_UP)
                .divide(valueOf(12), 8, HALF_UP);

        LOGGER.debug("Calculated monthly interest rate: {}", monthlyInterestRate);
        return monthlyInterestRate;
    }

    private BigDecimal getMonthlyPaymentAmount(BigDecimal amount, BigDecimal rate, int termsInMonths) {
        LOGGER.debug("Calculating monthly payment amount for: {}, {}, {}", amount, rate, termsInMonths);

        BigDecimal monthlyPaymentAmount = getInterestMonthlyAmount(
                amount, (rate.multiply(ONE.add(rate).pow(termsInMonths)))
                        .divide((ONE.add(rate).pow(termsInMonths)
                        .subtract(ONE)), 8, HALF_UP)
        );
        LOGGER.debug("Calculate monthly payment amount: {}", amount);

        return monthlyPaymentAmount;
    }

    private BigDecimal getInterestMonthlyAmount(BigDecimal currentLoanAmount, BigDecimal monthlyInterestRate) {
        return currentLoanAmount
                .multiply(monthlyInterestRate)
                .setScale(2, HALF_UP);
    }

    private BigDecimal calculateInterestAmount(Loan loan, BigDecimal currentLoanAmount, BigDecimal monthlyInterestRate) {
        return loan != null
                ? getInterestMonthlyAmount(currentLoanAmount, monthlyInterestRate)
                : null;
    }
}
