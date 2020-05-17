package com.example.week9;

import android.os.StrictMode;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class ReadXML {

    static ArrayList Schedule(String urlString){
        ArrayList<TheatreData> scheduleList = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Show");
            for (int i=0; i<nodeList.getLength(); i++){
                String dttmShowStart = doc.getElementsByTagName("dttmShowStart").item(i).getTextContent();
                String EventID = doc.getElementsByTagName("EventID").item(i).getTextContent();
                String Title = doc.getElementsByTagName("Title").item(i).getTextContent();
                String ProductionYear = doc.getElementsByTagName("ProductionYear").item(i).getTextContent();
                String TheatreID = doc.getElementsByTagName("TheatreID").item(i).getTextContent();
                String Theatre = doc.getElementsByTagName("Theatre").item(i).getTextContent();
                System.out.println(dttmShowStart +" "+ EventID+" "+ Title+" "+ ProductionYear+" "+ TheatreID+" "+ Theatre);
                TheatreData theatreData = new TheatreData(dttmShowStart, EventID, Title, ProductionYear, TheatreID, Theatre);
                scheduleList.add(theatreData);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scheduleList;
    }
    static ArrayList TheatreAreas(String urlString){
        ArrayList<TheatreData> theatreList = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("TheatreArea");
            for (int i=0; i<nodeList.getLength(); i++){
                String TheatreID = doc.getElementsByTagName("ID").item(i).getTextContent();
                String Theatre = doc.getElementsByTagName("Name").item(i).getTextContent();
                TheatreData theatreData = new TheatreData(TheatreID, Theatre);
                theatreList.add(theatreData);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return theatreList;
    }
    static ArrayList Events(String urlString){
        ArrayList<String> eventsList = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Event");
            for (int i=0; i<nodeList.getLength(); i++){
                String Title = doc.getElementsByTagName("Title").item(i).getTextContent();
                eventsList.add(Title);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return eventsList;
    }
}
