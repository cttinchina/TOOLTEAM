package com.ctt.tools.excel;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * 
 * @author CTT
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ReadTemplateExportExcel<T>
{
	
	public ReadTemplateExportExcel(){
		
	} 
	
	
	private HSSFWorkbook workbook;						//生成工作簿
	private int startRow = 11;							//导出起始行
	private int startCol = 6;							//导出起始列
	private String dateFormat = "yyyy-MM-dd";			//导出时间格式
	private int picCellHeight = 60;						//导出图片行高度
	private short picCellWidth = (short) (35.7 * 80);   //导出图片列宽度
	
	
	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param in
	 *            模版文件流
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 */
	@SuppressWarnings("unchecked")
	public Workbook exportExcel(InputStream in,
			Collection<T> dataset)throws IOException
	{
		workbook = new HSSFWorkbook(in);
		// 生成一个表格
		HSSFSheet sheet = workbook.getSheetAt(0);

		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

		// 产生表格标题行
		HSSFRow row;

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = startRow;
		while (it.hasNext())
		{
			row = sheet.getRow(index);
			if(row == null)row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			int colRow2 = startCol;
			for (short i = 0; i < fields.length; i++)
			{
				HSSFCell cell = row.createCell(colRow2);
				if(cell == null)cell = row.createCell(colRow2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try
				{
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[]
							{});
					Object value = getMethod.invoke(t, new Object[]
					{});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Boolean)
					{
						boolean bValue = (Boolean) value;
						textValue = "男";
						if (!bValue)
						{
							textValue = "女";
						}
					}
					else if (value instanceof Date)
					{
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
						textValue = sdf.format(date);
					}
					else if (value instanceof byte[])
					{
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(picCellHeight);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, picCellWidth);
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) colRow2, index, (short) colRow2, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					}
					else
					{
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null)
					{
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches())
						{
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						}
						else
						{
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				}
				catch (SecurityException e)
				{
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
				finally
				{
					// 清理资源
				}
				colRow2++;
			}
			index++;
		}
		return workbook;
	}
//
//	public static void main(String[] args)
//	{
//		// 测试图书
//		ReadTemplateExportExcel<Book> ex2 = new ReadTemplateExportExcel<Book>();
//		List<Book> dataset2 = new ArrayList<Book>();
//		try
//		{
//			BufferedInputStream bis = new BufferedInputStream(
//					new FileInputStream("/Users/chentengteng/Documents/book.jpg"));
//			byte[] buf = new byte[bis.available()];
//			while ((bis.read(buf)) != -1)
//			{
//			}
//			dataset2.add(new Book(1, "jsp1", "leno", 300.33f, "1234567",
//					"清华出版社1", buf));
//			dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
//					"阳光出版社1", buf));
//			dataset2.add(new Book(3, "DOM艺术", "lenotang", 300.33f, "1234567",
//					"清华出版社1", buf));
//			dataset2.add(new Book(4, "c++经典1", "leno", 400.33f, "1234567",
//					"清华出版社", buf));
//			dataset2.add(new Book(5, "c#入门1", "leno", 300.33f, "1234567",
//					"汤春秀出版社", buf));
//
//			InputStream in = new FileInputStream("/Users/chentengteng/Documents/b.xls");
//			OutputStream out2 = new FileOutputStream("/Users/chentengteng/Documents/b1.xls");
//			Workbook workbook = ex2.exportExcel(in,dataset2);
//			workbook.write(out2);
//			System.out.println("excel导出成功！");
//		}
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}


	public HSSFWorkbook getWorkbook() {
		return workbook;
	}


	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}


	public int getStartRow() {
		return startRow;
	}


	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}


	public int getStartCol() {
		return startCol;
	}


	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}


	public String getDateFormat() {
		return dateFormat;
	}


	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}


	public int getPicCellHeight() {
		return picCellHeight;
	}


	public void setPicCellHeight(int picCellHeight) {
		this.picCellHeight = picCellHeight;
	}


	public short getPicCellWidth() {
		return picCellWidth;
	}


	public void setPicCellWidth(short picCellWidth) {
		this.picCellWidth = picCellWidth;
	}
	
}