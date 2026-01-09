package taco.cloud.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table
@AllArgsConstructor
public class Ingredient {
    @Id
    private long id;
    private final String name;
    @Enumerated(EnumType.STRING)
    private final Type type;

    public Ingredient() {
        name = "Some";
        type = Type.WRAP;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
