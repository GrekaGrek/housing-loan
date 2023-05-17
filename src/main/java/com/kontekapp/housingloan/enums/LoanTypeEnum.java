package com.kontekapp.housingloan.enums;

import java.math.BigDecimal;

public enum LoanTypeEnum {
    HOUSING(BigDecimal.valueOf(3.5)),
    CAR(BigDecimal.valueOf(5.2)),
    TRIP_TO_MARS(BigDecimal.valueOf(25.6));

    private final BigDecimal rate;

    public BigDecimal getRate() {
        return rate;
    }

    LoanTypeEnum(BigDecimal rate) {
        this.rate = rate;
    }
}
