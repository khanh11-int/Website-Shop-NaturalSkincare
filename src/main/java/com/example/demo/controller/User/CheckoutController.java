package com.example.demo.controller.User;

import com.example.demo.model.CartItem;
import com.example.demo.model.KhachHang;
import com.example.demo.service.CartService;
import com.example.demo.service.CheckoutService;
import com.example.demo.service.KhachHangService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class CheckoutController {
    private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private CartService cartService;
    @Autowired
    private KhachHangService khService;
    @Autowired
    private CheckoutService checkoutService;

    // bank config injected from application.properties
    @Value("để số tài khoản ngân hàng ở đây")
    private String bankAccountNumber;
    @Value("${bank.account.name:}")
    private String bankAccountName;
    @Value("${bank.name:}")
    private String bankName;

    @GetMapping("/checkout")
    public String checkoutForm(HttpSession session, Model model) {
        List<CartItem> items = cartService.getItems(session);
        model.addAttribute("items", items);
        model.addAttribute("total", cartService.getTotal(session));

        Integer userid = (Integer) session.getAttribute("userid");
        if (userid != null) {
            Optional<KhachHang> opt = khService.findById(userid);
            if (opt.isPresent()) model.addAttribute("khach", opt.get());
        }

        return "checkout"; // render checkout.html
    }

    @PostMapping("/checkout/pay")
    public String doPay(HttpSession session,
                        @RequestParam String tenkh,
                        @RequestParam String email,
                        @RequestParam String sodienthoai,
                        @RequestParam String diachi,
                        @RequestParam String paymentType,
                        Model model) {
        List<CartItem> items = cartService.getItems(session);
        long total = cartService.getTotal(session);
        Integer userid = (Integer) session.getAttribute("userid");

        // Ensure items exist
        if (items == null || items.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng rỗng");
            return "checkout";
        }

        // If user is not logged in, require guest info
        if (userid == null) {
            boolean missing = false;
            StringBuilder sb = new StringBuilder();
            if (tenkh == null || tenkh.trim().isEmpty()) { missing = true; sb.append("Họ tên không được để trống. "); }
            if (email == null || email.trim().isEmpty()) { missing = true; sb.append("Email không được để trống. "); }
            if (sodienthoai == null || sodienthoai.trim().isEmpty()) { missing = true; sb.append("Số điện thoại không được để trống. "); }
            if (diachi == null || diachi.trim().isEmpty()) { missing = true; sb.append("Địa chỉ không được để trống. "); }
            if (missing) {
                model.addAttribute("error", sb.toString());
                model.addAttribute("items", items);
                model.addAttribute("total", total);
                return "checkout";
            }
        }

        // process order (makh if logged in)
        Integer makh = userid;
        Integer madonhang;
        try {
            madonhang = checkoutService.processOrder(session, makh, items, total,
                    tenkh, email, sodienthoai, diachi);
        } catch (Exception ex) {
            // Friendly error message when the DB rejects the INSERT due to makh being NOT NULL
            String msg = "Không thể lưu hoá đơn: ";
            String lower = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
            if (lower.contains("column 'makh' cannot be null") || lower.contains("makh")) {
                msg += "Cơ sở dữ liệu yêu cầu trường makh (customer id) không được null. \n" +
                        "Nếu bạn muốn cho phép khách (guest) thanh toán mà không đăng nhập, hãy chạy migration để cho phép makh NULL và thêm các trường guest. \n" +
                        "(SQL suggestion: ALTER TABLE checkout MODIFY COLUMN makh INT NULL; ALTER TABLE checkout ADD COLUMN tenkh VARCHAR(255) NULL, ADD COLUMN email VARCHAR(255) NULL, ADD COLUMN sodienthoai VARCHAR(50) NULL, ADD COLUMN diachi VARCHAR(500) NULL;)";
            } else {
                msg += "Lỗi nội bộ: " + ex.getMessage();
            }
            model.addAttribute("error", msg);
            model.addAttribute("items", items);
            model.addAttribute("total", total);
            // If user is logged in, add khach to prefill the form
            Integer userid2 = (Integer) session.getAttribute("userid");
            if (userid2 != null) {
                khService.findById(userid2).ifPresent(kh -> model.addAttribute("khach", kh));
            }
            return "checkout";
        }

        model.addAttribute("orderId", madonhang);
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("paymentType", paymentType);

        if ("bank".equals(paymentType)) {
            // Build a concise bank QR payload including account and amount so scanners can pick up the amount
            // Format: BANK|ACC:<acc>|NAME:<name>|AMOUNT:<amount>
            String payload = String.format("BANK|ACC:%s|NAME:%s|AMOUNT:%d",
                    bankAccountNumber == null ? "" : bankAccountNumber,
                    bankAccountName == null ? "" : bankAccountName,
                    total);

            try {
                QRCodeWriter qrWriter = new QRCodeWriter();
                BitMatrix matrix = qrWriter.encode(payload, BarcodeFormat.QR_CODE, 350, 350);
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
                    String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                    String dataUrl = "data:image/png;base64," + base64;
                    model.addAttribute("qrDataUrl", dataUrl);
                    log.info("Generated ZXing QR payload='{}' ({} bytes)", payload, base64.length());
                }
            } catch (WriterException we) {
                log.error("Failed to generate QR image: {}", we.getMessage());
            } catch (Exception e) {
                log.error("Unexpected error generating QR image: {}", e.getMessage());
            }

            // also expose the bank fields for the view to display plainly
            model.addAttribute("bankAccountNumber", bankAccountNumber);
            model.addAttribute("bankAccountName", bankAccountName);
            model.addAttribute("bankName", bankName);
        }

        return "checkout_success";
    }
}
