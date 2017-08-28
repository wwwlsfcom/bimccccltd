package com.hdsx.dlxc.demo.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.hdsx.dlxc.base.BaseActionSupport;
import com.hdsx.dlxc.demo.bean.Demo;
import com.hdsx.dlxc.demo.service.DemoService;
import com.hdsx.dlxc.util.EasyUIPage;
import com.hdsx.dlxc.util.JsonUtils;



@Controller
public class DemoAction extends BaseActionSupport<Demo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Demo demo=new Demo();
	@Resource(name="demoServiceImpl")
	private DemoService service;
	
	public void selectTime(){
		try {
		List<Demo> demo = service.selectTime();
//		EasyUIPage<Demo> ep = new EasyUIPage<Demo>();
//		ep.setRows(demo);
		JsonUtils.write(demo, getResponse().getWriter());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	@Override
	protected void prepareModel() throws Exception {
	
	}
	
	@Override
	public Demo getModel() {
		return demo;
	}



	public Demo getDemo() {
		return demo;
	}



	public void setDemo(Demo demo) {
		this.demo = demo;
	}


}
