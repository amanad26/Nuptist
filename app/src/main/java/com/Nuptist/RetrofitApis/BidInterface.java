package com.Nuptist.RetrofitApis;

public interface BidInterface {

    void addBid(String  minPrice ,String MaxPrice, String date , String pid , String service_id);
    void addBid2(String  minPrice ,String MaxPrice, String date , String pid , String service_id);

    void addOfferBid(String  minPrice ,String MaxPrice, String date , String pid , String service_id);
    void addOfferBid2(String  minPrice ,String MaxPrice, String date , String pid , String service_id);

}
