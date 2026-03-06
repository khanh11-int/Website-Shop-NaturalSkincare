package com.example.demo.service;

import com.example.demo.model.KhachHang;

import com.example.demo.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DangNhapService {
    @Autowired
    private KhachHangRepository repokh;
    @Autowired
    private PasswordEncoder passwordEncoder;
    // thực hiện đăng nhập
    public Optional<KhachHang> authenticate(String username, String rawPassword) {
        if (username == null || rawPassword == null) return Optional.empty();
        Optional<KhachHang> opt = repokh.findByUsername(username);
        if (opt.isPresent()) {
            KhachHang kh = opt.get();
            if (passwordEncoder.matches(rawPassword, kh.getMatkhau())) return Optional.of(kh);
        }
        return Optional.empty();
    }
}
