package com.lanxin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lanxin.bean.Product;
import com.lanxin.dao.ProductMapper;
import com.lanxin.service.ProductServiceImpl;
import com.lanxin.util.RedisUtil;




@Controller
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductServiceImpl productService;
	@Autowired
	private RedisUtil redisUtil;
	//插入数据
		 @RequestMapping(value="/insert")
		 @ResponseBody 
		 public Map<String,Object> InsertUser( Product requestPeople) {
			 System.out.println(requestPeople);
		       Integer i=  productMapper.insertProduct(requestPeople);
		      // redisUtil.set("product", requestPeople);
		       
		       Map<String,Object> data = new HashMap<String,Object>();
		       if(i==0) {
		    	   data.put("code","00");
					data.put("data", "插入产品失败！"); 
		       }else {
		    	    data.put("code","200");
					data.put("data", "插入产品成功！"); 
		       }
		      
		       return data;
	    }
		 
		 
		 @RequestMapping(value="/download")
		 @ResponseBody
		 public void  downloadFile(HttpServletRequest request,@RequestBody HashMap<?, ?> param,HttpServletResponse response) throws IOException{
			   String imgurl = (String)param.get("imgurl");
			   // String path = request.getSession().getServletContext().getRealPath("\\image")+"\\"+filename;  
			   // String path="D:\\upload\\image\\"+filename;
			    File file=new File("D:"+imgurl);
	            //获取输入流  
	            @SuppressWarnings("resource")
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	            //转码，免得文件名中文乱码  
	            String filename = URLEncoder.encode(file.getName(),"UTF-8");  
	            String name = filename.substring(0, filename.indexOf("."));
	        
	            //设置文件下载头  
	            response.setHeader("Content-Disposition", "attachment;filename=" + name);    
	            //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
	            response.setContentType("application/x-msdownload");   
	            response.setCharacterEncoding("UTF-8");
	            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
	            int len = 0;  
	            byte[] buffer = new byte[1024];
	            while((len = inputStream.read(buffer)) != -1){  
	                out.write(buffer,0,len);  
	                out.flush();  
	            }  
	            out.close();  
		 }
	//上传文件
		 @RequestMapping(value="/upload")
		 @ResponseBody
		 public Map<String,Object> UploadFile(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException{
			 Map<String,Object> data = new HashMap<String,Object>();
			 if(!file.isEmpty()) {
				String path=request.getServletContext().getRealPath("/image/");
				File savePath=new File("D:\\upload\\image");
				System.out.println(path);
				//获取文件名
				String fileName= file.getOriginalFilename();
				File filePath=new File(path,fileName);
				//判断路径是否存在，不存在句创建一个
				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}
				file.transferTo(new File(savePath + File.separator + fileName));
				//productMapper.updateProductByName(name,"/upload/image/" + fileName);
				data.put("code",200);
				data.put("message","图片上传成功");
				Map<String,Object> data0 = new HashMap<String,Object>();
				data0.put("imgurl","/upload/image/" + fileName);
				data.put("data",data0);
			}else {
				data.put("code",00);
				data.put("message","图片上传失败");	
			}
			 return data;
		 }
	//查询所有商品
		 @RequestMapping(value="/allproduct")
		 @ResponseBody
		 public Map<String,Object> SelectAllProduct( @RequestBody HashMap<?, ?>  param){
			 Integer page = (Integer)param.get("page");
			 Integer count = (Integer)param.get("current");
			 List<Product> list = productService.selectAllProduct(page-1,count);
			 Integer total=productService.selectAllCount();
			 Map<String,Object> data = new HashMap<String,Object>();
			 Map<String,Object> listObject=new HashMap<String,Object>();
			 listObject.put("list",list);
			 listObject.put("page",page);
			 listObject.put("total",total);
			 data.put("code",200);
			 data.put("data",listObject);
			 data.put("message","查询成功");
			 return data;
		 }
		// 更新商品
		 @RequestMapping(value="/update")
		 @ResponseBody
		 public Map<String,Object> UpdateProduct(@RequestBody Product product) {
			 System.out.println(product);
		       Integer i=  productMapper.updateProduct(product);
		      // redisUtil.set("product", requestPeople);
		       
		       Map<String,Object> data = new HashMap<String,Object>();
		       if(i==0) {
		    	   data.put("code",00);
					data.put("data", "更新产品失败！"); 
		       }else {
		    	    data.put("code",200);
					data.put("data", "更新产品成功！"); 
		       }
		      
		       return data;
	    }
		 
		 
}
