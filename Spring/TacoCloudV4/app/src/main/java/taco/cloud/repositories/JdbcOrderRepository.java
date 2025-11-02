package taco.cloud.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import taco.cloud.models.IngredientRef;
import taco.cloud.models.Taco;
import taco.cloud.models.TacoOrder;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<TacoOrder> findAll() {
        return jdbcTemplate.query("select id, name, type from Ingredient", this::mapRowToOrder);
    }

    @Override
    public Optional<TacoOrder> findById(Long id) {
        return Optional.of(jdbcTemplate.queryForObject("select id, name, type from Ingredient where id=?",
                new RowMapper<TacoOrder>() {
                    public TacoOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new TacoOrder(rs.getLong("id"), rs.getString("deliveryName"),
                                rs.getString("deliveryStreet"), rs.getString("deliveryCity"),
                                rs.getString("deliveryState"), rs.getString("deliveryZip"), rs.getString("ccNumber"),
                                rs.getString("ccExpiration"), rs.getString("ccCVV"), rs.getDate("placed"), List.of());
                    };
                }, id));
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco_Order " + "(delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number, " + "cc_expiration, cc_cvv, placed_at) "
                        + "values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        order.setPlaced(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(order.getDeliveryName(),
                order.getDeliveryStreet(), order.getDeliveryCity(), order.getDeliveryState(), order.getDeliveryZip(),
                order.getCcNumber(), order.getCcExpiration(), order.getCcCVV(), order.getPlaced()));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }
        return order;
    }

    @Override
    public TacoOrder update(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "update Taco_Order set delivery_name=?, delivery_street=?, delivery_city=?, "
                        + "delivery_state=?, delivery_zip=?, cc_number=?, cc_expiration=?, cc_cvv=?, placed_at=? "
                        + "where id=?",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                Arrays.asList(tacoOrder.getDeliveryName(), tacoOrder.getDeliveryStreet(), tacoOrder.getDeliveryCity(),
                        tacoOrder.getDeliveryState(), tacoOrder.getDeliveryZip(), tacoOrder.getCcNumber(),
                        tacoOrder.getCcExpiration(), tacoOrder.getCcCVV(), tacoOrder.getPlaced(), tacoOrder.getId()));
        jdbcTemplate.update(psc);

        List<Taco> tacos = tacoOrder.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            updateTaco(tacoOrder.getId(), i++, taco);
        }
        return tacoOrder;
    }

    @Override
    public boolean remove(TacoOrder tacoOrder) {
        boolean removed = true;

        try {
            jdbcTemplate.update("delete Taco_Order where id=?", tacoOrder.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return !removed;
        }

        return removed;
    }

    private TacoOrder mapRowToOrder(ResultSet row, int rowNum) throws SQLException {
        return new TacoOrder(row.getLong("id"), row.getString("deliveryName"), row.getString("deliveryStreet"),
                row.getString("deliveryCity"), row.getString("deliveryState"), row.getString("deliveryZip"),
                row.getString("ccNumber"), row.getString("ccExpiration"), row.getString("ccCVV"), row.getDate("placed"),
                List.of());
    }

    private long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreated(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "insert into Taco " + "(name, created_at, taco_order, taco_order_key) " + "values (?, ?, ?, ?)",
                Types.VARCHAR, Types.TIMESTAMP, Types.INTEGER, Types.INTEGER);
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf
                .newPreparedStatementCreator(Arrays.asList(taco.getName(), taco.getCreated(), orderId, orderKey));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);
        saveIngredientRefs(tacoId, taco.getIngredientRefs());
        return tacoId;
    }

    private long updateTaco(Long orderId, int orderKey, Taco taco) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "update Taco set name=?, created_at=?, taco_order=?, taco_order_key=?) where id=?", Types.VARCHAR,
                Types.TIMESTAMP, Types.INTEGER, Types.INTEGER, Types.INTEGER);
        PreparedStatementCreator psc = pscf
                .newPreparedStatementCreator(Arrays.asList(taco.getName(), taco.getCreated(), orderId, orderKey));
        jdbcTemplate.update(psc);
        return taco.getId();
    }

    private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;
        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcTemplate.update("insert into Ingredient_Ref (ingredient, taco, taco_key) values (?, ?, ?)",
                    ingredientRef.getIngredient(), tacoId, key++);
        }
    }
}
