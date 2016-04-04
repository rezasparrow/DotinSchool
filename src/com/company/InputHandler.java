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

        for (int i = 0 ; i < elementNames.size() ; ++i){
            elementNames.set(i , elementNames.get(i).toLowerCase());
        }
    }

    public ArrayList<Object> parse(){
        boolean findElement = false;
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
                        } else if(elementNames.contains(localPart.toLowerCase())){
                            findElement = true;
                        }
                        else if(localPart.equalsIgnoreCase("deposits")){
                            startFile = true;
                        }
                        else{
                            System.out.println("here");
                        }
                        break;


                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if(findElement){
                            System.out.println(elementName
                                    + characters.getData());
                            findElement = false;
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
