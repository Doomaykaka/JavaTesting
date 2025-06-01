package caveatemptor.models;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;

import caveatemptor.models.advanced.MonetaryAmount;

@javax.persistence.metamodel.StaticMetamodel(value = Item.class)
public class Item_ {

    public static volatile SingularAttribute<Item, Long> id;
    public static volatile SingularAttribute<Item, String> name;
    public static volatile SingularAttribute<Item, AuctionType> auctionType;
    public static volatile SingularAttribute<Item, MonetaryAmount> buyNowPrice;
    public static volatile SingularAttribute<Item, MonetaryAmount> initialPrice;
    public static volatile SingularAttribute<Item, Date> auctionEnd;
    public static volatile ListAttribute<Item, List<Bid>> bids;
    public static volatile MapAttribute<LinkedHashMap<String, String>, String, String> images;
}
