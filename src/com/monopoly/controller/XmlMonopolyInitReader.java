package com.monopoly.controller;

import com.monopoly.logic.engine.monopolyInitReader.CouldNotReadMonopolyInitReader;
import com.monopoly.logic.engine.monopolyInitReader.MonopolyInitReader;
import com.monopoly.logic.model.board.KeyCells;
import com.monopoly.logic.model.board.KeyCellsBuilder;
import com.monopoly.logic.model.card.AlertCard;
import com.monopoly.logic.model.card.CardPack;
import com.monopoly.logic.model.card.GotoAlertCard;
import com.monopoly.logic.model.card.GotoGameStartCard;
import com.monopoly.logic.model.card.GotoJailCard;
import com.monopoly.logic.model.card.GotoSurpriseCard;
import com.monopoly.logic.model.card.MoneyEarnCard;
import com.monopoly.logic.model.card.MoneyPenaltyCard;
import com.monopoly.logic.model.card.OutOfJailCard;
import com.monopoly.logic.model.card.SurpriseCard;
import com.monopoly.logic.model.cell.AlertCell;
import com.monopoly.logic.model.cell.Cell;
import com.monopoly.logic.model.cell.City;
import com.monopoly.logic.model.cell.Company;
import com.monopoly.logic.model.cell.Jail;
import com.monopoly.logic.model.cell.JailGate;
import com.monopoly.logic.model.cell.Parking;
import com.monopoly.logic.model.cell.PropertyGroup;
import com.monopoly.logic.model.cell.RoadStart;
import com.monopoly.logic.model.cell.SurpriseCell;
import com.monopoly.utils.Utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlMonopolyInitReader implements MonopolyInitReader
{
    public static final String BOARD_TAG_NAME                       = "board";
    public static final String NAME_ATTRIBUTE_NAME                  = "name";
    public static final String PROPERTY_PRICE_ATTRIBUTE_NAME        = "cost";
    public static final String HOUSE_PRICE_ATTRIBUTE_NAME           = "houseCost";
    public static final String RENT_ATTRIBUTE_NAME                  = "stayCost";
    public static final String RENT_ONE_HOUSE_ATTRIBUTE_NAME        = "stayCost1";
    public static final String RENT_TWO_HOUSES_ATTRIBUTE_NAME       = "stayCost2";
    public static final String RENT_THREE_HOUSES_ATTRIBUTE_NAME     = "stayCost3";
    public static final int    ONLY_ELEMENT_INDEX                   = 0;
    public static final String TYPE_ATTRIBUTE_NAME                  = "type";
    public static final String SURPRISE_CARD_PACK_TAG_NAME          = "surprise";
    public static final String CARD_TEXT_ATTRIBUTE_NAME             = "text";
    public static final String AMOUNT_ATTRIBUTE_NAME                = "sum";
    public static final String MONEY_SOURCE                         = "who";
    public static final String ALERT_CARD_PACK_TAG_NAME             = "warrant";
    public static final String GO_TO_CARD                           = "goto";
    public static final String GET_OUT_OF_JAIL_CARD                 = "getOutOfJail";
    public static final String MONETARY_CARD                        = "monetary";
    public static final String ROAD_START                           = "startSquare";
    public static final String SQUARE                               = "square";
    public static final String UTILITY_TYPE                         = "UTILITY";
    public static final String TRANSPORTATION_TYPE                  = "TRANSPORTATION";
    public static final String CITY_TYPE                            = "CITY";
    public static final String SURPRISE_TYPE                        = "SURPRISE";
    public static final String ALERT_TYPE                           = "WARRANT";
    public static final String JAIL                                 = "jailSlashFreeSpaceSquare";
    public static final String PARKING                              = "parkingSquare";
    public static final String GO_TO_JAIL                           = "gotoJailSquare";
    public static final String XSD_FILE_PATH                        = Utils
            .getAbsolutePath(XmlMonopolyInitReader.class, "/com/monopoly/res/monopoly_config.xsd");
    public static final String GO_TO_CARD_DESTINATION               = "to";
    public static final String START_SURPRISE_DESTINATION           = "START";
    public static final String NEXT_SURPRISE_DESTINATION            = "NEXT_SURPRISE";
    public static final String TREASURY_MONEY_SOURCE                = "TREASURY";
    public static final String PLAYERS_MONEY_SOURCE                 = "PLAYERS";
    public static final String NUM_OF_CARD_DISPLAYS                 = "num";
    public static final String NEXT_WARRANT_DESTINATION             = "NEXT_WARRANT";
    public static final String JAIL_DESTINATION                     = "JAIL";
    public static final String ASSETS_TAG_NAME                      = "assets";
    public static final String COUNTRIES_ASSET_GROUP_TAG_NAME       = "countries";
    public static final String UTILITIES_ASSET_GROUP_TAG_NAME       = "utilities";
    public static final String TRANSPORTATIONS_ASSET_GROUP_TAG_NAME = "transportations";
    public static final String MONOPOLY_RENT_COST                   = "stayCost";
    public static final String UTILITY                              = "Utility";
    public static final String TRANSPORTATION                       = "Transportation";

    private String xmlFilePath;

    private List<Cell> cells = new ArrayList<>();
    private KeyCells keyCells;
    private List<AlertCell>    alertCells    = new ArrayList<>();
    private List<SurpriseCell> surpriseCells = new ArrayList<>();
    private Jail     jailCell;
    private JailGate jailGate;
    private Parking  parkingCell;

    private CardPack<SurpriseCard> surpriseCardPack;
    private List<SurpriseCard> surpriseCards = new ArrayList<>();
    private CardPack<AlertCard> alertCardPack;
    private List<AlertCard> alertCards = new ArrayList<>();
    private List<PropertyGroup> propertyGroups = new ArrayList<>();

    private Queue<City>    allCities       = new LinkedList<>();
    private Queue<Company> utilities       = new LinkedList<>();
    private Queue<Company> transportations = new LinkedList<>();

    public XmlMonopolyInitReader(String xmlFilePath)
    {
        this.xmlFilePath = xmlFilePath;
    }

    @Override
    public List<Cell> getCells()
    {
        return cells;
    }

    @Override
    public CardPack<AlertCard> getAlertCards()
    {
        return alertCardPack;
    }

    @Override
    public CardPack<SurpriseCard> getSurpriseCards()
    {
        return surpriseCardPack;
    }

    @Override
    public KeyCells getKeyCells()
    {
        if (keyCells == null)
        {
            keyCells = new KeyCellsBuilder().setAlertCells(alertCells).setSurpriseCells(surpriseCells).setJailCell(jailCell)
                    .setJailGate(jailGate).setParkingCell(parkingCell).setPropertyGroups(propertyGroups).createKeyCells();
        }
        return keyCells;
    }

    @Override
    public void read() throws CouldNotReadMonopolyInitReader
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = getDocumentBuilder(factory);
            Document document = getParseDocument(builder);
            parseMonopolyXML(document);
        } catch (CouldNotReadMonopolyXML e)
        {
            throw new CouldNotReadMonopolyInitReader(e.getMessage());
        }
    }

    private void parseMonopolyXML(Document document)
    {
        validateXMLAgainstXSD();
        parseSurpriseCards(document);
        parseAlertCards(document);
        parseAssets(document);
        parseCells(document);
    }

    private void parseAssets(Document document)
    {
        NodeList cardsNodeList = getOnlyElementByTagName(document, ASSETS_TAG_NAME).getChildNodes();
        for (int i = 0; i < cardsNodeList.getLength(); i++)
        {
            // Ignore white spaces
            if (cardsNodeList.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            addAssets(cardsNodeList.item(i));
        }
    }

    private void addAssets(Node assetGroup)
    {
        String assetGroupTagName = assetGroup.getNodeName();
        switch (assetGroupTagName)
        {
            case COUNTRIES_ASSET_GROUP_TAG_NAME:
                addCountries(assetGroup);
                break;
            case UTILITIES_ASSET_GROUP_TAG_NAME:
                addCompanyGroup(utilities, UTILITY, assetGroup);
                break;
            case TRANSPORTATIONS_ASSET_GROUP_TAG_NAME:
                addCompanyGroup(transportations, TRANSPORTATION, assetGroup);
                break;
            default:
                throw new CouldNotReadMonopolyXML("Unknown asset group");
        }
    }

    private void addCompanyGroup(Queue<Company> companies, String companyType, Node utilitiesContainer)
    {
        int monopolyPrice = getNumericAttribute(utilitiesContainer.getAttributes(), MONOPOLY_RENT_COST);
        addCompanies(companies, utilitiesContainer, monopolyPrice);
        PropertyGroup companyGroup = new PropertyGroup(companyType, new ArrayList<>(companies));
        propertyGroups.add(companyGroup);
        companies.forEach(company -> company.setPropertyGroup(companyGroup));
    }

    private void addCompanies(Queue<Company> companies, Node utilitiesContainer, int monopolyPrice)
    {
        NodeList utilitiesNodeList = utilitiesContainer.getChildNodes();
        for (int i = 0; i < utilitiesNodeList.getLength(); i++)
        {
            if (utilitiesNodeList.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            companies.add(createCompanyBranch(utilitiesNodeList.item(i), monopolyPrice));
        }
    }

    private Company createCompanyBranch(Node companyBranchNode, int monopolyRentPrice)
    {
        String companyName = companyBranchNode.getAttributes().getNamedItem(NAME_ATTRIBUTE_NAME).getNodeValue();
        int price = getNumericAttribute(companyBranchNode.getAttributes(), PROPERTY_PRICE_ATTRIBUTE_NAME);
        int rent = getNumericAttribute(companyBranchNode.getAttributes(), RENT_ATTRIBUTE_NAME);
        return new Company(companyName, price, rent, monopolyRentPrice);
    }

    private void addCountries(Node countriesContainer)
    {
        NodeList countries = countriesContainer.getChildNodes();
        for (int i = 0; i < countries.getLength(); i++)
        {
            if (countries.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            addCountry(countries.item(i));
        }
    }

    private void addCountry(Node countryNode)
    {
        List<City> cities = getCitiesForCountry(countryNode);
        String countryName = countryNode.getAttributes().getNamedItem(NAME_ATTRIBUTE_NAME).getNodeValue();
        createCountry(countryName, cities);
        this.allCities.addAll(cities);
    }

    private void validateXMLAgainstXSD()
    {
        if (Files.exists(Paths.get(XSD_FILE_PATH)) && !Utils.validateXMLAgainstXSD(xmlFilePath, XSD_FILE_PATH))
        {
            throw new CouldNotReadMonopolyXML("Validation against the XSD failed. The xml's format is wrong");
        }
    }

    private void parseAlertCards(Document document)
    {
        NodeList cardsNodeList = getOnlyElementByTagName(document, ALERT_CARD_PACK_TAG_NAME).getChildNodes();
        for (int i = 0; i < cardsNodeList.getLength(); i++)
        {
            if (cardsNodeList.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            addAlertCards(cardsNodeList.item(i));
        }
        alertCardPack = new CardPack<>(alertCards);
    }

    private void addAlertCards(Node cardNode)
    {
        for (int i = 0; i < getNumericAttribute(cardNode.getAttributes(), NUM_OF_CARD_DISPLAYS); i++)
        {
            addAlertCard(cardNode);
        }
    }

    private void addAlertCard(Node cardNode)
    {
        String cardText = cardNode.getAttributes().getNamedItem(CARD_TEXT_ATTRIBUTE_NAME).getNodeValue();
        switch (cardNode.getNodeName())
        {
            case GO_TO_CARD:
                addGotoAlertCard(cardNode, cardText);
                break;
            case MONETARY_CARD:
                addMoneyPenaltyCard(cardNode, cardText);
                break;
            default:
                throw new CouldNotReadMonopolyXML("No such a card " + cardNode.getNodeName());
        }
    }

    private void addGotoAlertCard(Node cardNode, String cardText)
    {
        String destination = cardNode.getAttributes().getNamedItem(GO_TO_CARD_DESTINATION).getNodeValue();
        switch (destination)
        {
            case NEXT_WARRANT_DESTINATION:
                alertCards.add(new GotoAlertCard(cardText));
                break;
            case JAIL_DESTINATION:
                alertCards.add(new GotoJailCard(cardText));
                break;
            default:
                throw new CouldNotReadMonopolyXML("Unknown destination" + destination);
        }
    }

    private void addMoneyPenaltyCard(Node cardNode, String cardText)
    {
        int amount = getNumericAttribute(cardNode.getAttributes(), AMOUNT_ATTRIBUTE_NAME);
        String moneySource = cardNode.getAttributes().getNamedItem(MONEY_SOURCE).getNodeValue();
        if (!moneySource.equals(TREASURY_MONEY_SOURCE) && !moneySource.equals(PLAYERS_MONEY_SOURCE))
        {
            throw new CouldNotReadMonopolyXML("Unknown money source" + moneySource);
        }
        alertCards.add(new MoneyPenaltyCard(cardText, amount, moneySource.equals(PLAYERS_MONEY_SOURCE)));
    }

    private void parseSurpriseCards(Document document)
    {
        NodeList cardsNodeList = getOnlyElementByTagName(document, SURPRISE_CARD_PACK_TAG_NAME).getChildNodes();
        for (int i = 0; i < cardsNodeList.getLength(); i++)
        {
            // Ignore white spaces
            if (cardsNodeList.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            addSurpriseCards(cardsNodeList.item(i));
        }
        surpriseCardPack = new CardPack<>(surpriseCards);
    }

    private void addSurpriseCards(Node cardNode)
    {
        for (int i = 0; i < getNumericAttribute(cardNode.getAttributes(), NUM_OF_CARD_DISPLAYS); i++)
        {
            addSurpriseCard(cardNode);
        }
    }

    private void addSurpriseCard(Node cardNode)
    {
        String cardText = cardNode.getAttributes().getNamedItem(CARD_TEXT_ATTRIBUTE_NAME).getNodeValue();
        switch (cardNode.getNodeName())
        {
            case GO_TO_CARD:
                addGotoSurpriseCard(cardNode, cardText);
                break;
            case GET_OUT_OF_JAIL_CARD:
                surpriseCards.add(new OutOfJailCard(cardText));
                break;
            case MONETARY_CARD:
                addMoneyEarnCard(cardNode, cardText);
                break;
            default:
                throw new CouldNotReadMonopolyXML("No such a card " + cardNode.getNodeName());
        }
    }

    private void addGotoSurpriseCard(Node cardNode, String cardText)
    {
        String destination = cardNode.getAttributes().getNamedItem(GO_TO_CARD_DESTINATION).getNodeValue();
        switch (destination)
        {
            case START_SURPRISE_DESTINATION:
                surpriseCards.add(new GotoGameStartCard(cardText));
                break;
            case NEXT_SURPRISE_DESTINATION:
                surpriseCards.add(new GotoSurpriseCard(cardText));
                break;
            default:
                throw new CouldNotReadMonopolyXML("Unknown destination" + destination);
        }
    }

    private void addMoneyEarnCard(Node cardNode, String cardText)
    {
        int amount = getNumericAttribute(cardNode.getAttributes(), AMOUNT_ATTRIBUTE_NAME);
        String moneySource = cardNode.getAttributes().getNamedItem(MONEY_SOURCE).getNodeValue();
        if (!moneySource.equals(TREASURY_MONEY_SOURCE) && !moneySource.equals(PLAYERS_MONEY_SOURCE))
        {
            throw new CouldNotReadMonopolyXML("Unknown money source" + moneySource);
        }
        surpriseCards.add(new MoneyEarnCard(cardText, amount, moneySource.equals(PLAYERS_MONEY_SOURCE)));
    }

    private void parseCells(Document document)
    {
        NodeList cellsNodeList = getOnlyElementByTagName(document, BOARD_TAG_NAME).getChildNodes();
        for (int i = 0; i < cellsNodeList.getLength(); i++)
        {
            if (cellsNodeList.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            addCell(cellsNodeList.item(i));
        }
    }

    private void addCell(Node currentNode)
    {
        switch (currentNode.getNodeName())
        {
            case ROAD_START:
                cells.add(new RoadStart());
                break;
            case SQUARE:
                addSquare(currentNode);
                break;
            case JAIL:
                jailCell = new Jail();
                cells.add(jailCell);
                break;
            case PARKING:
                parkingCell = new Parking();
                cells.add(parkingCell);
                break;
            case GO_TO_JAIL:
                jailGate = new JailGate();
                cells.add(jailGate);
                break;
            default:
                throw new CouldNotReadMonopolyXML("Unknown Cell Type " + currentNode.getNodeName());
        }
    }

    private void addSquare(Node squareNode)
    {
        String squareType = squareNode.getAttributes().getNamedItem(TYPE_ATTRIBUTE_NAME).getNodeValue();
        switch (squareType)
        {
            case CITY_TYPE:
                cells.add(allCities.poll());
                break;
            case TRANSPORTATION_TYPE:
                cells.add(transportations.poll());
                break;
            case UTILITY_TYPE:
                cells.add(utilities.poll());
                break;
            case SURPRISE_TYPE:
                SurpriseCell surpriseCell = new SurpriseCell(surpriseCardPack);
                surpriseCells.add(surpriseCell);
                cells.add(surpriseCell);
                break;
            case ALERT_TYPE:
                AlertCell alertCell = new AlertCell(alertCardPack);
                alertCells.add(alertCell);
                cells.add(alertCell);
                break;
            default:
                throw new CouldNotReadMonopolyXML("Unknown square type " + squareType);
        }
    }

    private Node getOnlyElementByTagName(Document document, String tagName)
    {
        NodeList boardNodeList = document.getElementsByTagName(tagName);
        if (boardNodeList.getLength() != 1)
        {
            throw new CouldNotReadMonopolyXML("There should be only one " + tagName);
        }
        return boardNodeList.item(ONLY_ELEMENT_INDEX);
    }

    private void createCountry(String name, List<City> cities)
    {
        PropertyGroup country = new PropertyGroup(name, cities);
        propertyGroups.add(country);
        cities.forEach(c -> c.setPropertyGroup(country));
    }

    private List<City> getCitiesForCountry(Node countryNode)
    {
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < countryNode.getChildNodes().getLength(); i++)
        {
            if (countryNode.getChildNodes().item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            cities.add(createCity(countryNode.getChildNodes().item(i)));
        }
        return cities;
    }

    private City createCity(Node cityNode)
    {
        NamedNodeMap cityAttributes = cityNode.getAttributes();
        String cityName = cityAttributes.getNamedItem(NAME_ATTRIBUTE_NAME).getNodeValue();
        int propertyPrice = getNumericAttribute(cityAttributes, PROPERTY_PRICE_ATTRIBUTE_NAME);
        int housePrice = getNumericAttribute(cityAttributes, HOUSE_PRICE_ATTRIBUTE_NAME);
        int[] rentPrices = getRentPrices(cityAttributes);
        return new City(cityName, propertyPrice, housePrice, rentPrices);
    }

    private int[] getRentPrices(NamedNodeMap cityAttributes)
    {
        return new int[]{
                getNumericAttribute(cityAttributes, RENT_ATTRIBUTE_NAME),
                getNumericAttribute(cityAttributes, RENT_ONE_HOUSE_ATTRIBUTE_NAME),
                getNumericAttribute(cityAttributes, RENT_TWO_HOUSES_ATTRIBUTE_NAME),
                getNumericAttribute(cityAttributes, RENT_THREE_HOUSES_ATTRIBUTE_NAME)};
    }

    private int getNumericAttribute(NamedNodeMap attributes, String attributeName)
    {
        try
        {
            return Integer.parseInt(attributes.getNamedItem(attributeName).getNodeValue());
        } catch (NumberFormatException e)
        {
            throw new CouldNotReadMonopolyXML("Tried to parse numeric attribute " + attributeName + " but the value is not a number");
        }
    }

    private Document getParseDocument(DocumentBuilder builder) throws CouldNotReadMonopolyXML
    {
        try
        {
            return builder.parse(xmlFilePath);
        } catch (SAXException | IOException e)
        {
            throw new CouldNotReadMonopolyXML(e.getMessage());
        }
    }

    private DocumentBuilder getDocumentBuilder(DocumentBuilderFactory factory)
    {
        try
        {
            return factory.newDocumentBuilder();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private class CouldNotReadMonopolyXML extends RuntimeException
    {
        public CouldNotReadMonopolyXML(String message)
        {
            super(message);
        }
    }
}
