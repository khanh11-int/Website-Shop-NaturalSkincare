package com.example.demo.service;

import com.example.demo.model.CTSanPham;
import com.example.demo.model.Loai;
import com.example.demo.model.SanPham;
import com.example.demo.model.SanPhamWithPrice;
import com.example.demo.repository.CTSanPhamRepository;
import com.example.demo.repository.LoaiRepository;
import com.example.demo.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamService {
    private final SanPhamRepository spRepo;
    private final CTSanPhamRepository ctRepo;
    private final LoaiRepository loaiRepo;

    public Page<SanPhamWithPrice> latest(int page, int size) {
        return spRepo.findLatestWithPrice( PageRequest.of(page, size));
    }
    public Page<SanPhamWithPrice> sale(int page, int size) {
        return spRepo.findSaleWithPrice( PageRequest.of(page, size));
    }
    public Optional<SanPham> get(Integer id) {
        return spRepo.findById(id);
    }
//  public List<SanPham> getAll() {return spRepo.findAll();}
    public Page<SanPhamWithPrice> getAll(int page, int size) {
        return spRepo.findAllWithPrice(PageRequest.of(page, size));
    }
    public Page<SanPhamWithPrice> findAll(int page, int size) {
        return spRepo.findLatestWithPrice(PageRequest.of(page, size));
    }
    public Page<SanPhamWithPrice> byCategoryTop(Integer maloai, int limit) {
        return loaiRepo.findByLoaiWithPrice(maloai, PageRequest.of(0, limit));
    }
    public List<Loai> getAllLoai() {
        return loaiRepo.findAll();
    }
    public List<CTSanPham> variants(Integer masp) {
        return ctRepo.findByProductIdWithDetail(masp);
    }
    // find a variant by product, dungtich and muihuong (returns null if not found)
    public CTSanPham getVariant(int masp, int dungtichId, int muiId) {
        List<CTSanPham> list = ctRepo.findByProductIdWithDetail(masp);
        if (list == null) return null;

        for (CTSanPham v : list) {
            if (v.getDungTich().getIddungtich() == dungtichId && v.getMui().getIdmui() == muiId) {
                return v;
            }
        }
        return null;
    }
}

