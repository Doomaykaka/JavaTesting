package caveatemptor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
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

        EntityManager entityManager = this.entityFactory.createEntityManager();

        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bid> query = queryBuilder.createQuery(Bid.class);
        Root<Bid> fromBidsTable = query.from(Bid.class);
        fromBidsTable.fetch(BID_FIELD_ITEM_NAME);
        query.select(fromBidsTable);
        query.where(queryBuilder.equal(fromBidsTable.get(BID_FIELD_ID_NAME), bid.getId()));
        bidItem = entityManager.createQuery(query).getSingleResult().getItem();

        return bidItem;
    }

    public boolean setBidItem(Bid bid, Item item) {
        boolean bidUpdated = false;

        Bid bidToUpdate = null;
        Item itemToConnect = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        bidToUpdate = entityManager.find(Bid.class, bid.getId());
        itemToConnect = entityManager.find(Item.class, item.getId());
        bidUpdated = bidToUpdate != null && itemToConnect != null;

        if (bidUpdated) {
            entityTransaction.begin();

            bidToUpdate.setItem(itemToConnect);
            itemToConnect.addBid(bidToUpdate);

            entityManager.merge(bidToUpdate);
            entityManager.merge(itemToConnect);

            entityTransaction.commit();
        }

        return bidUpdated;
    }

    public boolean removeBidItem(Bid bid, Item item) {
        boolean bidUpdated = false;

        Bid bidToUpdate = null;
        Item itemToDisconnect = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        bidToUpdate = entityManager.find(Bid.class, bid.getId());
        itemToDisconnect = entityManager.find(Item.class, item.getId());
        bidUpdated = bidToUpdate != null && itemToDisconnect != null;

        if (bidUpdated) {
            entityTransaction.begin();

            bidToUpdate.setItem(null);
            itemToDisconnect.getBids().remove(bidToUpdate);

            entityManager.merge(bidToUpdate);
            entityManager.merge(itemToDisconnect);

            entityTransaction.commit();
        }

        return bidUpdated;
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

        Bid bidToCreate = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        bidToCreate = entityManager.find(Bid.class, entity.getId());
        bidCreated = bidToCreate != null;

        if (!bidCreated) {
            entityTransaction.begin();

            entity.setId(null);

            entityManager.persist(entity);

            entityTransaction.commit();

            bidCreated = entity != null;
        }

        return bidCreated;
    }
}
