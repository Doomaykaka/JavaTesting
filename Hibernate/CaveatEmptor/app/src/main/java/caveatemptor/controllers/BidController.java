package caveatemptor.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManagerFactory;

import caveatemptor.dao.BidDAO;
import caveatemptor.dao.ItemDAO;
import caveatemptor.models.Bid;
import caveatemptor.models.Item;
import caveatemptor.utils.ConsoleUtils;

public class BidController implements GenericController<Bid> {
    private BidDAO bidDAO;
    private ItemDAO itemDAO;
    private Scanner scan;

    private static final String GET_BID_ITEM_START_MESSAGE_FIRST_PART = "Bid item with bid_id=";
    private static final String GET_BID_ITEM_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_BID_START_MESSAGE_FIRST_PART = "Bid with id=";
    private static final String GET_BID_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_BIDS_START_MESSAGE = "Bids:";
    private static final String REMOVE_BID_START_MESSAGE = "Bid remove:";
    private static final String UPDATE_BID_START_MESSAGE = "Bid update:";
    private static final String UPDATE_BID_SHOW_OLD_STATE_MESSAGE = "Old Bid = ";
    private static final String UPDATE_BID_GET_ITEM_ID_MESSAGE = "Input new Bid Item Id (X if need old) - ";
    private static final String SAVE_DATA_OLD_STATE_INPUT_VALUE = "X";
    private static final String CREATE_BID_START_MESSAGE = "Bid create:";
    private static final String CREATE_BID_GET_ITEM_ID_MESSAGE = "Input new Bid Item Id - ";
    private static final String BAD_INPUT_ERROR_MESSAGE = "Bad input!";
    private static final String OPERATION_SUCCESS_MESSAGE = "Success";
    private static final String OPERATION_NOT_SUCCESS_MESSAGE = "Not success";
    private static final String OPERATION_BID_NEW_ITEM_MESSAGE = "New Bid Item:";

    public BidController(EntityManagerFactory entityFactory, Scanner scan) {
        this.bidDAO = new BidDAO(entityFactory);
        this.itemDAO = new ItemDAO(entityFactory);
        this.scan = scan;
    }

    public List<String> getBidItem(long id) {
        List<String> outputData = new ArrayList<>();

        Bid foundBid = this.bidDAO.get(id);
        Item bidItem = null;

        if (foundBid != null) {
            bidItem = this.bidDAO.getBidItem(foundBid);
        }

        String startMessage = GET_BID_ITEM_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_BID_ITEM_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundBid != null && bidItem != null) {
            outputData.add(foundBid.toString());
            outputData.add(bidItem.toString());
        }

        return outputData;
    }

    @Override
    public List<String> get(long id) {
        List<String> outputData = new ArrayList<>();

        Bid foundBid = this.bidDAO.get(id);

        String startMessage = GET_BID_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_BID_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundBid != null) {
            outputData.add(foundBid.toString());
        }

        return outputData;
    }

    @Override
    public List<String> getAll() {
        List<String> outputData = new ArrayList<>();

        List<Bid> bids = this.bidDAO.getAll();

        outputData.add(GET_BIDS_START_MESSAGE);

        for (Bid bid : bids) {
            outputData.add(bid.toString());
        }

        return outputData;
    }

    @Override
    public List<String> remove(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(REMOVE_BID_START_MESSAGE);

        if (this.bidDAO.remove(id)) {
            outputData.add(OPERATION_SUCCESS_MESSAGE);
        } else {
            outputData.add(OPERATION_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    @Override
    public List<String> update(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(UPDATE_BID_START_MESSAGE);

        Bid foundBid = this.bidDAO.get(id);

        if (foundBid == null) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(UPDATE_BID_SHOW_OLD_STATE_MESSAGE + foundBid.toString());

        Item bidItem = this.bidDAO.getBidItem(foundBid);

        try {
            bidItem = readAndSetItem(foundBid, bidItem);

            if (bidItem == null) {
                outputData.add(BAD_INPUT_ERROR_MESSAGE);

                return outputData;
            }

            outputData.add(OPERATION_BID_NEW_ITEM_MESSAGE);
            outputData.add(bidItem.toString());
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        boolean bidUpdated = this.bidDAO.update(foundBid);

        if (bidUpdated) {
            outputData.add(OPERATION_SUCCESS_MESSAGE);
        } else {
            outputData.add(OPERATION_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    private Item readAndSetItem(Bid foundBid, Item bidItem) throws NumberFormatException {
        String newItemIdInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_BID_GET_ITEM_ID_MESSAGE);

        if (newItemIdInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            return bidItem;
        }

        Long newItemId = Long.parseLong(newItemIdInput);

        Item newBidItem = this.itemDAO.get(newItemId);

        if (newBidItem != null) {
            foundBid.setItem(newBidItem);
            newBidItem.addBid(foundBid);
        }

        return newBidItem;
    }

    @Override
    public List<String> create() {
        List<String> outputData = new ArrayList<>();

        outputData.add(CREATE_BID_START_MESSAGE);

        Bid newBid = new Bid();

        Item newBidItem = null;

        try {
            newBidItem = readAndSetItem(newBid);

            if (newBidItem == null) {
                outputData.add(BAD_INPUT_ERROR_MESSAGE);

                return outputData;
            }

            outputData.add(OPERATION_BID_NEW_ITEM_MESSAGE);
            outputData.add(newBidItem.toString());
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        boolean bidCreated = this.bidDAO.create(newBid);

        if (bidCreated) {
            outputData.add(OPERATION_SUCCESS_MESSAGE);
        } else {
            outputData.add(OPERATION_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    private Item readAndSetItem(Bid newBid) {
        Long newItemId = -1L;

        String newItemIdInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_BID_GET_ITEM_ID_MESSAGE);
        newItemId = Long.parseLong(newItemIdInput);

        Item newBidItem = this.itemDAO.get(newItemId);

        if (newBidItem != null) {
            newBid.setItem(newBidItem);
            newBidItem.addBid(newBid);
        }

        return newBidItem;
    }
}
