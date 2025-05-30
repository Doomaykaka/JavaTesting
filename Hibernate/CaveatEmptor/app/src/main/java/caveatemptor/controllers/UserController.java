package caveatemptor.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManagerFactory;

import caveatemptor.dao.BillingDetailsDAO;
import caveatemptor.dao.UserDAO;
import caveatemptor.models.BillingDetails;
import caveatemptor.models.User;

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
    private static final String UPDATE_USER_GET_ADDRESS_STREET_MESSAGE = "Input new Address street (X if need old) - ";
    private static final String UPDATE_USER_GET_ADDRESS_CITY_MESSAGE = "Input new Address city (X if need old) - ";
    private static final String UPDATE_USER_GET_ZIPCODE_MESSAGE = "Input new Zipcode (like '12 USD', X if need old) - ";
    private static final String UPDATE_USER_GET_BILLING_DETAILS_MESSAGE = "Input new Billing details identifiers (like '1,2,43', X if need old) - ";
    private static final String UPDATE_USER_NEW_BILLING_DETAILS_MESSAGE = "New User Billing details:";
    private static final String UPDATE_USER_SUCCESS_MESSAGE = "Success";
    private static final String UPDATE_USER_NOT_SUCCESS_MESSAGE = "Not success";

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

        outputData.add();

        return null;
    }

    @Override
    public List<String> create() {
        // TODO Auto-generated method stub
        return null;
    }

}
