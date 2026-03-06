package com.example.demo.model;

import jakarta.persistence.*;

@Entity @Table(name="muihuong")
public class MuiHuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Idmui;
    private String muihuong;
    // Getter/Setter...

    public Integer getIdmui() {return Idmui;}
    public void setIdmui(Integer Idmui) {this.Idmui = Idmui;}
    public String getMuihuong() {return muihuong;}
    public void setMuihuong(String muihuong) {this.muihuong = muihuong;}
}