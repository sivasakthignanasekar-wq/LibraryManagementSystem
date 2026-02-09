package com.kce.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kce.book.bean.BookBean;
import com.kce.book.util.DBUtil;

public class BookDAO {
	public int createBook(BookBean bookBean) {
		Connection connection=DBUtil.getDBConnection();
		String query="Insert into Book_Tbl VALUES(?,?,?,?,?)";
	    try {
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setString(1,bookBean.getIsbn());
		ps.setString(2, bookBean.getBookName());
		ps.setString(3, String.valueOf(bookBean.getBookType()));
		ps.setInt(4, bookBean.getAuthor().getAuthorCode());
		ps.setFloat(5,bookBean.getCost() );
		int row=ps.executeUpdate();
		if(row>0) {
			return 1;
		}
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
		return 0;	
	}
	public BookBean fetchBook(String isbn) {
	    Connection connection = DBUtil.getDBConnection();
	    String query = "SELECT * FROM BOOK_TBL WHERE ISBN=?";

	    try {
	        PreparedStatement ps = connection.prepareStatement(query);
	        ps.setString(1, isbn);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            BookBean bookbean = new BookBean();
	            bookbean.setIsbn(rs.getString("ISBN"));
	            bookbean.setBookName(rs.getString("BOOK_TITLE")); 
	            bookbean.setBookType(rs.getString("BOOK_TYPE").charAt(0)); 
	            bookbean.setAuthor(new AuthorDAO().getAuthor(rs.getInt("AUTHOR_CODE")));
	            bookbean.setCost(rs.getFloat("BOOK_COST"));

	            return bookbean;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    }
	    return null;
	}


}