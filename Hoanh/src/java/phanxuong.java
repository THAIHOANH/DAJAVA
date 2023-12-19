import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controlphanxuong;
import controller.menu;

@WebServlet("/Xemphanxuong")
public class phanxuong extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Controlphanxuong in = new Controlphanxuong();

    public phanxuong() {
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
        out.println("<title>Quản lý Phân Xưởng</title>");
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
        
        out.println("<h2>Tìm Kiếm Phân Xưởng </h2>");
        out.println("<form  method='get'>");
        out.println("  <label for='searchMaPhanXuong'>Mã Phân Xưởng:</label>");
        out.println("  <input type='text' id='searchMaPhanXuong' name='searchMaPhanXuong' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Form Nhập Dữ liệu Phân Xưởng  </h2>");
        out.println("<form method='get'>");
        out.println("  <label for='MaPhanXuong'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='MaPhanXuong' name='MaPhanXuong' required><br>");
        out.println("  <label for='TenPhanXuong'>Tên Phân Xưởng:</label>");
        out.println("  <input type='text' id='TenPhanXuong' name='TenPhanXuong' required><br>");
        out.println("  <label for='DiaChi'>Địa Chỉ:</label>");
        out.println("  <input type='text' id='DiaChi' name='DiaChi' required><br>");
        out.println("  <label for='DienThoai'>Điện Thoại:</label>");
        out.println("  <input type='text' id='DienThoai' name='DienThoai' required><br>");
        out.println("  <label for='QuanDoc'>Quản Đốc:</label>");
        out.println("  <input type='text' id='QuanDoc' name='QuanDoc' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("<table>");
        

        String searchMaPhanXuong = req.getParameter("searchMaPhanXuong");
        if (searchMaPhanXuong == null) {
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
        out.println("<title>Form Nhập Dữ Liệu</title>");
        out.println("</head>");
        out.println("<body>");
        String MaPhanXuong = request.getParameter("MaPhanXuong");
        out.println("<h2>Form Nhập Dữ Liệu Phân Xưởng</h2>");
        out.println("<form method='post'>");
       
        out.println("  <input type='hidden' name='MaPhanXuong' value='" + MaPhanXuong + "'/>");
        
        out.println("  <label for='TenPhanXuong'>Tên Phân Xưởng:</label>");
        out.println("  <input type='text' id='TenPhanXuong' name='TenPhanXuong' required><br>");

        out.println("  <label for='DiaChi'>Địa Chỉ :</label>");
        out.println("  <input type='text' id='DiaChi' name='DiaChi' required><br>");

        out.println("  <label for='DienThoai'>Điện Thoại:</label>");
        out.println("  <input type='text' id='DienThoai' name='DienThoai' required><br>");
        
        out.println("  <label for='QuanDoc'>Quản Đốc:</label>");
        out.println("  <input type='text' id='QuanDoc' name='QuanDoc' required><br>");
        
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
     
        String TenPhanXuong = request.getParameter("TenPhanXuong");
        String DiaChi = request.getParameter("DiaChi");
        String DienThoai = request.getParameter("DienThoai");
        String QuanDoc = request.getParameter("QuanDoc");
        
        if(MaPhanXuong!=null && TenPhanXuong != null && DiaChi !=null && DienThoai !=null && QuanDoc !=null )
        {
        in.sua(request, response, out);
        }
        
    }}