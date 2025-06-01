package caveatemptor.controllers;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import javax.persistence.EntityManagerFactory;

import caveatemptor.dao.BidDAO;
import caveatemptor.dao.ItemDAO;
import caveatemptor.models.AuctionType;
import caveatemptor.models.Bid;
import caveatemptor.models.Item;
import caveatemptor.models.advanced.MonetaryAmount;
import caveatemptor.utils.ConsoleUtils;

public class ItemController implements GenericController<Item> {
    private ItemDAO itemDAO;
    private BidDAO bidDAO;
    private Scanner scan;

    private static final String GET_ITEM_BIDS_START_MESSAGE_FIRST_PART = "Item bids with item_id=";
    private static final String GET_ITEM_BIDS_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_ITEM_START_MESSAGE_FIRST_PART = "Item with id=";
    private static final String GET_ITEM_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_ITEM_START_MESSAGE = "Items:";
    private static final String REMOVE_ITEM_START_MESSAGE = "Item remove:";
    private static final String UPDATE_ITEM_START_MESSAGE = "Item update:";
    private static final String UPDATE_ITEM_SHOW_OLD_STATE_MESSAGE = "Old Item = ";
    private static final String UPDATE_ITEM_GET_ID_MESSAGE = "Input new Item Id (X if need old) - ";
    private static final String UPDATE_ITEM_GET_NAME_MESSAGE = "Input new Item name (X if need old) - ";
    private static final String UPDATE_ITEM_GET_AUCTION_END_MESSAGE = "Input new Auction end date (like '1999-01-01', X if need old) - ";
    private static final String UPDATE_ITEM_GET_AUCTION_TYPE_MESSAGE = "Input new Auction type (like 'HIGHEST_BID', 'LOWEST_BID' or 'FIXED_PRICE', X if need old) - ";
    private static final String UPDATE_ITEM_GET_BUY_NOW_PRICE_MESSAGE = "Input new Buy now price (like '12 USD', X if need old) - ";
    private static final String UPDATE_ITEM_GET_INITIAL_PRICE_MESSAGE = "Input new Initial price (like '12 USD', X if need old) - ";
    private static final String UPDATE_ITEM_GET_IMAGES_MESSAGE = "Input new Images (like 'image-image.png,image2-2/image.png', X if need old) - ";
    private static final String UPDATE_ITEM_GET_BIDS_MESSAGE = "Input new Bids identifiers (like '1,2,43', X if need old) - ";
    private static final String SAVE_DATA_OLD_STATE_INPUT_VALUE = "X\n";
    private static final String DATE_POSTFIX = "T00:00:00Z";
    private static final String ELEMENTS_INPUT_SEPARATOR_REGEXP = ",";
    private static final String KEY_VALUE_INPUT_SEPARATOR_REGEXP = "-";
    private static final String CREATE_ITEM_START_MESSAGE = "Item create:";
    private static final String CREATE_ITEM_GET_ID_MESSAGE = "Input new Item Id - ";
    private static final String CREATE_ITEM_GET_NAME_MESSAGE = "Input new Item name - ";
    private static final String CREATE_ITEM_GET_AUCTION_END_MESSAGE = "Input new Auction end date (like '1999-01-01') - ";
    private static final String CREATE_ITEM_GET_AUCTION_TYPE_MESSAGE = "Input new Auction type (like 'HIGHEST_BID', 'LOWEST_BID' or 'FIXED_PRICE') - ";
    private static final String CREATE_ITEM_GET_BUY_NOW_PRICE_MESSAGE = "Input new Buy now price (like '12 USD') - ";
    private static final String CREATE_ITEM_GET_INITIAL_PRICE_MESSAGE = "Input new Initial price (like '12 USD') - ";
    private static final String CREATE_ITEM_GET_IMAGES_MESSAGE = "Input new Images (like 'image-image.png,image2-2/image.png') - ";
    private static final String CREATE_ITEM_GET_BIDS_MESSAGE = "Input new Bids identifiers (like '1,2,43') - ";
    private static final String BAD_INPUT_ERROR_MESSAGE = "Bad input!";
    private static final String OPERATION_ITEM_SUCCESS_MESSAGE = "Success";
    private static final String OPERATION_ITEM_NOT_SUCCESS_MESSAGE = "Not success";
    private static final String OPERATION_ITEM_NEW_BIDS_MESSAGE = "New Item Bids:";

    public ItemController(EntityManagerFactory entityFactory, Scanner scan) {
        this.itemDAO = new ItemDAO(entityFactory);
        this.bidDAO = new BidDAO(entityFactory);
        this.scan = scan;
    }

    public List<String> getItemBids(long id) {
        List<String> outputData = new ArrayList<>();

        Item foundItem = this.itemDAO.get(id);
        List<Bid> itemBids = null;

        if (foundItem != null) {
            itemBids = this.itemDAO.getItemBids(foundItem);
        }

        String startMessage = GET_ITEM_BIDS_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_ITEM_BIDS_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundItem != null && itemBids != null) {
            outputData.add(foundItem.toString());

            for (Bid bid : itemBids) {
                outputData.add(bid.toString());
            }
        }

        return outputData;
    }

    @Override
    public List<String> get(long id) {
        List<String> outputData = new ArrayList<>();

        Item foundItem = this.itemDAO.get(id);

        String startMessage = GET_ITEM_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_ITEM_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundItem != null) {
            outputData.add(foundItem.toString());
        }

        return outputData;
    }

    @Override
    public List<String> getAll() {
        List<String> outputData = new ArrayList<>();

        List<Item> items = this.itemDAO.getAll();

        String startMessage = GET_ITEM_START_MESSAGE;

        outputData.add(startMessage);

        for (Item item : items) {
            outputData.add(item.toString());
        }

        return outputData;
    }

    @Override
    public List<String> remove(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(REMOVE_ITEM_START_MESSAGE);

        if (this.itemDAO.remove(id)) {
            outputData.add(OPERATION_ITEM_SUCCESS_MESSAGE);
        } else {
            outputData.add(OPERATION_ITEM_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    @Override
    public List<String> update(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(UPDATE_ITEM_START_MESSAGE);

        Item foundItem = this.itemDAO.get(id);

        if (foundItem == null) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(UPDATE_ITEM_SHOW_OLD_STATE_MESSAGE + foundItem.toString());

        Long newId = -1L;

        try {
            newId = readId(foundItem);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        String newName = readName(foundItem);

        Date newAuctionEnd = null;

        try {
            newAuctionEnd = readAuctionEnd(foundItem);
        } catch (NullPointerException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (IllegalArgumentException b) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (DateTimeParseException c) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        AuctionType newAuctionType = readAuctionType(foundItem);

        MonetaryAmount newBuyNowPrice = readBuyNowPrice(foundItem);

        MonetaryAmount newInitialPrice = readInitialPrice(foundItem);

        Map<String, String> newImages = null;

        try {
            newImages = readImages(foundItem);
        } catch (NoSuchElementException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (PatternSyntaxException b) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        List<Bid> newBids = null;

        try {
            List<Bid> oldBids = this.itemDAO.getItemBids(foundItem);

            newBids = readAndSetBids(foundItem, oldBids);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (PatternSyntaxException b) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        if (newBids == null) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(OPERATION_ITEM_NEW_BIDS_MESSAGE);
        outputData.add(newBids.toString());

        foundItem.setId(newId);
        foundItem.setName(newName);
        foundItem.setAuctionEnd(newAuctionEnd);
        foundItem.setAuctionType(newAuctionType);
        foundItem.setBuyNowPrice(newBuyNowPrice);
        foundItem.setInitialPrice(newInitialPrice);
        foundItem.setImages(newImages);

        boolean itemUpdated = this.itemDAO.update(foundItem);

        if (itemUpdated) {
            outputData.add(OPERATION_ITEM_SUCCESS_MESSAGE);
        } else {
            outputData.add(OPERATION_ITEM_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    private Long readId(Item foundItem) throws NumberFormatException {
        Long newId = -1L;

        String newIdInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_ID_MESSAGE);

        if (newIdInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newId = foundItem.getId();
        } else {
            newId = Long.parseLong(newIdInput);
        }

        return newId;
    }

    private String readName(Item foundItem) {
        String newName = "";

        String newNameInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_NAME_MESSAGE);

        if (newNameInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newName = foundItem.getName();
        } else {
            newName = newNameInput;
        }

        return newName;
    }

    private Date readAuctionEnd(Item foundItem)
            throws NullPointerException, IllegalArgumentException, DateTimeParseException {
        Date newDate = null;

        String newDateInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_AUCTION_END_MESSAGE);
        newDateInput += "T00:00:00Z";

        if (newDateInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newDate = foundItem.getAuctionEnd();
        } else {
            newDate = Date.from(Instant.parse(newDateInput));
        }

        return newDate;
    }

    private AuctionType readAuctionType(Item foundItem) {
        AuctionType newAuctionType = null;

        String newAuctionTypeInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_AUCTION_TYPE_MESSAGE);

        if (newAuctionTypeInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newAuctionType = foundItem.getAuctionType();
        } else {
            newAuctionType = AuctionType.valueOf(newAuctionTypeInput);
        }

        return newAuctionType;
    }

    private MonetaryAmount readBuyNowPrice(Item foundItem) {
        MonetaryAmount newBuyNowPrice = null;

        String newBuyNowPriceInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_BUY_NOW_PRICE_MESSAGE);

        if (newBuyNowPriceInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newBuyNowPrice = foundItem.getBuyNowPrice();
        } else {
            newBuyNowPrice = MonetaryAmount.fromString(newBuyNowPriceInput);
        }

        return newBuyNowPrice;
    }

    private MonetaryAmount readInitialPrice(Item foundItem) {
        MonetaryAmount newInitialPrice = null;

        String newInitialPriceInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_INITIAL_PRICE_MESSAGE);

        if (newInitialPriceInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newInitialPrice = foundItem.getInitialPrice();
        } else {
            newInitialPrice = MonetaryAmount.fromString(newInitialPriceInput);
        }

        return newInitialPrice;
    }

    private Map<String, String> readImages(Item foundItem) throws NoSuchElementException, PatternSyntaxException {
        Map<String, String> newImages = new HashMap<>();

        String newImagesInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_IMAGES_MESSAGE);

        if (newImagesInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newImages = foundItem.getImages();
        } else {
            String[] images = newImagesInput.split(ELEMENTS_INPUT_SEPARATOR_REGEXP);

            for (String imageData : images) {
                List<String> imageDataPart = List.of(imageData.split(KEY_VALUE_INPUT_SEPARATOR_REGEXP));

                String key = imageDataPart.getFirst();
                String value = imageDataPart.getLast();

                newImages.put(key, value);
            }
        }

        return newImages;
    }

    private List<Bid> readAndSetBids(Item foundItem, List<Bid> oldBids)
            throws NumberFormatException, PatternSyntaxException {
        Set<Bid> newBids = new HashSet<>();

        String newBidsIdentifiersInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_ITEM_GET_BIDS_MESSAGE);

        if (newBidsIdentifiersInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            return oldBids;
        }

        String[] bidsIdentifiers = newBidsIdentifiersInput.split(ELEMENTS_INPUT_SEPARATOR_REGEXP);

        for (String bidIdentifier : bidsIdentifiers) {
            if (bidIdentifier.isEmpty()) {
                break;
            }

            Long bidId = Long.parseLong(bidIdentifier);

            Bid foundBid = this.bidDAO.get(bidId);

            if (foundBid != null) {
                newBids.add(foundBid);
            }
        }

        List<Bid> bidsList = List.copyOf(newBids);
        List<Bid> oldBidsList = this.itemDAO.getItemBids(foundItem);

        for (Bid bid : oldBidsList) {
            this.itemDAO.removeItemBid(foundItem, bid);
        }

        for (Bid bid : bidsList) {
            this.itemDAO.setItemBid(foundItem, bid);
        }

        return bidsList;
    }

    @Override
    public List<String> create() {
        List<String> outputData = new ArrayList<>();

        outputData.add(CREATE_ITEM_START_MESSAGE);

        Item newItem = new Item();

        Long newId = -1L;

        try {
            newId = readId();
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        String newName = readName();

        Date newAuctionEnd = null;

        try {
            newAuctionEnd = readAuctionEnd();
        } catch (NullPointerException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (IllegalArgumentException b) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (DateTimeParseException c) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        AuctionType newAuctionType = readAuctionType();

        MonetaryAmount newBuyNowPrice = readBuyNowPrice();

        MonetaryAmount newInitialPrice = readInitialPrice();

        Map<String, String> newImages = null;

        try {
            newImages = readImages();
        } catch (NoSuchElementException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (PatternSyntaxException b) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        List<Bid> newBids = null;

        try {
            newBids = readAndSetBids(newItem);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (PatternSyntaxException b) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        if (newBids == null) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(OPERATION_ITEM_NEW_BIDS_MESSAGE);
        outputData.add(newBids.toString());

        newItem.setId(newId);
        newItem.setName(newName);
        newItem.setAuctionEnd(newAuctionEnd);
        newItem.setAuctionType(newAuctionType);
        newItem.setBuyNowPrice(newBuyNowPrice);
        newItem.setInitialPrice(newInitialPrice);
        newItem.setImages(newImages);

        boolean itemCreated = this.itemDAO.create(newItem);

        if (itemCreated) {
            outputData.add(OPERATION_ITEM_SUCCESS_MESSAGE);
        } else {
            outputData.add(OPERATION_ITEM_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    private Long readId() throws NumberFormatException {
        Long newId = -1L;

        String newIdInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_ID_MESSAGE);
        newId = Long.parseLong(newIdInput);

        return newId;
    }

    private String readName() {
        String newName = "";

        String newNameInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_NAME_MESSAGE);
        newName = newNameInput;

        return newName;
    }

    private Date readAuctionEnd() throws NullPointerException, IllegalArgumentException, DateTimeParseException {
        Date newDate = null;

        String newDateInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_AUCTION_END_MESSAGE);
        newDateInput += DATE_POSTFIX;

        newDate = Date.from(Instant.parse(newDateInput));

        return newDate;
    }

    private AuctionType readAuctionType() {
        AuctionType newAuctionType = null;

        String newAuctionTypeInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_AUCTION_TYPE_MESSAGE);
        newAuctionType = AuctionType.valueOf(newAuctionTypeInput);

        return newAuctionType;
    }

    private MonetaryAmount readBuyNowPrice() {
        MonetaryAmount newBuyNowPrice = null;

        String newBuyNowPriceInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_BUY_NOW_PRICE_MESSAGE);
        newBuyNowPrice = MonetaryAmount.fromString(newBuyNowPriceInput);

        return newBuyNowPrice;
    }

    private MonetaryAmount readInitialPrice() {
        MonetaryAmount newInitialPrice = null;

        String newInitialPriceInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_INITIAL_PRICE_MESSAGE);
        newInitialPrice = MonetaryAmount.fromString(newInitialPriceInput);

        return newInitialPrice;
    }

    private Map<String, String> readImages() throws NoSuchElementException, PatternSyntaxException {
        Map<String, String> newImages = new HashMap<>();

        String newImagesInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_IMAGES_MESSAGE);

        String[] images = newImagesInput.split(ELEMENTS_INPUT_SEPARATOR_REGEXP);

        for (String imageData : images) {
            List<String> imageDataPart = List.of(imageData.split(KEY_VALUE_INPUT_SEPARATOR_REGEXP));

            String key = imageDataPart.getFirst();
            String value = imageDataPart.getLast();

            newImages.put(key, value);
        }

        return newImages;
    }

    private List<Bid> readAndSetBids(Item foundItem) throws NumberFormatException, PatternSyntaxException {
        Set<Bid> newBids = new HashSet<>();

        String newBidsIdentifiersInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_ITEM_GET_BIDS_MESSAGE);

        String[] bidsIdentifiers = newBidsIdentifiersInput.split(ELEMENTS_INPUT_SEPARATOR_REGEXP);

        for (String bidIdentifier : bidsIdentifiers) {
            if (bidIdentifier.isEmpty()) {
                break;
            }

            Long bidId = Long.parseLong(bidIdentifier);

            Bid foundBid = this.bidDAO.get(bidId);

            if (foundBid != null) {
                newBids.add(foundBid);
            }
        }

        List<Bid> bidsList = List.copyOf(newBids);

        for (Bid bid : bidsList) {
            this.itemDAO.setItemBid(foundItem, bid);
        }

        return bidsList;
    }
}
