package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ctsanpham")
public class CTSanPham {

    @EmbeddedId
    private CTSanPhamId id;

    @Column(name = "dongia")
    private Float dongia;

    @Column(name = "soluongton")
    private Integer soluongton;

    // JOIN để lấy label mùi & dung tích (read-only theo id tổng hợp)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmui", referencedColumnName = "Idmui", insertable = false, updatable = false)
    private MuiHuong mui;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddungtich", referencedColumnName = "Iddungtich", insertable = false, updatable = false)
    private DungTich dungTich;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsanpham", referencedColumnName = "masp", insertable = false, updatable = false)
    private SanPham sanpham;

    // getters/setters …
    public CTSanPhamId getId() { return id; }
    public void setId(CTSanPhamId id) { this.id = id; }
    public Float getDongia() { return dongia; }
    public void setDongia(Float dongia) { this.dongia = dongia; }
    public Integer getSoluongton() { return soluongton; }
    public void setSoluongton(Integer soluongton) { this.soluongton = soluongton; }
    public MuiHuong getMui() { return mui; }
    public DungTich getDungTich() { return dungTich; }
    public SanPham getSanpham() { return sanpham; }
}
