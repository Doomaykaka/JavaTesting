/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package caveatemptorliteh;

import java.util.Date;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class App {
    public static void main(String[] args) {
        HiberConfiguration.addEntity(User.class);
        HiberConfiguration.addEntity(Bid.class);
        HiberConfiguration.addEntity(Item.class);
        HiberConfiguration.build();

        EntityManager manager = null;

        manager = HiberConfiguration.getEntityManager();

        User user = new User();
        user.setName("Jora Petrovitch");

        try {
            // Transaction check object changes
            // Persist start checking
            // After transaction object changes don't check

            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();

            user.setName("Jora Petrovitchnew");

            manager.persist(user);

            user.setName("Jora Petrovitchnewnew");

            transaction.commit();

        } catch (IllegalStateException e) {
            System.out.println("Illegal state");
        } catch (EntityExistsException e) {
            System.out.println("Entity is exists");
        }

        Item item = new Item();
        item.setName("Ershik");
        item.setAuctionEnd(new Date());

        Bid billing = new Bid();
        billing.setItem(item);

        item.addBid(billing);

        try {
            // Transaction check object changes
            // Persist start checking
            // After transaction object changes don't check
            EntityTransaction transaction = manager.getTransaction();

            transaction.begin();

            manager.persist(item);

            transaction.commit();
        } catch (IllegalStateException e) {
            System.out.println("Illegal state");
        } catch (EntityExistsException e) {
            System.out.println("Entity is exists");
        }

        manager.close();
    }
}
