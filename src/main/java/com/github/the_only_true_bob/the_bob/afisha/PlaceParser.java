package com.github.the_only_true_bob.the_bob.afisha;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PlaceParser {

    public void findPlace(String placeID, String file) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {

                boolean placeExists = false;
                boolean bId = false;
                boolean bAddress = false;
                boolean bName = false;

                public void startElement(String uri, String localName,String qName,
                                         Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("company-id")) bId = true;
                    if (qName.equalsIgnoreCase("address")) bAddress = true;
                    if (qName.equalsIgnoreCase("name")) bName = true;
                }

                public void characters(char ch[], int start, int length) throws SAXException {
                    if (bId) {
                        String id = new String(ch, start, length);
                        if (id.equals(placeID)) {
                            placeExists = true;
                            System.out.println("Place-id : " + id);
                        }
                        bId = false;
                    }
                    if (bName) {
                        if (placeExists) {
                        System.out.println("Name : " + new String(ch, start, length));
                         }
                        bName = false;
                    }
                    if (bAddress) {
                        if (placeExists) {
                        System.out.println("Address : " + new String(ch, start, length));
                               placeExists = false;
                        }
                        bAddress = false;
                    }
                }
            };
            saxParser.parse(file, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
