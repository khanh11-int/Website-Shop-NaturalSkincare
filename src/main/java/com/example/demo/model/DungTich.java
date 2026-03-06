package com.example.demo.model;

import jakarta.persistence.*;

@Entity @Table(name="dungtich")
public class DungTich {
    @Id
    @Column(name = "iddungtich")
    private Integer Iddungtich;
    @Column(name = "dungtich")
    private String dungtich; // ml
    // Getter/Setter...

    public Integer getIddungtich() {return Iddungtich;}
    public void setIddungtich(Integer iddungtich) {Iddungtich = iddungtich;}
    public String getDungtich() {return dungtich;}
    public void setDungtich(String dungtich) {this.dungtich = dungtich;}
}