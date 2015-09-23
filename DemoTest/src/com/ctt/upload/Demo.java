package com.ctt.upload;
import java.io.*;
import java.util.*;

public class Demo {

	
	
	public static void main(String arg[])throws Exception{
//		readFileNameAndReplaceName();
		readTxtFile();
	}
	
	/**
	 * 
	 * 
	 * --`id` int(10) NOT NULL AUTO_INCREMENT,
  --`classifyid` int(11) DEFAULT NULL,
  --`classifyname` varchar(255) DEFAULT NULL,
  --`schoolid` int(11) DEFAULT NULL,
  --`schoolname` varchar(255) DEFAULT NULL,
  --`subjectid` int(11) DEFAULT NULL,
  --`subjectname` varchar(255) DEFAULT NULL,
  --`courseid` int(11) DEFAULT NULL,
  --`coursename` varchar(255) DEFAULT NULL,
  --`title` varchar(50) DEFAULT NULL,
  --`year` varchar(5) DEFAULT NULL,   --如果没有年份 那么就抛出异常  记录文件
  --`rank` int(1) DEFAULT NULL, 
  `image` varchar(255) DEFAULT NULL,    －－生成一张图片  －－ 后面3张图片字段空着
  `intro` varchar(500) DEFAULT NULL,
  `page` int(10) DEFAULT NULL,
  `ext` varchar(10) DEFAULT NULL,
  `filesize` int(50) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `oldname` varchar(255) NOT NULL,
  `fileurl` varchar(255) DEFAULT NULL,
  `thumb1` varchar(255) DEFAULT NULL,
  `thumb2` varchar(255) DEFAULT NULL,
  `thumb3` varchar(255) DEFAULT NULL,
  `thumb4` varchar(255) DEFAULT NULL,
  `thumb5` varchar(255) DEFAULT NULL,
  `hash` varchar(255) NOT NULL,
  `uid` int(10) DEFAULT NULL,
  `add_time` int(11) DEFAULT NULL,
  `tags` varchar(100) NOT NULL,
  `hits` int(10) DEFAULT '0',
  `like` int(1) DEFAULT NULL,
  `isrecommend` int(1) DEFAULT NULL,
  `status` tinyint(1) unsigned NOT NULL DEFAULT '2' COMMENT '0:关闭1:待审核2:通过3:置顶4:推荐5:焦点图',
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 

	 */

//	public static String path = "/Users/chentengteng/Downloads/nhsj";
//	private static  String newFile = "/Users/chentengteng/Downloads/njshs";
//	private static String filenum = "1";
//	private static int id = 2000;
	
	public static String path = "/Users/chentengteng/Downloads/nlgsj";
	private static String newFile = "/Users/chentengteng/Downloads/nlgsjs";
	private static String filenum = "2";
	private static int id = 2700;

	public  static void readFileNameAndReplaceName() throws Exception{
		 File file=new File(path);
		  File[] tempList = file.listFiles();
		  System.out.println("该目录下对象个数："+tempList.length);
		  //
		  	//保存文件问件夹
		   
		    //tempList.length
		  for (int i = 0; i < tempList.length; i++) {
		   if (tempList[i].isFile()) {
		    String fileName = tempList[i].getName();
		    int hashcode = tempList[i].hashCode();
		    //year
		    String year = getYear(fileName);
		    //----------保存文件名
		    String name = fileName.substring(0,fileName.indexOf("."));
		    //----------科目名称
		    int start = name.indexOf("《");
		    int end = name.indexOf("》");
		    if(start < 0 || end <0){
		    	contentToerror(fileName);
		    	continue;
		    }
		    String coursename = name.substring(start+1,end);
		    //新文件名
		    String newFileName = KeyUtil.getKey();
		    String fileType = ".pdf";
		    //----------新文件路径
		    String newFilePath = newFile + "/" +newFileName+fileType;
		    
		    //按照文件规则生成新文件
		    copy(tempList[i],newFilePath);
		    
		    
		    contentToTxt(id+"_"+fileName+"_"+newFileName);
		    
		    //新文件夹名称生成规则
		    //文件图片 
		    //年份字段如何判断
		    //文件年份中没有科目名称如何判断
		    
//		   String sql = "INSERT INTO `yt_file` VALUES ('"+id+"', '1', '试卷', '3', '南京理工大学', null, null, null, '"+coursename+"', '"+
//		    name+"', '"+year+"', '5', '/data/upload/docimg/2015-09/"+newFileName+".jpg', null, 0, 'pdf', '0', '', '', '/data/upload/doc_con/2015-09/"+newFileName+
//		    ".pdf', '', '', '', null, null, '"+hashcode+"', '2', null, '', '0', null, null, '2');";
		   
		   String sql = "INSERT INTO `yt_file` VALUES ('"+id+"', '1', '试卷', '1', '南京航空航天大学', null, null, null, '"+coursename+"', '"+
				    name+"', '"+year+"', '5', '/data/upload/docimg/2015-09/"+newFileName+".jpg', null, 0, 'pdf', '0', '', '', '/data/upload/doc_con/2015-09/"+newFileName+
				    ".pdf', '', '', '', null, null, '"+hashcode+"', '2', null, '', '0', null, null, '2');";
		   
		   contentTosql(sql);
		   }
		   id++;
		   
		   
		   
		  }
	}
	
	
	private  static String getYear(String fileName ){
		String year = "";
		// 判断是否有－ 如果有则获取后4为数字
		// 判断是否有 20 有则获取20+2位
		//判断是否有19有则获取19+两位
		if(fileName.indexOf("-")!= -1 ){
			year = fileName.substring(fileName.indexOf("-")+1,fileName.indexOf("-")+5);
		}else if(fileName.indexOf("20")!= -1 ){
			year = fileName.substring(fileName.indexOf("20"),fileName.indexOf("20")+4);
		}else if(fileName.indexOf("19")!= -1 ){
			year = fileName.substring(fileName.indexOf("19"),fileName.indexOf("19")+4);
		}
		return year;
		
	}
	
	private static boolean copy(File fileFrom, String fileTo) {
        try {
            FileInputStream in = new java.io.FileInputStream(fileFrom);
            FileOutputStream out = new FileOutputStream(fileTo);
            byte[] bt = new byte[1024];
            int count;
            while ((count = in.read(bt)) > 0) {
                out.write(bt, 0, count);
            }
            in.close();
            out.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
	
	
	
	
	
	
	public static String toHexString(String s) 
	{ 
	String str=""; 
	for (int i=0;i<s.length();i++) 
	{ 
	int ch = (int)s.charAt(i); 
	String s4 = Integer.toHexString(ch); 
	str = str + s4; 
	} 
	return str; 
	} 
	
	public static String demoChangeStringToHex(String inputString) {
	    int changeLine = 1;
	    String s = "Convert a string to HEX/こんにちは/你好";
	    if (inputString != null) {
	        s = inputString;
	    }
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < s.length(); i++) {
	        byte[] ba = s.substring(i, i + 1).getBytes();
	        // & 0xFF for preventing minus
	        String tmpHex = Integer.toHexString(ba[0] & 0xFF);
	        sb.append(tmpHex.toUpperCase());
	        if (changeLine++ % 8 == 0) {
	        }
	        // Multiply byte according
	        if (ba.length == 2) {
	            tmpHex = Integer.toHexString(ba[1] & 0xff);
	            sb.append(tmpHex.toUpperCase());
	        }
	    }
	    return sb.toString();
	}
	
	
	public static void contentTosql(String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File("/Users/chentengteng/Downloads/sql"+filenum+".txt");
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
	
	public static void contentToerror(String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File("/Users/chentengteng/Downloads/error"+filenum+".txt");
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
	
	
	public static void contentToupdate(String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File("/Users/chentengteng/Downloads/update"+filenum+".txt");
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
	
	
	
	
	
	public static void contentToTxt(String content) {
        String str = new String(); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File("/Users/chentengteng/Downloads/demo"+filenum+".txt");
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
	
	
	
	 /**
	  * 读TXT文件内容
	  * @param fileName
	  * @return
	  */
	 public static String readTxtFile()throws Exception{
	  String result=null;
	  FileReader fileReader=null;
	  BufferedReader bufferedReader=null;
	  try{
	   fileReader=new FileReader("/Users/chentengteng/Downloads/demo"+filenum+".txt");
	   bufferedReader=new BufferedReader(fileReader);
	   try{
	    String read=null;
	    while((read=bufferedReader.readLine())!=null){
	    	String[] st = read.split("_");
	    	String id = st[0];
	    	String newName = st[2];
	    	int page = PDF.CreateImg(newFile+"/"+newName+".pdf");
	    	contentToupdate("UPDATE  yt_file SET page="+page+"  where id="+id+";");
	    }
	   }catch(Exception e){
	    e.printStackTrace();
	   }
	  }catch(Exception e){
	   e.printStackTrace();
	  }finally{
	   if(bufferedReader!=null){
	    bufferedReader.close();
	   }
	   if(fileReader!=null){
	    fileReader.close();
	   }
	  }
	  System.out.println("读取出来的文件内容是："+"\r\n"+result);
	  return result;
	 }
	
}



