package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@   Table(name = "sanpham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masp;
    private String tensp;
    private Float giamgia;               // Khuyên dùng BigDecimal nếu cần chính xác tiền tệ
    private String hinh;
    private Integer maloai;
    @Column(columnDefinition = "bit(1)")
    private Boolean dacbiet;             // map bit(1) -> Boolean
    private Integer soluotxem;
    private LocalDate ngaylap;
    @Column(length = 2000)
    private String mota;
    private String thuonghieu;
    // 1 = available, 0 = out of stock
    private Integer tinhtrang;

    public Integer  getMasp() {return masp;}
    public void setMasp(Integer masp) {this.masp = masp;}
    public String getTensp() {return tensp;}
    public void setTensp(String tensp) {this.tensp = tensp;}
    public Float getGiamgia() {return giamgia;}
    public void setGiamgia(float giamgia) {this.giamgia = giamgia; }
    public String getHinh() { return hinh;}
    public void setHinh(String hinh) {this.hinh = hinh;}
    public Integer getMaloai() {return maloai;}
    public void setMaloai(Integer maloai) {this.maloai = maloai;}
    public Boolean getDacbiet() {return dacbiet;}
    public void setDacbiet(Boolean dacbiet) {this.dacbiet = dacbiet;}
    public Integer getSoluotxem() {return soluotxem;}
    public void setSoluotxem(Integer soluotxem) {this.soluotxem = soluotxem;}
    public LocalDate getNgaylap() {return ngaylap;}
    public void setNgaylap(LocalDate ngaylap) {this.ngaylap = ngaylap;}
    public String getMota() {return mota;}
    public void setMota(String mota) {this.mota = mota;}
    public String getThuonghieu() {return thuonghieu;}
    public void setThuonghieu(String thuonghieu) {this.thuonghieu = thuonghieu;}
    public Integer getTinhtrang() { return tinhtrang; }
    public void setTinhtrang(Integer tinhtrang) { this.tinhtrang = tinhtrang; }
}