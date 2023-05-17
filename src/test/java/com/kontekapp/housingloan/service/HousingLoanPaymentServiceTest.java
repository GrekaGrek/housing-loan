package com.kontekapp.housingloan.service;

import com.kontekapp.housingloan.enums.LoanTypeEnum;
import com.kontekapp.housingloan.model.Loan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.kontekapp.housingloan.enums.LoanTypeEnum.HOUSING;
import static com.kontekapp.housingloan.enums.LoanTypeEnum.TRIP_TO_MARS;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(MockitoExtension.class)
class HousingLoanPaymentServiceTest {

    @InjectMocks
    private HousingLoanPaymentService service;

    @Test
    void calculateMonthlyPayments() {
        var loan = createLoan(valueOf(10000), HOUSING);

        var actualResult = service.calculateMonthlyPayments(loan);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.monthlyPayments().size()).isEqualTo(12);

        assertSoftly(softly -> {
            assertThat(actualResult.monthlyPayments().get(0).monthNumber())
                    .isEqualTo(0);
            assertThat(actualResult.monthlyPayments().get(0).loanAmount())
                    .isEqualTo(valueOf(10000));
            assertThat(actualResult.monthlyPayments().get(0).principalAmount())
                    .isEqualTo(valueOf(820.05));
            assertThat(actualResult.monthlyPayments().get(0).paymentAmount())
                    .isEqualTo(valueOf(849.22));
            assertThat(actualResult.monthlyPayments().get(0).interestAmount())
                    .isEqualTo(valueOf(29.17));
        });
    }

//    @Test
//    void calculatePaymentsFailsLoanTypeIsDifferent() {
//        var loan = createLoan(valueOf(20), TRIP_TO_MARS);
//
//        assertThrows(NoSuchElementException.class, () -> {
//            service.calculateMonthlyPayments(loan);
//        });
//    }

    private Loan createLoan(BigDecimal loanAmount, LoanTypeEnum loanType) {
        return new Loan(1, loanAmount, loanType);
    }
}