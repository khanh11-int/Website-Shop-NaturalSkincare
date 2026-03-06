package com.example.demo.model;

import java.io.Serializable;

public class CartItem implements Serializable {

    private String key; // productId-dungtichId-muiId
    private Integer productId;
    private String name;
    private String image;

    private Integer dungtichId;
    private String dungtichLabel;

    private Integer muiId;
    private String muiLabel;

    private long unitPrice;
    private int quantity;

    public CartItem() {}

    public CartItem(String key, Integer productId, String name, String image,
                    Integer dungtichId, String dungtichLabel,
                    Integer muiId, String muiLabel,
                    long unitPrice, int quantity) {

        this.key = key;
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.dungtichId = dungtichId;
        this.dungtichLabel = dungtichLabel;
        this.muiId = muiId;
        this.muiLabel = muiLabel;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Integer getDungtichId() { return dungtichId; }
    public void setDungtichId(Integer dungtichId) { this.dungtichId = dungtichId; }

    public String getDungtichLabel() { return dungtichLabel; }
    public void setDungtichLabel(String dungtichLabel) { this.dungtichLabel = dungtichLabel; }

    public Integer getMuiId() { return muiId; }
    public void setMuiId(Integer muiId) { this.muiId = muiId; }

    public String getMuiLabel() { return muiLabel; }
    public void setMuiLabel(String muiLabel) { this.muiLabel = muiLabel; }

    public long getUnitPrice() { return unitPrice; }
    public void setUnitPrice(long unitPrice) { this.unitPrice = unitPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public long getSubtotal() {
        return unitPrice * (long) quantity;
    }
}
