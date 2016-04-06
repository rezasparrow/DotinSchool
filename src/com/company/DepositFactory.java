package com.company;

import util.ArgumentOutOfBoundsException;

import java.math.BigDecimal;

/**
 * Created by Dotin School1 on 4/6/2016.
 */
public class DepositFactory {
    public static Deposit getShape(BigDecimal balance , int durationInDay , int customerNumber , String depositType) throws ArgumentOutOfBoundsException {
        if(depositType == null){
            return null;
        }
        else if(depositType.equalsIgnoreCase("ShortTerm")){
            return new ShortTermDeposit(balance , durationInDay ,customerNumber);
        }
        else if(depositType.equalsIgnoreCase("LongTerm")) {
            return new LongTermDeposit(balance, durationInDay, customerNumber);
        }
        else if(depositType.equalsIgnoreCase("Qarz")) {
            return new QarzDeposit(balance, durationInDay, customerNumber);
        }
        return  null;

    }
}
