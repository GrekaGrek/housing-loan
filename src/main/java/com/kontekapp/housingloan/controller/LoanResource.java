package com.kontekapp.housingloan.controller;

import com.kontekapp.housingloan.model.Loan;
import com.kontekapp.housingloan.model.PaymentSchedule;
import com.kontekapp.housingloan.service.LoanPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
class LoanResource {

    private final LoanPaymentService service;

    public LoanResource(LoanPaymentService service) {
        this.service = service;
    }

    @PostMapping("/payments")
    public ResponseEntity<PaymentSchedule> createPayments(@RequestBody Loan loan) {
        return new ResponseEntity<>(service.calculateMonthlyPayments(loan), HttpStatus.CREATED);
    }
}
