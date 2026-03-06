package com.example.demo.controller.Admin;

import com.example.demo.model.SanPham;
import com.example.demo.repository.LoaiRepository;
import com.example.demo.repository.SanPhamRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ProductsAdminController {

    private final SanPhamRepository sanPhamRepository;
    private final LoaiRepository loaiRepository;
    private final AdminControllerSupport adminControllerSupport;

    @GetMapping("/admin/products")
    public String products(Model model, HttpSession session) {
        String redirect = adminControllerSupport.requireAdmin(session);
        if (redirect != null) return redirect;

        Map<Integer, Integer> stockMap = adminControllerSupport.buildStockMap();
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
        String redirect = adminControllerSupport.requireAdmin(session);
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
        String redirect = adminControllerSupport.requireAdmin(session);
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
        String redirect = adminControllerSupport.requireAdmin(session);
        if (redirect != null) return redirect;

        if (!sanPhamRepository.existsById(id)) {
            ra.addFlashAttribute("error", "Sản phẩm không tồn tại.");
            return "redirect:/admin/products";
        }

        sanPhamRepository.deleteById(id);
        ra.addFlashAttribute("success", "Đã xoá sản phẩm #" + id + ".");
        return "redirect:/admin/products";
    }
}
