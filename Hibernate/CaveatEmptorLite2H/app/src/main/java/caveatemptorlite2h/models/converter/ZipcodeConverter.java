package caveatemptorlite2h.models.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import caveatemptorlite2h.models.GermanZipcode;
import caveatemptorlite2h.models.Zipcode;

@Converter
public class ZipcodeConverter implements AttributeConverter<Zipcode, String> {
    
    @Override
    public String convertToDatabaseColumn(Zipcode attribute) {
        return attribute.getValue();
    }

    @Override
    public Zipcode convertToEntityAttribute(String s) {
        if (s.length() == 5)
            return new GermanZipcode(s);
        else
            throw new IllegalArgumentException("Unsupported zipcode in database: " + s);
    }
}