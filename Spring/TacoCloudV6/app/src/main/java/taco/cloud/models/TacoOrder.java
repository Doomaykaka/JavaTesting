package taco.cloud.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class TacoOrder implements Serializable {
    @Id
    private String id;

    @Field("delivery_name")
    private String deliveryName;

    @Field("delivery_street")
    private String deliveryStreet;

    @Field("delivery_city")
    private String deliveryCity;

    @Field("delivery_state")
    private String deliveryState;

    @Field("delivery_zip")
    private String deliveryZip;

    @Field("cc_number")
    private String ccNumber;

    @Field("cc_expiration")
    private String ccExpiration;

    @Field("cc_cvv")
    private String ccCVV;

    @Field("placed_at")
    private LocalDateTime placedAt = LocalDateTime.now();

    @Field("tacos")
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        if (this.tacos == null) {
            this.tacos = new ArrayList<>();
        }
        this.tacos.add(taco);
    }
}
