package com.imooc.mvcdemo.model;

public class Message {
	
	//消息id
	private Integer messageId;
	
	//指令
	private String userCommand;
	
	//描述
	private String descr;
	
	//内容
	private String content;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getUserCommand() {
		return userCommand;
	}

	public void setUserCommand(String userCommand) {
		this.userCommand = userCommand;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString(){
		return "Message{" + "messageId='" + messageId + '\'' + ", userCommand='" + userCommand + '\'' + ", descr='" + descr + '\'' + ", content='" + content + '\'' + '}';
	}

}
