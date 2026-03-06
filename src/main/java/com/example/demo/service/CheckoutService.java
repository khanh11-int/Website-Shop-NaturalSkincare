package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CheckoutService {

    @Autowired
    private DonHangRepository donHangRepo;

    @Autowired
    private CTDonHangRepository ctDonHangRepo;

    @Autowired
    private CTSanPhamRepository ctspRepo;

    @Autowired
    private SanPhamRepository spRepo;


    @Transactional
    public Integer processOrder(HttpSession session,
                                Integer makh,
                                List<CartItem> items,
                                Long total ,String tenkh, String email, String sodienthoai, String diachi) {

        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("Giỏ hàng rỗng");

        // Tạo đơn hàng (KHÔNG dùng tên, email, số ĐT nữa)
        DonHang dh = new DonHang();
        dh.setMakh(makh);
        dh.setNgaydat(LocalDate.now());
        dh.setTongtien(total);
        // set guest/customer info (can be null)
        dh.setTenkh(tenkh);
        dh.setEmail(email);
        dh.setSodienthoai(sodienthoai);
        dh.setDiachi(diachi);

        dh = donHangRepo.save(dh); // Lưu và lấy mã đơn

        List<CTDonHang> savedDetails = new ArrayList<>();

        for (CartItem it : items) {

            CTDonHang ct = new CTDonHang();
            ct.setMadonhang(dh.getMadonhang());
            ct.setMasp(it.getProductId());
            ct.setSoluongmua(it.getQuantity());
            ct.setMuihuong(it.getMuiLabel());   // map đúng với DB
            ct.setDungtich(it.getDungtichId());
            ct.setThanhtien(it.getSubtotal());
            ct.setTinhtrang(1);

            ctDonHangRepo.save(ct);
            savedDetails.add(ct);

            // Cập nhật kho
            CTSanPhamId key = new CTSanPhamId(
                    it.getProductId(),
                    it.getMuiId(),
                    it.getDungtichId()
            );

            Optional<CTSanPham> opt = ctspRepo.findById(key);

            if (opt.isPresent()) {
                CTSanPham spct = opt.get();
                int stock = spct.getSoluongton() == null ? 0 : spct.getSoluongton();
                int newStock = Math.max(0, stock - it.getQuantity());
                spct.setSoluongton(newStock);
                ctspRepo.save(spct);

                // Chỉ ẩn sản phẩm ở trang khách khi tổng tồn kho của toàn bộ biến thể = 0
                boolean hasStock = ctspRepo.existsByIdIdsanphamAndSoluongtonGreaterThan(it.getProductId(), 0);
                spRepo.findById(it.getProductId()).ifPresent(sp -> {
                    sp.setTinhtrang(hasStock ? 1 : 0);
                    spRepo.save(sp);
                });
            }
        }

        // Xóa giỏ hàng session
        session.removeAttribute("CART");

        return dh.getMadonhang(); // trả về mã đơn
    }
}
