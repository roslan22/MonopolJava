package com.monopoly.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XmlMonopolyInitReaderTest
{
    public static final String path = "C:\\Develop\\NetBeans\\MonopolyGameEx1\\test\\com\\monopoly\\controller\\MonopolyInit.xml";
    XmlMonopolyInitReader monopolyInitReader;

    @Before
    public void setUp() throws Exception
    {
        monopolyInitReader = new XmlMonopolyInitReader(path);
        monopolyInitReader.read();
    }

    @Test
    public void testGetCells() throws Exception
    {
        Assert.assertEquals(monopolyInitReader.getCells().size(), 36);
    }

    @Test
    public void testGetAlertCards() throws Exception
    {

    }

    @Test
    public void testGetSurpriseCards() throws Exception
    {

    }

    @Test
    public void testGetKeyCells() throws Exception
    {

    }

    @Test
    public void testRead() throws Exception
    {

    }
}