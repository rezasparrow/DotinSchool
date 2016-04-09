package com.company;

/**
 * Created by Dotin School1 on 4/9/2016.
 */
public abstract  class DepositType {

    public DepositType(){
        interestRate = getInterestRate();
    }

    private float interestRate;

    public float getInterestRate() {
        return interestRate;
    }
}
