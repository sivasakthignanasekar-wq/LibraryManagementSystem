package com.kce.book.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.kce.book.bean.BookBean;
import com.kce.book.dao.AuthorDAO;
import com.kce.book.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation=request.getParameter("operation");
		if(operation.equals("AddBook")) {
			String res=addBook(request);
			if(res.equals("SUCCESS")) {
				response.sendRedirect("Menu.html");
			}
			else if(res.equals("INVALID")) {
				response.sendRedirect("Invalid.html");
			}
			else if(res.equals("FAILURE")) {
				response.sendRedirect("Failure.html");
			}
		}
		else if(operation.equals("Search")) {
			String isbn=request.getParameter("isbn");
			BookBean bookBean =viewBook(isbn);
			if(bookBean==null) {
				response.sendRedirect("Invalid.html");
			}
			else {
				HttpSession session = request.getSession();
				session.setAttribute("book", bookBean);
				RequestDispatcher rd=request.getRequestDispatcher("ViewServlet");
						rd.forward(request, response);
			}
		}
	}
	public String addBook(HttpServletRequest request) {
		String isbn=request.getParameter("isbn");
		String bookName= request.getParameter("bookName");
		String bookType=request.getParameter("bookType");
		String authorName=request.getParameter("authorName");
		String cost=request.getParameter("cost");
		BookBean bookBean=new BookBean();
		bookBean.setIsbn(isbn);
		bookBean.setBookName(bookName);
		bookBean.setBookType(bookType.charAt(0));
		bookBean.setCost(Float.parseFloat(cost));
		bookBean.setAuthor(new AuthorDAO().getAuthor(authorName));
		String res=new Administrator().addBook(bookBean);
		return res;
	}
	public BookBean viewBook(String isbn) {
		Administrator administrator=new Administrator();
		return administrator.viewBook(isbn);
	}
}