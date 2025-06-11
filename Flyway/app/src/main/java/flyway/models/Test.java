package flyway.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @SequenceGenerator(name = "test_seq", sequenceName = "test_sequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_seq")
    private Long id = null;

    @Column(nullable = false)
    private String data;

    public Test() {
    }

    public Test(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Test other = (Test) obj;
        return Objects.equals(data, other.data) && Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Test [id=" + id + ", data=" + data + "]";
    }
}
