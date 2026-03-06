package com.example.demo.controller;

import com.example.demo.model.KhachHang;
import com.example.demo.model.DonHang;
import com.example.demo.model.MyOrderView;
import com.example.demo.model.Role;
import com.example.demo.model.SanPham;
import com.example.demo.repository.CTSanPhamRepository;
import com.example.demo.repository.DonHangRepository;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.LoaiRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SanPhamRepository;
import com.example.demo.service.OrderQueryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final CTSanPhamRepository ctSanPhamRepository;
    private final DonHangRepository donHangRepository;
    private final LoaiRepository loaiRepository;
    private final RoleRepository roleRepository;
    private final OrderQueryService orderQueryService;
    private final PasswordEncoder passwordEncoder;

    private boolean isAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equalsIgnoreCase(role.toString());
    }

    private String requireAdmin(HttpSession session) {
        return isAdmin(session) ? null : "redirect:/login";
    }

    private Map<Integer, Integer> buildStockMap() {
        Map<Integer, Integer> stockMap = new HashMap<>();
        for (Object[] row : ctSanPhamRepository.sumStockByProduct()) {
            stockMap.put((Integer) row[0], ((Number) row[1]).intValue());
        }
        return stockMap;
    }

    private Map<Integer, String> orderStatusMap() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "Chờ xác nhận");
        map.put(2, "Đã xác nhận");
        map.put(3, "Đang giao");
        map.put(4, "Hoàn thành");
        map.put(5, "Đã hủy");
        return map;
    }

    @GetMapping("/admin")
    public String dashboard(Model model, HttpSession session) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        List<KhachHang> accounts = khachHangRepository.findAllByOrderByMakhDesc();
        List<SanPham> products = sanPhamRepository.findAllByOrderByNgaylapDesc();
        List<MyOrderView> orders = orderQueryService.getAllOrders();


        Map<Integer, Integer> stockMap = buildStockMap();

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

    @GetMapping("/admin/customers")
    public String customers(Model model, HttpSession session) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        model.addAttribute("customers", khachHangRepository.findAllByOrderByMakhDesc());
        model.addAttribute("roles", roleRepository.findAll());
        return "Admin/customers";
    }

    @PostMapping("/admin/customers/create")
    public String createCustomer(@RequestParam String tenkh,
                                 @RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam(required = false) String sodienthoai,
                                 @RequestParam(required = false) String diachi,
                                 @RequestParam String matkhau,
                                 @RequestParam Integer roleId,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        if (khachHangRepository.existsByUsername(username) || khachHangRepository.existsByEmail(email)) {
            ra.addFlashAttribute("error", "Username hoặc email đã tồn tại.");
            return "redirect:/admin/customers";
        }

        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            ra.addFlashAttribute("error", "Role không hợp lệ.");
            return "redirect:/admin/customers";
        }

        KhachHang customer = new KhachHang();
        customer.setTenkh(tenkh);
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setSodienthoai(sodienthoai);
        customer.setDiachi(diachi);
        customer.setMatkhau(passwordEncoder.encode(matkhau));
        customer.setRole(role);
        khachHangRepository.save(customer);

        ra.addFlashAttribute("success", "Đã thêm khách hàng mới.");
        return "redirect:/admin/customers";
    }

    @PostMapping("/admin/customers/{id}/update")
    public String updateCustomer(@PathVariable Integer id,
                                 @RequestParam String tenkh,
                                 @RequestParam String email,
                                 @RequestParam(required = false) String sodienthoai,
                                 @RequestParam(required = false) String diachi,
                                 @RequestParam(required = false) String matkhau,
                                 @RequestParam Integer roleId,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        KhachHang customer = khachHangRepository.findById(id).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        if (customer == null || role == null) {
            ra.addFlashAttribute("error", "Không tìm thấy khách hàng hoặc role.");
            return "redirect:/admin/customers";
        }

        customer.setTenkh(tenkh);
        customer.setEmail(email);
        customer.setSodienthoai(sodienthoai);
        customer.setDiachi(diachi);
        customer.setRole(role);
        if (matkhau != null && !matkhau.isBlank()) {
            customer.setMatkhau(passwordEncoder.encode(matkhau));
        }


        khachHangRepository.save(customer);
        ra.addFlashAttribute("success", "Đã cập nhật khách hàng #" + id + ".");
        return "redirect:/admin/customers";
    }

    @PostMapping("/admin/customers/{id}/delete")
    public String deleteCustomer(@PathVariable Integer id,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        if (!khachHangRepository.existsById(id)) {
            ra.addFlashAttribute("error", "Khách hàng không tồn tại.");
            return "redirect:/admin/customers";
        }
        khachHangRepository.deleteById(id);
        ra.addFlashAttribute("success", "Đã xoá khách hàng #" + id + ".");
        return "redirect:/admin/customers";
    }

    @GetMapping("/admin/products")
    public String products(Model model, HttpSession session) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        Map<Integer, Integer> stockMap = buildStockMap();

        model.addAttribute("products", sanPhamRepository.findAllByOrderByNgaylapDesc());
        model.addAttribute("categories", loaiRepository.findAll());
        model.addAttribute("stockMap", stockMap);
        return "Admin/products";
    }

    @PostMapping("/admin/products/create")
    public String createProduct(@RequestParam String tensp,
                                @RequestParam(defaultValue = "0") Float giamgia,
                                @RequestParam String hinh,
                                @RequestParam Integer maloai,
                                @RequestParam(required = false) String thuonghieu,
                                @RequestParam(required = false) String mota,
                                @RequestParam(defaultValue = "0") Integer dacbiet,
                                @RequestParam(defaultValue = "1") Integer tinhtrang,
                                HttpSession session,
                                RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        SanPham p = new SanPham();
        p.setTensp(tensp);
        p.setGiamgia(giamgia == null ? 0f : giamgia);
        p.setHinh(hinh);
        p.setMaloai(maloai);
        p.setThuonghieu(thuonghieu);
        p.setMota(mota);
        p.setDacbiet(dacbiet != null && dacbiet == 1);
        p.setTinhtrang(tinhtrang);
        p.setSoluotxem(0);
        p.setNgaylap(LocalDate.now());
        sanPhamRepository.save(p);

        ra.addFlashAttribute("success", "Đã thêm sản phẩm mới.");
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/{id}/update")
    public String updateProduct(@PathVariable Integer id,
                                @RequestParam String tensp,
                                @RequestParam(defaultValue = "0") Float giamgia,
                                @RequestParam String hinh,
                                @RequestParam Integer maloai,
                                @RequestParam(required = false) String thuonghieu,
                                @RequestParam(required = false) String mota,
                                @RequestParam(defaultValue = "0") Integer dacbiet,
                                @RequestParam(defaultValue = "1") Integer tinhtrang,
                                HttpSession session,
                                RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        SanPham p = sanPhamRepository.findById(id).orElse(null);
        if (p == null) {
            ra.addFlashAttribute("error", "Không tìm thấy sản phẩm.");
            return "redirect:/admin/products";
        }

        p.setTensp(tensp);
        p.setGiamgia(giamgia == null ? 0f : giamgia);
        p.setHinh(hinh);
        p.setMaloai(maloai);
        p.setThuonghieu(thuonghieu);
        p.setMota(mota);
        p.setDacbiet(dacbiet != null && dacbiet == 1);
        p.setTinhtrang(tinhtrang);
        sanPhamRepository.save(p);

        ra.addFlashAttribute("success", "Đã cập nhật sản phẩm #" + id + ".");
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products/{id}/delete")
    public String deleteProduct(@PathVariable Integer id,
                                HttpSession session,
                                RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        if (!sanPhamRepository.existsById(id)) {
            ra.addFlashAttribute("error", "Sản phẩm không tồn tại.");
            return "redirect:/admin/products";
        }

        sanPhamRepository.deleteById(id);
        ra.addFlashAttribute("success", "Đã xoá sản phẩm #" + id + ".");
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/orders")
    public String orders(Model model, HttpSession session) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        model.addAttribute("orders", orderQueryService.getAllOrders());
        model.addAttribute("orderStatusMap", orderStatusMap());
        return "Admin/orders";
    }

    @PostMapping("/admin/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Integer id,
                                    @RequestParam Integer trangthai,
                                    HttpSession session,
                                    RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        DonHang order = donHangRepository.findById(id).orElse(null);
        if (order == null) {
            ra.addFlashAttribute("error", "Đơn hàng không tồn tại.");
            return "redirect:/admin/orders";
        }
        order.setTrangthai(trangthai);
        donHangRepository.save(order);
        ra.addFlashAttribute("success", "Đã cập nhật trạng thái đơn #" + id + ".");
        return "redirect:/admin/orders";
    }

    @PostMapping("/admin/orders/{id}/delete")
    public String deleteOrder(@PathVariable Integer id,
                              HttpSession session,
                              RedirectAttributes ra) {
        String redirect = requireAdmin(session);
        if (redirect != null) return redirect;

        if (!donHangRepository.existsById(id)) {
            ra.addFlashAttribute("error", "Đơn hàng không tồn tại.");
            return "redirect:/admin/orders";
        }
        donHangRepository.deleteById(id);
        ra.addFlashAttribute("success", "Đã xoá đơn #" + id + ".");
        return "redirect:/admin/orders";
    }
}