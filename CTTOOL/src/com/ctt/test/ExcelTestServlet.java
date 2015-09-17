package com.ctt.test;

import java.io.*;

import com.ctt.test.bean.*;
import com.ctt.tools.excel.ExcelServlet;
import com.ctt.tools.excel.ReadTemplateExportExcel;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author CTT
 *
 */
public class ExcelTestServlet extends ExcelServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 测试图书
		ReadTemplateExportExcel<Book> ex2 = new ReadTemplateExportExcel<Book>();
		List<Book> dataset2 = new ArrayList<Book>();
		try
		{
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream("/Users/chentengteng/Documents/book.jpg"));
			byte[] buf = new byte[bis.available()];
			while ((bis.read(buf)) != -1)
			{
			}
			dataset2.add(new Book(1, "jsp1", "leno", 300.33f, "1234567",
					"清华出版社1", buf));
			dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
					"阳光出版社1", buf));
			dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
					"清华出版社1", buf));
			dataset2.add(new Book(4, "c++经典1", "leno", 400.33f, "1234567",
					"清华出版社", buf));
			dataset2.add(new Book(5, "c#入门1", "leno", 300.33f, "1234567",
					"汤春秀出版社", buf));

			InputStream in = new FileInputStream("/Users/chentengteng/Documents/b.xls");
			String fileName = "demo.xls";
			Workbook workbook = ex2.exportExcel(in,dataset2);
			super.download(fileName, workbook, response);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
}