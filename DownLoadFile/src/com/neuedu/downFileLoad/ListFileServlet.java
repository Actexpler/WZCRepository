package com.neuedu.downFileLoad;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/* 
* 项目名称：DownLoadFile 
* @author:wzc
* @date 创建时间：2017年8月19日 下午9:19:00
* @Description:列出web系统中可下载的文件
* @parameter  
*   */
public class ListFileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	//获取上传文件的目录
		String uploadFilepath=this.getServletContext().getRealPath("/WEB-INF/upload");
		//存储要下载的文件
		Map<String, String> fileNameMap =new HashMap<>();
		//递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到Map
		listfile(new File(uploadFilepath),fileNameMap);
        //将Map结合发送到listfile.jsp页面进行显示
		req.setAttribute("fileNameMap", fileNameMap);
		req.getRequestDispatcher("/listfile.jsp").forward(req, resp);
		
	}
	public void listfile(File file,Map<String , String> map){
		//如果file代表的不是一个文件，而是一个目录
		if (!file.isFile()) {
			//列出该目录下的所有文件和目录
			File files[] =file.listFiles();//进行迭代
			//遍历files[]数组
			for(File f:files){
				//递归
				listfile(f, map);				
			}
		}
		else {
			//处理文件名，去除UUID标识
			String realName=file.getName().substring(file.getName().indexOf("_")+1);
			map.put(file.getName(), realName);
		}
		
	}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doGet(req, resp);
    }
}
