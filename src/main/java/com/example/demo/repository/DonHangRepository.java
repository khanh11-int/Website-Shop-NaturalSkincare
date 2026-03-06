// ...existing code...
package com.example.demo.repository;

import com.example.demo.model.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Integer> {
    List<DonHang> findAllByOrderByNgaydatDescMadonhangDesc();
    List<DonHang> findByMakhOrderByNgaydatDescMadonhangDesc(Integer makh);
    long countByTrangthai(Integer trangthai);
}

