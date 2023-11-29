package idioms;

import java.util.Objects;

public class EqualsUsageExample {
    public int field;
    
    public EqualsUsageExample(int field) {
        this.field = field;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EqualsUsageExample other = (EqualsUsageExample) obj;
        return field == other.field;
    }
}
