package com.lanxin.controller;

import java.util.ArrayList;

import java.util.Collection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lanxin.bean.User;
import com.lanxin.bean.UserExample;
import com.lanxin.dao.UserMapper;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserMapper userMapper;
//	get���� ��ѯ���е����ݣ������ݲ���
	@RequestMapping(value = "/selectall")
	@ResponseBody
	public List<User> selectAlluser1() {
		List<User> list = userMapper.selectByExample(new UserExample());
		return list;

	}
// ���ݵ�����
	@RequestMapping(value = "/select")
	@ResponseBody
	public User selectUser(Integer id) {
		User user1 = userMapper.selectByPrimaryKey(id);
		return user1;
	}
//���ݶ������
	@RequestMapping(value="/selectByParam")
	@ResponseBody
	public User selectByParam(String username,String password) {
		User user2=userMapper.selectByParam(username,password);
		return user2;
	}
//�ö���ʽ���ղ���
	@RequestMapping(value="/selectByPJ")
    @ResponseBody
    public User selectByPJ(User user) {
    	User user3=userMapper.selectByPJ(user);
    	return user3;
    }
	//Map��ʽ���ղ���
	@RequestMapping(value="/selectByMap")
	@ResponseBody
	public User selectByMap(String username,String password) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("username",username);
		params.put("password",password);
		User user4=userMapper.selectByMap(params);
		return user4;
	}
  //collectin��ʽ����
	@RequestMapping(value="/selectByCollection")
	@ResponseBody
	public User SelectByCollection(String username,String password) {
		Collection<String> collection=new ArrayList<String>();
		collection.add(username);
		collection.add(password);
		User user5=userMapper.selectByCollection(collection);
		return user5;
	}
//array��ʽ����
	@RequestMapping(value="/selectByArray")
	@ResponseBody
	public List<User> SelectByArray(@RequestBody Integer[] ids) {
		List<User> list2=userMapper.selectByArray(ids);
		return  list2;
	}
	@RequestMapping(value = "/post")
	@ResponseBody
	public Map<String, Object> selectByPost(User requestPeople) {
		System.out.println(requestPeople);
		Integer id = requestPeople.getId();
		// ���Ҷ�Ӧid���û���Ϣ
		User people = userMapper.selectByPrimaryKey(id);
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("code","200");
		data.put("data", people);
		return data;
	}

//��������
	 @RequestMapping(value="/insert")
	 @ResponseBody 
	 public Integer InsertUser( User requestPeople) {
	       System.out.println(requestPeople);
	       Integer i=  userMapper.insert(requestPeople);
	       return i;
    }
//delete����
	
	  @RequestMapping(value="/delete")
	  @ResponseBody 
	  public Map<String, Object> DeleteUser(@RequestBody User user) {
	         Integer id=user.getId();
	         Integer j=userMapper.deleteByPrimaryKey(id);
	         Map<String,Object> data = new HashMap<String,Object>();
	 		data.put("code","200");
	 		if(j==1) {
	 			data.put("data", "ɾ���ɹ�");
	 		}else {
	 			data.put("data", "��������������");
	 		}
	 		
	         
	       return data;
	  }
//update����
	  @RequestMapping(value="/update")
	  @ResponseBody
	  public Map<String,Object>UpdateUser(@RequestBody User newUser){
		   Integer i=userMapper.updateByPrimaryKeySelective(newUser);
		   Map<String,Object> data = new HashMap<String,Object>();
	 		data.put("code","200");
	 		if(i==1) {
	 			data.put("data", "�������ݳɹ�");
	 		}else {
	 			data.put("data", "��������ʧ��");
	 		}
	 		return data;
	  }
	 

}
