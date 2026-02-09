package com.kce.book.service;

import com.kce.book.bean.BookBean;
import com.kce.book.dao.BookDAO;

public class Administrator {
	public String addBook(BookBean bookBean) {
	    if (bookBean == null ||
	        bookBean.getBookName().isEmpty() ||
	        bookBean.getIsbn().isEmpty() ||
	        bookBean.getCost() <= 0 ||
	        bookBean.getAuthor() == null ||
	        bookBean.getAuthor().getAuthorName().isEmpty() ||
	        (bookBean.getBookType() != 'G' && bookBean.getBookType() != 'T')) {

	        return "INVALID";
	    }

	    int res = new BookDAO().createBook(bookBean);
	    if (res == 1) {
	        return "SUCCESS";
	    }
	    return "FAILURE";
	}

	public BookBean viewBook(String isbn) {
		if(isbn.isEmpty()) {
			return null;
		}
		BookDAO bookDao=new BookDAO();
		return bookDao.fetchBook(isbn);
	}
	
}