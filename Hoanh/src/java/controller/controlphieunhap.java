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
public class controlphieunhap {
        Connection con ;
        Statement stmt ;
    public controlphieunhap() {
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
            String sql = "select * from phieunhaphang";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Số Phiếu</th><th>NgayLapPhieu</th><th>MaKhachHang</th></tr>");
            while (rs.next()) {
                int SoPhieu = rs.getInt("SoPhieu");
                String NgayLapPhieu = rs.getString("NgayLapPhieu");
                String MaKhachHang = rs.getString("MaKhachHang");
                
                out.println("<tr><td>" + SoPhieu  + "</td><td>" + NgayLapPhieu + "</td><td>" + MaKhachHang + "</td><td>"+"<td><a href='Xemnhaphang?SoPhieuXoa=" + SoPhieu + "'>Xóa</a></td>"
                        +"<td><form method='post'>" +
                        "<input type='hidden' name='SoPhieu' value='" + SoPhieu + "'>" +
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
        String SoPhieuParam = request.getParameter("SoPhieu");
        String NgayLapPhieu = request.getParameter("NgayLapPhieu");
        String MaKhachHang = request.getParameter("MaKhachHang");
        int SoPhieu = Integer.parseInt(SoPhieuParam);
        
        try {
           openConnection();
           String sql = "INSERT INTO phieunhaphang (SoPhieu, NgayLapPhieu, MaKhachHang) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setInt(1, SoPhieu);
                statement.setString(2,NgayLapPhieu);
                statement.setString(3,MaKhachHang);

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
            String SoPhieuXoa = request.getParameter("SoPhieuXoa");

            // Kiểm tra xem mã khách có tồn tại không
            if (SoPhieuXoa != null && !SoPhieuXoa.isEmpty()) {
                // Tạo câu lệnh SQL để xóa khách hàng
                String sql = "DELETE FROM phieunhaphang WHERE SoPhieu = ?";
                
                // Tạo đối tượng PreparedStatement để thực hiện câu lệnh SQL
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, SoPhieuXoa);
                    
                    // Thực hiện xóa dữ liệu
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        out.println("Xóa Phiếu thành công!");
                    } else {
                        out.println("Không tìm thấy Phiếu có mã " + SoPhieuXoa + " để xóa.");
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
        String searchSoPhieu = req.getParameter("searchSoPhieu");
        try {
            openConnection();
            String sql = "SELECT * FROM phieunhaphang WHERE SoPhieu=?";
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
                    out.println("<tr><th>Số Phiếu</th><th>Ngày Lập Phiếu</th><th>Mã Khách Hàng</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("SoPhieu") + "</td><td>" + resultSet.getString("NgaylapPhieu") + "</td><td>" + resultSet.getString("MaKhachHang") + "</td> "+"<td><a href='Xemphieunhap?SoPhieuXoa=" + resultSet.getString("SoPhieu") + "'>Xóa</a></td>"+"<td><a href='Sua?SoPhieu=" + resultSet.getString("SoPhieu") + "'>Sua</a></td> </tr>");
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
        String SoPhieuParam = req.getParameter("SoPhieu");
        String NgayLapPhieu = req.getParameter("NgayLapPhieu");
        String MaKhachHang = req.getParameter("MaKhachHang");
        int SoPhieu = Integer.parseInt(SoPhieuParam);
        try {
            openConnection();
            
            String sql = "UPDATE phieunhaphang SET NgayLapPhieu=?, MaKhachHang=? WHERE SoPhieu=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, NgayLapPhieu);
                statement.setString(2, MaKhachHang);
                statement.setInt(3,SoPhieu);
                
               
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