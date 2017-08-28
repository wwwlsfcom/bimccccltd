package com.hdsx.dlxc.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdsx.dlxc.demo.bean.Demo;
import com.hdsx.dlxc.demo.mapper.DemoMapper;
import com.hdsx.dlxc.demo.service.DemoService;


@Service
@Transactional
public class DemoServiceImpl implements DemoService {

	@Resource(name="demoMapper")
	private DemoMapper mapper;

	@Override
	public List<Demo> selectTime() {
		// TODO Auto-generated method stub
		return mapper.selectTime();
	}




	

}
