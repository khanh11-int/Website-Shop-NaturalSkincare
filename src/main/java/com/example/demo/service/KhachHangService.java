package com.example.demo.service;

import com.example.demo.model.KhachHang;
import com.example.demo.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KhachHangService {

    @Autowired
    private KhachHangRepository khRepo;

    public Optional<KhachHang> findByUsername(String username) {
        if (username == null || username.isEmpty()) return Optional.empty();
        return khRepo.findByUsername(username);
    }

    public Optional<KhachHang> findById(Integer id) {
        if (id == null) return Optional.empty();
        return khRepo.findById(id);
    }

    public KhachHang save(KhachHang kh) {
        return khRepo.save(kh);
    }
}
