package com.example.demo.service;

import com.example.demo.model.KhachHang;
import com.example.demo.repository.KhachHangRepository;
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
    // thực hiện pt lưu đối tượng khách hàng
    public KhachHang register(KhachHang kh){

        kh.setMatkhau(passwordEncoder.encode(kh.getMatkhau()));
        return repokh.save(kh);
    }
}
