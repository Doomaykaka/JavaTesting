package caveatemptor.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import caveatemptor.models.BillingDetails;
import caveatemptor.models.User;

public class UserDAO implements GenericDAO<User> {
    private EntityManagerFactory entityFactory;

    private static final String USER_FIELD_BILLING_DETAILS_NAME = "billingDetails";
    private static final String USER_FIELD_ID_NAME = "id";

    public UserDAO(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public List<BillingDetails> getUserBillingDetails(User user) {
        Set<BillingDetails> userBillingDetails = null;

        if (user == null) {
            return null;
        }

        EntityManager entityManager = this.entityFactory.createEntityManager();

        try {
            CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> query = queryBuilder.createQuery(User.class);
            Root<User> fromUsersTable = query.from(User.class);
            fromUsersTable.fetch(USER_FIELD_BILLING_DETAILS_NAME);
            query.select(fromUsersTable);
            query.where(queryBuilder.equal(fromUsersTable.get(USER_FIELD_ID_NAME), user.getId()));
            userBillingDetails = entityManager.createQuery(query).getSingleResult().getBillingDetails();
        } catch (NoResultException a) {
            userBillingDetails = null;
        }

        return List.copyOf(userBillingDetails);
    }

    @Override
    public User get(long id) {
        User foundUser = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        foundUser = entityManager.find(User.class, id);

        return foundUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = queryBuilder.createQuery(User.class);
        Root<User> fromUsersTable = query.from(User.class);
        query.select(fromUsersTable);
        users = entityManager.createQuery(query).getResultList();

        return users;
    }

    @Override
    public boolean remove(long id) {
        boolean userRemoved = false;

        User userToRemove = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        userToRemove = entityManager.find(User.class, id);
        userRemoved = userToRemove != null;

        if (userRemoved) {
            entityTransaction.begin();

            entityManager.remove(userToRemove);

            entityTransaction.commit();
        }

        return userRemoved;
    }

    @Override
    public boolean update(User entity) {
        boolean userUpdated = false;

        if (entity == null) {
            return userUpdated;
        }

        User userToUpdate = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        userToUpdate = entityManager.find(User.class, entity.getId());
        userUpdated = userToUpdate != null;

        if (userUpdated) {
            entityTransaction.begin();

            entityManager.merge(entity);

            entityTransaction.commit();
        }

        return userUpdated;
    }

    @Override
    public boolean create(User entity) {
        boolean userCreated = false;

        if (entity == null) {
            return userCreated;
        }

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();

        userCreated = entity != null;

        return userCreated;
    }
}
