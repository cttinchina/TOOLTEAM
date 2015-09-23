package com.ctt.upload;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.jpedal.PdfDecoder;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;

public class PDF {

	
	
	public static void  main(String args[])throws Exception{
//		CreateImg();
	}
	
	public  static int CreateImg(String f) throws IOException, PdfException,
	InterruptedException {

//	String f = "/Users/chentengteng/Downloads/nhsj/南航《材料工程基础试卷》考试试卷（二）及参考答案.pdf";
	
	
	//f = "2014年南航《核辐射物理学》研究生入学考试试题.pdf";
	String imgPath =  f.substring(0,f.lastIndexOf(".")+1)+"jpg";
	

	/**instance of PdfDecoder to convert PDF into image*/
	PdfDecoder decode_pdf = new PdfDecoder(true);
	
	/**set mappings for non-embedded fonts to use*/
	FontMappings.setFontReplacements();
	int a  = 0;
	/**open the PDF file - can also be a URL or a byte array*/
	try {
	decode_pdf.openPdfFile(f); //file
	//decode_pdf.openPdfFile("C:/myPDF.pdf", "password"); //encrypted file
	//decode_pdf.openPdfArray(bytes); //bytes is byte[] array with PDF
	//decode_pdf.openPdfFileFromURL("http://www.mysite.com/myPDF.pdf",false);
	
	/**get page 1 as an image*/
	//page range if you want to extract all pages with a loop
	int start = 1;
	a = decode_pdf.getPageCount();
	for(int i=start;i<2;i++){
	BufferedImage img=decode_pdf.getPageAsImage(i);
	ImageIO.write(img, "jpg", new File(imgPath));  
	}
	/**close the pdf file*/
	decode_pdf.closePdfFile();
	
	} catch (PdfException e) {
	e.printStackTrace();
	}
	return a;
}
	
	public static int getCount(String f){
		PdfDecoder decode_pdf = new PdfDecoder(true);
		FontMappings.setFontReplacements();
		int a  = 0;
		try {
			System.out.println(f);
		decode_pdf.openPdfFile(f); //file
		a = decode_pdf.getPageCount();
		decode_pdf.closePdfFile();
		} catch (PdfException e) {
		e.printStackTrace();
		}
		return a;
	}
	
}
