package com.example.demo.model;

public class SanPhamWithPrice {
    private Integer masp;
    private String  tensp;
    private String  hinh;
    private Float   dongia;      // min(ct.dongia)
    private Float   giamgia;     // sp.giamgia
    private String  thuonghieu;  // sp.thuonghieu

    public SanPhamWithPrice(Integer masp, String tensp, String hinh,
                            Float dongia, Float giamgia, String thuonghieu) {
        this.masp = masp; this.tensp = tensp; this.hinh = hinh;
        this.dongia = dongia; this.giamgia = giamgia; this.thuonghieu = thuonghieu;
    }
    public Integer getMasp() { return masp; }
    public String  getTensp() { return tensp; }
    public String  getHinh() { return hinh; }
    public Float   getDongia() { return dongia; }
    public Float   getGiamgia() { return giamgia; }
    public String  getThuonghieu() { return thuonghieu; }
}
