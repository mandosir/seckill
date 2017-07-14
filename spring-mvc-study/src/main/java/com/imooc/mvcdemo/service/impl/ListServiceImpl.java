package com.imooc.mvcdemo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.mvcdemo.dao.MessageDao;
import com.imooc.mvcdemo.model.Message;
import com.imooc.mvcdemo.service.ListService;

//@Service("listService")
//public class ListServiceImpl implements ListService {
	
//	@Autowired
//	private MessageDao listDao;
//	/**
//	 * 获取列表信息功能
//	 */
//	@Override
//	public List<Message> getList() {
//		// TODO Auto-generated method stub
//		return listDao.getList();
//	}
//	/**
//	 * 按条件查询功能
//	 */
//	@Override
//	public List<Message> search(String command, String description) {
//		// TODO Auto-generated method stub
//    	StringBuilder sql = new StringBuilder("select ID as messageId,COMMAND as userCommand,DESCRIPTION as descr,CONTENT as content from message where 1=1");
//    	List<String> paramList = new ArrayList<String>();
//    	if (command != null&&!"".equals(command.trim())) {
//			sql.append(" and COMMAND=?");
//			paramList.add(command);
//		}
//    	if (description != null&&!"".equals(description.trim())) {
//			sql.append(" and DESCRIPTION like '%' ? '%'");
//			paramList.add(description);
//		}
//		return listDao.search(sql.toString(), paramList);
//	}

//}
