package com.example.demo.controller.Admin;

import com.example.demo.model.DonHang;
import com.example.demo.repository.DonHangRepository;
import com.example.demo.service.OrderQueryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class OrdersAdminController {

    private final DonHangRepository donHangRepository;
    private final OrderQueryService orderQueryService;
    private final AdminControllerSupport adminControllerSupport;

    @GetMapping("/admin/orders")
    public String orders(Model model, HttpSession session) {
        String redirect = adminControllerSupport.requireAdmin(session);
        if (redirect != null) return redirect;

        model.addAttribute("orders", orderQueryService.getAllOrders());
        model.addAttribute("orderStatusMap", adminControllerSupport.orderStatusMap());
        return "Admin/orders";
    }

    @PostMapping("/admin/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Integer id,
                                    @RequestParam Integer trangthai,
                                    HttpSession session,
                                    RedirectAttributes ra) {
        String redirect = adminControllerSupport.requireAdmin(session);
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
        String redirect = adminControllerSupport.requireAdmin(session);
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
