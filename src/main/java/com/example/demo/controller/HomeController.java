package com.example.demo.controller;

import com.example.demo.model.*;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import com.example.demo.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SanPhamService service;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("latest", service.latest(0, 12).getContent());
        model.addAttribute("sale", service.sale(0, 12).getContent());
        return "Home";
    }
//      TẤT CẢ SẢN PHÂM THEO CÁCH LÀM CỦA CÔ
//    @GetMapping("/products")
//    public String products(Model model) {
//        model.addAttribute("products", service.getAll());
//        return "products";
//    }

//      TẤT CẢ SẢN PHÂM CÓ GIÁ TIỀN VÀ GIẢM GIÁ
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", service.getAll(0, 40).getContent());
        return "products";
    }

//      TẤT CẢ SẢN PHÂM CÓ BỘ LỌC
//    @GetMapping("/products")
//    public String products(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "12") int size,
//            Model model) {
//
//        Page<SanPhamWithPrice> p = service.findAll(page, size);
//        model.addAttribute("products", p.getContent());
//        model.addAttribute("page", p);
//        return "products";
//    }


    @GetMapping("/product/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        SanPham sp = service.get(id).orElse(null);
        if (sp == null) return "redirect:/";

        List<CTSanPham> variants = service.variants(id);

        Float discount = sp.getGiamgia() == null ? 0f : sp.getGiamgia();       // giảm tiền
        Float minBase  = variants.stream()
                .map(CTSanPham::getDongia)
                .min(Float::compare).orElse(0f);                                // giá gốc min theo biến thể
        Float minAfter = Math.max(0f, minBase - discount);

        LinkedHashSet<MuiHuong> muis = variants.stream()
                .map(CTSanPham::getMui)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        LinkedHashSet<DungTich> volumes = variants.stream()
                .map(CTSanPham::getDungTich)
                .collect(Collectors.toCollection(LinkedHashSet::new));// giá sau giảm

        model.addAttribute("product", sp);
        model.addAttribute("variants", variants);
        model.addAttribute("muis", muis);
        model.addAttribute("volumes", volumes);
        model.addAttribute("baseMinPrice", minBase);     // giá gốc min
        model.addAttribute("mainPrice",    minAfter);    // giá min sau giảm
        model.addAttribute("discount",     discount);    // số tiền giảm

        return "chitietsanpham";
    }

    @GetMapping("/sale")
    public String sale(Model model) {
        model.addAttribute("products", service.sale(0, 12).getContent()); // Page -> list
        return "products";
    }

    // com.example.demo.controller.HomeController
    @GetMapping("/loai/{id}")
    public String byCategory(@PathVariable Integer id, Model model) {
        model.addAttribute("products", service.byCategoryTop(id, 40));
        return "products";
    }
    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", service.getAllLoai());
        return "categories"; // templates/categories.html
    }

}
