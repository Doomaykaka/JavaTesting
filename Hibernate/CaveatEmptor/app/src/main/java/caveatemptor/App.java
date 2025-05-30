/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package caveatemptor;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import caveatemptor.configuration.ApplicationConfigReader;
import caveatemptor.configuration.HiberConfiguration;
import caveatemptor.dao.BidDAO;
import caveatemptor.dao.BillingDetailsDAO;
import caveatemptor.dao.ItemDAO;
import caveatemptor.dao.UserDAO;
import caveatemptor.models.Address;
import caveatemptor.models.AuctionType;
import caveatemptor.models.BankAccount;
import caveatemptor.models.Bid;
import caveatemptor.models.BillingDetails;
import caveatemptor.models.CreditCard;
import caveatemptor.models.GermanZipcode;
import caveatemptor.models.Item;
import caveatemptor.models.Item_;
import caveatemptor.models.User;
import caveatemptor.models.Zipcode;
import caveatemptor.models.advanced.MonetaryAmount;
import caveatemptor.models.converter.ZipcodeConverter;

public class App {
    public static void main(String[] args) throws IOException {
        // Configuration

        ApplicationConfigReader configReader = new ApplicationConfigReader();

        HiberConfiguration.addEntity(User.class);
        HiberConfiguration.addEntity(Bid.class);
        HiberConfiguration.addEntity(Item.class);
        HiberConfiguration.addEntity(BillingDetails.class);
        HiberConfiguration.build(configReader);

        EntityManagerFactory managerFactory = null;

        managerFactory = HiberConfiguration.getEntityManagerFactory();

        BidDAO bidDTO = new BidDAO(managerFactory);
        BillingDetailsDAO billingDetailsDTO = new BillingDetailsDAO(managerFactory);
        ItemDAO itemDTO = new ItemDAO(managerFactory);
        UserDAO userDTO = new UserDAO(managerFactory);

        // Manage objects

        CreditCard card = new CreditCard();
        card.setCardNumber("1717171717");
        card.setExpMonth("07");
        card.setExpYear("28");
        card.setOwner("JP");

        Zipcode zipcode = (new ZipcodeConverter()).convertToEntityAttribute("12567");
        Address address = new Address("Vasiliyevskaya", zipcode, "Moscow");

        User user = new User();
        user.setName("Jora Petrovitch");
        user.setAddress(address);
        user.addBillingDetail(card);

        card.setUser(user);

        BankAccount account = new BankAccount();
        account.setAccount("vit_mel");
        account.setBankname("Bank Of Lies");
        account.setOwner("VM");
        account.setSwift("u8f8g8hihll6k");

        Zipcode zipcode2 = (new ZipcodeConverter()).convertToEntityAttribute("12567");
        Address address2 = new Address("Petrovskaya", zipcode2, "Moscow");

        User user2 = new User();
        user2.setName("Vitaliy Metaliy");
        user2.setAddress(address2);
        user2.addBillingDetail(account);

        account.setUser(user2);

        user.setName("Jora Petrovitchnew");
        userDTO.create(user);

        user2.setName("Jora Petrovitchnewnew");
        userDTO.create(user2);

        // Manage related objects

        Item item = new Item();
        item.setName("Ershik");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        Date current = cal.getTime();
        item.setAuctionEnd(current);
        item.setAuctionType(AuctionType.FIXED_PRICE);
        item.setInitialPrice(MonetaryAmount.fromString("12 USD"));
        item.setBuyNowPrice(MonetaryAmount.fromString("12 USD"));
        item.getImages().put("main", "item-main.jpeg");

        Bid billing = new Bid();
        billing.setItem(item);

        item.addBid(billing);

        itemDTO.create(item);

//        // Using queryies
//
//        Query findOlderQuery = manager.createNamedQuery("findUser");
//        List<User> users = findOlderQuery.getResultList();
//
//        System.out.println("Users:");
//        for (User listUser : users) {
//            System.out.println(listUser.getName());
//        }
//
//        // Validation (custom exec)
//
//        ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
//        Validator validator = vFactory.getValidator();
//
//        Item testItem = new Item();
//        item.setName("Item");
//        item.setAuctionEnd(new Date());
//        item.setAuctionType(AuctionType.FIXED_PRICE);
//        item.setInitialPrice(MonetaryAmount.fromString("12 USD"));
//        item.setBuyNowPrice(MonetaryAmount.fromString("12 USD"));
//        item.getImages().put("mainSecond", "item-main-second.jpeg");
//
//        Set<ConstraintViolation<Item>> violations = validator.validate(item);
//
//        System.out.println("Violations size: " + violations.size());
//
//        ConstraintViolation<Item> violation = violations.iterator().next();
//        String failedPropertyName = violation.getPropertyPath().iterator().next().getName();
//
//        System.out.println("Faild property: " + failedPropertyName);
//
//        // Metamodel (dynamic)
//
//        Metamodel mm = manager.getMetamodel();
//        Set<ManagedType<?>> managedTypes = mm.getManagedTypes();
//
//        System.out.println("Items (metamodel):");
//        for (ManagedType itemType : managedTypes) {
//            try {
//                SingularAttribute nameAttribute = itemType.getSingularAttribute("name");
//                System.out.println("Name field type: " + nameAttribute.getJavaType());
//                System.out.println("Name field persistent type: " + nameAttribute.getPersistentAttributeType());
//                System.out.println("Name field is optional: " + nameAttribute.isOptional());
//
//                SingularAttribute auctionEndAttribute = itemType.getSingularAttribute("auctionEnd");
//                System.out.println("AuctionEnd field type: " + nameAttribute.getJavaType());
//                System.out.println("AuctionEnd field is collection: " + nameAttribute.isCollection());
//                System.out.println("AuctionEnd field is association: " + nameAttribute.isAssociation());
//            } catch (IllegalArgumentException e) {
//                e.getStackTrace();
//            }
//        }
//
//        // Metamodel (static)
//
//        CriteriaBuilder cb = manager.getCriteriaBuilder();
//        CriteriaQuery<Item> query = cb.createQuery(Item.class);
//        Root<Item> fromItem = query.from(Item.class);
//        query.select(fromItem);
//        List<Item> items = manager.createQuery(query).getResultList();
//
//        Path<String> namePath = fromItem.get("name");
//        query.where(cb.like(namePath, cb.parameter(String.class, "pattern")));
//        items = manager.createQuery(query).setParameter("pattern", "Ershik").getResultList();
//
//        System.out.println("Items (metamodel-static):");
//        for (Item listItem : items) {
//            System.out.println(listItem.getName() + " " + listItem.getAuctionEnd());
//        }
//
//        // Metamodel (static with metamodel using)
//
//        CriteriaBuilder cbm = manager.getCriteriaBuilder();
//        CriteriaQuery<Item> querym = cbm.createQuery(Item.class);
//        Root<Item> fromItemM = querym.from(Item.class);
//        querym.select(fromItemM);
//        List<Item> itemsM = manager.createQuery(querym).getResultList();
//
//        Path<String> namePathM = fromItemM.get(Item_.name);
//        querym.where(cbm.like(namePathM, cbm.parameter(String.class, "pattern")));
//        itemsM = manager.createQuery(querym).setParameter("pattern", "Ershik").getResultList();
//
//        System.out.println("Items (metamodel-static with metamodel using):");
//        for (Item listItemM : itemsM) {
//            System.out.println(listItemM.getName() + " " + listItemM.getAuctionEnd());
//        }
//
//        // Exit
//
//        manager.close();
    }
}
