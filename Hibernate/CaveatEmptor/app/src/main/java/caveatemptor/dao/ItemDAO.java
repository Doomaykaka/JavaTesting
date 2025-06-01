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

public class ItemDAO implements GenericDAO<Item> {
    private EntityManagerFactory entityFactory;

    private static final String ITEM_FIELD_BIDS_NAME = "bids";
    private static final String ITEM_FIELD_ID_NAME = "id";

    public ItemDAO(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public List<Bid> getItemBids(Item item) {
        List<Bid> itemBids = null;

        if (item == null) {
            return itemBids;
        }

        EntityManager entityManager = this.entityFactory.createEntityManager();

        try {
            CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Item> query = queryBuilder.createQuery(Item.class);
            Root<Item> fromItemsTable = query.from(Item.class);
            fromItemsTable.fetch(ITEM_FIELD_BIDS_NAME);
            query.select(fromItemsTable);
            query.where(queryBuilder.equal(fromItemsTable.get(ITEM_FIELD_ID_NAME), item.getId()));
            itemBids = entityManager.createQuery(query).getSingleResult().getBids();
        } catch (NoResultException a) {
            itemBids = null;
        }

        return itemBids;
    }

    @Override
    public Item get(long id) {
        Item foundItem = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        foundItem = entityManager.find(Item.class, id);

        return foundItem;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = queryBuilder.createQuery(Item.class);
        Root<Item> fromItemsTable = query.from(Item.class);
        query.select(fromItemsTable);
        items = entityManager.createQuery(query).getResultList();

        return items;
    }

    @Override
    public boolean remove(long id) {
        boolean itemRemoved = false;

        Item itemToRemove = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        itemToRemove = entityManager.find(Item.class, id);
        itemRemoved = itemToRemove != null;

        if (itemRemoved) {
            entityTransaction.begin();

            entityManager.remove(itemToRemove);

            entityTransaction.commit();
        }

        return itemRemoved;
    }

    @Override
    public boolean update(Item entity) {
        boolean itemUpdated = false;

        if (entity == null) {
            return itemUpdated;
        }

        Item itemToUpdate = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        itemToUpdate = entityManager.find(Item.class, entity.getId());
        itemUpdated = itemToUpdate != null;

        if (itemUpdated) {
            entityTransaction.begin();

            entityManager.merge(entity);

            entityTransaction.commit();
        }

        return itemUpdated;
    }

    @Override
    public boolean create(Item entity) {
        boolean itemCreated = false;

        if (entity == null) {
            return itemCreated;
        }

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();

        itemCreated = entity != null;

        return itemCreated;
    }

}
