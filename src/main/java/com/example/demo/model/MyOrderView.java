package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

public class MyOrderView {
    private final Integer orderId;
    private final Integer makh;
    private final String tenkh;
    private final String email;
    private final LocalDate ngaydat;
    private final Long tongtien;
    private final Integer trangthai;
    private final String trangthaiLabel;
    private final List<OrderItemView> items;

    public MyOrderView(Integer orderId,
                       LocalDate ngaydat,
                       Long tongtien,
                       Integer trangthai,
                       String trangthaiLabel,
                       List<OrderItemView> items) {
        this(orderId, null, null, null, ngaydat, tongtien, trangthai, trangthaiLabel, items);
    }

    public MyOrderView(Integer orderId,
                       Integer makh,
                       String tenkh,
                       String email,
                       LocalDate ngaydat,
                       Long tongtien,
                       Integer trangthai,
                       String trangthaiLabel,
                       List<OrderItemView> items) {
        this.orderId = orderId;
        this.makh = makh;
        this.tenkh = tenkh;
        this.email = email;
        this.ngaydat = ngaydat;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
        this.trangthaiLabel = trangthaiLabel;
        this.items = items;
    }

    public Integer getOrderId() { return orderId; }
    public Integer getMadonhang() { return orderId; }
    public Integer getMakh() { return makh; }
    public String getTenkh() { return tenkh; }
    public String getEmail() { return email; }
    public LocalDate getNgaydat() { return ngaydat; }
    public Long getTongtien() { return tongtien; }
    public Integer getTrangthai() { return trangthai; }
    public String getTrangthaiLabel() { return trangthaiLabel; }
    public List<OrderItemView> getItems() { return items; }
}
