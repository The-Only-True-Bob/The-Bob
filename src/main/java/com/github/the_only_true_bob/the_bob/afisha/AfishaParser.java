package com.github.the_only_true_bob.the_bob.afisha;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpressionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AfishaParser implements Afisha {

    final String EVENTS_FILE = "src/main/resources/creations.xml";
    final String SCHEDULES_SPB_FILE = "src/main/resources/schedules_spb.xml";
    final String COMPANIES_FILE = "src/main/resources/companies.xml";

    private ArrayList<Objects> events = new ArrayList<>();

    @Override
    public void getInfoFromAfisha() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory
                .newInstance();
        Document documentEvents = null;
        Document documentSchedulesSpb = null;
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            documentEvents = documentBuilder.parse(EVENTS_FILE);
            documentSchedulesSpb = documentBuilder.parse(SCHEDULES_SPB_FILE);
        } catch (ParserConfigurationException
                | SAXException
                | IOException e) {
            e.printStackTrace();
        }
      /*  parseXML(documentEvents);
        for(Objects event: events) {
            parseSchedulesSpb(documentSchedulesSpb, event);
        }*/
       // parseSchedulesSpb(documentSchedulesSpb, null);
        parsePlaces(null);
    }

    private ArrayList<Objects> parseXML(Document src) {

        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr;
            expr = xpath.compile("/creations/creation");

            Object result = expr.evaluate(src, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                NodeList child = nodes.item(i).getChildNodes();
                for(int j = 1; j < child.getLength(); j++) {
                    if (!child.item(j).getNodeName().equals("#text") && child.item(j).getTextContent().length() != 0) {
                        System.out.println(child.item(j).getNodeName());
                        System.out.println(child.item(j).getTextContent());
                    }
                }
            }
        } catch (XPathExpressionException e) {e.printStackTrace ();}
        return null;
    }

    private void parseSchedulesSpb(Document src, Object event) {

        String eventID = "Movie167016";
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr;
            expr = xpath.compile("/schedules/schedule");

            Object result = expr.evaluate(src, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                if (xpath.compile("./creation-id").evaluate(nodes.item(i)).equals(eventID)) {
                    parseDate(nodes, i);
                }
            }
        } catch (XPathExpressionException e) {e.printStackTrace ();}
    }

    private void parseDate(NodeList nodes, int i) {
        NodeList child = nodes.item(i).getChildNodes();
        for (int j = 1; j < child.getLength(); j++) {
            if (child.item(j).getNodeName().equals("place-id")) {
                System.out.println(child.item(j).getTextContent());
            }
            if (child.item(j).getNodeName().equals("dates")) {
                NodeList dateList = child.item(j).getChildNodes();

                for (int k = 1; k < dateList.getLength(); k++) {
                    if (dateList.item(k).getNodeName().equals("date")) {
                        System.out.println(dateList.item(k).getNodeName());
                        System.out.println(dateList.item(k).getTextContent());
                    }
                }
            }
        }
    }

    private void parsePlace(NodeList nodes, int i) {
        NodeList child = nodes.item(i).getChildNodes();
        for (int j = 1; j < child.getLength(); j++) {
            if (child.item(j).getNodeName().equals("name")) {
                System.out.println(child.item(j).getTextContent());
            }
            if (child.item(j).getNodeName().equals("address")) {
                System.out.println(child.item(j).getTextContent());
            }
            if (child.item(j).getNodeName().equals("country")) {
                System.out.println(child.item(j).getTextContent());
            }
        }
    }

    private void parsePlaces(Object event) {
        String placeID = "Museum7453";
        PlaceParser parser = new PlaceParser();
        parser.findPlace(placeID, COMPANIES_FILE);
    }
}
