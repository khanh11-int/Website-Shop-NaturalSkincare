package com.example.demo.controller;

import com.example.demo.model.KhachHang;
import com.example.demo.model.MyOrderView;
import com.example.demo.model.SanPham;
import com.example.demo.repository.CTSanPhamRepository;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.SanPhamRepository;
import com.example.demo.service.OrderQueryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final CTSanPhamRepository ctSanPhamRepository;
    private final OrderQueryService orderQueryService;

    @GetMapping("/admin")
    public String dashboard(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || !"admin".equalsIgnoreCase(username)) {
            return "redirect:/login";
        }

        List<KhachHang> accounts = khachHangRepository.findAllByOrderByMakhDesc();
        List<SanPham> products = sanPhamRepository.findAllByOrderByNgaylapDesc();
        List<MyOrderView> orders = orderQueryService.getAllOrders();

        Map<Integer, Integer> stockMap = new HashMap<>();
        for (Object[] row : ctSanPhamRepository.sumStockByProduct()) {
            stockMap.put((Integer) row[0], ((Number) row[1]).intValue());
        }

        model.addAttribute("accounts", accounts);
        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        model.addAttribute("stockMap", stockMap);

        model.addAttribute("totalAccounts", accounts.size());
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalOrders", orders.size());

        return "admin/dashboard";
    }
}
