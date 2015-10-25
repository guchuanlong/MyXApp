package com.myunihome.myxapp.base.dubbo.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象.<br>
 * 提供给外部系统的分页Bean<br>
 * @param <T> 结果集的数据类型
 * Date: 2015年10月7日 <br>
 * Copyright (c) 2015 myunihome.com <br>
 * @author gucl
 */
public class PageInfo<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer pageNo = 1;// 请求查询的页码

	private Integer pageSize;// 每页显示条数

	private List<T> result;// 结果集

	private int count = 0;// 总条数

	private int pageCount;//总页数

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * 获取开始行
	 * 
	 * @return
	 * @author gucl
	 */
	public int getStartRowIndex() {
		return (this.getPageNo() - 1) * this.getPageSize();
	}

	/**
	 * 获取结束行
	 * 
	 * @return
	 * @author gucl
	 */
	public int getEndRowIndex() {
		return this.getPageNo() * this.getPageSize();
	}

	/**
	 * 获取最大页数
	 * 
	 * @return
	 * @author gucl
	 */
	public int getPageCount() {
		int quotient = this.getCount() / this.getPageSize();
		int remainder = this.getCount() % this.getPageSize();
		pageCount = quotient;
		if (remainder > 0) {
			pageCount += 1;
		}
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

}
