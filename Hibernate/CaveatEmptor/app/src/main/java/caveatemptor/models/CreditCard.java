package caveatemptor.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class CreditCard extends BillingDetails {
    @NotNull
    private String cardNumber;

    @NotNull
    private String expMonth;

    @NotNull
    private String expYear;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    @Override
    public String toString() {
        return "CreditCard [cardNumber=" + cardNumber + ", expMonth=" + expMonth + ", expYear=" + expYear + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hash(cardNumber, expMonth, expYear);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreditCard other = (CreditCard) obj;
        return Objects.equals(cardNumber, other.cardNumber) && Objects.equals(expMonth, other.expMonth)
                && Objects.equals(expYear, other.expYear);
    }
}
