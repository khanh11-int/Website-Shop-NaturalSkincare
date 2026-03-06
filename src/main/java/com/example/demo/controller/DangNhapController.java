package com.example.demo.controller;

import com.example.demo.service.DangNhapService;
import com.example.demo.model.KhachHang;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
@Controller
public class DangNhapController {
    @Autowired
    private DangNhapService khService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        Optional<KhachHang> opt = khService.authenticate(username, password);
        if (opt.isPresent()) {
            KhachHang kh = opt.get();
            session.setAttribute("username", kh.getUsername());
            session.setAttribute("userid", kh.getMakh());
            System.out.println(username);
            return "redirect:/"; // thành công về trang chủ

        }
        model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
        return "login";
    }

    // Lấy GET logout (link)
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // Nhấn vào logout thì dùng invalidate session để đăng xuất
    // invalidate(): Hủy toàn bộ session (đăng xuất, hết hạn)
    @PostMapping("/logout")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
