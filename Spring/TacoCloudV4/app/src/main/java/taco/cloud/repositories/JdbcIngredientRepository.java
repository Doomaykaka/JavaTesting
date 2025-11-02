package taco.cloud.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import taco.cloud.models.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbcTemplate;

    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id, name, type from Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return Optional.of(jdbcTemplate.queryForObject("select id, name, type from Ingredient where id=?",
                new RowMapper<Ingredient>() {
                    public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Ingredient(rs.getLong("id"), rs.getString("name"),
                                Ingredient.Type.valueOf(rs.getString("type")));
                    };
                }, id));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("insert into Ingredient (id, name, type) values (?, ?, ?)", ingredient.getId(),
                ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }

    @Override
    public Ingredient update(Ingredient ingredient) {
        jdbcTemplate.update("update Ingredient set name=?, type=? where id=?", ingredient.getName(),
                ingredient.getType().toString(), ingredient.getId());
        return ingredient;
    }

    @Override
    public boolean remove(Ingredient ingredient) {
        boolean removed = true;

        try {
            jdbcTemplate.update("delete Ingredient where id=?", ingredient.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return !removed;
        }

        return removed;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        return new Ingredient(row.getLong("id"), row.getString("name"), Ingredient.Type.valueOf(row.getString("type")));
    }
}
