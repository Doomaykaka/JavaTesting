package caveatemptor.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import javax.persistence.EntityManagerFactory;

import caveatemptor.dao.BillingDetailsDAO;
import caveatemptor.dao.UserDAO;
import caveatemptor.models.Address;
import caveatemptor.models.BillingDetails;
import caveatemptor.models.GermanZipcode;
import caveatemptor.models.User;
import caveatemptor.models.Zipcode;
import caveatemptor.utils.ConsoleUtils;

public class UserController implements GenericController<User> {
    private UserDAO userDAO;
    private BillingDetailsDAO billingDetailsDAO;
    private Scanner scan;

    private static final String GET_USER_BILLING_DETAILS_START_MESSAGE_FIRST_PART = "User billing details with user_id=";
    private static final String GET_USER_BILLING_DETAILS_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_USER_START_MESSAGE_FIRST_PART = "User with id=";
    private static final String GET_USER_START_MESSAGE_SECOND_PART = ":";
    private static final String GET_USER_START_MESSAGE = "Users:";
    private static final String REMOVE_USER_START_MESSAGE = "User remove:";
    private static final String REMOVE_USER_SUCCESS_MESSAGE = "Success";
    private static final String REMOVE_USER_NOT_SUCCESS_MESSAGE = "Not success";
    private static final String UPDATE_USER_START_MESSAGE = "User update:";
    private static final String UPDATE_USER_SHOW_OLD_STATE_MESSAGE = "Old User = ";
    private static final String UPDATE_USER_GET_ID_MESSAGE = "Input new User Id (X if need old) - ";
    private static final String UPDATE_USER_GET_FIRSTNAME_MESSAGE = "Input new User firstname (X if need old) - ";
    private static final String UPDATE_USER_GET_LASTNAME_MESSAGE = "Input new User lastname (X if need old) - ";
    private static final String UPDATE_USER_NEED_ADDRESS_TYPE_UPDATE_MESSAGE = "Do I need to change the type and exact information of the address (Yes, No) ?";
    private static final String UPDATE_USER_ADDRESS_TYPE_UPDATE_MESSAGE = "Please enter the address type (German and etc) :";
    private static final String UPDATE_USER_ADDRESS_TYPE_GERMAN = "German";
    private static final String UPDATE_USER_GET_ADDRESS_STREET_MESSAGE = "Input new Address street (X if need old) - ";
    private static final String UPDATE_USER_GET_ADDRESS_CITY_MESSAGE = "Input new Address city (X if need old) - ";
    private static final String UPDATE_USER_GET_ZIPCODE_MESSAGE = "Input new Zipcode (like '12 USD', X if need old) - ";
    private static final String UPDATE_USER_GET_BILLING_DETAILS_MESSAGE = "Input new Billing details identifiers (like '1,2,43', X if need old) - ";
    private static final String UPDATE_USER_NEW_BILLING_DETAILS_MESSAGE = "New User Billing details:";
    private static final String UPDATE_USER_SUCCESS_MESSAGE = "Success";
    private static final String UPDATE_USER_NOT_SUCCESS_MESSAGE = "Not success";
    private static final String UPDATE_USER_SHOW_NEW_STATE_MESSAGE = "New user = ";
    private static final String YES_INPUT_VALUE = "Yes\n";
    private static final String NO_INPUT_VALUE = "No\n";
    private static final String SAVE_DATA_OLD_STATE_INPUT_VALUE = "X\n";
    private static final String ELEMENTS_INPUT_SEPARATOR_REGEXP = ",";
    private static final String KEY_VALUE_INPUT_SEPARATOR_REGEXP = "-";
    private static final String BAD_INPUT_ERROR_MESSAGE = "Bad input!";

    public UserController(EntityManagerFactory entityFactory, Scanner scan) {
        this.userDAO = new UserDAO(entityFactory);
        this.billingDetailsDAO = new BillingDetailsDAO(entityFactory);
        this.scan = scan;
    }

    public List<String> getUserBillingDetails(long id) {
        List<String> outputData = new ArrayList<>();

        User foundUser = this.userDAO.get(id);
        List<BillingDetails> userBillingDetails = null;

        if (foundUser != null) {
            userBillingDetails = this.userDAO.getUserBillingDetails(foundUser);
        }

        String startMessage = GET_USER_BILLING_DETAILS_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_USER_BILLING_DETAILS_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundUser != null && userBillingDetails != null) {
            outputData.add(foundUser.toString());

            for (BillingDetails billingDetails : userBillingDetails) {
                outputData.add(billingDetails.toString());
            }
        }

        return outputData;
    }

    @Override
    public List<String> get(long id) {
        List<String> outputData = new ArrayList<>();

        User foundUser = this.userDAO.get(id);

        String startMessage = GET_USER_START_MESSAGE_FIRST_PART;
        startMessage += id;
        startMessage += GET_USER_START_MESSAGE_SECOND_PART;

        outputData.add(startMessage);

        if (foundUser != null) {
            outputData.add(foundUser.toString());
        }

        return outputData;
    }

    @Override
    public List<String> getAll() {
        List<String> outputData = new ArrayList<>();

        List<User> users = this.userDAO.getAll();

        String startMessage = GET_USER_START_MESSAGE;

        outputData.add(startMessage);

        for (User user : users) {
            outputData.add(user.toString());
        }

        return outputData;
    }

    @Override
    public List<String> remove(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(REMOVE_USER_START_MESSAGE);

        if (this.userDAO.remove(id)) {
            outputData.add(REMOVE_USER_SUCCESS_MESSAGE);
        } else {
            outputData.add(REMOVE_USER_NOT_SUCCESS_MESSAGE);
        }

        return outputData;
    }

    @Override
    public List<String> update(long id) {
        List<String> outputData = new ArrayList<>();

        outputData.add(UPDATE_USER_START_MESSAGE);

        User foundUser = this.userDAO.get(id);

        if (foundUser == null) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(UPDATE_USER_SHOW_OLD_STATE_MESSAGE + foundUser.toString());

        Long newId = -1L;
        String newFirstname = "";
        String newLastname = "";
        Address newAddress = null;
        Set<BillingDetails> newBillingDetails = null;

        try {
            newId = readId(foundUser);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        try {
            newFirstname = readFirstname(foundUser);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        try {
            newLastname = readLastname(foundUser);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        try {
            List<BillingDetails> oldBillingDetails = this.userDAO.getUserBillingDetails(foundUser);

            newBillingDetails = readAndSetBillingDetails(foundUser, oldBillingDetails);
        } catch (NumberFormatException a) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        } catch (PatternSyntaxException b) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        if (newBillingDetails == null) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(UPDATE_USER_NEW_BILLING_DETAILS_MESSAGE);
        outputData.add(newBillingDetails.toString());

        String needUpdateInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_NEED_ADDRESS_TYPE_UPDATE_MESSAGE);

        boolean needUpdate = needUpdateInput.equals(YES_INPUT_VALUE);

        if (!needUpdateInput.equals(YES_INPUT_VALUE) && !needUpdateInput.equals(NO_INPUT_VALUE)) {
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        if (!needUpdate) {
            foundUser.setId(newId);
            foundUser.setFirstname(newFirstname);
            foundUser.setLastname(newLastname);

            boolean userUpdated = this.userDAO.update(foundUser);

            if (userUpdated) {
                outputData.add(UPDATE_USER_SUCCESS_MESSAGE);
            } else {
                outputData.add(UPDATE_USER_NOT_SUCCESS_MESSAGE);
            }

            return outputData;
        }

        String addressTypeInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_ADDRESS_TYPE_UPDATE_MESSAGE);

        switch (addressTypeInput) {
        case UserController.UPDATE_USER_ADDRESS_TYPE_GERMAN:
            Zipcode addressZipcode = null;

            String zipcode = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_GET_ZIPCODE_MESSAGE);
            String city = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_GET_ADDRESS_CITY_MESSAGE);
            String street = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_GET_ADDRESS_STREET_MESSAGE);

            addressZipcode = new GermanZipcode(zipcode);

            newAddress = new Address(street, addressZipcode, city);

            foundUser.setId(newId);
            foundUser.setFirstname(newFirstname);
            foundUser.setLastname(newLastname);

            foundUser.setAddress(newAddress);

            boolean userUpdated = this.userDAO.update(foundUser);

            if (userUpdated) {
                outputData.add(UPDATE_USER_SUCCESS_MESSAGE);
            } else {
                outputData.add(UPDATE_USER_NOT_SUCCESS_MESSAGE);
            }

            break;
        default:
            outputData.add(BAD_INPUT_ERROR_MESSAGE);

            return outputData;
        }

        outputData.add(UPDATE_USER_SHOW_NEW_STATE_MESSAGE + foundUser.toString());

        return outputData;
    }

    private Long readId(User foundUser) throws NumberFormatException {
        Long newId = -1L;

        String newIdInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_GET_ID_MESSAGE);

        if (newIdInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newId = foundUser.getId();
        } else {
            newId = Long.parseLong(newIdInput);
        }

        return newId;
    }

    private String readFirstname(User foundUser) {
        String newFirstname = "";

        String newFirstnameInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_GET_FIRSTNAME_MESSAGE);

        String oldFirstname = foundUser.getFirstname();

        if (newFirstnameInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newFirstname = oldFirstname;
        } else {
            newFirstname = newFirstnameInput;
        }

        return newFirstname;
    }

    private String readLastname(User foundUser) {
        String newLastname = "";

        String newLastnameInput = ConsoleUtils.readLineWithQuestion(scan, UPDATE_USER_GET_LASTNAME_MESSAGE);

        String oldLastname = foundUser.getFirstname();

        if (newLastnameInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            newLastname = oldLastname;
        } else {
            newLastname = newLastnameInput;
        }

        return newLastname;
    }

    private Set<BillingDetails> readAndSetBillingDetails(User foundUser, List<BillingDetails> oldBillingDetails)
            throws NumberFormatException, PatternSyntaxException {
        Set<BillingDetails> newBillingDetails = new HashSet<>();

        String newBillingDetailsIdentifiersInput = ConsoleUtils.readLineWithQuestion(scan,
                UPDATE_USER_GET_BILLING_DETAILS_MESSAGE);

        if (newBillingDetailsIdentifiersInput.equals(SAVE_DATA_OLD_STATE_INPUT_VALUE)) {
            return Set.copyOf(oldBillingDetails);
        }

        String[] billingDetailsIdentifiers = newBillingDetailsIdentifiersInput.split(ELEMENTS_INPUT_SEPARATOR_REGEXP);

        for (String billingDetailsIdentifier : billingDetailsIdentifiers) {
            Long billingDetailsId = Long.parseLong(billingDetailsIdentifier);

            BillingDetails foundBillingDetails = this.billingDetailsDAO.get(billingDetailsId);

            if (foundBillingDetails != null) {
                newBillingDetails.add(foundBillingDetails);
            }
        }

        List<BillingDetails> oldbillingDetailsList = this.userDAO.getUserBillingDetails(foundUser);

        for (BillingDetails billingDetails : oldbillingDetailsList) {
            this.userDAO.removeUserBillingDetails(foundUser, billingDetails);
        }

        for (BillingDetails billingDetails : newBillingDetails) {
            this.userDAO.setUserBillingDetails(foundUser, billingDetails);
        }

        return newBillingDetails;
    }

    @Override
    public List<String> create() {
        // TODO Auto-generated method stub
        return null;
    }

}
