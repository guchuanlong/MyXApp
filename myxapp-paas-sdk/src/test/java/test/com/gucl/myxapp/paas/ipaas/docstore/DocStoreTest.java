package test.com.gucl.myxapp.paas.ipaas.docstore;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.myunihome.myxapp.paas.docstore.DocStoreFactory;
import com.myunihome.myxapp.paas.docstore.client.IDocStoreClient;

public class DocStoreTest {
	
	@Test
	public void testSaveFileByBytes(){
		IDocStoreClient client=DocStoreFactory.getDocStorageClient();
		String filepath="g:\\test.png";
		byte[] buffer=file2byte(filepath);
		String fileid=client.save(buffer, "imgtest");
		System.out.println("存储文档成功，fileid="+fileid);
		System.out.println("验证存储文档，fileid="+fileid);
		readFile(fileid);
	}
	
	@Test
	public void testSaveFile(){
		IDocStoreClient client=DocStoreFactory.getDocStorageClient();
		File img=new File("g:\\test.png");
		String fileid=client.save(img, "imgtest");
		System.out.println("存储文档成功，fileid="+fileid);
		System.out.println("验证存储文档，fileid="+fileid);
		readFile(fileid);
	}
	@Test
	public void testReadFile(){
		String fileid="DSS001$55f0fada702e8837e4fc97a3";
		readFile(fileid);
	}
	
	public void readFile(String fileid){
		IDocStoreClient client=DocStoreFactory.getDocStorageClient();
		byte[] imgbytes=client.read(fileid);
		System.out.println("imgbytes="+imgbytes);
	}
	
	public static byte[] file2byte(String filePath)
	{
		byte[] buffer = null;
		try
		{
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return buffer;
	}

	public static void byte2file(byte[] buf, String filePath, String fileName)
	{
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try
		{
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory())
			{
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (bos != null)
			{
				try
				{
					bos.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (fos != null)
			{
				try
				{
					fos.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
