package com.github.the_only_true_bob.the_bob.afisha;

import com.github.the_only_true_bob.the_bob.dao.entitites.EventEntity;
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
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class AfishaParser implements Afisha {

    final String EVENTS_FILE = "src/main/resources/creations.xml";
    final String SCHEDULES_SPB_FILE = "src/main/resources/schedules_spb.xml";
    final String COMPANIES_FILE = "src/main/resources/companies.xml";

    private ArrayList<EventEntity> eventsEntityTmp = new ArrayList<>();
    private ArrayList<EventEntity> eventsEntity = new ArrayList<>();
    private ArrayList<EventPlace> eventsPlaces = new ArrayList<>();

    @Override
    public ArrayList<EventEntity> getInfoFromAfisha() {
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
        System.out.println("start");
        parseXML(documentEvents);
        System.out.println("parseXML is successful");
        parseSchedulesSpb(documentSchedulesSpb);
        System.out.println("parseSchedulesSpb is successful");
        parsePlaces();
        matchEventsWithEventPlaces();
        System.out.println("____________________________END_____________________________");
        filterResults();
        return eventsEntity;
    }

    private void filterResults() {
        List<EventPlace> events = eventsPlaces
                .stream()
                .filter(place -> Objects.nonNull(place.getPlaceAddress()))
                .filter(place -> Objects.nonNull(place.getPlaceName()))
                .filter(place -> Objects.nonNull(place.getName()))
                .filter(place -> Objects.nonNull(place.getType()))
                .filter(place -> Objects.nonNull(place.getDate()))
                .collect(toList());

        convertEventPlaceToEventEntity(events);
    }

    private void convertEventPlaceToEventEntity(List<EventPlace> events) {
        for(EventPlace e:events) {
            EventEntity eventEntity = new EventEntity();
            eventEntity.setAfishaId(e.getAfishaId());
            eventEntity.setName(e.getName());
            eventEntity.setType(e.getType());
            eventEntity.setAfishaUrl(e.getAfishaUrl());
            eventEntity.setAfishaImgUrl(e.getAfishaImgUrl());
            eventEntity.setDate(e.getDate());
            eventEntity.setPlaceName(e.getPlaceName());
            eventEntity.setPlaceAddress(e.getPlaceAddress());
            eventsEntity.add(eventEntity);
        }
    }

    private void matchEventsWithEventPlaces() {
        eventsPlaces.forEach(p -> {
            eventsEntityTmp.forEach(e -> {
                if (p.getAfishaId().equals(e.getAfishaId())) {
                    p.setName(e.getName());
                    p.setType(e.getType());
                    p.setAfishaImgUrl(e.getAfishaImgUrl());
                    p.setAfishaUrl(e.getAfishaUrl());
                }
            });
        });
    }

    private void parseXML(Document src) {
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr;
            expr = xpath.compile("/creations/creation");

            Object result = expr.evaluate(src, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            for (int i = 0; i < 500; i++) {
                EventEntity eventEntity = new EventEntity();
                String creationId = xpath.compile("./creation-id").evaluate(nodes.item(i));
                eventEntity.setAfishaId(creationId);
                String type = getEventType(creationId);
                eventEntity.setType(type);

                String name = xpath.compile("./name").evaluate(nodes.item(i));
                eventEntity.setName(name);

                String url = xpath.compile("./url").evaluate(nodes.item(i));
                eventEntity.setAfishaUrl(url);

                String imgUrl = xpath.compile("./main-photo").evaluate(nodes.item(i));
                eventEntity.setAfishaImgUrl(imgUrl);

                eventsEntityTmp.add(eventEntity);
            }
        } catch (XPathExpressionException e) {e.printStackTrace ();}
    }

    private String getEventType(String event) {
        return event.replaceFirst("[0-9]+", "");
    }

    private void parseSchedulesSpb(Document src) {
        try {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr;
            expr = xpath.compile("/schedules/schedule");

            Object result = expr.evaluate(src, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
            for (int i = 0; i < 1000; i++) {
                String afishaId = xpath.compile("./creation-id").evaluate(nodes.item(i));
                String placeId = xpath.compile("./place-id").evaluate(nodes.item(i));

                EventPlace eventPlace = new EventPlace();
                eventPlace.setAfishaId(afishaId);
                eventPlace.setPlaceId(placeId);
                eventsPlaces.add(eventPlace);
                parseDate(nodes, i, eventsPlaces.indexOf(eventPlace));
            }
        } catch (XPathExpressionException e) {e.printStackTrace ();}
    }

    private void parseDate(NodeList nodes, int i, int index) {
        NodeList child = nodes.item(i).getChildNodes();
        for (int j = 1; j < child.getLength(); j++) {

            if (child.item(j).getNodeName().equals("dates")) {
                NodeList dateList = child.item(j).getChildNodes();

                int bol = 0;
                int counter = index;
                for (int k = 1; k < dateList.getLength(); k++) {
                    if (dateList.item(k).getNodeName().equals("date")) {
                        String date = dateList.item(k).getTextContent();
                        if (bol == 0) {
                            eventsPlaces.get(counter).setDate(date);
                            bol++;
                        } if (bol < 1) {
                            counter = initializeNewEvent(date, counter);
                            bol++;
                        }
                    }
                }
            }
        }
    }

    private int initializeNewEvent(String date, int counter) {
        String afishaId = eventsPlaces.get(counter).getAfishaId();
        String placeId = eventsPlaces.get(counter).getPlaceId();

        EventPlace eventPlace = new EventPlace();
        eventPlace.setAfishaId(afishaId);
        eventPlace.setPlaceId(placeId);
        eventPlace.setDate(date);
        eventsPlaces.add(eventPlace);

        return eventsPlaces.indexOf(eventPlace);
    }

    private void parsePlaces() {
        int i = 0;
        String placeId = "";
        String placeName = "";
        String placeAddress = "";
        for(EventPlace eventPlace: eventsPlaces) {
            System.out.println("parseAddress: " + i);
            String currentPlaceId = eventPlace.getPlaceId();
            if (!currentPlaceId.equals(placeId)) {
                PlaceParser parser = new PlaceParser();
                parser.findPlace(eventPlace.getPlaceId(), COMPANIES_FILE);
                placeAddress = parser.address;
                placeName = parser.placeName;
                placeId = currentPlaceId;
            }
            eventPlace.setPlaceName(placeName);
            eventPlace.setPlaceAddress(placeAddress);
            i++;
        }
    }
}
