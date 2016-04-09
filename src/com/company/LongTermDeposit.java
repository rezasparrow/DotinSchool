package com.company;

import util.ArgumentOutOfBoundsException;

import java.math.BigDecimal;

/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class LongTermDeposit extends DepositType {

    @Override
    public float getInterestRate() {
        return 20f;
    }


}
