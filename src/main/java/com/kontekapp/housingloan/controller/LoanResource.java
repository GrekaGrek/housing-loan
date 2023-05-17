package com.kontekapp.housingloan.controller;

import com.kontekapp.housingloan.model.Loan;
import com.kontekapp.housingloan.model.PaymentSchedule;
import com.kontekapp.housingloan.service.CalculationStrategy;
import com.kontekapp.housingloan.service.LoanPaymentCalcFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
class LoanResource {

    private final LoanPaymentCalcFactory factory;

    public LoanResource(LoanPaymentCalcFactory factory) {
        this.factory = factory;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentSchedule> createPayments(@RequestBody Loan loan) {
        CalculationStrategy cs = factory.createStrategy(loan);
        return new ResponseEntity<>(cs.calculateMonthlyPayments(loan), HttpStatus.CREATED);
    }
}
