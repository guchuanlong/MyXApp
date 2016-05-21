package com.myunihome.myxapp.paas.excel.factory;

import com.myunihome.myxapp.paas.excel.client.AbstractExcelHelper;
import com.myunihome.myxapp.paas.excel.client.impl.HssfExcelHelper;
import com.myunihome.myxapp.paas.excel.client.impl.JxlExcelHelper;
import com.myunihome.myxapp.paas.excel.client.impl.XssfExcelHelper;

public class ExcelFactory {
	/**
	 * Excel2003 .xls文件操作工具类--JXL 
	 * @return
	 * @author gucl
	 */
	public static AbstractExcelHelper getJxlExcelHelper(){
		return JxlExcelHelper.getInstance();
	}
	/**
	 * Excel2003 .xls文件操作工具类--POI
	 * @return
	 * @author gucl
	 */
	public static AbstractExcelHelper getHssfExcelHelper(){
		return HssfExcelHelper.getInstance();
	}
	/**
	 * Excel2007 .xlsx文件操作工具类--POI
	 * @return
	 * @author gucl
	 */
	public static AbstractExcelHelper getXssfExcelHelper(){
		return XssfExcelHelper.getInstance();
	}
}
