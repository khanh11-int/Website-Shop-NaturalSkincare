package com.example.demo.repository;

import com.example.demo.model.SanPham;
import com.example.demo.model.SanPhamWithPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {

    @Query("""
      select new com.example.demo.model.SanPhamWithPrice(
        sp.masp, sp.tensp, sp.hinh, min(ct.dongia), sp.giamgia, sp.thuonghieu
      )
      from SanPham sp
      join CTSanPham ct on ct.id.idsanpham = sp.masp
      group by sp.masp, sp.tensp, sp.hinh, sp.giamgia, sp.thuonghieu
      order by sp.ngaylap desc
    """)
    Page<SanPhamWithPrice> findLatestWithPrice(Pageable pageable);



    @Query("""
    select new com.example.demo.model.SanPhamWithPrice(
        sp.masp, sp.tensp, sp.hinh,
        min(ct.dongia),
        sp.giamgia,
        sp.thuonghieu
    )
    from SanPham sp
    join CTSanPham ct on ct.id.idsanpham = sp.masp
    where sp.giamgia > 0
    group by sp.masp, sp.tensp, sp.hinh, sp.giamgia, sp.thuonghieu
    order by (sp.giamgia / min(ct.dongia)) desc
""")
    Page<SanPhamWithPrice> findSaleWithPrice(Pageable pageable);


    Page<SanPham> findByMaloai(Integer maloai, Pageable pageable);
    @Query("""
  select new com.example.demo.model.SanPhamWithPrice(
      sp.masp, sp.tensp, sp.hinh, min(ct.dongia), sp.giamgia, sp.thuonghieu
  )
  from SanPham sp
  join CTSanPham ct on ct.id.idsanpham = sp.masp
  group by sp.masp, sp.tensp, sp.hinh, sp.giamgia, sp.thuonghieu
  order by sp.ngaylap desc
""")
    Page<SanPhamWithPrice> findAllWithPrice(Pageable pageable);



}
