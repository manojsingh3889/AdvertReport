package org.crealytics.extra;


import org.crealytics.utility.CSVProperty;

public class DummyProduct{
    private int quantity;

    @CSVProperty("cost_price")
    private float costPrice;

    @CSVProperty("sell_price")
    private float sellPrice;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }
}
