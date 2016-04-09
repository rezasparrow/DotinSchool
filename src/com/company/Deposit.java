package com.company;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

import util.ArgumentOutOfBoundsException;

/**
 * Created by Dotin School1 on 4/4/2016.
 */
public  class Deposit implements Comparable<Deposit> {
    private BigDecimal balance;
    private int durationInDay;
    private String customerNumber;
    private BigDecimal payedInterest;
    private DepositType depositType;


    public Deposit(String depositType, BigDecimal balance, int durationInDay, String customerNumber) throws ArgumentOutOfBoundsException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        setBalance(balance);
        setDurationInDay(durationInDay);
        setCustomerNumber(customerNumber);

        Class cls = Class.forName("com.company." + depositType + "Deposit");
        this.depositType = (DepositType) cls.newInstance();
        payedInterest = calculatePayedInterest();
    }


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) throws ArgumentOutOfBoundsException {
        if (balance.compareTo(new BigDecimal(0)) < 0) {
            throw new ArgumentOutOfBoundsException("balance can not be negative");
        }

        this.balance = balance;
    }

    public void setDurationInDay(int durationInDay) throws ArgumentOutOfBoundsException {
        if (durationInDay <= 0) {
            throw new ArgumentOutOfBoundsException("Duration day must be positive");
        }

        this.durationInDay = durationInDay;
    }

    public int getDurationInDay() {
        return durationInDay;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public BigDecimal getPayedInterest(){
        return payedInterest;
    }

    public BigDecimal calculatePayedInterest() {
        BigDecimal multiplyResult = balance.multiply(new BigDecimal(durationInDay)).multiply(new BigDecimal(depositType.getInterestRate()));
        BigDecimal result = multiplyResult.divide(new BigDecimal("35600"), 4, RoundingMode.HALF_UP);
        return result;
    }

    @Override
    public int compareTo(Deposit deposit) {
        return this.payedInterest.compareTo(deposit.payedInterest);
    }

}
