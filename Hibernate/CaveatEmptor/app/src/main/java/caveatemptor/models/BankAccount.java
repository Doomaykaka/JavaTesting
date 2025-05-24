package caveatemptor.models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class BankAccount extends BillingDetails {
    @NotNull
    private String account;

    @NotNull
    private String bankname;

    @NotNull
    private String swift;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    @Override
    public String toString() {
        return "BankAccount [account=" + account + ", bankname=" + bankname + ", swift=" + swift + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, bankname, swift);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BankAccount other = (BankAccount) obj;
        return Objects.equals(account, other.account) && Objects.equals(bankname, other.bankname)
                && Objects.equals(swift, other.swift);
    }
}
