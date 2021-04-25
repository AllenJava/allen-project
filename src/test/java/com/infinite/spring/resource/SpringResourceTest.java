package com.infinite.spring.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;

/**
 * spring框架提供的资源加载接口Resource
 * @author allen
 *
 */
public class SpringResourceTest {

	public static void main(String[] args) {
		try {
		
			//使用系统文件路径方式加载文件
//			String filePath="C:/java/git/project/allen-project/src/main/resources/application-dev.properties";
//			WritableResource wr=new PathResource(filePath);
			//使用WritableResource接口写资源文件
//	        OutputStream os=wr.getOutputStream();
//	        os.write("SpringResourceTest".getBytes());
//	        os.close();
	        
			//使用类路径方式加载文件
	        Resource resource=new ClassPathResource("mybatis/pagehelper/mybatis-page-helper.xml");
	        System.out.println(resource.getFilename());
	        System.out.println(resource.getDescription());
	        
	        //使用Resource读取资源文件
	        ByteArrayOutputStream bos=new ByteArrayOutputStream();
	        InputStream is=resource.getInputStream();
	        int i;
	        while((i=is.read())!=-1){
	        	bos.write(i);
	        }
	        System.out.println(bos.toString());
	        bos.close();
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
