package com.company;

import exeption.ArgumentOutOfBoundsException;

import java.math.BigDecimal;

/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class LongTermDeposit extends Deposit {
    public LongTermDeposit(BigDecimal balance, int durationInDay) throws ArgumentOutOfBoundsException {
        super(balance, durationInDay);
    }

    @Override
    public float getInterestRate() {
        return 20;
    }
}
