package com.brandWall.util;

import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;

public class ZipDecompressing {
	public static void main(String[] args) throws Exception {  
        /*long startTime=System.currentTimeMillis();  
        try {  
            ZipInputStream Zin=new ZipInputStream(new FileInputStream(  
                    "C:\\Users\\HAN\\Desktop\\stock\\SpectreCompressed.zip"));//输入源zip路径  
            BufferedInputStream Bin=new BufferedInputStream(Zin);  
            String Parent="C:\\Users\\HAN\\Desktop"; //输出路径（文件夹目录）  
            File Fout=null;  
            ZipEntry entry;  
            try {  
                while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
                    Fout=new File(Parent,entry.getName());  
                    if(!Fout.exists()){  
                        (new File(Fout.getParent())).mkdirs();  
                    }  
                    FileOutputStream out=new FileOutputStream(Fout);  
                    BufferedOutputStream Bout=new BufferedOutputStream(out);  
                    int b;  
                    while((b=Bin.read())!=-1){  
                        Bout.write(b);  
                    }  
                    Bout.close();  
                    out.close();  
                    System.out.println(Fout+"解压成功");      
                }  
                Bin.close();  
                Zin.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }  
        long endTime=System.currentTimeMillis();  
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");  */
		unzip("D:\\img\\saas\\mc.zip","D:\\img\\saas\\mc");
    } 
	
	  /**  
	    * 解压zip格式压缩包  
	    * 对应的是ant.jar  
	    */   
	   public static void unzip(String sourceZip,String destDir) throws Exception{    
	       try{    
	           Project p = new Project();    
	           Expand e = new Expand();    
	           e.setProject(p);    
	           e.setSrc(new File(sourceZip));    
	           e.setOverwrite(false);    
	           e.setDest(new File(destDir));    
	           /*  
	           ant下的zip工具默认压缩编码为UTF-8编码，  
	           而winRAR软件压缩是用的windows默认的GBK或者GB2312编码  
	           所以解压缩时要制定编码格式  
	           */   
	           e.setEncoding("utf-8");    
	           e.execute();    
	       }catch(Exception e){    
	           throw e;    
	       }    
	   }  
  
}
