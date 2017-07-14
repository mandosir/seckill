package com.imooc.mvcdemo.dao;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.imooc.mvcdemo.model.Message;

/*
 * 和message表相关的数据库操作
 */
@Repository
public class MessageDao {
//    @Autowired 
//    private JdbcTemplate jdbcTemplate;   
//    /**
//     * 获取列表全部信息
//     * @return
//     */
//    public List<Message> getList(){
//    	
//    	String sql="select ID as messageId,COMMAND as userCommand,DESCRIPTION as descr,CONTENT as content from message";
//    	RowMapper<Message> rowMapper=new BeanPropertyRowMapper<Message>(Message.class);
//    	List<Message> messages = jdbcTemplate.query(sql, rowMapper);
//    	return messages;
//    }
//    /**
//     * 获取条件查询结果
//     * @param sql
//     * @param paramList
//     * @return
//     */
//    public List<Message> search(String sql, List<String> paramList) {
//     
//    	Object[] params = new Object[paramList.size()];
//    	for (int i = 0; i < params.length; i++) {
//			params[i] = paramList.get(i);
//		}
//    	RowMapper<Message> rowMapper=new BeanPropertyRowMapper<Message>(Message.class);
//    	List<Message> messages = jdbcTemplate.query(sql, params, rowMapper);
//    	return messages;
//    } 
}
