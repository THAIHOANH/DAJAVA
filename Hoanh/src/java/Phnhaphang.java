import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Index;
import controller.controlphieunhap;
import controller.menu;

@WebServlet("/Xemnhaphang")
public class Phnhaphang extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private controlphieunhap in = new controlphieunhap();

    public Phnhaphang() {
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
        out.println("<title>Quản lý Phiếu Nhập</title>");
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
        
        out.println("<h2>Tìm Kiếm Phiếu Nhập</h2>");
        out.println("<form  method='get'>");
        out.println("  <label for='searchSoPhieu'>Số Phiếu:</label>");
        out.println("  <input type='text' id='searchSoPhieu' name='searchSoPhieu' required>");
        out.println("  <input type='submit' value='Tìm Kiếm'>");
        out.println("</form>");

        out.println("<h2>Form Nhập Dữ liệu Phiếu Nhập</h2>");
        out.println("<form method='get'>");
        out.println("  <label for='SoPhieu'>Số Phiếu:</label>");
        out.println("  <input type='text' id='SoPhieu' name='SoPhieu' required><br>");
        out.println("  <label for='NgayLapPhieu'>Ngày Lập Phiếu:</label>");
        out.println("  <input type='text' id='NgayLapPhieu' name='NgayLapPhieu' required><br>");
        out.println("  <label for='MaKhachHang'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='MaKhachHang' name='MaKhachHang' required><br>");
        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("<table>");
        

        String searchSoPhieu = req.getParameter("searchSoPhieu");
        if (searchSoPhieu == null) {
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
        String SoPhieu = request.getParameter("SoPhieu");
        out.println("<h2>Form Nhập Dữ Liệu Phiếu Nhập</h2>");
        out.println("<form method='post'>");
       
        out.println("  <input type='hidden' name='SoPhieu' value='" + SoPhieu + "'/>");
        

        out.println("  <label for='NgayLapPhieu'>Ngày Lập Phiếu:</label>");
        out.println("  <input type='text' id='NgayLapPhieu' name='NgayLapPhieu' required><br>");

        out.println("  <label for='MaKhachHang'>Mã Khách Hàng:</label>");
        out.println("  <input type='text' id='MaKhachHang' name='MaKhachHang' required><br>");

        out.println("  <input type='submit' value='Submit'>");
        out.println("</form>");
        
        out.println("</body>");
        out.println("</html>");
     
        String NgayLapPhieu = request.getParameter("NgayLapPhieu");
        String MaKhachHang = request.getParameter("MaKhachHang");
        
        if(SoPhieu!=null && NgayLapPhieu != null && MaKhachHang !=null  )
        {
        in.sua(request, response, out);
        }
        
    }}