package com.imooc.mvcdemo.controller;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.mvcdemo.model.Message;
import com.imooc.mvcdemo.service.ListService;

@Controller
@RequestMapping("/WeChat")
public class WeChatController {
	
	//@Autowired
	private ListService listService;
	//查询消息列表
	@RequestMapping(value="/messages", method=RequestMethod.GET)
	public String viewList(Model model){
		//调用service层方法
		List<Message> messages = listService.getList();
		//获取数据库查询数据返回给servletDispatcher
		model.addAttribute("messageList", messages);
		//跳转到目标页面展示数据
		return "back/list";
	}
	//测试数据库连接、查询
	@RequestMapping(value="/testList", method=RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String testList(){
		System.out.println(listService.getList().toString());
		return listService.getList().toString();
		
	}
	//根据条件查询消息列表
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String search(@RequestParam("command") String command, 
			@RequestParam("description") String description, Model model){
		//调用service层方法
		List<Message> messages = listService.search(command, description);
		//保存查询的条件
		model.addAttribute("command", command);
		model.addAttribute("description", description);
		//获取数据库查询数据返回给servletDispatcher
		model.addAttribute("messageList", messages);
		//跳转到目标页面展示数据
		return "back/list";
	}

}
