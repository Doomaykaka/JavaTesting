package caveatemptor.cli;

import java.util.List;
import java.util.Scanner;

import caveatemptor.controllers.BidController;
import caveatemptor.controllers.BillingDetailsController;
import caveatemptor.controllers.ItemController;
import caveatemptor.controllers.UserController;
import caveatemptor.utils.ConsoleUtils;

public class ApplicationCLI implements Runnable {
    private Scanner scanner;
    private BidController bidController;
    private BillingDetailsController billingDetailsController;
    private ItemController itemController;
    private UserController userController;

    private static final String APP_START_MESSAGE = "----------Caveat Emptor----------";
    private static final String READ_COMMAND_MESSAGE = "-(type 'help' to see all commands)->";
    private static final String SPLIT_COMMAND_PARTS_REGEXP = "\s";

    private static final String GET_BID_ITEM_COMMAND = "get_bid_item";
    private static final String GET_BID_COMMAND = "get_bid";
    private static final String GET_BIDS_COMMAND = "get_bids";
    private static final String REMOVE_BID_COMMAND = "remove_bid";
    private static final String UPDATE_BID_COMMAND = "update_bid";
    private static final String CREATE_BID_COMMAND = "create_bid";

    private static final String GET_BILLING_DETAILS_USER_COMMAND = "get_billing_details_user";
    private static final String GET_BILLING_DETAILS_COMMAND = "get_billing_details";
    private static final String GET_ALL_BILLING_DETAILS_COMMAND = "get_all_billing_details";
    private static final String REMOVE_BILLING_DETAILS_COMMAND = "remove_billing_details";
    private static final String UPDATE_BILLING_DETAILS_COMMAND = "update_billing_details";
    private static final String CREATE_BILLING_DETAILS_COMMAND = "create_billing_details";

    private static final String GET_ITEM_BIDS_COMMAND = "get_item_bids";
    private static final String GET_ITEM_COMMAND = "get_item";
    private static final String GET_ITEMS_COMMAND = "get_items";
    private static final String REMOVE_ITEM_COMMAND = "remove_item";
    private static final String UPDATE_ITEM_COMMAND = "update_item";
    private static final String CREATE_ITEM_COMMAND = "create_item";

    private static final String GET_USER_BILLING_DETAILS_COMMAND = "get_user_billing_details";
    private static final String GET_USER_COMMAND = "get_user";
    private static final String GET_USERS_COMMAND = "get_users";
    private static final String REMOVE_USER_COMMAND = "remove_user";
    private static final String UPDATE_USER_COMMAND = "update_user";
    private static final String CREATE_USER_COMMAND = "create_user";

    private static final String HELP_COMMAND = "help";
    private static final String EXIT_COMMAND = "exit";
    private static final String BAD_COMMAND_MESSAGE = "Bad command";
    private static final List<String> HELP_MESSAGE = List.of("Help:", "get_bid_item {bid_id} - get bid item",
            "get_bid {bid_id} - get bid", "get_bids - get all bids", "remove_bid {bid_id} - remove bid",
            "update_bid {bid_id} - update bid", "create_bid - create bid",
            "get_billing_details_user {billing_details_id} - get billing details user",
            "get_billing_details {billing_details_id} - get billing details",
            "get_all_billing_details - get all billing details",
            "remove_billing_details {billing_details_id} - remove billing details",
            "update_billing_details {billing_details_id} - update billing details",
            "create_billing_details - create billing details", "get_item_bids {item_id} - get item bids",
            "get_item {item_id} - get item", "get_items - get all items", "remove_item {item_id} - remove item",
            "update_item {item_id} - update item", "create_item - create item", "help - view all commands",
            "exit - exit from app");
    private static final int COMMAND_INDEX = 0;
    private static final int FIRST_PARAMETER_INDEX = 1;

    public ApplicationCLI(Scanner scanner, BidController bidController,
            BillingDetailsController billingDetailsController, ItemController itemController,
            UserController userController) {
        this.scanner = scanner;
        this.bidController = bidController;
        this.billingDetailsController = billingDetailsController;
        this.itemController = itemController;
        this.userController = userController;
    }

    @Override
    public void run() {
        boolean appIsWork = true;

        ConsoleUtils.printText(APP_START_MESSAGE);

        while (appIsWork) {
            appIsWork = !processCommandAndCheckAppExit();
        }
    }

    private boolean processCommandAndCheckAppExit() {
        boolean appIsClosed = false;

        String command = ConsoleUtils.readLineWithQuestion(this.scanner, READ_COMMAND_MESSAGE);

        String[] commandParts = command.split(SPLIT_COMMAND_PARTS_REGEXP);

        switch (commandParts[COMMAND_INDEX]) {
        case GET_BID_ITEM_COMMAND:
            Long bidForItemId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.bidController.getBidItem(bidForItemId));
            break;
        case GET_BID_COMMAND:
            Long bidId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.bidController.get(bidId));
            break;
        case GET_BIDS_COMMAND:
            ConsoleUtils.printText(this.bidController.getAll());
            break;
        case REMOVE_BID_COMMAND:
            Long bidToRemoveId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.bidController.remove(bidToRemoveId));
            break;
        case UPDATE_BID_COMMAND:
            Long bidToUpdateId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.bidController.update(bidToUpdateId));
            break;
        case CREATE_BID_COMMAND:
            ConsoleUtils.printText(this.bidController.create());
            break;
        case GET_BILLING_DETAILS_USER_COMMAND:
            Long userForBillingDetailsId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.billingDetailsController.getBillingDetailsUser(userForBillingDetailsId));
            break;
        case GET_BILLING_DETAILS_COMMAND:
            Long billingDetailsId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.billingDetailsController.get(billingDetailsId));
            break;
        case GET_ALL_BILLING_DETAILS_COMMAND:
            ConsoleUtils.printText(this.billingDetailsController.getAll());
            break;
        case REMOVE_BILLING_DETAILS_COMMAND:
            Long billingDetailsToRemoveId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.billingDetailsController.remove(billingDetailsToRemoveId));
            break;
        case UPDATE_BILLING_DETAILS_COMMAND:
            Long billingDetailsToUpdateId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.billingDetailsController.update(billingDetailsToUpdateId));
            break;
        case CREATE_BILLING_DETAILS_COMMAND:
            ConsoleUtils.printText(this.billingDetailsController.create());
            break;
        case GET_ITEM_BIDS_COMMAND:
            Long itemForBidsId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.itemController.getItemBids(itemForBidsId));
            break;
        case GET_ITEM_COMMAND:
            Long itemId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.itemController.get(itemId));
            break;
        case GET_ITEMS_COMMAND:
            ConsoleUtils.printText(this.itemController.getAll());
            break;
        case REMOVE_ITEM_COMMAND:
            Long itemToRemoveId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.itemController.remove(itemToRemoveId));
            break;
        case UPDATE_ITEM_COMMAND:
            Long ItemToUpdateId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.itemController.update(ItemToUpdateId));
            break;
        case CREATE_ITEM_COMMAND:
            ConsoleUtils.printText(this.itemController.create());
            break;
        case GET_USER_BILLING_DETAILS_COMMAND:
            Long userBillingDetailsId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.userController.getUserBillingDetails(userBillingDetailsId));
            break;
        case GET_USER_COMMAND:
            Long userId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.userController.get(userId));
            break;
        case GET_USERS_COMMAND:
            ConsoleUtils.printText(this.userController.getAll());
            break;
        case REMOVE_USER_COMMAND:
            Long userToRemoveId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.userController.remove(userToRemoveId));
            break;
        case UPDATE_USER_COMMAND:
            Long userToUpdateId = Long.parseLong(commandParts[FIRST_PARAMETER_INDEX]);

            ConsoleUtils.printText(this.userController.update(userToUpdateId));
            break;
        case CREATE_USER_COMMAND:
            ConsoleUtils.printText(this.userController.create());
            break;
        case HELP_COMMAND:
            ConsoleUtils.printText(HELP_MESSAGE);
            break;
        case EXIT_COMMAND:
            appIsClosed = true;
            break;
        default:
            ConsoleUtils.printText(BAD_COMMAND_MESSAGE);
        }

        return appIsClosed;
    }
}
