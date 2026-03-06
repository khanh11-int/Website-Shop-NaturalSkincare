package com.example.demo.controller.Auth;

import com.example.demo.service.DangkyService;
import com.example.demo.model.KhachHang;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DangKyController {
    @Autowired
    private DangkyService khService;
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("khachhang")) {
            model.addAttribute("khachhang", new KhachHang());
        }
        return "register";
    }

    // khi người dùng nhấn nút đăng ký thì controller nhận yêu cầu POST tới /register
    @PostMapping({"/dangky", "/register"})
    public String processRegistration(@Valid @ModelAttribute("khachhang") KhachHang kh,
                                      BindingResult bindingResult,
                                      Model model) {
        if (!bindingResult.hasErrors()) {
            if (kh.getConfirmPassword() == null || kh.getConfirmPassword().isBlank()) {
                bindingResult.rejectValue("confirmPassword", "confirm.empty", "Vui lòng nhập xác nhận mật khẩu");
            } else if (kh.getMatkhau() == null || !kh.getMatkhau().equals(kh.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "password.mismatch", "Mật khẩu xác nhận không khớp");
            }
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            khService.register(kh);
            //khi lưu có trường hợp username, email bị trùng, service sẽ ném IllegalArgumentException
        } catch (IllegalArgumentException ex) {
            String msg=ex.getMessage();
            if("username_exists".equals(msg)) {
                bindingResult.rejectValue("username","username.exists", "Tên đăng nhập đã tồn tại");
                return "register";
            }
            else if("email_exists".equals(msg)) {
                bindingResult.rejectValue("email","email.exists", "Email đã tồn tại");
                return "register";
            }
            else {
                // other errors
                model.addAttribute("error", "Đăng ký thất bại do lỗi hệ thống. Vui lòng thử lại sau.");
                return "register";
            }

        }

        // success - redirect to home
        return "redirect:/";
    }

}
