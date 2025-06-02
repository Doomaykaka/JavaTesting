package caveatemptor.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@org.hibernate.annotations.DynamicInsert
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "billing_details")
public abstract class BillingDetails {
    @Id
    @SequenceGenerator(name = "billing_seq", sequenceName = "billing_sequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_seq")
    private Long id = null;

    @NotNull
    private String owner;

    @ManyToOne()
    private User user;

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BillingDetails [id=" + id + ", owner=" + owner + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BillingDetails other = (BillingDetails) obj;
        return Objects.equals(id, other.id) && Objects.equals(owner, other.owner);
    }
}
