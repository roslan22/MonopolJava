package com.monopoly.controller;

import com.monopoly.logic.engine.MonopolyInitReader;
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

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XmlMonopolyInitReader implements MonopolyInitReader
{
    public static final  String BOARD_TAG_NAME                       = "Board";
    public static final  String NAME_ATTRIBUTE_NAME                  = "name";
    public static final  String PROPERTY_PRICE_ATTRIBUTE_NAME        = "property_price";
    public static final  String HOUSE_PRICE_ATTRIBUTE_NAME           = "house_price";
    public static final  String RENT_ATTRIBUTE_NAME                  = "rent";
    public static final  String RENT_ONE_HOUSE_ATTRIBUTE_NAME        = "rent_one_house";
    public static final  String RENT_TWO_HOUSES_ATTRIBUTE_NAME       = "rent_two_houses";
    public static final  String RENT_THREE_HOUSES_ATTRIBUTE_NAME     = "rent_three_houses";
    public static final  int    ONLY_ELEMENT_INDEX                   = 0;
    private static final String MONOPOLY_RENT_ATTRIBUTE_NAME         = "monopoly_rent";
    public static final  String TYPE_ATTRIBUTE_NAME                  = "type";
    public static final  String SURPRISE_CARD_PACK_TAG_NAME          = "SurpriseCardPack";
    public static final  String CARD_TEXT_ATTRIBUTE_NAME             = "text";
    public static final  String AMOUNT_ATTRIBUTE_NAME                = "amount";
    public static final  String IS_FROM_OTHER_PLAYERS_ATTRIBUTE_NAME = "is_from_other_players";
    private static final String ALERT_CARD_PACK_TAG_NAME             = "AlertCardPack";
    private static final String IS_TO_OTHER_PLAYERS_ATTRIBUTE_NAME   = "is_to_other_players";
    public static final  String GO_TO_ALERT_CARD                     = "GoToAlertCard";
    public static final  String GO_TO_JAIL_CARD                      = "GoToJailCard";
    public static final  String MONEY_PENALTY_CARD                   = "MoneyPenaltyCard";
    public static final  String GO_TO_ROAD_START_CARD                = "GoToRoadStartCard";
    public static final  String GO_TO_SURPRISE_CARD                  = "GoToSurpriseCard";
    public static final  String GET_OUT_OF_JAIL_CARD                 = "GetOutOfJailCard";
    public static final  String MONEY_EARN_CARD                      = "MoneyEarnCard";
    public static final  String ROAD_START                           = "RoadStart";
    public static final  String COMPANY                              = "Company";
    public static final  String COUNTRY                              = "Country";
    public static final  String SURPRISE                             = "Surprise";
    public static final  String ALERT                                = "Alert";
    public static final  String JAIL                                 = "Jail";
    public static final  String PARKING                              = "Parking";
    public static final  String GO_TO_JAIL                           = "GoToJail";

    private KeyCells keyCells;
    private List<AlertCell>    alertCells    = new ArrayList<>();
    private List<SurpriseCell> surpriseCells = new ArrayList<>();
    private Jail     jailCell;
    private JailGate jailGate;
    private Parking  parkingCell;

    private String path;
    private List<Cell>                 cells                  = new ArrayList<>();
    private Map<String, List<Company>> companyTypeToCompanies = new HashMap<>();

    private CardPack<SurpriseCard> surpriseCardPack;
    private List<SurpriseCard> surpriseCards = new ArrayList<>();
    private CardPack<AlertCard> alertCardPack;
    private List<AlertCard> alertCards = new ArrayList<>();

    public XmlMonopolyInitReader(String path)
    {
        this.path = path;
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
                    .setJailGate(jailGate).setParkingCell(parkingCell).createKeyCells();
        }
        return keyCells;
    }

    @Override
    public void read()
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = getDocumentBuilder(factory);
        Document document = getParseDocument(builder);
        parseMonopolyXML(document);
    }

    private void parseMonopolyXML(Document document)
    {
        parseSurpriseCards(document);
        parseAlertCards(document);
        parseCells(document);
    }

    private void parseAlertCards(Document document)
    {
        NodeList cardsNdeList = getOnlyElementByTagName(document, ALERT_CARD_PACK_TAG_NAME).getChildNodes();
        for (int i = 0; i < cardsNdeList.getLength(); i++)
        {
            if (cardsNdeList.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            addAlertCard(cardsNdeList.item(i));
        }
        alertCardPack = new CardPack<>(alertCards);
    }

    private void addAlertCard(Node cardNode)
    {
        String cardText = cardNode.getAttributes().getNamedItem(CARD_TEXT_ATTRIBUTE_NAME).getNodeValue();
        switch (cardNode.getNodeName())
        {
            case GO_TO_ALERT_CARD:
                alertCards.add(new GotoAlertCard(cardText));
                break;
            case GO_TO_JAIL_CARD:
                alertCards.add(new GotoJailCard(cardText));
                break;
            case MONEY_PENALTY_CARD:
                addMoneyPenaltyCard(cardNode, cardText);
                break;
            default:
                throw new CouldNotParseXMLFile("No such a card " + cardNode.getNodeName());
        }

    }

    private void addMoneyPenaltyCard(Node cardNode, String cardText)
    {
        int amount = getNumericAttribute(cardNode.getAttributes(), AMOUNT_ATTRIBUTE_NAME);
        boolean isToOthers = Boolean
                .parseBoolean(cardNode.getAttributes().getNamedItem(IS_TO_OTHER_PLAYERS_ATTRIBUTE_NAME).getNodeValue());
        alertCards.add(new MoneyPenaltyCard(cardText, amount, isToOthers));
    }

    private void parseSurpriseCards(Document document)
    {
        NodeList cardsNdeList = getOnlyElementByTagName(document, SURPRISE_CARD_PACK_TAG_NAME).getChildNodes();
        for (int i = 0; i < cardsNdeList.getLength(); i++)
        {
            if (cardsNdeList.item(i).getNodeType() == Node.TEXT_NODE)
            {
                continue;
            }
            addSurpriseCard(cardsNdeList.item(i));
        }
        surpriseCardPack = new CardPack<>(surpriseCards);
    }

    private void addSurpriseCard(Node cardNode)
    {
        String cardText = cardNode.getAttributes().getNamedItem(CARD_TEXT_ATTRIBUTE_NAME).getNodeValue();
        switch (cardNode.getNodeName())
        {
            case GO_TO_ROAD_START_CARD:
                surpriseCards.add(new GotoGameStartCard(cardText));
                break;
            case GO_TO_SURPRISE_CARD:
                surpriseCards.add(new GotoSurpriseCard(cardText));
                break;
            case GET_OUT_OF_JAIL_CARD:
                surpriseCards.add(new OutOfJailCard(cardText));
                break;
            case MONEY_EARN_CARD:
                addMoneyEarnCard(cardNode, cardText);
                break;
            default:
                throw new CouldNotParseXMLFile("No such a card " + cardNode.getNodeName());
        }
    }

    private void addMoneyEarnCard(Node cardNode, String cardText)
    {
        int amount = getNumericAttribute(cardNode.getAttributes(), AMOUNT_ATTRIBUTE_NAME);
        boolean isFromOthers = Boolean
                .parseBoolean(cardNode.getAttributes().getNamedItem(IS_FROM_OTHER_PLAYERS_ATTRIBUTE_NAME).getNodeValue());
        surpriseCards.add(new MoneyEarnCard(cardText, amount, isFromOthers));
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
        addCompanyTypes();
    }

    private void addCompanyTypes()
    {
        for (String companyType : companyTypeToCompanies.keySet())
        {
            PropertyGroup propertyGroup = new PropertyGroup(companyType, companyTypeToCompanies.get(companyType));
            companyTypeToCompanies.get(companyType).forEach(company -> company.setPropertyGroup(propertyGroup));
        }
    }

    private void addCell(Node currentNode)
    {
        switch (currentNode.getNodeName())
        {
            case ROAD_START:
                cells.add(new RoadStart());
                break;
            case COMPANY:
                addCompany(currentNode);
                break;
            case COUNTRY:
                addCountry(currentNode);
                break;
            case SURPRISE:
                SurpriseCell surpriseCell = new SurpriseCell(surpriseCardPack);
                surpriseCells.add(surpriseCell);
                cells.add(surpriseCell);
                break;
            case ALERT:
                AlertCell alertCell = new AlertCell(alertCardPack);
                alertCells.add(alertCell);
                cells.add(alertCell);
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
                throw new CouldNotParseXMLFile("Unknown Cell Type " + currentNode.getNodeName());
        }
    }

    private Node getOnlyElementByTagName(Document document, String tagName)
    {
        NodeList boardNodeList = document.getElementsByTagName(tagName);
        if (boardNodeList.getLength() != 1)
        {
            throw new CouldNotParseXMLFile("There should be only one " + tagName);
        }
        return boardNodeList.item(ONLY_ELEMENT_INDEX);
    }

    private void addCompany(Node companyNode)
    {
        NamedNodeMap companyAttributes = companyNode.getAttributes();

        Company company = getCompany(companyAttributes);
        cells.add(company);
        String companyType = companyAttributes.getNamedItem(TYPE_ATTRIBUTE_NAME).getNodeValue();
        saveCompanyType(companyType, company);
    }

    private void saveCompanyType(String companyType, Company company)
    {
        List<Company> companies = companyTypeToCompanies.getOrDefault(companyType, new ArrayList<>());
        companies.add(company);
        companyTypeToCompanies.put(companyType, companies);
    }

    private Company getCompany(NamedNodeMap companyAttributes)
    {
        String companyName = companyAttributes.getNamedItem(NAME_ATTRIBUTE_NAME).getNodeValue();
        int propertyPrice = getNumericAttribute(companyAttributes, PROPERTY_PRICE_ATTRIBUTE_NAME);
        int rentPrice = getNumericAttribute(companyAttributes, RENT_ATTRIBUTE_NAME);
        int monopolyRentPrice = getNumericAttribute(companyAttributes, MONOPOLY_RENT_ATTRIBUTE_NAME);
        return new Company(companyName, propertyPrice, rentPrice, monopolyRentPrice);
    }

    private void addCountry(Node countryNode)
    {
        List<City> cities = getCitiesForCountry(countryNode);
        String countryName = countryNode.getAttributes().getNamedItem(NAME_ATTRIBUTE_NAME).getNodeValue();
        createCountry(countryName, cities);
        cells.addAll(cities);
    }

    private void createCountry(String name, List<City> cities)
    {
        PropertyGroup country = new PropertyGroup(name, cities);
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
            throw new CouldNotParseXMLFile("Tried to parse numeric attribute " + attributeName + " but the value is not a number");
        }
    }

    private Document getParseDocument(DocumentBuilder builder)
    {
        try
        {
            return builder.parse(path);
        } catch (SAXException e)
        {
            throw new XmlMonopolyInitReader.CouldNotParseXMLFile();
        } catch (IOException e)
        {
            throw new XmlMonopolyInitReader.PathNotFound();
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

    public class PathNotFound extends RuntimeException
    {
    }

    public class CouldNotParseXMLFile extends RuntimeException
    {
        public CouldNotParseXMLFile()
        {
            super();
        }

        public CouldNotParseXMLFile(String message)
        {
            super(message);
        }
    }
}
