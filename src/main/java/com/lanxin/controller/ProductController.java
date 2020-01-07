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
	//��������
		 @RequestMapping(value="/insert")
		 @ResponseBody 
		 public Map<String,Object> InsertUser( Product requestPeople) {
			 System.out.println(requestPeople);
		       Integer i=  productMapper.insertProduct(requestPeople);
		      // redisUtil.set("product", requestPeople);
		       
		       Map<String,Object> data = new HashMap<String,Object>();
		       if(i==0) {
		    	   data.put("code","00");
					data.put("data", "�����Ʒʧ�ܣ�"); 
		       }else {
		    	    data.put("code","200");
					data.put("data", "�����Ʒ�ɹ���"); 
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
	            //��ȡ������  
	            @SuppressWarnings("resource")
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
	            //ת�룬����ļ�����������  
	            String filename = URLEncoder.encode(file.getName(),"UTF-8");  
	            String name = filename.substring(0, filename.indexOf("."));
	        
	            //�����ļ�����ͷ  
	            response.setHeader("Content-Disposition", "attachment;filename=" + name);    
	            //1.�����ļ�ContentType���ͣ��������ã����Զ��ж������ļ�����    
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
	//�ϴ��ļ�
		 @RequestMapping(value="/upload")
		 @ResponseBody
		 public Map<String,Object> UploadFile(HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException{
			 Map<String,Object> data = new HashMap<String,Object>();
			 if(!file.isEmpty()) {
				String path=request.getServletContext().getRealPath("/image/");
				File savePath=new File("D:\\upload\\image");
				System.out.println(path);
				//��ȡ�ļ���
				String fileName= file.getOriginalFilename();
				File filePath=new File(path,fileName);
				//�ж�·���Ƿ���ڣ������ھ䴴��һ��
				if(!filePath.getParentFile().exists()) {
					filePath.getParentFile().mkdirs();
				}
				file.transferTo(new File(savePath + File.separator + fileName));
				//productMapper.updateProductByName(name,"/upload/image/" + fileName);
				data.put("code",200);
				data.put("message","ͼƬ�ϴ��ɹ�");
				Map<String,Object> data0 = new HashMap<String,Object>();
				data0.put("imgurl","/upload/image/" + fileName);
				data.put("data",data0);
			}else {
				data.put("code",00);
				data.put("message","ͼƬ�ϴ�ʧ��");	
			}
			 return data;
		 }
	//��ѯ������Ʒ
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
			 data.put("message","��ѯ�ɹ�");
			 return data;
		 }
		// ������Ʒ
		 @RequestMapping(value="/update")
		 @ResponseBody
		 public Map<String,Object> UpdateProduct(@RequestBody Product product) {
			 System.out.println(product);
		       Integer i=  productMapper.updateProduct(product);
		      // redisUtil.set("product", requestPeople);
		       
		       Map<String,Object> data = new HashMap<String,Object>();
		       if(i==0) {
		    	   data.put("code",00);
					data.put("data", "���²�Ʒʧ�ܣ�"); 
		       }else {
		    	    data.put("code",200);
					data.put("data", "���²�Ʒ�ɹ���"); 
		       }
		      
		       return data;
	    }
		 
		 
}
