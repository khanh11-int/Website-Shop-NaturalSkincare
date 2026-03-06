package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.CTDonHangRepository;
import com.example.demo.repository.DonHangRepository;
import com.example.demo.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final DonHangRepository donHangRepository;
    private final CTDonHangRepository ctDonHangRepository;
    private final SanPhamRepository sanPhamRepository;

    public List<MyOrderView> getOrdersByCustomer(Integer makh) {
        List<DonHang> orders = donHangRepository.findByMakhOrderByNgaydatDescMadonhangDesc(makh);
        return buildOrderViews(orders);
    }

    public List<MyOrderView> getAllOrders() {
        List<DonHang> orders = donHangRepository.findAllByOrderByNgaydatDescMadonhangDesc();
        return buildOrderViews(orders);
    }

    private List<MyOrderView> buildOrderViews(List<DonHang> orders) {
        Map<Integer, SanPham> products = new HashMap<>();
        for (SanPham sp : sanPhamRepository.findAll()) {
            products.put(sp.getMasp(), sp);
        }

        List<MyOrderView> result = new ArrayList<>();
        for (DonHang order : orders) {
            List<CTDonHang> details = ctDonHangRepository.findByMadonhangOrderByMaspAsc(order.getMadonhang());
            List<OrderItemView> items = new ArrayList<>();

            for (CTDonHang detail : details) {
                SanPham sp = products.get(detail.getMasp());
                items.add(new OrderItemView(
                        detail.getMasp(),
                        sp != null ? sp.getTensp() : "Sản phẩm #" + detail.getMasp(),
                        sp != null ? sp.getHinh() : null,
                        detail.getSoluongmua(),
                        detail.getMuihuong(),
                        detail.getDungtich(),
                        detail.getThanhtien()
                ));
            }

            result.add(new MyOrderView(order.getMadonhang(), order.getNgaydat(), order.getTongtien(), items));
        }
        return result;
    }
}
