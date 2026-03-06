// ...existing code...
package com.example.demo.repository;

import com.example.demo.model.CTDonHang;
import com.example.demo.model.CTDonHangId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CTDonHangRepository extends JpaRepository<CTDonHang, CTDonHangId> {
    List<CTDonHang> findByMadonhangOrderByMaspAsc(Integer madonhang);
}

