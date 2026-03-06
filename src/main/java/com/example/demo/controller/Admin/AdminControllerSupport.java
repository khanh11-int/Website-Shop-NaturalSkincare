package com.example.demo.controller.Admin;

import com.example.demo.repository.CTSanPhamRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AdminControllerSupport {

    private final CTSanPhamRepository ctSanPhamRepository;

    public String requireAdmin(HttpSession session) {
        Object role = session.getAttribute("role");
        return role != null && "ADMIN".equalsIgnoreCase(role.toString()) ? null : "redirect:/login";
    }

    public Map<Integer, Integer> buildStockMap() {
        Map<Integer, Integer> stockMap = new HashMap<>();
        for (Object[] row : ctSanPhamRepository.sumStockByProduct()) {
            stockMap.put((Integer) row[0], ((Number) row[1]).intValue());
        }
        return stockMap;
    }

    public Map<Integer, String> orderStatusMap() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "Chờ xác nhận");
        map.put(2, "Đã xác nhận");
        map.put(3, "Đang giao");
        map.put(4, "Hoàn thành");
        map.put(5, "Đã hủy");
        return map;
    }
}
