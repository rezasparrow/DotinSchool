package com.company;


import util.ArgumentOutOfBoundsException;
import util.FileFormatException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class Main {

    private static void sortDeposits(List<Deposit> deposits) {
        Collections.sort(deposits, (Deposit first , Deposit second)-> second.compareTo(first));

    }

    public static void main(String[] args)
            throws NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {

        XmlDepositParser  xmlDepositParser = new XmlDepositParser("input.xml");

        List<Deposit> deposits = new ArrayList<>();
        try {
            try {
                deposits = xmlDepositParser.parse();
            } catch (ArgumentOutOfBoundsException e) {
                e.printStackTrace();
            }
        }catch (FileFormatException e){
            System.out.println(e.getMessage());
            return;
        }

        Collections.sort(deposits , Collections.<Deposit>reverseOrder());
        try{

            File file = new File("output.txt");
            // creates the file
            file.createNewFile();

            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file);
            for (Deposit deposit : deposits) {

                writer.write(deposit.getCustomerNumber() + "#" + deposit.getPayedInterest() + "\n");

            }

            writer.flush();
            writer.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }
}
