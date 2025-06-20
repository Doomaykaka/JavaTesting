package liquibase.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import liquibase.models.Test;

public class TestDAO {
    private EntityManagerFactory entityFactory;

    public TestDAO(EntityManagerFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

    public Test get(long id) {
        Test foundTest = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        foundTest = entityManager.find(Test.class, id);

        return foundTest;
    }

    public List<Test> getAll() {
        List<Test> tests = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();

        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Test> query = queryBuilder.createQuery(Test.class);
        Root<Test> fromTestsTable = query.from(Test.class);
        query.select(fromTestsTable);
        tests = entityManager.createQuery(query).getResultList();

        return tests;
    }

    public boolean remove(long id) {
        boolean testRemoved = false;

        Test testToRemove = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        testToRemove = entityManager.find(Test.class, id);
        testRemoved = testToRemove != null;

        if (testRemoved) {
            entityTransaction.begin();

            entityManager.remove(testToRemove);

            entityTransaction.commit();
        }

        return testRemoved;
    }

    public boolean update(Test entity) {
        boolean testUpdated = false;

        if (entity == null) {
            return testUpdated;
        }

        Test TestToUpdate = null;

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        TestToUpdate = entityManager.find(Test.class, entity.getId());
        testUpdated = TestToUpdate != null;

        if (testUpdated) {
            entityTransaction.begin();

            entityManager.merge(entity);

            entityTransaction.commit();
        }

        return testUpdated;
    }

    public boolean create(Test entity) {
        boolean testCreated = false;

        if (entity == null) {
            return testCreated;
        }

        EntityManager entityManager = this.entityFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();

        testCreated = entity != null;

        return testCreated;
    }
}
