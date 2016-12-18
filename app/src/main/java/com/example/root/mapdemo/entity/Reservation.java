package com.example.root.mapdemo.entity;

import com.google.android.gms.identity.intents.AddressConstants;

import java.util.List;


public class Reservation {

    private String token;

    private String payerId;

    private String itemTotal;

    private String orderTotal;

    private String promotionCode;

    private int clientId;

    private List<AddressConstants.Extras> extras;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public List<AddressConstants.Extras> getExtras() {
        return extras;
    }

    public void setExtras(List<AddressConstants.Extras> extras) {
        this.extras = extras;
    }
}
