package com.neuedu.downFileLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import sun.misc.BASE64Encoder;
import sun.nio.ch.IOUtil;

@WebServlet("/MyFileDownload")
public class MyFileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取文件路径
		ServletContext servletContext = request.getServletContext();
		String fileName="风吹麦浪.mp3";
		String realPath = servletContext.getRealPath("/WEB-INF/"+fileName);	
		//获取文件名
		File file=new File(realPath);
		String mimeType = servletContext.getMimeType(fileName);
		 // 请求客户端操作系统的信息  
	    final String userAgent = request.getHeader("USER-AGENT");  
	    if(userAgent.contains("Firefox")){
			//是火狐浏览器，使用BASE64编码
			fileName = "=?utf-8?b?"+new BASE64Encoder().encode(fileName.getBytes("utf-8"))+"?=";
		}else{
			//给文件名进行URL编码
			//URLEncoder.encode()需要两个参数，第一个参数时要编码的字符串，第二个是编码所采用的字符集
			fileName = URLEncoder.encode(fileName, "utf-8");
		}
		//输入流
		InputStream in=new FileInputStream(file);
		//设置响应头  
		 response.setContentType(mimeType);
		 response.addHeader("Content-Disposition", "attachment; filename=" + fileName);  
		    
//		//输出流
//		PrintWriter out=response.getWriter();
//		IOUtils.copy(in, out);
		    byte[] b = new byte[100];  
		    int len;  
		    while ((len = in.read(b)) > 0) {  
		        response.getOutputStream().write(b, 0, len);  
		    }  
		    in.close();  
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
