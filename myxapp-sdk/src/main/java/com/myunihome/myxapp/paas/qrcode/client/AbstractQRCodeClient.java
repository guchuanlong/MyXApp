package com.myunihome.myxapp.paas.qrcode.client;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;

public abstract class AbstractQRCodeClient {

	public void writeQRCode(String filepath,String text,int width,int height) throws Exception{
		File outputFile = new File(filepath);
		Path path=outputFile.toPath();
		writeQRCode(path,text,width,height,1,"UTF-8", "png");
	}
	public void writeQRCode(String filepath,String text,int width,int height,int margin) throws Exception{
		File outputFile = new File(filepath);
		Path path=outputFile.toPath();
		writeQRCode(path,text,width,height,margin,"UTF-8", "png");
	}
	public void writeQRCode(String filepath,String text,int width,int height,int margin,String encode) throws Exception{
		File outputFile = new File(filepath);
		Path path=outputFile.toPath();
		writeQRCode(path,text,width,height,margin,encode, "png");
	}
	public void writeQRCode(String filepath,String text,int width,int height,int margin,String encode, String format) throws Exception{
		File outputFile = new File(filepath);
		Path path=outputFile.toPath();
		writeQRCode(path,text,width,height,margin,encode, format);
	}
	
	public void writeQRCode(File file,String text,int width,int height) throws Exception{
		Path path=file.toPath();
		writeQRCode(path,text,width,height,1,"UTF-8", "png");
	}
	public void writeQRCode(File file,String text,int width,int height,int margin) throws Exception{
		Path path=file.toPath();
		writeQRCode(path,text,width,height,margin,"UTF-8", "png");
	}
	public void writeQRCode(File file,String text,int width,int height,int margin,String encode) throws Exception{
		Path path=file.toPath();
		writeQRCode(path,text,width,height,margin,encode, "png");
	}
	public void writeQRCode(File file,String text,int width,int height,int margin,String encode, String format) throws Exception{
		Path path=file.toPath();
		writeQRCode(path,text,width,height,margin,encode, format);
	}
	
	public abstract void writeQRCode(Path path,String text,int width,int height,int margin,String encode, String format) throws Exception;
	
	
	
	public void writeQRCode(OutputStream os,String text,int width,int height) throws Exception{
		writeQRCode(os,text,width,height,1, "UTF-8","png");
	}
	public void writeQRCode(OutputStream os,String text,int width,int height,int margin) throws Exception{
		writeQRCode(os,text,width,height,margin, "UTF-8","png");
	}
	public void writeQRCode(OutputStream os,String text,int width,int height,int margin, String encode) throws Exception{
		writeQRCode(os,text,width,height,margin, encode,"png");
	}
	public abstract void writeQRCode(OutputStream os,String text,int width,int height,int margin,String encode, String format) throws Exception;
}
