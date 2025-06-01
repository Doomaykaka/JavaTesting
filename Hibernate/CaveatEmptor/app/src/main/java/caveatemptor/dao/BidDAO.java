package caveatemptor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import caveatemptor.models.Bid;
import caveatemptor.models.Item;

public class BidDAO implements GenericDAO<Bid> {
    private EntityManagerFactory entityFactory;

    private static final String BID_FIELD_ITEM_NAME = "item";
    private static final String BID_FIELD_ID_NAME = "id";

    public BidDAO(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public Item getBidItem(Bid bid) {
        Item bidItem = null;

        if (bid == null) {
            return bidItem;
        }

        EntityManager entityManager = this.entityFactory.createEntityManager();

        try {
            CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Bid> query = queryBuilder.createQuery(Bid.class);
            Root<Bid> fromBidsTable = query.from(Bid.class);
            fromBidsTable.fetch(BID_FIELD_ITEM_NAME);
            query.select(fromBidsTable);
            query.where(queryBuilder.equal(fromBidsTable.get(BID_FIELD_ID_NAME), bid.getId()));
            bidItem = entityManager.createQuery(query).getSingleResult().getItem();
        } catch (NoResultException a) {
            bidItem = null;
        }

        return bidItem;
    }

    @Override
    public Bid get(long id) {
        Bid foundBid = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        foundBid = entityManager.find(Bid.class, id);

        return foundBid;
    }

    @Override
    public List<Bid> getAll() {
        List<Bid> bids = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bid> query = queryBuilder.createQuery(Bid.class);
        Root<Bid> fromBidsTable = query.from(Bid.class);
        query.select(fromBidsTable);
        bids = entityManager.createQuery(query).getResultList();

        return bids;
    }

    @Override
    public boolean remove(long id) {
        boolean bidRemoved = false;

        Bid bidToRemove = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        bidToRemove = entityManager.find(Bid.class, id);
        bidRemoved = bidToRemove != null;

        if (bidRemoved) {
            entityTransaction.begin();

            entityManager.remove(bidToRemove);

            entityTransaction.commit();
        }

        return bidRemoved;
    }

    @Override
    public boolean update(Bid entity) {
        boolean bidUpdated = false;

        if (entity == null) {
            return bidUpdated;
        }

        Bid bidToUpdate = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        bidToUpdate = entityManager.find(Bid.class, entity.getId());
        bidUpdated = bidToUpdate != null;

        if (bidUpdated) {
            entityTransaction.begin();

            entityManager.merge(entity);

            entityTransaction.commit();
        }

        return bidUpdated;
    }

    @Override
    public boolean create(Bid entity) {
        boolean bidCreated = false;

        if (entity == null) {
            return bidCreated;
        }

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();

        bidCreated = entity != null;

        return bidCreated;
    }
}
