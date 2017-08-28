package com.hdsx.dlxc.demo.mapper;

import java.util.List;

import com.hdsx.dlxc.dao.Mapper;
import com.hdsx.dlxc.demo.bean.Demo;


@Mapper
public interface DemoMapper{
	List<Demo> selectTime();
}
