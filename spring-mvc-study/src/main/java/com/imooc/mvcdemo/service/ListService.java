package com.imooc.mvcdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imooc.mvcdemo.model.Message;

@Service
public interface ListService {
	
	List<Message> getList();
	
	List<Message> search(String command, String description);

}
