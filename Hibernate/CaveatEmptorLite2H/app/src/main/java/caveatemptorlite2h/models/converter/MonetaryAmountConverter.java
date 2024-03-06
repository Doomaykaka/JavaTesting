package caveatemptorlite2h.models.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import caveatemptorlite2h.models.advanced.MonetaryAmount;

@Converter(autoApply = true)
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String> {
    @Override
    public String convertToDatabaseColumn(MonetaryAmount monetaryAmount) {
        return monetaryAmount.toString();
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(String s) {
        return MonetaryAmount.fromString(s);
    }
}
