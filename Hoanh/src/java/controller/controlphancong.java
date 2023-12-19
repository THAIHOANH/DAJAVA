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

public class controlphancong {
    private Connection con;
    private Statement stmt;

    public controlphancong() {
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlduocthuoc1?useUnicode=true&characterEncoding=UTF-8", "root", "");
    }

    public void displayData(PrintWriter out) {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from phancong";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Số Phân Công</th><th>Mã Phân Xưởng</th><th>Số Phiếu </th><th>Mã Thuốc</th><th>Số Lượng</th></tr>");
            while (rs.next()) {
                int SoPhanCong = rs.getInt("SoPhanCong");
                String MaPhanXuong = rs.getString("MaPhanXuong");
                String SoPhieu = rs.getString("SoPhieu");
                String MaThuoc = rs.getString("MaThuoc");
                String SoLuong= rs.getString("SoLuong");

                out.println("<tr><td>" + SoPhanCong + "</td><td>" + MaPhanXuong + "</td><td>" + SoPhieu + "</td><td>" + MaThuoc + "</td><td>" + SoLuong + "</td><td>" +
                        "<td><a href='Xemphancong?SoPhanCongXoa=" + SoPhanCong + "'>Xóa</a></td>" +
                        "<td><form method='post'>" +
                        "<input type='hidden' name='SoPhanCong' value='" + SoPhanCong + "'>" +
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
        String SoPhanCongParam = request.getParameter("SoPhanCong");
        String MaPhanXuong = request.getParameter("MaPhanXuong");
        String SoPhieu = request.getParameter("SoPhieu");
        String MaThuoc = request.getParameter("MaThuoc");
        String SoLuong = request.getParameter("SoLuong");
        int SoPhanCong = Integer.parseInt(SoPhanCongParam);

        try {
            openConnection();
            String sql = "INSERT INTO phieunhaphang (SoPhanCong, MaPhanXuong, SoPhieu, MaThuoc, SoLuong) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setInt(1, SoPhanCong);
                statement.setString(2, MaPhanXuong);
                statement.setString(3, SoPhieu);
                statement.setString(4, MaThuoc);
                statement.setString(5, SoLuong);

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
            String SoPhanCongXoa = request.getParameter("SoPhanCongXoa");

            if (SoPhanCongXoa != null && !SoPhanCongXoa.isEmpty()) {
                String sql = "DELETE FROM phanxuong WHERE MaPhanXuong = ?";

                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, SoPhanCongXoa);

                    int rowsDeleted = statement.executeUpdate();

                    if (rowsDeleted > 0) {
                        out.println("Xóa Phiếu thành công!");
                    } else {
                        out.println("Không tìm thấy Phiếu có mã " + SoPhanCongXoa + " để xóa.");
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
        String searchSoPhanCong = req.getParameter("searchSoPhanCong");
        try {
            openConnection();
            String sql = "SELECT * FROM phancong WHERE SoPhanCong=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchSoPhanCong);

                // Xử lý kết quả truy vấn và in ra bảng
            
            ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Số Phân Công </th><th>Mã Phân Xưởng</th><th>Số Phiếu </th><th>Mã Thuốc </th><th>Số Lượng </th></tr>");
                    out.println("<tr><td>" + resultSet.getString("SoPhanCong") + "</td><td>" + resultSet.getString("MaPhanXuong") + "</td><td>" + resultSet.getString("SoPhieu") + "</td><td>" + resultSet.getString("MaThuoc") + "</td><td>" + resultSet.getString("SoLuong") + "</td> "+"<td><a href='Xoa?MaThuocXoa=" + resultSet.getString("MaThuoc") + "'>Xóa</a></td>"+"<td><a href='Sua?SoPhanCong=" + resultSet.getString("SoPhanCong") + "'>Sua</a></td> </tr>");
                    out.println("</table>");
                } else {
                 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public void sua(HttpServletRequest req, HttpServletResponse res, PrintWriter out) {
        res.setContentType("text/html");
        String SoPhanCongParam = req.getParameter("SoPhanCong");
        String MaPhanXuong = req.getParameter("MaPhanXuong");
        String SoPhieu = req.getParameter("SoPhieu");
        String MaThuoc = req.getParameter("MaThuoc");
        String SoLuong = req.getParameter("SoLuong");
        int SoPhanCong = Integer.parseInt(SoPhanCongParam);
        try {
            openConnection();

            String sql = "UPDATE phanxuong SET TenPhanXuong=?, DiaChi=?, Dienthoai=?, Quandoc=? WHERE MaPhanXuong=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, MaPhanXuong);
                statement.setString(2, SoPhieu);
                statement.setString(3, MaThuoc);
                statement.setString(4, SoLuong);
                statement.setInt(5, SoPhanCong);

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
