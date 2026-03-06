package com.example.demo.model;

public class OrderItemView {
    private final Integer productId;
    private final String productName;
    private final String image;
    private final Integer quantity;
    private final String muihuong;
    private final Integer dungtich;
    private final Long thanhtien;

    public OrderItemView(Integer productId, String productName, String image, Integer quantity, String muihuong, Integer dungtich, Long thanhtien) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
        this.quantity = quantity;
        this.muihuong = muihuong;
        this.dungtich = dungtich;
        this.thanhtien = thanhtien;
    }

    public Integer getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getImage() { return image; }
    public Integer getQuantity() { return quantity; }
    public String getMuihuong() { return muihuong; }
    public Integer getDungtich() { return dungtich; }
    public Long getThanhtien() { return thanhtien; }
}
