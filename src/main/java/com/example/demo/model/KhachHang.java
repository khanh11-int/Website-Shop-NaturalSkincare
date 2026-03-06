package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "khachhang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer makh;

    @NotBlank(message = "Tên không được để trống")
    private String tenkh;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 4, max = 50, message = "Tên đăng nhập phải từ 4 đến 50 ký tự")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String matkhau;

    @Transient
    private String confirmPassword;

    @Email(message = "Địa chỉ email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Column(unique = true)
    private String email;

    private String diachi;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?0?\\d{9,12}$", message = "Số điện thoại không hợp lệ")
    private String sodienthoai;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    // Getters & Setters
    public Integer getMakh() { return makh; }
    public void setMakh(Integer makh) { this.makh = makh; }
    public String getTenkh() { return tenkh; }
    public void setTenkh(String tenkh) { this.tenkh = tenkh; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getMatkhau() { return matkhau; }
    public void setMatkhau(String matkhau) { this.matkhau = matkhau; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDiachi() { return diachi; }
    public void setDiachi(String diachi) { this.diachi = diachi; }
    public String getSodienthoai() { return sodienthoai; }
    public void setSodienthoai(String sodienthoai) { this.sodienthoai = sodienthoai; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
