import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.controlphancong;
import controller.menu;

@WebServlet("/Xemphancong")
public class phancong extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private controlphancong in = new controlphancong();

    public phancong() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Quản lý Phân Công</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif;}");
        out.println("form { margin-bottom: 20px; }");
        out.println("label { display: block; margin-bottom: 5px; }");
        out.println("input[type='text'] { width: 300px; padding: 8px; margin-bottom: 10px; }");
        out.println("input[type='submit'] { padding: 8px 20px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }");
        out.println("table { border-collapse: collapse; width: 80%; }");
        out.println("th, td { border: 1px solid #dddddd; text-align: left; padding: 8px; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
 menu menu = new menu();
        menu.htmenu(res, out); 
        out.println("<h2>Tìm Kiếm Phân Công </h2>");
        out.println("<form  method='get'>");
        out.println("  <label for='searchSoPhanCong'>Số Phân Công:</label>");
        out.println("  <input type='text' id='searchSoPhanCong' name='searchSoPhanCong' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        
        
        out.println("<h2>Form Nhập Dữ liệu Phân Xưởng  </h2>");
        out.println("<form method='get'>");
        out.println("  <label for='SoPhanCong'>Số Phân Công:</label>");
        out.println("  <input type='text' id='SoPhanCong' name='SoPhanCong' required><br>");
        out.println("  <label for='MaPhanXuong'>Mã Phân Xưởng:</label>");
        out.println("  <input type='text' id='MaPhanXuong' name='MaPhanXuong' required><br>");
        out.println("  <label for='SoPhieu'>Số Phiếu:</label>");
        out.println("  <input type='text' id='SoPhieu' name='SoPhieu' required><br>");
        out.println("  <label for='MaThuoc'>Mã Thuốc:</label>");
        out.println("  <input type='text' id='SoPhieu' name='SoPhieu' required><br>");
        out.println("  <label for='SoLuong'>Số lượng:</label>");
        out.println("  <input type='text' id='SoLuong' name='SoLuong' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("<table>");
        

        String searchSoPhanCong = req.getParameter("searchSoPhanCong");
        if (searchSoPhanCong == null) {
            in.displayData(out);
        } else {
            in.TK(req, res, out);
        }
        in.xoa(req, res, out);
        in.nhap(req, res);

        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Form Nhập Dữ Liệu Phân Công</title>");
        out.println("</head>");
        out.println("<body>");
        String SoPhanCong = request.getParameter("SoPhanCong");
        out.println("<h2>Form Nhập Dữ Liệu Phân Công</h2>");
        out.println("<form method='post'>");
       
        out.println("  <input type='hidden' name='SoPhanCong' value='" + SoPhanCong + "'/>");
        
        out.println("  <label for='MaPhanXuong'>Mã Phân Xưởng :</label>");
        out.println("  <input type='text' id='MaPhanXuong' name='MaPhanXuong' required><br>");

        out.println("  <label for='SoPhieu'>Số Phiếu :</label>");
        out.println("  <input type='text' id='SoPhieu' name='SoPhieu' required><br>");

        out.println("  <label for='MaThuoc'>Mã Thuốc:</label>");
        out.println("  <input type='text' id='MaThuoc' name='MaThuoc' required><br>");
        
        out.println("  <label for='SoLuong'>Số Lượng:</label>");
        out.println("  <input type='text' id='SoLuong' name='SoLuong' required><br>");
        
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
     
        String MaPhanXuong = request.getParameter("MaPhanXuong");
        String SoPhieu = request.getParameter("SoPhieu");
        String MaThuoc = request.getParameter("MaThuoc");
        String SoLuong = request.getParameter("SoLuong");
        
        if(SoPhanCong!=null && MaPhanXuong != null && SoPhieu !=null && MaThuoc !=null && SoLuong !=null )
        {
        in.sua(request, response, out);
        }
        
    }}