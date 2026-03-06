package com.example.demo.repository;

import com.example.demo.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface KhachHangRepository extends JpaRepository<KhachHang, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    // viết theo dạng DSL
    Optional<KhachHang> findByUsername(String username);
    List<KhachHang> findAllByOrderByMakhDesc();
}