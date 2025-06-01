package caveatemptor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import caveatemptor.models.BillingDetails;
import caveatemptor.models.User;

public class BillingDetailsDAO implements GenericDAO<BillingDetails> {
    private EntityManagerFactory entityFactory;

    private static final String BILLING_DETAILS_FIELD_USER_NAME = "user";
    private static final String BILLING_DETAILS_FIELD = "id";

    public BillingDetailsDAO(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public User getBillingDetailsUser(BillingDetails billingDetails) {
        User billingDetailsUser = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        try {
            CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<BillingDetails> query = queryBuilder.createQuery(BillingDetails.class);
            Root<BillingDetails> fromBillingDetailsTable = query.from(BillingDetails.class);
            fromBillingDetailsTable.fetch(BILLING_DETAILS_FIELD_USER_NAME);
            query.select(fromBillingDetailsTable);
            query.where(queryBuilder.equal(fromBillingDetailsTable.get(BILLING_DETAILS_FIELD), billingDetails.getId()));
            billingDetailsUser = entityManager.createQuery(query).getSingleResult().getUser();
        } catch (NoResultException a) {
            billingDetailsUser = null;
        }

        return billingDetailsUser;
    }

    public boolean setBillingDetailsUser(BillingDetails billingDetails, User user) {
        boolean billingDetailsUpdated = false;

        BillingDetails billingDetailsToUpdate = null;
        User userToConnect = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        billingDetailsToUpdate = entityManager.find(BillingDetails.class, billingDetails.getId());
        userToConnect = entityManager.find(User.class, user.getId());
        billingDetailsUpdated = billingDetailsToUpdate != null && userToConnect != null;

        if (billingDetailsUpdated) {
            entityTransaction.begin();

            billingDetailsToUpdate.setUser(userToConnect);
            userToConnect.addBillingDetail(billingDetailsToUpdate);

            entityManager.merge(billingDetailsToUpdate);
            entityManager.merge(userToConnect);

            entityTransaction.commit();
        }

        return billingDetailsUpdated;
    }

    public boolean removeBillingDetailsUser(BillingDetails billingDetails, User user) {
        boolean billingDetailsUpdated = false;

        BillingDetails billingDetailsToUpdate = null;
        User userToDisconnect = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        billingDetailsToUpdate = entityManager.find(BillingDetails.class, billingDetails.getId());
        userToDisconnect = entityManager.find(User.class, user.getId());
        billingDetailsUpdated = billingDetailsToUpdate != null && userToDisconnect != null;

        if (billingDetailsUpdated) {
            entityTransaction.begin();

            billingDetailsToUpdate.setUser(null);
            userToDisconnect.getBillingDetails().remove(billingDetailsToUpdate);

            entityManager.merge(billingDetailsToUpdate);
            entityManager.merge(userToDisconnect);

            entityTransaction.commit();
        }

        return billingDetailsUpdated;
    }

    @Override
    public BillingDetails get(long id) {
        BillingDetails foundBillingDetails = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        foundBillingDetails = entityManager.find(BillingDetails.class, id);

        return foundBillingDetails;
    }

    @Override
    public List<BillingDetails> getAll() {
        List<BillingDetails> billingDetails = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BillingDetails> query = queryBuilder.createQuery(BillingDetails.class);
        Root<BillingDetails> fromBillingDetailsTable = query.from(BillingDetails.class);
        query.select(fromBillingDetailsTable);
        billingDetails = entityManager.createQuery(query).getResultList();

        return billingDetails;
    }

    @Override
    public boolean remove(long id) {
        boolean billingDetailsRemoved = false;

        BillingDetails billingDetailsToRemove = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        billingDetailsToRemove = entityManager.find(BillingDetails.class, id);
        billingDetailsRemoved = billingDetailsToRemove != null;

        if (billingDetailsRemoved) {
            entityTransaction.begin();

            entityManager.remove(billingDetailsToRemove);

            entityTransaction.commit();
        }

        return billingDetailsRemoved;
    }

    @Override
    public boolean update(BillingDetails entity) {
        boolean billingDetailsUpdated = false;

        BillingDetails billingDetailsToUpdate = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        billingDetailsToUpdate = entityManager.find(BillingDetails.class, entity.getId());
        billingDetailsUpdated = billingDetailsToUpdate != null;

        if (billingDetailsUpdated) {
            entityTransaction.begin();

            entityManager.merge(entity);

            entityTransaction.commit();
        }

        return billingDetailsUpdated;
    }

    @Override
    public boolean create(BillingDetails entity) {
        boolean billingDetailsCreated = false;

        BillingDetails billingDetailsToCreate = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        billingDetailsToCreate = entityManager.find(BillingDetails.class, entity.getId());
        billingDetailsCreated = billingDetailsToCreate != null;

        if (!billingDetailsCreated) {
            entityTransaction.begin();

            entity.setId(null);

            entityManager.merge(entity);

            entityTransaction.commit();

            billingDetailsCreated = entity != null;
        }

        return billingDetailsCreated;
    }

}
