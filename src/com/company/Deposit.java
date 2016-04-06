package com.company;

import java.math.BigDecimal;

import util.ArgumentOutOfBoundsException;

/**
 * Created by Dotin School1 on 4/4/2016.
 */
public abstract class Deposit {
    private BigDecimal balance;
    private int durationInDay;
    private float interestRate;
    private Integer customerNumber;



    public Deposit(BigDecimal balance , int durationInDay , int customerNumber) throws ArgumentOutOfBoundsException {
        setBalance(balance);
        setDurationInDay(durationInDay);
        setCustomerNumber(customerNumber);
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) throws ArgumentOutOfBoundsException {
        if(balance.compareTo(new BigDecimal(0)) < 0){
            throw new ArgumentOutOfBoundsException("balance can not be negative");
        }

        this.balance = balance;
    }

    public void setDurationInDay(int durationInDay) throws ArgumentOutOfBoundsException {
        if(durationInDay <= 0){
            throw new ArgumentOutOfBoundsException("Duration day must be positive");
        }

        this.durationInDay = durationInDay;
    }

    public int getDurationInDay() {
        return durationInDay;
    }

    public BigDecimal payedInterest(){
        BigDecimal multiplyResult =  balance.multiply(new BigDecimal(durationInDay) ).multiply(new BigDecimal(getInterestRate()));
        return multiplyResult.divide(new BigDecimal(35600) );
    }

    public abstract float getInterestRate();

    public Integer getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }
}
