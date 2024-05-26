package caveatemptorlite3h.models;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import caveatemptorlite3h.models.converter.ZipcodeConverter;

@Embeddable
public class Address {
    
    @NotNull
    @Column(nullable = false)
    private String street;
    
    @NotNull
    @Convert(converter = ZipcodeConverter.class)
    @Column(nullable = false, length = 5)
    private Zipcode zipcode;
    
    @NotNull
    @Column(nullable = false)
    private String city;

    private Address() {
    }

    public Address(String street, Zipcode zipcode, String city) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Zipcode getZipcode() {
        return zipcode;
    }

    public void setZipcode(Zipcode zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
