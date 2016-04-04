package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class InputHandler {
    private String pathFile;
    private List<String> elementNames = Arrays.asList("depositBalance" , "durationInDay" , "customerNumber" , "depositType");
    public InputHandler(String pathFile){
        this.pathFile = pathFile;
    }

    public ArrayList<Object> parse(){
        boolean findBalance = false;
        boolean findDurationInDay = false;
        boolean findCustomerNumber = false;
        boolean findDepositType = false;
        boolean startFile = false;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(
                            new FileReader(pathFile));
            String elementName = "";
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String localPart = startElement.getName().getLocalPart();
                        if (localPart.equalsIgnoreCase("deposit")) {
                            System.out.println("Start Element : deposit");
                        } else if (localPart.equalsIgnoreCase("depositBalance")) {
                            findBalance = true;
                        } else if (localPart.equalsIgnoreCase("durationInDay")) {
                            findDurationInDay = true;
                        } else if (localPart.equalsIgnoreCase("customerNumber")) {
                            findCustomerNumber = true;
                        }else if (localPart.equalsIgnoreCase("depositType")) {
                            findDepositType = true;
                        }
                        if(localPart.equalsIgnoreCase("deposits")){
                            startFile = true;
                        }
                        else{
                            System.out.println("here");
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if(findBalance){
                            System.out.println("Balance : "
                                    + characters.getData());
                            findBalance = false;
                        }
                        if(findDurationInDay){
                            System.out.println("Duration In Day: " + characters.getData());
                            findDurationInDay = false;
                        }
                        if(findCustomerNumber){
                            System.out.println("Customer Number: "
                                    + characters.getData());
                            findCustomerNumber = false;
                        }
                        if(findDepositType){
                            System.out.println("Deposit Type: "
                                    + characters.getData());
                            findDepositType = false;
                        }
                        break;
                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("deposit")){
                            System.out.println("End Element : deposit");
                            System.out.println();
                            System.out.println();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return null;
    }
}
