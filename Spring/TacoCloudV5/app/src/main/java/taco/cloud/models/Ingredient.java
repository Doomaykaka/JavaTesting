package taco.cloud.models;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Table("ingredients")
@AllArgsConstructor
public class Ingredient {
    @PrimaryKey
    private long id;
    private final String name;
    private final Type type;

    public Ingredient() {
        name = "Some";
        type = Type.WRAP;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
