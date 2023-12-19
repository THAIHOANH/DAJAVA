
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import controller.DAO;

@WebServlet("/user")
public class user extends HttpServlet {
private static final long serialVersionUID = 1L;
    public user() {
        super();
    }
    DAO in = new DAO();
        protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");
        
        // Tạo biểu mẫu nhập liệu
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("</body>");
        out.println("<style>");
        out.println("body");
        out.println("table {border-collapse: collapse; width: 80%;}");
        out.println("th, td {border: 1px solid #dddddd; text-align: left; padding: 8px;}");
        out.println("th {background-color: #f2f2f2;}");
        out.println("</style>");
        out.println("</head>");
        out.println("</table>");
        out.println("</body></html>");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String userRole = in.getUserRole(username, password);
        if(userRole == "admin")
        {
            res.sendRedirect("XemUser");
        }
        else if(userRole == "user")
        {
            res.sendRedirect("XemDS"); 
            out.print(userRole);
        }
        else
        {
        out.print("lổi đăng nhập");
        }
        }
        
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<body>");
        
        // Tạo biểu mẫu nhập liệu
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Form Nhập Thông Tin</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
        out.println("h2 { text-align: center; }");
        out.println("form { max-width: 400px; margin: auto; }");
        out.println("label, input[type='submit'] { display: block; margin-bottom: 10px; }");
        out.println("input[type='text'], input[type='email'], input[type='password'] { width: calc(100% - 20px); padding: 5px; }");
        out.println("input[type='submit'] { padding: 8px 15px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }");
        out.println("input[type='submit']:hover { background-color: #45a049; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Form Nhập Thông Tin</h2>");
        out.println("<form method='post'>");
        out.println("<label for='gmail'>Gmail:</label>");
        out.println("<input type='email' id='gmail' name='gmail' required><br>");
        out.println("<label for='username'>Username:</label>");
        out.println("<input type='text' id='username' name='username' required><br>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' id='password' name='password' required><br>");
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");
        String gmail = req.getParameter("gmail");
        if(gmail !=null)
        {
        in.nhapUser(req, res);
        }
    }


}
