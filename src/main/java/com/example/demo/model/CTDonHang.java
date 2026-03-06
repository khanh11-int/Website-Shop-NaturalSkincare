// ...existing code...
package com.example.demo.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "ctdonhang")
@IdClass(CTDonHangId.class)
public class CTDonHang implements Serializable {
    @Id
    @Column(name = "madonhang")
    private Integer madonhang;

    @Id
    @Column(name = "masp")
    private Integer masp;

    @Column(name = "soluongmua")
    private Integer soluongmua;

    @Column(name = "muihuong")
    private String muihuong;

    @Column(name = "dungtich")
    private Integer dungtich;

    @Column(name = "thanhtien")
    private Long thanhtien;

    @Column(name = "tinhtrang")
    private Integer tinhtrang;

    public Integer getMadonhang() { return madonhang; }
    public void setMadonhang(Integer madonhang) { this.madonhang = madonhang; }

    public Integer getMasp() { return masp; }
    public void setMasp(Integer masp) { this.masp = masp; }

    public Integer getSoluongmua() { return soluongmua; }
    public void setSoluongmua(Integer soluongmua) { this.soluongmua = soluongmua; }

    public String getMuihuong() { return muihuong; }
    public void setMuihuong(String muihuong) { this.muihuong = muihuong; }

    public Integer getDungtich() { return dungtich; }
    public void setDungtich(Integer dungtich) { this.dungtich = dungtich; }

    public Long getThanhtien() { return thanhtien; }
    public void setThanhtien(Long thanhtien) { this.thanhtien = thanhtien; }

    public Integer getTinhtrang() { return tinhtrang; }
    public void setTinhtrang(Integer tinhtrang) { this.tinhtrang = tinhtrang; }

}

