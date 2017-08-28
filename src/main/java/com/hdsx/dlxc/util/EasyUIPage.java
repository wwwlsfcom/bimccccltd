package com.hdsx.dlxc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * easyui的分页查询类
 * @author 
 *
 * @param <T>
 */
public class EasyUIPage<T> {
	
	/**
	 * 总记录数
	 */
	private int total;
	
	/**
	 * 每页数据
	 */
	private List<T> rows = new ArrayList<T>();
	
	//以上为返回结果，以下为查询参数
	
	/**
	 * 起始页数
	 */
	private int start=1;
	
	/**
	 * 每页显示数量
	 */
	private int limit=10;
	
	/**
	 * 总页数
	 */
	private int pages;
	/**
	 * 查询参数，将查询参数放入此Map中
	 */
	private Map<String, Object> parameter = new HashMap<String, Object>();
	public EasyUIPage(){}
	public EasyUIPage(int start, int limit){
		//分页参数自动设置
		if(start < 1){
			start = 1;
		}
		if(limit < 1){
			limit = 10;
		}
		this.start = start;
		this.limit = limit;
		parameter.put("begin", (start - 1)*limit);
		parameter.put("end", start*limit);
	}
	
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public Map<String, Object> getParameter() {
		return parameter;
	}
	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}
}
