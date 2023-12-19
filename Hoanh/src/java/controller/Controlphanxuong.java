package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Controlphanxuong {
    private Connection con;
    private Statement stmt;

    public Controlphanxuong() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlduocthuoc1?useUnicode=true&characterEncoding=UTF-8", "root", "");
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from phanxuong";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Phân Xưởng</th><th>Tên Phân Xưởng</th><th>Địa Chỉ</th><th>Điện Thoại</th><th>Quản Đốc</th></tr>");
            while (rs.next()) {
                int MaPhanXuong = rs.getInt("MaPhanXuong");
                String TenPhanXuong = rs.getString("TenPhanXuong");
                String DiaChi = rs.getString("DiaChi");
                String DienThoai = rs.getString("DienThoai");
                String QuanDoc = rs.getString("QuanDoc");

                out.println("<tr><td>" + MaPhanXuong + "</td><td>" + TenPhanXuong + "</td><td>" + DiaChi + "</td><td>" + DienThoai + "</td><td>" + QuanDoc + "</td><td>" +
                        "<td><a href='Xemphanxuong?MaPhanXuongXoa=" + MaPhanXuong + "'>Xóa</a></td>" +
                        "<td><form method='post'>" +
                        "<input type='hidden' name='MaPhanXuong' value='" + MaPhanXuong + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td>");
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
        }
    }

    public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String MaPhanXuongParam = request.getParameter("MaPhanXuong");
        String TenPhanXuong = request.getParameter("TenPhanXuong");
        String DiaChi = request.getParameter("DiaChi");
        String DienThoai = request.getParameter("DienThoai");
        String QuanDoc = request.getParameter("QuanDoc");
        int MaPhanXuong = Integer.parseInt(MaPhanXuongParam);

        try {
            openConnection();
            String sql = "INSERT INTO phieunhaphang (MaPhanXuong, tenphanxuong, diachi, dienthoai, quandoc) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, MaPhanXuong);
                statement.setString(2, TenPhanXuong);
                statement.setString(3, DiaChi);
                statement.setString(4, DienThoai);
                statement.setString(5, QuanDoc);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
                    out.println("Dữ liệu đã được chèn thành công!");
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("Không thể chèn dữ liệu!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xoa(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            openConnection();
            String MaPhanXuongXoa = request.getParameter("MaPhanXuongXoa");

            if (MaPhanXuongXoa != null && !MaPhanXuongXoa.isEmpty()) {
                String sql = "DELETE FROM phanxuong WHERE MaPhanXuong = ?";

                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, MaPhanXuongXoa);

                    int rowsDeleted = statement.executeUpdate();

                    if (rowsDeleted > 0) {
                        out.println("Xóa Phiếu thành công!");
                    } else {
                        out.println("Không tìm thấy Phiếu có mã " + MaPhanXuongXoa + " để xóa.");
                    }
                }
            } else {
                // Xử lý trường hợp maPhanXuongXoa không hợp lệ
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi: " + e.getMessage());
        }
    }

    public void TK(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        String searchMaPhanXuong = req.getParameter("searchMaPhanXuong");
        try {
            openConnection();
            String sql = "SELECT * FROM phanxuong WHERE MaPhanXuong=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaPhanXuong);

                // Xử lý kết quả truy vấn và in ra bảng
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void sua(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        String MaPhanXuongParam = req.getParameter("MaPhanXuong");
        String TenPhanXuong = req.getParameter("TenPhanXuong");
        String DiaChi = req.getParameter("DiaChi");
        String DienThoai = req.getParameter("DienThoai");
        String QuanDoc = req.getParameter("QuanDoc");
        int MaPhanXuong = Integer.parseInt(MaPhanXuongParam);
        try {
            openConnection();

            String sql = "UPDATE phanxuong SET TenPhanXuong=?, DiaChi=?, Dienthoai=?, Quandoc=? WHERE MaPhanXuong=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, TenPhanXuong);
                statement.setString(2, DiaChi);
                statement.setString(3, DienThoai);
                statement.setString(4, QuanDoc);
                statement.setInt(5, MaPhanXuong);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = res.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                } else {
                    out = res.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Phân Xưởng tồn tại.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

}
