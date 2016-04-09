package com.company;
import javafx.util.Pair;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import util.ArgumentOutOfBoundsException;
import util.FileFormatException;


/**
 * Created by Dotin School1 on 4/4/2016.
 */
public class XmlDepositParser {
    private String pathFile;
    private List<String> attributesName = Arrays.asList("depositBalance" , "durationInDays" , "customerNumber" , "depositType");
    public XmlDepositParser(String pathFile){
        this.pathFile = pathFile;
    }

    private void validateData (ArrayList<Pair<String , String>> attributes) throws FileFormatException {
        boolean findAttribute = false;
        for(String attributeName : attributesName){
            findAttribute = false;
            for(Pair<String , String> attribute : attributes){
                if(attribute.getKey().toString().equals(attributeName)){
                    if(findAttribute){
                        throw new FileFormatException("File format is invalid \n "+ attribute.getKey() + "tag is more than one time" );
                    }
                    findAttribute = true;
                }
            }
            if(!findAttribute){
                throw new FileFormatException("File format is invalid\n " +attributeName + "tag can not be find in this deposit tag");
            }
        }

    }

    private Deposit getObject(ArrayList<Pair<String, String>> attributes)
            throws FileFormatException, ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException, ArgumentOutOfBoundsException {
        BigDecimal balance = null;
        int durationInDay = 0;
        String customerNumber = "";
        String depositType = "";

        for(Pair<String , String> attribute : attributes){
            if(attribute.getKey().equals("depositBalance")){
                balance = new BigDecimal(attribute.getValue());
            }
            else if(attribute.getKey().equals("durationInDays")){
                durationInDay = Integer.parseInt(attribute.getValue());
            }
            else if(attribute.getKey().equals("customerNumber")){
                customerNumber = attribute.getValue();
            }
            else if(attribute.getKey().equals("depositType")){
                depositType = attribute.getValue();
            }
        }

        return new Deposit(depositType , balance, durationInDay, customerNumber);

    }



    public List<Deposit> parse()
            throws FileFormatException, IllegalAccessException,
            NoSuchMethodException,
            InstantiationException, ArgumentOutOfBoundsException {
        List<Deposit> objects = new ArrayList<Deposit>();
        boolean findElement = false;
        boolean startFile = false;
        boolean findStartDepositTag = false;

        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(
                            new FileReader(pathFile));
            String elementName = "";
            ArrayList<Pair<String , String>> depositData = new ArrayList<Pair<String, String>>();
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                switch(event.getEventType()){
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String localPart = startElement.getName().getLocalPart();

                        if (localPart.equals("deposit")) {
                            findStartDepositTag = true;
                        } else if(attributesName.contains(localPart)){
                            elementName = localPart;
                            findElement = true;
                        }
                        else if(localPart.equals("deposits")){
                            startFile = true;
                        }
                        else{
                            throw new FileFormatException("file format is invalid\n undefined " + localPart + " tag");
                        }
                        break;


                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if(findElement){
                            depositData.add(new Pair<String, String>(elementName , characters.getData().trim()));
                            findElement = false;
                        }
                        break;


                    case  XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        if(endElement.getName().getLocalPart().equalsIgnoreCase("deposit"))
                        {
                            validateData(depositData);
                            try {
                                objects.add(getObject(depositData));
                            } catch (ClassNotFoundException e){
                                throw new FileFormatException("File format is invalid\n" +
                                        "deposit type is invalid");
                            } catch (InvocationTargetException e){
                                throw new FileFormatException(e.getTargetException().getMessage());
                            }
                            depositData.clear();
                        }
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return objects;
    }
}
