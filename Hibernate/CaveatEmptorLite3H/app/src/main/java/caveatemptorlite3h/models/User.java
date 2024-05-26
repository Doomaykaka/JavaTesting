package caveatemptorlite3h.models;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Table(
        name = "users",
        indexes = {
           @Index(
                   name = "idx_firstname",
                   columnList = "firstname"
           )     
        }
       )
public class User {

    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private Address address;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<BillingDetails> billingDetails = new HashSet<BillingDetails>(); 
    
    @NotNull
    @Column(nullable = false)
    private String firstname;
    
    @NotNull
    @Column(nullable = false)
    private String lastname;

    public String getName() {
        return firstname + ' ' + lastname;
    }

    public void setName(String name) {
        StringTokenizer st = new StringTokenizer(name);
        firstname = st.nextToken();
        lastname = st.nextToken();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<BillingDetails> getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(Set<BillingDetails> billingDetails) {
        this.billingDetails = billingDetails;
    }
    
    public void addBillingDetail(BillingDetails billingDetail) {
        if (billingDetail == null)
            throw new NullPointerException("Cant't add null BillingDetails");
        
        billingDetails.add(billingDetail);
    } 
}
