package com.company;

import util.FileFormatException;

/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class Main {

    public static void main(String[] args) throws FileFormatException {
        InputHandler inputHandler = new InputHandler("input.xml");
        inputHandler.parse();
    }
}
