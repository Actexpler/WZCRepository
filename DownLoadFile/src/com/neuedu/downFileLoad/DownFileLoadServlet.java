package com.neuedu.downFileLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/DownFileLoadServlet")
public class DownFileLoadServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//得到要下载的文件名
		String fileName=request.getParameter("filename");
		fileName=new String(fileName.getBytes("iso8859-1"), "UTF-8");
		String fileSavaRootPath=this.getServletContext().getRealPath("/WEB-INF/upload");
		//通过文件名找到文件的所在目录
		
		//得到要下载的文件
		File file =new File(fileSavaRootPath+"\\"+fileName);
		System.out.println(file.getPath());
		//如果文件不存在
		if (!file.exists()) {
			request.setAttribute("message", "您要下载的文件已经删除");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//处理文件名
		String realname=fileName.substring(fileName.indexOf("_")+1);
		//设置响应头，控制浏览器下载改文件
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream inputStream=new FileInputStream(fileSavaRootPath+"\\"+fileName);
		 //创建输出流
		 OutputStream out = response.getOutputStream();
		//创建缓冲区
		 byte buffer[] = new byte[1024];
		 int len = 0;
		 //循环将输入流中的内容读取到缓冲区当中
		 while((len=inputStream.read(buffer))>0){
			 out.write(buffer, 0, len);
		 }
		 inputStream.close();
		 out.close();
		 }
				


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
