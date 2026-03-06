package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="loai")
public class Loai {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maloai;
    private String tenloai;
    private Integer idmenu;
    // Getter/Setter...
    public Integer getMaloai() {
        return maloai;
    }
    public void setMaloai(Integer maloai) {
        this.maloai = maloai;
    }
    public String getTenloai() {
        return tenloai;
    }
    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
    public Integer getIdmenu() {
        return idmenu;
    }
}