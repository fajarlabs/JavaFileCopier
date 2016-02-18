/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spasi.coppier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author SPASI Mini 1
 */
public class CopyFolder {
    private JTextField txt_current = null;
    private JLabel labelProses = null;
    
    public CopyFolder(JTextField txt_current) {
        this.txt_current = txt_current;
        this.labelProses = labelProses;
    }
    
    public void copyFolder(File src, File dest)
    	throws IOException{
    	
    	if(src.isDirectory()){
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		}
    		
    		//list all the directory contents
    		String files[] = src.list();
    		
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
    	    
    	}else{
            OutputStream out;
            InputStream in;
                try { //if file, then copy it
                    //Use bytes stream to support all file types
                    in = new FileInputStream(src);
                    out = new FileOutputStream(dest);
                    byte[] buffer = new byte[1024];
                    int length;
                    //copy the file content in bytes
                    while ((length = in.read(buffer)) > 0){
                        out.write(buffer, 0, length);
                    }   
                    in.close();
                    out.close();
                } catch(Exception e) {
                } finally {
                    this.txt_current.setText(null);
                    this.txt_current.setText(src.getName());
                }
    	}
    }
}
