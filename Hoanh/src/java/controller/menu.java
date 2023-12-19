/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class menu {
    public void htmenu(HttpServletResponse res,PrintWriter out)
    {
        out.println("<div style='display: flex; justify-content: space-around; background-color: #f1f1f1; padding: 10px;'>");
        out.println("<a href='XemDS' style='text-decoration: none; color: #333;'>Thuốc</a>");
        out.println("<a href='Xemkh' style='text-decoration: none; color: #333;'>Khách Hàng</a>");
        out.println("<a href='Xemnhaphang' style='text-decoration: none; color: #333;'>Phiếu Nhập Hàng</a>");
        out.println("<a href='Xemctphieudathang' style='text-decoration: none; color: #333;'>Chi Tiết Phiếu Đặt Hàng</a>");
        out.println("<a href='Xemphancong' style='text-decoration: none; color: #333;'>Phân Công </a>");
        out.println("<a href='Xemphanxuong' style='text-decoration: none; color: #333;'>Phân Xưởng</a>");
        out.println("<a href='index.html' style='text-decoration: none; color: #333;'>Đăng xuất </a>");
        out.println("</div>");

    }
}
