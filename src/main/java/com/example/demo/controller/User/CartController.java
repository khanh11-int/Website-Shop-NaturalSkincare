package com.example.demo.controller.User;

import com.example.demo.model.CartItem;
import com.example.demo.model.CTSanPham;
import com.example.demo.service.CartService;
import com.example.demo.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    private final CartService cartService;
    private final SanPhamService sanPhamService;

    public CartController(CartService cartService, SanPhamService sanPhamService) {
        this.cartService = cartService;
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("items", cartService.getItems(session));
        model.addAttribute("total", cartService.getTotal(session));
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(HttpSession session,
                            @RequestParam Integer productId,
                            @RequestParam String name,
                            @RequestParam String image,
                            @RequestParam(required = false) Integer dungtichId,
                            @RequestParam(required = false) Integer muiId,
                            @RequestParam(required = false) Integer quantity,
                            RedirectAttributes redirect) {
        // Basic validation: dungtich and mui must be selected
        if (dungtichId == null || muiId == null) {
            redirect.addFlashAttribute("error", "Vui lòng chọn mui và dungtich");
            return "redirect:/product/" + productId;
        }

        int qty = (quantity == null || quantity <= 0) ? 1 : quantity;

        // lookup variant server-side (price and stock authoritative)
        CTSanPham variant = sanPhamService.getVariant(productId, dungtichId, muiId);
        if (variant == null) {
            redirect.addFlashAttribute("error", "Biến thể không tồn tại");
            return "redirect:/product/" + productId;
        }

        Integer stock = variant.getSoluongton();
        if (stock == null) stock = 0;
        if (stock < qty) {
            redirect.addFlashAttribute("error", "Số lượng yêu cầu vượt quá tồn kho");
            return "redirect:/product/" + productId;
        }

        long unitPriceLong = 0L;
        Float p = variant.getDongia();
        if (p != null) unitPriceLong = Math.round(p);

        String dungtichLabel = variant.getDungTich() != null ? variant.getDungTich().getDungtich(): "";
        String colorLabel = variant.getMui() != null ? variant.getMui().getMuihuong() : "";

        String key = productId + "_" + dungtichId + "_" + muiId;
        CartItem item = new CartItem(key, productId, name, image, dungtichId, dungtichLabel, muiId, colorLabel, unitPriceLong, qty);
        cartService.addItem(session, item);
        redirect.addFlashAttribute("msg", "Đã thêm vào giỏ");
        return "redirect:/cart";
    }

    @PostMapping("/cart/increment")
    public String increment(HttpSession session, @RequestParam String key) {
        cartService.increment(session, key);
        return "redirect:/cart";
    }

    @PostMapping("/cart/decrement")
    public String decrement(HttpSession session, @RequestParam String key) {
        cartService.decrement(session, key);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String remove(HttpSession session, @RequestParam String key) {
        cartService.remove(session, key);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clear(HttpSession session) {
        cartService.clear(session);
        return "redirect:/cart";
    }
}
