package caveatemptor.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import caveatemptor.models.advanced.MonetaryAmount;
import caveatemptor.models.converter.MonetaryAmountConverter;

@Entity
@org.hibernate.annotations.DynamicInsert
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "items")
public class Item {

    @Id
    @SequenceGenerator(name = "item_seq", sequenceName = "item_sequence", initialValue = 1, allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    private Long id = null;

    @NotNull
    @Size(min = 2, max = 255, message = "Name is required, maximum 255 characters.")
    private String name;

    @Future
    @Column(name = "auction_end")
    private Date auctionEnd;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuctionType auctionType = AuctionType.HIGHEST_BID;

    @NotNull
    @Convert(converter = MonetaryAmountConverter.class, disableConversion = false)
    @Column(name = "current_price", length = 63)
    protected MonetaryAmount buyNowPrice;

    @NotNull
    @Convert(converter = MonetaryAmountConverter.class, disableConversion = false)
    @Column(name = "initial_price", length = 63)
    protected MonetaryAmount initialPrice;

    @ElementCollection
    @CollectionTable(name = "image")
    @MapKeyColumn(name = "imagename")
    @Column(name = "filename")
    @org.hibernate.annotations.OrderBy(clause = "filename desc")
    protected Map<String, String> images = new LinkedHashMap<String, String>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item", orphanRemoval = true)
    private List<Bid> bids = new ArrayList<Bid>();

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Long getId() {
        return id;
    }

    public void addBid(Bid itemBid) {
        if (itemBid == null)
            throw new NullPointerException("Cant't add null Bid");

        getBids().add(itemBid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Date auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public AuctionType getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(AuctionType auctionType) {
        this.auctionType = auctionType;
    }

    public MonetaryAmount getBuyNowPrice() {
        return buyNowPrice;
    }

    public void setBuyNowPrice(MonetaryAmount buyNowPrice) {
        this.buyNowPrice = buyNowPrice;
    }

    public MonetaryAmount getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(MonetaryAmount initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + ", auctionEnd=" + auctionEnd + ", auctionType=" + auctionType
                + ", buyNowPrice=" + buyNowPrice + ", initialPrice=" + initialPrice + ", images=" + images + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionEnd, auctionType, buyNowPrice, id, images, initialPrice, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        return Objects.equals(auctionEnd, other.auctionEnd) && auctionType == other.auctionType
                && Objects.equals(buyNowPrice, other.buyNowPrice) && Objects.equals(id, other.id)
                && Objects.equals(images, other.images) && Objects.equals(initialPrice, other.initialPrice)
                && Objects.equals(name, other.name);
    }
}
