package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CTSanPhamId implements Serializable {

    @Column(name = "idsanpham")
    private Integer idsanpham;

    @Column(name = "idmui")
    private Integer idmui;

    @Column(name = "iddungtich")
    private Integer iddungtich;

    public CTSanPhamId() { }

    public CTSanPhamId(Integer idsanpham, Integer idmui, Integer iddungtich) {
        this.idsanpham = idsanpham;
        this.idmui = idmui;
        this.iddungtich = iddungtich;
    }

    public Integer getIdsanpham() { return idsanpham; }
    public void setIdsanpham(Integer idsanpham) { this.idsanpham = idsanpham; }

    public Integer getIdmui() { return idmui; }
    public void setIdmui(Integer idmui) { this.idmui = idmui; }

    public Integer getIddungtich() { return iddungtich; }
    public void setIddungtich(Integer iddungtich) { this.iddungtich = iddungtich; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CTSanPhamId)) return false;
        CTSanPhamId that = (CTSanPhamId) o;
        return Objects.equals(idsanpham, that.idsanpham)
                && Objects.equals(idmui, that.idmui)
                && Objects.equals(iddungtich, that.iddungtich);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idsanpham, idmui, iddungtich);
    }
}
