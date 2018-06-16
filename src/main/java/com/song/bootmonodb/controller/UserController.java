package com.song.bootmonodb.controller;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.song.bootmonodb.bean.User;

@RestController
public class UserController {

	@Autowired
	private MongoTemplate mongoTemplate ; 
	
	@RequestMapping(value = "/save")
	public void save() {
		User user = new User() ;
		user.setId("1dasdsads0");
		user.setName("宋小辉");
		user.setSex("男");
		user.setText("文本文档文档");
		user.setAge(20);
		
		mongoTemplate.insert(user, "test");
		
	}
	@RequestMapping(value = "/getdata" , method = RequestMethod.GET)
	public Object getData() {
		
		List<String> list= new ArrayList<String>();
		FindIterable<Document> find = mongoTemplate.getCollection("test").find();
		for (Document s : find ) {
			list.add(s.toJson()) ;
		}
		return list ;
	}	
	
	
	
}
