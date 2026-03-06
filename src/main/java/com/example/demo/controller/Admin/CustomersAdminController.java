package com.example.demo.controller.Admin;

import com.example.demo.model.KhachHang;
import com.example.demo.model.Role;
import com.example.demo.repository.KhachHangRepository;
import com.example.demo.repository.RoleRepository;
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

@Controller
@RequiredArgsConstructor
public class CustomersAdminController {

    private final KhachHangRepository khachHangRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminControllerSupport adminControllerSupport;

    @GetMapping("/admin/customers")
    public String customers(Model model, HttpSession session) {
        String redirect = adminControllerSupport.requireAdmin(session);
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
        String redirect = adminControllerSupport.requireAdmin(session);
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
        String redirect = adminControllerSupport.requireAdmin(session);
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
        String redirect = adminControllerSupport.requireAdmin(session);
        if (redirect != null) return redirect;

        if (!khachHangRepository.existsById(id)) {
            ra.addFlashAttribute("error", "Khách hàng không tồn tại.");
            return "redirect:/admin/customers";
        }
        khachHangRepository.deleteById(id);
        ra.addFlashAttribute("success", "Đã xoá khách hàng #" + id + ".");
        return "redirect:/admin/customers";
    }
}
