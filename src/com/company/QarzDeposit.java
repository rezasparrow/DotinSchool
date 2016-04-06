package com.company;

import util.ArgumentOutOfBoundsException;

import java.math.BigDecimal;

/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class QarzDeposit extends Deposit {


    public QarzDeposit(BigDecimal balance, int durationInDay, int customerNumber) throws ArgumentOutOfBoundsException {
        super(balance, durationInDay, customerNumber);
    }

    @Override
    public float getInterestRate() {
        return 0;
    }
}
