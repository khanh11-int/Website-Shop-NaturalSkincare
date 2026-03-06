// ...existing code...
package com.example.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class CTDonHangId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer madonhang;
    private Integer masp;

    public CTDonHangId() {}
    public CTDonHangId(Integer masohd, Integer masp) { this.madonhang = masohd; this.masp = masp; }

    public Integer getMadonhang() { return madonhang; }
    public void setMadonhang(Integer madonhang) { this.madonhang = madonhang; }
    public Integer getMasp() { return masp; }
    public void setMasp(Integer masp) { this.masp = masp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CTDonHangId that = (CTDonHangId) o;
        return Objects.equals(madonhang, that.madonhang) && Objects.equals(masp, that.masp);
    }

    @Override
    public int hashCode() { return Objects.hash(madonhang, masp); }
}

