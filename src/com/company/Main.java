package com.company;

import com.sun.deploy.util.ArrayUtil;
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

    private static void sortDeposits(List<Object> deposits) {
        Collections.sort(deposits, Collections.reverseOrder(new Comparator<Object>() {
            @Override
            public int compare(Object firstObject, Object secondObject) {
                Method payedInterest = null;
                BigDecimal profitOne = null;
                BigDecimal profitSecond = null;
                try {
                    payedInterest = firstObject.getClass().getSuperclass().getDeclaredMethod("payedInterest", new Class[]{});
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    profitOne = (BigDecimal) payedInterest.invoke(firstObject, null);
                    profitSecond = (BigDecimal) payedInterest.invoke(secondObject, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return profitOne.compareTo(profitSecond);
            }
        }));
    }

    public static void main(String[] args)
            throws NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {

        InputHandler inputHandler = new InputHandler("input.xml");

        List<Object> deposits = new ArrayList<Object>();
        try {
            deposits = inputHandler.parse();
        }catch (FileFormatException e){
            System.out.println(e.getMessage());
            return;
        }

        sortDeposits(deposits);
        try{

            File file = new File("output.txt");
            // creates the file
            file.createNewFile();

            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file);
            for (Object deposit : deposits) {
                Method payedInterest = deposit.getClass().getSuperclass().getDeclaredMethod("payedInterest", new Class[]{});
                BigDecimal profit = (BigDecimal) payedInterest.invoke(deposit, null);

                Method getCustomerNumber = deposit.getClass().getSuperclass().getDeclaredMethod("getCustomerNumber", new Class[]{});
                Integer customerNumber = (Integer) getCustomerNumber.invoke(deposit, null);

                // Writes the content to the file
                writer.write(customerNumber + "#" + profit + "\n");

            }

            writer.flush();
            writer.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }


    }
}
