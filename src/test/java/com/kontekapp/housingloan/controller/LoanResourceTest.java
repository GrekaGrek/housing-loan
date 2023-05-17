package com.kontekapp.housingloan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontekapp.housingloan.model.Loan;
import com.kontekapp.housingloan.service.HousingLoanPaymentService;
import com.kontekapp.housingloan.service.LoanPaymentCalcFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static com.kontekapp.housingloan.enums.LoanTypeEnum.HOUSING;
import static com.kontekapp.housingloan.enums.LoanTypeEnum.TRIP_TO_MARS;
import static java.math.BigDecimal.valueOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoanResourceTest {

    private static final String API_URL = "/api/payments";
    private final Loan LOAN = createLoan(valueOf(150000));

    @Mock
    private LoanPaymentCalcFactory mockLoanPaymentCalcFactory;

    @InjectMocks
    private LoanResource controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void createPaymentsSuccessfully() throws Exception {
        when(mockLoanPaymentCalcFactory.createStrategy(LOAN)).thenReturn(new HousingLoanPaymentService());

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapToJson(LOAN)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        verify(mockLoanPaymentCalcFactory).createStrategy(LOAN);
    }

    @Test
    void calculatePremiumPolicyReturnBadRequest() throws Exception {
        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapToJson(TRIP_TO_MARS)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoInteractions(mockLoanPaymentCalcFactory);
    }

    @Test
    void calculatePremiumPolicyReturnNotFound() throws Exception {
        mockMvc.perform(
                        post("/wrong-url")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapToJson(LOAN)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Loan createLoan(BigDecimal loanAmount) {
        return new Loan( 20, loanAmount, HOUSING);
    }

    private static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}