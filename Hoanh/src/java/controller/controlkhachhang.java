package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author thaih
 */
public class controlkhachhang {
        Connection con ;
        Statement stmt ;
    public controlkhachhang() {
    }
      public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qlduocthuoc1?useUnicode=true&characterEncoding=UTF-8", "root", "");
        }
        public void displayData(PrintWriter out) 
        {
        try {
            openConnection();
            stmt = con.createStatement();
            String sql = "select * from khachhang";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Khách Hàng</th><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Điện Thoại</th></tr>");
            while (rs.next()) {
                int MaKhachHang = rs.getInt("MaKhachHang");
                String TenKhachHang = rs.getString("TenKhachHang");
                String DiaChi= rs.getString("DiaChi");
                String SoDienThoai = rs.getString("SoDienThoai");
                
                out.println("<tr><td>" + MaKhachHang  + "</td><td>" + TenKhachHang + "</td><td>" + DiaChi + "</td><td>" + SoDienThoai  + "</td><td>"+"<td><a href='Xemkh?MaKhachHangXoa=" + MaKhachHang + "'>Xóa</a></td>"
                        +"<td><form method='post'>" +
                        "<input type='hidden' name='MaKhachHang' value='" + MaKhachHang + "'>" +
                        "<input type='submit' value='Sửa'>" +
                        "</form></td>" );
            }
            out.println("</table>");
            con.close();
            } catch (Exception e) {
            out.println("Lỗi: " + e.getMessage());
            }
        }
        public void nhap(HttpServletRequest request, HttpServletResponse response) throws IOException
        {
        String MaKhachHangParam = request.getParameter("MaKhachHang");
        String TenKhachHang = request.getParameter("TenKhachHang");
        String DiaChi = request.getParameter("DiaChi");
        String SoDienThoai = request.getParameter("SoDienThoai");
        int MaKhachHang = Integer.parseInt(MaKhachHangParam);
        
        try {
           openConnection();
           String sql = "INSERT INTO khachhang (MaKhachHang, TenKhachHang, DiaChi, SoDienThoai) VALUES (?, ?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setInt(1, MaKhachHang);
                statement.setString(2,TenKhachHang);
                statement.setString(3,DiaChi);
                statement.setString(4,SoDienThoai);

                // Thực hiện chèn dữ liệu
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    PrintWriter out = response.getWriter();
         
                    System.out.println("Dữ liệu đã được chèn thành công!");
                } 
                else 
                {
                    PrintWriter out = response.getWriter();
                  
                }
            }
            } catch (Exception e) 
            {
                   e.printStackTrace();
                    
            }
        }   
        public void xoa(HttpServletRequest request, HttpServletResponse response ,PrintWriter out)
        {
         try {
            openConnection();
            String MaKhachHangXoa = request.getParameter("MaKhachHangXoa");

            // Kiểm tra xem mã khách có tồn tại không
            if (MaKhachHangXoa != null && !MaKhachHangXoa.isEmpty()) {
                // Tạo câu lệnh SQL để xóa khách hàng
                String sql = "DELETE FROM khachhang WHERE MaKhachHang = ?";
                
                // Tạo đối tượng PreparedStatement để thực hiện câu lệnh SQL
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, MaKhachHangXoa);
                    
                    // Thực hiện xóa dữ liệu
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        out.println("Xóa khách hàng thành công!");
                    } else {
                        out.println("Không tìm thấy khách hàng có mã " + MaKhachHangXoa + " để xóa.");
                    }
                }
            } else {
                
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi: " + e.getMessage());
        }
        }
        public void TK(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        String searchSoPhieu = req.getParameter("searchMaKhachHang");
        try {
            openConnection();
            String sql = "SELECT * FROM khachhang WHERE MaKhachHang=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchSoPhieu);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Khách Hàng</th><th>Tên Khách Hàng</th><th>Địa Chỉ</th><th>Số Điện Thoại</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("MaKhachHang") + "</td><td>" + resultSet.getString("TenKhachHang") + "</td><td>" + resultSet.getString("DiaChi") + "</td><td>" + resultSet.getString("SoDienThoai") + "</td> "+"<td><a href='Xemckhachhang?MaKhachHangXoa=" + resultSet.getString("MaKhachHang") + "'>Xóa</a></td>"+"<td><a href='Sua?MaKhachHang=" + resultSet.getString("MaKhachHang") + "'>Sua</a></td> </tr>");
                    out.println("</table>");
                } else {
                 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        
        
        }
         public void sua(HttpServletRequest req, HttpServletResponse res ,PrintWriter out)
        {
        res.setContentType("text/html");
        String MaKhachHangParam = req.getParameter("MaKhachHang");
        String TenKhachHang = req.getParameter("TenKhachHang");
        String DiaChi = req.getParameter("DiaChi");
        String SoDienThoai = req.getParameter("SoDienThoai");
        int MaKhachHang = Integer.parseInt(MaKhachHangParam);
        try {
            openConnection();
            
            String sql = "UPDATE khachhang SET TenKhachHang=?, DiaChi=?, SoDienThoai=? WHERE MaKhachHang=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, TenKhachHang);
                statement.setString(2, DiaChi);
                statement.setString(3, SoDienThoai);
                statement.setInt(4,MaKhachHang);
                
               
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = res.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                }else {
                    out = res.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Số Phiếu tồn tại.");
                }
           
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
}