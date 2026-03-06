package com.example.demo.controller.User;

import com.example.demo.model.KhachHang;
import com.example.demo.service.KhachHangService;
import com.example.demo.service.OrderQueryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private KhachHangService khService;

    @Autowired
    private OrderQueryService orderQueryService;

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        // Lấy userid từ session (đã lưu khi login)
        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) {
            return "redirect:/login"; // chưa đăng nhập thì về login
        }

        // Tìm khách hàng theo ID
        Optional<KhachHang> opt = khService.findById(userId);
        if (opt.isPresent()) {
            model.addAttribute("khachhang", opt.get());
        } else {
            model.addAttribute("errorMessage", "Không tìm thấy thông tin khách hàng");
        }

        return "profile";
    }

    @GetMapping("/my-orders")
    public String myOrders(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("orders", orderQueryService.getOrdersByCustomer(userId));
        return "my-orders";
    }
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("khachhang") KhachHang formData, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) return "redirect:/login";

        // lấy bản gốc để tránh ghi đè password
        Optional<KhachHang> opt = khService.findById(userId);
        if (opt.isPresent()) {
            KhachHang kh = opt.get();
            kh.setTenkh(formData.getTenkh());
            kh.setEmail(formData.getEmail());
            kh.setSodienthoai(formData.getSodienthoai());
            kh.setDiachi(formData.getDiachi());
            khService.save(kh);
        }
        return "redirect:/profile";
    }
}
