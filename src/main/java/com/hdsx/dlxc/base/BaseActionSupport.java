package com.hdsx.dlxc.base;

import java.lang.reflect.ParameterizedType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class BaseActionSupport<T> extends ActionSupport implements ModelDriven<T>, Preparable{

	
	private static final long serialVersionUID = 5432160000580445020L;

	protected T model;
	
	public BaseActionSupport() {
		try {
			// 得到model的类型信息
			ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
			Class clazz = (Class) pt.getActualTypeArguments()[0];
			// 通过反射生成model的实例
			model = (T) clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public T getModel() {
		return model;
	}

	
	public void prepare() throws Exception {
		prepareModel();
	}

	protected abstract void prepareModel() throws Exception;

	
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	//解决中文乱码问题
	protected HttpServletResponse getResponse() {
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		return response;
	}

	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
}
