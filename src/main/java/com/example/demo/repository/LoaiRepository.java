package com.example.demo.repository;

import com.example.demo.model.Loai;
import com.example.demo.model.SanPhamWithPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoaiRepository extends JpaRepository<Loai, Integer> {

    @Query(
            value = """
        select new com.example.demo.model.SanPhamWithPrice(
            sp.masp, sp.tensp, sp.hinh,
            min(ct.dongia),
            sp.giamgia,
            sp.thuonghieu
        )
        from SanPham sp
        join CTSanPham ct on ct.id.idsanpham = sp.masp
        where sp.maloai = :maloai
        group by sp.masp, sp.tensp, sp.hinh, sp.giamgia, sp.thuonghieu
        """,
            countQuery = """
        select count(distinct sp.masp)
        from SanPham sp
        join CTSanPham ct on ct.id.idsanpham = sp.masp
        where sp.maloai = :maloai
        """
    )
    Page<SanPhamWithPrice> findByLoaiWithPrice(@Param("maloai") Integer maloai, Pageable pageable);
}
