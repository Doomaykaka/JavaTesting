package taco.cloud.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document
public class Ingredient {
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("type")
    private Type type;

    public Ingredient() {
    }

    @PersistenceCreator
    public Ingredient(String id, String name, Type type) {
        this.id = id;
        this.name = name != null ? name : "Some";
        this.type = type != null ? type : Type.WRAP;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
