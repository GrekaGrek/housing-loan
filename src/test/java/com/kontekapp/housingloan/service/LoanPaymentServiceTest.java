package com.kontekapp.housingloan.service;

import com.kontekapp.housingloan.enums.LoanTypeEnum;
import com.kontekapp.housingloan.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static com.kontekapp.housingloan.enums.LoanTypeEnum.HOUSING;
import static com.kontekapp.housingloan.enums.LoanTypeEnum.TRIP_TO_MARS;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LoanPaymentServiceTest {

    @InjectMocks
    private LoanPaymentService service;

    @Test
    void calculateMonthlyPayments() {
        var loan = createLoan(valueOf(10), valueOf(100), HOUSING);

        var actualResult = service.calculateMonthlyPayments(loan);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.monthlyPayments().size()).isEqualTo(12);

        assertSoftly(softly -> {
            assertThat(actualResult.monthlyPayments().get(0).monthNumber())
                    .isEqualTo(0);
            assertThat(actualResult.monthlyPayments().get(0).loanAmount())
                    .isEqualTo(valueOf(100));
            assertThat(actualResult.monthlyPayments().get(0).principalAmount())
                    .isEqualTo(valueOf(7.96));
            assertThat(actualResult.monthlyPayments().get(0).paymentAmount())
                    .isEqualTo(valueOf(8.79));
            assertThat(actualResult.monthlyPayments().get(0).interestAmount())
                    .isEqualTo(valueOf(0.83));
        });
    }

    @Test
    void calculatePaymentsFailsLoanTypeIsDifferent() {
        var loan = createLoan(valueOf(20), valueOf(1000000), TRIP_TO_MARS);

        assertThrows(NoSuchElementException.class, () -> {
            service.calculateMonthlyPayments(loan);
        });
    }

    private Loan createLoan(BigDecimal annualInterestRate, BigDecimal loanAmount, LoanTypeEnum loanType) {
        return new Loan(annualInterestRate, 1, loanAmount, loanType);
    }
}