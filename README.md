# TOOLTEAM
2015.09.17 version 1.1正式版
2015.09.17 add ExportExcel.java   用于导出无模版数据，可设定样式参数
2015.09.17 add ReadTemplateExportExcel.java  用于导出数据，实用模版， 导出不添加任何样式，实用模版原样式
2015.09.17 add ExcelServlet.java excel导出文件下载， 可以创建一个servlet继承ExcelServlet 实用download方法处理需下载的流
2015.09.17 excel文件导出部分图片存在问题，如果无硬性需要不建议实用
2015.09.17 ExportExcel.java 参数:
		private HSSFWorkbook workbook;						//生成工作簿
		private String sheetName = "Sheet1";				//设置表格名称
		private short defaultColumnWidth = (short) 15;		//表哥默认列宽度 默认宽度15个字节
		private HSSFCellStyle style;						//头部样式
		private HSSFCellStyle style2;						//内容样式
		private String docPrse = "";						//文档注释
		private String author = "System";					//文档作者
		private int startRow = 0;							//导出起始行
		private int startCol = 0;							//导出起始列
		private boolean useStyle = false;					//启用样式
		private String dateFormat = "yyyy-MM-dd";			//导出时间格式
		private int picCellHeight = 60;						//导出图片行高度
		private short picCellWidth = (short) (35.7 * 80);   //导出图片列宽度
		
2015.09.17  ReadTemplateExportExcel.java 参数：
		private HSSFWorkbook workbook;						//生成工作簿
		private int startRow = 11;							//导出起始行
		private int startCol = 6;							//导出起始列
		private String dateFormat = "yyyy-MM-dd";			//导出时间格式
		private int picCellHeight = 60;						//导出图片行高度
		private short picCellWidth = (short) (35.7 * 80);   //导出图片列宽度
2015.09.17  servlet参考代码：
		
		ReadTemplateExportExcel<Book> ex2 = new ReadTemplateExportExcel<Book>();
		List<Book> dataset2 = new ArrayList<Book>();
		BufferedInputStream bis = new BufferedInputStream(
				new FileInputStream("/Users/chentengteng/Documents/book.jpg"));
		byte[] buf = new byte[bis.available()];
		while ((bis.read(buf)) != -1)
		{
		}
		dataset2.add(new Book(1, "jsp1", "leno", 300.33f, "1234567",
				"清华出版社1", buf));
		dataset2.add(new Book(2, "java编程思想", "brucl", 300.33f, "1234567",
		InputStream in = new FileInputStream("/Users/chentengteng/Documents/b.xls");
		String fileName = "demo.xls";
		Workbook workbook = ex2.exportExcel(in,dataset2);
		super.download(fileName, workbook, response);
2015.09.17  为不影响最后文件输出，类中部分异常已经内部处理过滤。部分抛出
2015.09.17 测试环境 jdk1.6  tomcat7  
2015.09.17 jar : tomcat 
	commons-codec-1.5.jar
	commons-io-2.4.jar
	dom4j-1.6.1.jar
	poi-3.8-20120326.jar
	poi-examples-3.8-20120326.jar
	poi-excelant-3.8-20120326.jar
	poi-ooxml-3.8-20120326.jar
	poi-ooxml-schemas-3.8-20120326.jar
	poi-scratchpad-3.8-20120326.jar
	xmlbeans-2.3.0.jar
 

2015.09.14 version 1.1
2015.09.14 add ExcelHelper.java 用于导入excel  
2015.09.14 已经已经测试了office 2003 2013 版本，  之后会添加到处工具
2015.09.14 取得List<List<String>>  or   List<Map<String,Object>> 格式的数据
2015.09.14 其中已经对日期 数字等进行了处理，最后处理成string统一使用
2015.09.14  
	jar:poi-3.8-20120326.jar
		poi-examples-3.8-20120326.jar
		poi-excelant-3.8-20120326.jar
		poi-ooxml-3.8-20120326.jar
		poi-ooxml-schemas-3.8-20120326.jar
		poi-scratchpad-3.8-20120326.jar
		xmlbeans-2.3.0.jar
		dom4j-1.6.1.jar


2015.08.27 version 1.0
2015.08.27 add JsonUtil.java  json格式的反序列工具 根据不同的类型返回
2015.08.27 add KeyUtil.java 主键生成器 根据时间生成毫秒数， 可自定义前后缀 确保唯一
2015.08.27 jar plugins gson 2.2.4 source
