package com.example.demo.repository;

import com.example.demo.model.CTSanPham;
import com.example.demo.model.CTSanPhamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CTSanPhamRepository extends JpaRepository<CTSanPham, CTSanPhamId> {

    boolean existsByIdIdsanphamAndSoluongtonGreaterThan(Integer idsanpham, Integer soluongton);

    @Query("""
      select ct.id.idsanpham, coalesce(sum(ct.soluongton), 0)
      from CTSanPham ct
      group by ct.id.idsanpham
    """)
    List<Object[]> sumStockByProduct();

    @Query("""
      select ct from CTSanPham ct
      join fetch ct.mui m
      join fetch ct.dungTich d
      where ct.id.idsanpham = :id
      order by d.dungtich asc
    """)
    List<CTSanPham> findByProductIdWithDetail(@Param("id") Integer idsanpham);
}

