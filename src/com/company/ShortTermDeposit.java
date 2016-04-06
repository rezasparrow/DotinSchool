package com.company;

import util.ArgumentOutOfBoundsException;

import java.math.BigDecimal;

/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class ShortTermDeposit extends Deposit{


    public ShortTermDeposit(BigDecimal balance, int durationInDay, int customerNumber) throws ArgumentOutOfBoundsException {
        super(balance, durationInDay, customerNumber);
    }

    @Override
    public float getInterestRate() {
        return 10f;
    }
}
