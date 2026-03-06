package com.example.demo.controller.Admin;

import com.example.demo.model.KhachHang;
import com.example.demo.model.MyOrderView;
import com.example.demo.model.SanPham;
import com.example.demo.repository.DonHangRepository;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.SanPhamRepository;
import com.example.demo.service.OrderQueryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class DashboardAdminController {

    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final DonHangRepository donHangRepository;
    private final OrderQueryService orderQueryService;
    private final AdminControllerSupport adminControllerSupport;

    @GetMapping("/admin")
    public String dashboard(Model model, HttpSession session) {
        String redirect = adminControllerSupport.requireAdmin(session);
        if (redirect != null) return redirect;

        List<KhachHang> accounts = khachHangRepository.findAllByOrderByMakhDesc();
        List<SanPham> products = sanPhamRepository.findAllByOrderByNgaylapDesc();
        List<MyOrderView> orders = orderQueryService.getAllOrders();
        Map<Integer, Integer> stockMap = adminControllerSupport.buildStockMap();

        model.addAttribute("accounts", accounts);
        model.addAttribute("products", products);
        model.addAttribute("orders", orders);
        model.addAttribute("stockMap", stockMap);

        model.addAttribute("totalAccounts", accounts.size());
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("pendingOrders", donHangRepository.countByTrangthai(1));

        return "Admin/dashboard";
    }
}
