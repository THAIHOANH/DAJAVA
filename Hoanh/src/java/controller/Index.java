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
public class Index {
        Connection con ;
        Statement stmt ;
    public Index() {
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
            String sql = "select * from thuoc";
            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border=1 width=50% height=50%>");
            out.println("<tr><th>Mã Thuốc</th><th>Tên Thuốc</th><th>Quy Cách</th></tr>");
            while (rs.next()) {
                int MaThuoc = rs.getInt("MaThuoc");
                String TenThuoc = rs.getString("TenThuoc");
                String QuyCach = rs.getString("QuyCach");
                
                out.println("<tr><td>" + MaThuoc + "</td><td>" + TenThuoc + "</td><td>" + QuyCach + "</td><td>"+"<td><a href='XemDS?MaThuocXoa=" + MaThuoc + "'>Xóa</a></td>"
                        +"<td><form method='post'>" +
                        "<input type='hidden' name='MaThuoc' value='" + MaThuoc + "'>" +
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
        String MaThuocParam = request.getParameter("MaThuoc");
        String TenThuoc = request.getParameter("TenThuoc");
        String QuyCach = request.getParameter("QuyCach");
        int MaThuoc = Integer.parseInt(MaThuocParam);
        
        try {
           openConnection();
           String sql = "INSERT INTO thuoc (MaThuoc, TenThuoc, QuyCach) VALUES (?, ?, ?)";

            try (PreparedStatement statement = con.prepareStatement(sql))
            {
                statement.setInt(1, MaThuoc);
                statement.setString(2,TenThuoc);
                statement.setString(3,QuyCach);

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
            String MaThuocXoa = request.getParameter("MaThuocXoa");

            // Kiểm tra xem mã khách có tồn tại không
            if (MaThuocXoa != null && !MaThuocXoa.isEmpty()) {
                // Tạo câu lệnh SQL để xóa khách hàng
                String sql = "DELETE FROM thuoc WHERE MaThuoc = ?";
                
                // Tạo đối tượng PreparedStatement để thực hiện câu lệnh SQL
                try (PreparedStatement statement = con.prepareStatement(sql)) {
                    statement.setString(1, MaThuocXoa);
                    
                    // Thực hiện xóa dữ liệu
                    int rowsDeleted = statement.executeUpdate();
                    
                    if (rowsDeleted > 0) {
                        out.println("Xóa thuốc thành công!");
                    } else {
                        out.println("Không tìm thấy thuốc có mã " + MaThuocXoa + " để xóa.");
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
        String searchMaThuoc = req.getParameter("searchMaThuoc");
        try {
            openConnection();
            String sql = "SELECT * FROM thuoc WHERE MaThuoc=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, searchMaThuoc);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    out.println("<style>");
                    out.println("body");
                    out.println("table {border-collapse: collapse; width: 80%;}");
                    out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
                    out.println("th {background-color: #f2f2f2;}");
                    out.println("</style>");
                    out.println("<table %>");
                    out.println("<tr><th>Mã Thuốc</th><th>Tên Thuốc</th><th>Quy Cách</th></tr>");
                    out.println("<tr><td>" + resultSet.getString("MaThuoc") + "</td><td>" + resultSet.getString("TenThuoc") + "</td><td>" + resultSet.getString("QuyCach") + "</td> "+"<td><a href='Xoa?MaThuocXoa=" + resultSet.getString("MaThuoc") + "'>Xóa</a></td>"+"<td><a href='Sua?MaThuoc=" + resultSet.getString("MaThuoc") + "'>Sua</a></td> </tr>");
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
        String MaThuocParam = req.getParameter("MaThuoc");
        String TenThuoc = req.getParameter("TenThuoc");
        String QuyCach = req.getParameter("QuyCach");
        int MaThuoc = Integer.parseInt(MaThuocParam);
        try {
            openConnection();
            
            String sql = "UPDATE thuoc SET TenThuoc=?, QuyCach=? WHERE MaThuoc=?";
            try (PreparedStatement statement = con.prepareStatement(sql)) {
                statement.setString(1, TenThuoc);
                statement.setString(2, QuyCach);
                statement.setInt(3,MaThuoc);
                
               
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    out = res.getWriter();
                    out.println("Dữ liệu đã được cập nhật thành công!");
                }else {
                    out = res.getWriter();
                    out.println("Không thể cập nhật dữ liệu! Hãy chắc chắn rằng Mã Khách tồn tại.");
                }
           
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        }
}