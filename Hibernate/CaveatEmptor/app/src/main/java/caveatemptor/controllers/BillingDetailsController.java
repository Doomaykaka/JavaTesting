package caveatemptor.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManagerFactory;

import caveatemptor.dao.BillingDetailsDAO;
import caveatemptor.dao.UserDAO;
import caveatemptor.models.BankAccount;
import caveatemptor.models.BillingDetails;
import caveatemptor.models.CreditCard;
import caveatemptor.models.User;
import caveatemptor.utils.ConsoleUtils;

public class BillingDetailsController implements GenericController<BillingDetails> {
    private BillingDetailsDAO billingDetailsDAO;
    private UserDAO userDAO;
    private Scanner scan;

    private static final String GET_BILLING_DETAILS_USER_START_MESSAGE_FIRST_PART = "Billing details user with billing_details_id=";
    private static final String GET_BILLING_DETAILS_USER_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_BILLING_DETAILS_START_MESSAGE_FIRST_PART = "Billing details with id=";
    private static final String GET_BILLING_DETAILS_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_BILLING_DETAILS_START_MESSAGE = "Billing details:";
    private static final String REMOVE_BILLING_DETAILS_START_MESSAGE = "Billing details remove:";
    private static final String UPDATE_BILLING_DETAILS_START_MESSAGE = "Billing details update:";
    private static final String UPDATE_BILLING_DETAILS_SHOW_OLD_STATE_MESSAGE = "Old billing details = ";
    private static final String UPDATE_BILLING_DETAILS_GET_ID_MESSAGE = "Input new Billing details Id (X if need old) - ";
    private static final String UPDATE_BILLING_DETAILS_GET_OWNER_MESSAGE = "Input new Billing details Owner (X if need old) - ";
    private static final String UPDATE_BILLING_DETAILS_GET_USER_ID_MESSAGE = "Input new Billing details User Id (X if need old) - ";
    private static final String UPDATE_BILLING_DETAILS_NEED_TYPE_UPDATE_MESSAGE = "Do I need to change the type and exact information of the payment method (Yes, No) ?";
    private static final String YES_INPUT_VALUE = "Yes";
    private static final String NO_INPUT_VALUE = "No";
    private static final String SAVE_DATA_OLD_STATE_INPUT_VALUE = "X";
    private static final String CREATE_BILLING_DETAILS_START_MESSAGE = "Billing details create:";
    private static final String CREATE_BILLING_DETAILS_GET_ID_MESSAGE = "Input new Billing details Id - ";
    private static final String CREATE_BILLING_DETAILS_GET_OWNER_MESSAGE = "Input new Billing details Owner - ";
    private static final String CREATE_BILLING_DETAILS_GET_USER_ID_MESSAGE = "Input new Billing details User Id - ";
    private static final String BAD_INPUT_ERROR_MESSAGE = "Bad input!";
    private static final String OPERATION_BILLING_DETAILS_NEW_USER_MESSAGE = "New Billing details User:";
    private static final String OPERATION_BILLING_DETAILS_SUCCESS_MESSAGE = "Success";
    private static final String OPERATION_BILLING_DETAILS_NOT_SUCCESS_MESSAGE = "Not success";
    private static final String OPERATION_BILLING_DETAILS_SHOW_NEW_STATE_MESSAGE = "New billing details = ";
    private static final String OPERATION_BILLING_DETAILS_TYPE_UPDATE_MESSAGE = "Please enter the payment method type (Bank account, Credit card and etc) ";
    private static final String OPERATION_BILLING_DETAILS_TYPE_BANK_ACCOUNT = "Bank account";
    private static final String OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_ACCOUNT_MESSAGE = "Input new Bank account name - ";
    private static final String OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_BANKNAME_MESSAGE = "Input new Bank account bankname - ";
    private static final String OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_SWIFT_MESSAGE = "Input new Bank account swift - ";
    private static final String OPERATION_BILLING_DETAILS_TYPE_CREDIT_CARD = "Credit card";
    private static final String OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_CARD_NUMBER = "Input new Credit card number - ";
    private static final String OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_EXP_MONTH = "Input new Credit card exp month - ";
    private static final String OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_EXP_YEAR = "Input new Credit card exp year - ";

    public BillingDetailsController(EntityManagerFactory entityFactory, Scanner scan) {
        this.billingDetailsDAO = new BillingDetailsDAO(entityFactory);
        this.userDAO = new UserDAO(entityFactory);
        this.scan = scan;
    }

    public List<String> getBillingDetailsUser(long id) {
        List<String> outputData = new ArrayList<>();

        BillingDetails foundBillingDetails = this.billingDetailsDAO.get(id);
        User billingDetailsUser = null;

        if (foundBillingDetails != null) {
            billingDetailsUser = this.billingDetailsDAO.getBillingDetailsUser(foundBillingDetails);
        }

        String startMessage = GET_BILLING_DETAILS_USER_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_BILLING_DETAILS_USER_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundBillingDetails != null && billingDetailsUser != null) {
            outputData.add(foundBillingDetails.toString());
            outputData.add(billingDetailsUser.toString());
        }

        return outputData;
    }

    @Override
    public List<String> get(long id) {
        List<String> outputData = new ArrayList<>();

        BillingDetails foundBillingDetails = this.billingDetailsDAO.get(id);

        String startMessage = GET_BILLING_DETAILS_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_BILLING_DETAILS_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundBillingDetails != null) {
            outputData.add(foundBillingDetails.toString());
        }

        return outputData;
    }

    @Override
    public List<String> getAll() {
        List<String> outputData = new ArrayList<>();

        List<BillingDetails> billingDetails = this.billingDetailsDAO.getAll();

        outputData.add(GET_BILLING_DETAILS_START_MESSAGE);

        for (BillingDetails billingDetail : billingDetails) {
            outputData.add(billingDetail.toString());
        }

        return outputData;
    }

    @Override
    public List<String> remove(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(REMOVE_BILLING_DETAILS_START_MESSAGE);

        if (this.billingDetailsDAO.remove(id)) {
            outputData.add(OPERATION_BILLING_DETAILS_SUCCESS_MESSAGE);
        } else {
            outputData.add(OPERATION_BILLING_DETAILS_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    @Override
    public List<String> update(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(UPDATE_BILLING_DETAILS_START_MESSAGE);

        BillingDetails foundBillingDetails = this.billingDetailsDAO.get(id);

        if (foundBillingDetails == null) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(UPDATE_BILLING_DETAILS_SHOW_OLD_STATE_MESSAGE + foundBillingDetails.toString());

        Long newId = -1L;
        String newOwner = "";
        User newUser = null;

        try {
            newId = readId(foundBillingDetails);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        try {
            newOwner = readOwner(foundBillingDetails);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        User billingDetailsUser = this.billingDetailsDAO.getBillingDetailsUser(foundBillingDetails);

        try {
            newUser = readAndSetUser(foundBillingDetails, billingDetailsUser);

            outputData.add(OPERATION_BILLING_DETAILS_NEW_USER_MESSAGE);
            outputData.add(newUser.toString());
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        String needUpdateInput = ConsoleUtils.readLineWithQuestion(scan,
                UPDATE_BILLING_DETAILS_NEED_TYPE_UPDATE_MESSAGE);

        boolean needUpdate = needUpdateInput.equals(YES_INPUT_VALUE);

        if (!needUpdateInput.equals(YES_INPUT_VALUE) && !needUpdateInput.equals(NO_INPUT_VALUE)) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        if (!needUpdate) {
            foundBillingDetails.setId(newId);
            foundBillingDetails.setOwner(newOwner);

            boolean billingDetailsUpdated = this.billingDetailsDAO.update(foundBillingDetails);

            if (billingDetailsUpdated) {
                outputData.add(OPERATION_BILLING_DETAILS_SUCCESS_MESSAGE);
            } else {
                outputData.add(OPERATION_BILLING_DETAILS_NOT_SUCCESS_MESSAGE);
            }

            return outputData;
        }

        String billingDetailsTypeInput = ConsoleUtils.readLineWithQuestion(scan,
                OPERATION_BILLING_DETAILS_TYPE_UPDATE_MESSAGE);

        switch (billingDetailsTypeInput) {
        case OPERATION_BILLING_DETAILS_TYPE_BANK_ACCOUNT:
            BankAccount bankAccount = (BankAccount) foundBillingDetails;

            String account = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_ACCOUNT_MESSAGE);
            String bankname = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_BANKNAME_MESSAGE);
            String swift = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_SWIFT_MESSAGE);

            bankAccount.setId(newId);
            bankAccount.setOwner(newOwner);

            bankAccount.setAccount(account);
            bankAccount.setBankname(bankname);
            bankAccount.setSwift(swift);

            boolean billingDetailsUpdated = this.billingDetailsDAO.update(bankAccount);

            if (billingDetailsUpdated) {
                outputData.add(OPERATION_BILLING_DETAILS_SUCCESS_MESSAGE);
            } else {
                outputData.add(OPERATION_BILLING_DETAILS_NOT_SUCCESS_MESSAGE);
            }

            break;
        case OPERATION_BILLING_DETAILS_TYPE_CREDIT_CARD:
            CreditCard creditCard = (CreditCard) foundBillingDetails;

            String cardNumber = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_CARD_NUMBER);
            String expMonth = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_EXP_MONTH);
            String expYear = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_EXP_YEAR);

            creditCard.setId(newId);
            creditCard.setOwner(newOwner);

            creditCard.setCardNumber(cardNumber);
            creditCard.setExpMonth(expMonth);
            creditCard.setExpYear(expYear);

            break;
        default:
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(OPERATION_BILLING_DETAILS_SHOW_NEW_STATE_MESSAGE + foundBillingDetails.toString());

        return outputData;
    }

    private Long readId(BillingDetails foundBillingDetails) throws NumberFormatException {
        Long newId = -1L;

        String newIdInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_BILLING_DETAILS_GET_ID_MESSAGE);

        if (newIdInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newId = foundBillingDetails.getId();
        } else {
            newId = Long.parseLong(newIdInput);
        }

        return newId;
    }

    private String readOwner(BillingDetails foundBillingDetails) {
        String newOwner = "";

        String newOwnerInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_BILLING_DETAILS_GET_OWNER_MESSAGE);

        String oldOwner = foundBillingDetails.getOwner();

        if (newOwnerInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newOwner = oldOwner;
        } else {
            newOwner = newOwnerInput;
        }

        return newOwner;
    }

    private User readAndSetUser(BillingDetails foundBillingDetails, User billingDetailsUser) {
        String newUserIdInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_BILLING_DETAILS_GET_USER_ID_MESSAGE);

        Long newUserId = -1L;

        if (newUserIdInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            return billingDetailsUser;
        }

        newUserId = Long.parseLong(newUserIdInput);

        User newBillingDetailsUser = this.userDAO.get(newUserId);

        if (newBillingDetailsUser != null) {
            this.billingDetailsDAO.removeBillingDetailsUser(foundBillingDetails, billingDetailsUser);
            this.billingDetailsDAO.setBillingDetailsUser(foundBillingDetails, newBillingDetailsUser);
        }

        return newBillingDetailsUser;
    }

    @Override
    public List<String> create() {
        List<String> outputData = new ArrayList<>();

        outputData.add(CREATE_BILLING_DETAILS_START_MESSAGE);

        BillingDetails newBillingDetails = null;

        Long newId = -1L;

        try {
            newId = readNewId();
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        String newOwner = "";

        try {
            newOwner = readOwner();
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        User newUser = null;

        try {
            newUser = readUser();

            outputData.add(OPERATION_BILLING_DETAILS_NEW_USER_MESSAGE);
            outputData.add(newUser.toString());
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        String billingDetailsTypeInput = ConsoleUtils.readLineWithQuestion(scan,
                OPERATION_BILLING_DETAILS_TYPE_UPDATE_MESSAGE);

        switch (billingDetailsTypeInput) {
        case OPERATION_BILLING_DETAILS_TYPE_BANK_ACCOUNT:
            BankAccount bankAccount = new BankAccount();

            String account = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_ACCOUNT_MESSAGE);
            String bankname = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_BANKNAME_MESSAGE);
            String swift = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_BANK_ACCOUNT_GET_SWIFT_MESSAGE);

            bankAccount.setId(newId);
            bankAccount.setOwner(newOwner);
            bankAccount.setAccount(account);
            bankAccount.setBankname(bankname);
            bankAccount.setSwift(swift);

            boolean bankAccountCreated = this.billingDetailsDAO.create(bankAccount);

            this.billingDetailsDAO.removeBillingDetailsUser(bankAccount, newUser);
            this.billingDetailsDAO.setBillingDetailsUser(bankAccount, newUser);

            if (bankAccountCreated) {
                outputData.add(OPERATION_BILLING_DETAILS_SUCCESS_MESSAGE);
            } else {
                outputData.add(OPERATION_BILLING_DETAILS_NOT_SUCCESS_MESSAGE);
            }

            newBillingDetails = bankAccount;

            break;
        case OPERATION_BILLING_DETAILS_TYPE_CREDIT_CARD:
            CreditCard creditCard = new CreditCard();

            String cardNumber = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_CARD_NUMBER);
            String expMonth = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_EXP_MONTH);
            String expYear = ConsoleUtils.readLineWithQuestion(scan,
                    OPERATION_BILLING_DETAILS_CREDIT_CARD_GET_EXP_YEAR);

            creditCard.setId(newId);
            creditCard.setOwner(newOwner);
            creditCard.setCardNumber(cardNumber);
            creditCard.setExpMonth(expMonth);
            creditCard.setExpYear(expYear);

            boolean creditCardCreated = this.billingDetailsDAO.create(creditCard);

            this.billingDetailsDAO.removeBillingDetailsUser(creditCard, newUser);
            this.billingDetailsDAO.setBillingDetailsUser(creditCard, newUser);

            if (creditCardCreated) {
                outputData.add(OPERATION_BILLING_DETAILS_SUCCESS_MESSAGE);
            } else {
                outputData.add(OPERATION_BILLING_DETAILS_NOT_SUCCESS_MESSAGE);
            }

            newBillingDetails = creditCard;

            break;
        default:
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(OPERATION_BILLING_DETAILS_SHOW_NEW_STATE_MESSAGE + newBillingDetails.toString());

        return outputData;
    }

    private Long readNewId() throws NumberFormatException {
        Long newId = -1L;

        String newIdInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_BILLING_DETAILS_GET_ID_MESSAGE);
        newId = Long.parseLong(newIdInput);

        return newId;
    }

    private String readOwner() {
        String newOwner = "";

        newOwner = ConsoleUtils.readLineWithQuestion(scan, CREATE_BILLING_DETAILS_GET_OWNER_MESSAGE);

        return newOwner;
    }

    private User readUser() {
        User newBillingDetailsUser = null;

        String newUserIdInput = ConsoleUtils.readLineWithQuestion(scan, CREATE_BILLING_DETAILS_GET_USER_ID_MESSAGE);
        Long newUserId = Long.parseLong(newUserIdInput);

        newBillingDetailsUser = this.userDAO.get(newUserId);

        return newBillingDetailsUser;
    }
}
