package com.monopoly.controller;

import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.RoadStart;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class XmlMonopolyInitReaderTest
{
    public static final String VALID_XML_FILE_PATH    = "C:\\Develop\\NetBeans\\MonopolyGameEx1\\test\\com\\monopoly\\controller\\ValidMonopolyInit.xml";
    public static final int    ROAD_START_CELL_INDEX  = 0;
    public static final int    FIRST_CITY_CELL_INDEX  = 1;
    public static final String FIRST_CITY_NAME        = "Tashkent";
    public static final String FIRST_COUNTRY_NAME     = "Uzbekistan";
    public static final int[]  FIRST_CITY_RENT_PRICES = new int[]{2, 10, 30, 90};
    public static final int    FIRST_CITY_PRICE       = 60;

    public static final String INVALID_ALERT_CARD_XML_FILE_PATH  = "C:\\Develop\\NetBeans\\MonopolyGameEx1\\test\\com\\monopoly\\controller\\InvalidAlertCardMonopolyInit.xml";
    private static final String INVALID_SURPRISE_CARD_XML_FILE_PATH = "C:\\Develop\\NetBeans\\MonopolyGameEx1\\test\\com\\monopoly\\controller\\InvalidSurpriseCardMonopolyInit.xml";

    private XmlMonopolyInitReader monopolyInitReader;

    @Test
    public void testReadXMLFile_shouldNotThrowException() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
    }

    @Test
    public void testGetCells_countTheCells() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
        List<Cell> cells = monopolyInitReader.getCells();
        assertEquals(cells.size(), 36);
    }

    @Test
    public void testRoadStartCell_shouldBeAtPosition0() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
        Cell roadStartCell = monopolyInitReader.getCells().get(ROAD_START_CELL_INDEX);
        assertThat(roadStartCell, instanceOf(RoadStart.class));
    }

    @Test
    public void testCity_shouldBeAtPosition1() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
        Cell firstCity = monopolyInitReader.getCells().get(FIRST_CITY_CELL_INDEX);
        assertThat(firstCity, instanceOf(City.class));
    }

    @Test
    public void testCityProperties() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
        City firstCity = ((City) monopolyInitReader.getCells().get(FIRST_CITY_CELL_INDEX));
        testProperties(firstCity);
    }

    private void testProperties(City city)
    {
        assertEquals(city.getName(), FIRST_CITY_NAME);
        assertEquals(city.getPrice(), FIRST_CITY_PRICE);
        assertArrayEquals(city.getRentPrices(), FIRST_CITY_RENT_PRICES);
        assertEquals(city.getPropertyGroup().getName(), FIRST_COUNTRY_NAME);
    }

    @Test
    public void testGetAlertCards_countCardAmount() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
        CardPack<AlertCard> alertCards = monopolyInitReader.getAlertCards();
        assertEquals(alertCards.getSize(), 5);
    }

    @Test
    public void testGetSurpriseCards_countCardAmount() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
        CardPack<SurpriseCard> surpriseCards = monopolyInitReader.getSurpriseCards();
        assertEquals(surpriseCards.getSize(), 7);
    }

    @Test
    public void testGetKeyCells_shouldNotThrowException() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(VALID_XML_FILE_PATH);
        monopolyInitReader.read();
        monopolyInitReader.getKeyCells();
        monopolyInitReader.getKeyCells();
    }

    @Test(expected = CouldNotReadMonopolyInitReader.class)
    public void testReadInvalidAlertCardFile() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(INVALID_ALERT_CARD_XML_FILE_PATH);
        monopolyInitReader.read();
    }

    @Test(expected = CouldNotReadMonopolyInitReader.class)
    public void testReadInvalidSurpriseCardFile() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(INVALID_SURPRISE_CARD_XML_FILE_PATH);
        monopolyInitReader.read();
    }
}