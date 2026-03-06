-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 06, 2026 lúc 05:49 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `shopskincare`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `binhluan1`
--

CREATE TABLE `binhluan1` (
                             `mabl` int(11) NOT NULL,
                             `masp` int(11) NOT NULL,
                             `makh` int(11) NOT NULL,
                             `ngaybl` date NOT NULL,
                             `noidung` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `comment`
--

CREATE TABLE `comment` (
                           `idcomment` int(11) NOT NULL,
                           `idkh` int(11) NOT NULL,
                           `idsanpham` int(11) NOT NULL,
                           `content` text NOT NULL,
                           `luotthich` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `comment`
--

INSERT INTO `comment` (`idcomment`, `idkh`, `idsanpham`, `content`, `luotthich`) VALUES
                                                                                     (1, 3, 2, 'Làm sạch dịu nhẹ, không khô căng', 2),
                                                                                     (2, 3, 4, 'Thấm nhanh, không bết', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctdonhang`
--

CREATE TABLE `ctdonhang` (
                             `madonhang` int(11) NOT NULL,
                             `masp` int(11) NOT NULL,
                             `soluongmua` int(11) NOT NULL,
                             `muihuong` varchar(100) NOT NULL,
                             `dungtich` int(11) NOT NULL,
                             `thanhtien` int(11) NOT NULL,
                             `tinhtrang` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctdonhang`
--

INSERT INTO `ctdonhang` (`madonhang`, `masp`, `soluongmua`, `muihuong`, `dungtich`, `thanhtien`, `tinhtrang`) VALUES
                                                                                                                  (1, 3, 1, 'Hoa hồng', 50, 159000, 0),
                                                                                                                  (1, 4, 1, 'Không mùi', 30, 219000, 0),
                                                                                                                  (2, 1, 1, 'Trà xanh', 100, 139000, 0),
                                                                                                                  (2, 7, 2, 'Không mùi', 50, 598000, 0),
                                                                                                                  (4, 7, 1, 'Không mùi', 2, 299000, 1),
                                                                                                                  (5, 7, 1, 'Không mùi', 2, 299000, 1),
                                                                                                                  (6, 8, 1, 'Không mùi', 3, 169000, 1),
                                                                                                                  (7, 7, 1, 'Không mùi', 2, 299000, 1),
                                                                                                                  (8, 7, 1, 'Không mùi', 2, 299000, 1),
                                                                                                                  (8, 8, 1, 'Không mùi', 3, 169000, 1),
                                                                                                                  (9, 8, 1, 'Cam chanh', 3, 179000, 1),
                                                                                                                  (10, 6, 1, 'Không mùi', 2, 269000, 1),
                                                                                                                  (11, 8, 1, 'Cam chanh', 3, 179000, 1),
                                                                                                                  (12, 9, 1, 'Lavender', 2, 149000, 1),
                                                                                                                  (13, 7, 1, 'Không mùi', 2, 299000, 1),
                                                                                                                  (14, 7, 1, 'Không mùi', 2, 299000, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `ctsanpham`
--

CREATE TABLE `ctsanpham` (
                             `idsanpham` int(11) NOT NULL,
                             `idmui` int(11) NOT NULL,
                             `iddungtich` int(11) NOT NULL,
                             `dongia` float NOT NULL,
                             `soluongton` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `ctsanpham`
--

INSERT INTO `ctsanpham` (`idsanpham`, `idmui`, `iddungtich`, `dongia`, `soluongton`) VALUES
                                                                                         (1, 1, 3, 129000, 30),
                                                                                         (1, 2, 3, 139000, 20),
                                                                                         (2, 2, 3, 149000, 25),
                                                                                         (3, 3, 2, 159000, 18),
                                                                                         (3, 3, 3, 189000, 12),
                                                                                         (4, 1, 1, 219000, 40),
                                                                                         (4, 1, 2, 359000, 15),
                                                                                         (5, 1, 1, 239000, 35),
                                                                                         (6, 1, 2, 269000, 21),
                                                                                         (6, 1, 3, 339000, 10),
                                                                                         (7, 1, 2, 299000, 22),
                                                                                         (8, 1, 3, 169000, 24),
                                                                                         (8, 4, 3, 179000, 12),
                                                                                         (9, 5, 2, 149000, 18);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
                           `madonhang` int(11) NOT NULL,
                           `makh` int(11) NOT NULL,
                           `ngaydat` date NOT NULL,
                           `tongtien` int(11) NOT NULL,
                           `tenkh` varchar(255) DEFAULT NULL,
                           `email` varchar(255) DEFAULT NULL,
                           `sodienthoai` varchar(50) DEFAULT NULL,
                           `diachi` varchar(255) DEFAULT NULL,
                           `trangthai` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`madonhang`, `makh`, `ngaydat`, `tongtien`, `tenkh`, `email`, `sodienthoai`, `diachi`, `trangthai`) VALUES
                                                                                                                               (1, 3, '2025-10-05', 478000, NULL, NULL, NULL, NULL, 1),
                                                                                                                               (2, 6, '2025-10-06', 658000, NULL, NULL, NULL, NULL, 1),
                                                                                                                               (4, 16, '2025-12-04', 299000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (5, 16, '2025-12-04', 299000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (6, 16, '2025-12-04', 169000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (7, 16, '2025-12-04', 299000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (8, 16, '2025-12-04', 468000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (9, 16, '2025-12-04', 179000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (10, 16, '2025-12-04', 269000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (11, 16, '2025-12-04', 179000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (12, 16, '2025-12-04', 149000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (13, 16, '2025-12-04', 299000, 'Nguyễn Quốc Khánh', 'admin@gmail.com', '0123456789', 'gfh', 1),
                                                                                                                               (14, 17, '2026-03-06', 299000, 'Nguyễn Quốc Khánh', 'banhtrangnao@gmail.com', '0123456789', '123hcm', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dungtich`
--

CREATE TABLE `dungtich` (
                            `Iddungtich` int(11) NOT NULL,
                            `dungtich` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `dungtich`
--

INSERT INTO `dungtich` (`Iddungtich`, `dungtich`) VALUES
                                                      (1, 30),
                                                      (2, 50),
                                                      (3, 100),
                                                      (4, 150),
                                                      (5, 200);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
                             `makh` int(11) NOT NULL,
                             `tenkh` varchar(50) NOT NULL,
                             `username` varchar(25) NOT NULL,
                             `matkhau` varchar(100) NOT NULL,
                             `email` varchar(50) NOT NULL,
                             `diachi` text DEFAULT NULL,
                             `sodienthoai` varchar(12) DEFAULT NULL,
                             `role_id` int(11) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`makh`, `tenkh`, `username`, `matkhau`, `email`, `diachi`, `sodienthoai`, `role_id`) VALUES
                                                                                                                  (1, 'Tú Trần', 'tutran', '8f8e2909a8f683c31159ee51d593a642', 'tu@gmail.com', 'HCM', '09090789678', 2),
                                                                                                                  (2, 'Minh Minh', 'minhminh', '8f8e2909a8f683c31159ee51d593a642', 'minh@gmail.com', 'Bình Định', '090907896789', 2),
                                                                                                                  (3, 'Tèo', 'teoteo', '3ff19fad9f5844248f601ab23381cc88', 'teo123@gmail.com', 'HCM', '09090789698', 2),
                                                                                                                  (6, 'An An', 'anan', 'af57f975857768de31f3c083a1c163cc', 'an@gmail.com', 'HCM', '012345', 2),
                                                                                                                  (9, 'Nam Anh', 'namanh', '206a93184bcd24a5625312acf1a03922', 'namanh@gmail.com', 'HCM', '123456', 2),
                                                                                                                  (17, 'Nguyễn Quốc Khánh', 'admin', '$2a$10$xeSpLdcv95OSvcy2pSjYt.ZBz212Q7tHTmNVN1NDGq0j7/u.1H/h.', 'banhtrangnao@gmail.com', '123hcm', '0123456789', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loai`
--

CREATE TABLE `loai` (
                        `maloai` int(11) NOT NULL,
                        `tenloai` varchar(50) NOT NULL,
                        `idmenu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `loai`
--

INSERT INTO `loai` (`maloai`, `tenloai`, `idmenu`) VALUES
                                                       (1, 'Sữa rửa mặt', 3),
                                                       (2, 'Toner/Nước hoa hồng', 3),
                                                       (3, 'Serum/Tinh chất', 3),
                                                       (4, 'Kem dưỡng ẩm', 3),
                                                       (5, 'Chống nắng', 3),
                                                       (6, 'Tẩy trang', 3),
                                                       (7, 'Mặt nạ', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `muihuong`
--

CREATE TABLE `muihuong` (
                            `Idmui` int(11) NOT NULL,
                            `muihuong` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `muihuong`
--

INSERT INTO `muihuong` (`Idmui`, `muihuong`) VALUES
                                                 (1, 'Không mùi'),
                                                 (2, 'Trà xanh'),
                                                 (3, 'Hoa hồng'),
                                                 (4, 'Cam chanh'),
                                                 (5, 'Lavender');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
                            `idnv` int(11) NOT NULL,
                            `hoten` varchar(250) NOT NULL,
                            `dia` text NOT NULL,
                            `username` varchar(250) NOT NULL,
                            `matkhau` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`idnv`, `hoten`, `dia`, `username`, `matkhau`) VALUES
    (1, 'Nguyễn Hạo Vy', 'HCM', 'admin', '123456');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `role`
--

CREATE TABLE `role` (
                        `id` int(11) NOT NULL,
                        `name` varchar(50) NOT NULL,
                        `description` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `role`
--

INSERT INTO `role` (`id`, `name`, `description`) VALUES
                                                     (1, 'ADMIN', 'Quản trị viên hệ thống'),
                                                     (2, 'USER', 'Khách hàng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
                           `masp` int(11) NOT NULL,
                           `tensp` varchar(120) NOT NULL,
                           `giamgia` float NOT NULL,
                           `hinh` varchar(100) NOT NULL,
                           `maloai` int(11) NOT NULL,
                           `dacbiet` bit(1) NOT NULL,
                           `soluotxem` int(11) NOT NULL,
                           `ngaylap` date NOT NULL,
                           `mota` varchar(2000) NOT NULL,
                           `thuonghieu` varchar(80) DEFAULT NULL,
                           `tinhtrang` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`masp`, `tensp`, `giamgia`, `hinh`, `maloai`, `dacbiet`, `soluotxem`, `ngaylap`, `mota`, `thuonghieu`, `tinhtrang`) VALUES
                                                                                                                                               (1, 'Sữa rửa mặt dịu nhẹ Amino', 0, 'srmdn.jpg', 1, b'1', 12, '2024-08-08', 'Làm sạch dịu nhẹ, pH cân bằng 5.5', 'DermaCare', 1),
                                                                                                                                               (2, 'Sữa rửa mặt tạo bọt Trà Xanh', 20000, 'srm_tx.jpg', 1, b'0', 35, '2024-10-01', 'Giảm dầu thừa, giảm bóng', 'GreenLeaf', 1),
                                                                                                                                               (3, 'Toner Hoa Hồng cấp ẩm', 0, 'toner_hh.jpg', 2, b'0', 22, '2024-11-20', 'Cấp ẩm, làm dịu, se khít lỗ chân lông', 'RoseLab', 1),
                                                                                                                                               (4, 'Serum Niacinamide 10%', 30000, 'serum_nia.jpg', 3, b'1', 58, '2025-01-12', 'Giảm dầu, hỗ trợ sáng da', 'Glow+', 1),
                                                                                                                                               (5, 'Serum Hyaluronic Acid 2%', 0, 'serum_ha.jpg', 3, b'0', 44, '2025-02-05', 'Cấp nước multi-weight HA', 'HydraPro', 1),
                                                                                                                                               (6, 'Kem dưỡng ẩm Ceramide', 0, 'cream_cer.jpg', 4, b'0', 18, '2025-03-10', 'Phục hồi hàng rào da', 'SkinShield', 1),
                                                                                                                                               (7, 'Kem chống nắng SPF50 PA++++', 0, 'sunscreen50.jpg', 5, b'1', 76, '2025-04-01', 'Chống nắng quang phổ rộng, finish khô ráo', 'SunGuard', 1),
                                                                                                                                               (8, 'Nước tẩy trang Micellar', 0, 'micellar.jpg', 6, b'0', 16, '2025-05-20', 'Làm sạch lớp trang điểm, không cay mắt', 'PureDrop', 1),
                                                                                                                                               (9, 'Mặt nạ đất sét Bùn Khoáng', 0, 'mask_clay.jpg', 7, b'0', 21, '2025-06-02', 'Làm sạch sâu, hút dầu', 'MudPure', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `binhluan1`
--
ALTER TABLE `binhluan1`
    ADD PRIMARY KEY (`mabl`);

--
-- Chỉ mục cho bảng `comment`
--
ALTER TABLE `comment`
    ADD PRIMARY KEY (`idcomment`);

--
-- Chỉ mục cho bảng `ctdonhang`
--
ALTER TABLE `ctdonhang`
    ADD PRIMARY KEY (`madonhang`,`masp`);

--
-- Chỉ mục cho bảng `ctsanpham`
--
ALTER TABLE `ctsanpham`
    ADD PRIMARY KEY (`idsanpham`,`idmui`,`iddungtich`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
    ADD PRIMARY KEY (`madonhang`);

--
-- Chỉ mục cho bảng `dungtich`
--
ALTER TABLE `dungtich`
    ADD PRIMARY KEY (`Iddungtich`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
    ADD PRIMARY KEY (`makh`);

--
-- Chỉ mục cho bảng `loai`
--
ALTER TABLE `loai`
    ADD PRIMARY KEY (`maloai`);

--
-- Chỉ mục cho bảng `muihuong`
--
ALTER TABLE `muihuong`
    ADD PRIMARY KEY (`Idmui`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
    ADD PRIMARY KEY (`idnv`);

--
-- Chỉ mục cho bảng `role`
--
ALTER TABLE `role`
    ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_role_name` (`name`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
    ADD PRIMARY KEY (`masp`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `binhluan1`
--
ALTER TABLE `binhluan1`
    MODIFY `mabl` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `comment`
--
ALTER TABLE `comment`
    MODIFY `idcomment` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
    MODIFY `madonhang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `dungtich`
--
ALTER TABLE `dungtich`
    MODIFY `Iddungtich` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
    MODIFY `makh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT cho bảng `loai`
--
ALTER TABLE `loai`
    MODIFY `maloai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `muihuong`
--
ALTER TABLE `muihuong`
    MODIFY `Idmui` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
    MODIFY `idnv` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `role`
--
ALTER TABLE `role`
    MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
    MODIFY `masp` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
