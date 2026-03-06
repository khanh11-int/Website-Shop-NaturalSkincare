package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "donhang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer madonhang;

    // may be null for guest checkout
    private Integer makh;

    private LocalDate ngaydat;

    private Long tongtien;

    // guest/customer fields (new in DB schema)
    private String tenkh;
    private String email;
    private String sodienthoai;
    private String diachi;

    public Integer getMadonhang() { return madonhang; }
    public void setMadonhang(Integer madonhang) { this.madonhang = madonhang; }

    public Integer getMakh() { return makh; }
    public void setMakh(Integer makh) { this.makh = makh; }

    public LocalDate getNgaydat() { return ngaydat; }
    public void setNgaydat(LocalDate ngaydat) { this.ngaydat = ngaydat; }

    public Long getTongtien() { return tongtien; }
    public void setTongtien(Long tongtien) { this.tongtien = tongtien; }

    public String getTenkh() { return tenkh; }
    public void setTenkh(String tenkh) { this.tenkh = tenkh; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSodienthoai() { return sodienthoai; }
    public void setSodienthoai(String sodienthoai) { this.sodienthoai = sodienthoai; }

    public String getDiachi() { return diachi; }
    public void setDiachi(String diachi) { this.diachi = diachi; }
}
