package com.example.demo.service;

import com.example.demo.model.KhachHang;
import com.example.demo.model.Role;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DangkyService {
    @Autowired
    private KhachHangRepository repokh;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    // thực hiện pt lưu đối tượng khách hàng
    public KhachHang register(KhachHang kh){
        if (repokh.existsByUsername(kh.getUsername())) {
            throw new IllegalArgumentException("username_exists");
        }
        if (repokh.existsByEmail(kh.getEmail())) {
            throw new IllegalArgumentException("email_exists");
        }

        Role userRole = roleRepository.findByNameIgnoreCase("USER")
                .orElseThrow(() -> new IllegalStateException("Thiếu role USER trong cơ sở dữ liệu"));

        kh.setRole(userRole);
        kh.setMatkhau(passwordEncoder.encode(kh.getMatkhau()));
        return repokh.save(kh);
    }
}
