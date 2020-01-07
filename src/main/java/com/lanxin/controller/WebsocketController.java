package com.lanxin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.lanxin.socket.SpringWebSocketHandler;

@Controller
@RequestMapping(value = "/websocket")
public class WebsocketController {
	 
	/*
	 * @Bean//���ע����Spring�����ó�Bean public SpringWebSocketHandler infoHandler() {
	 * 
	 * return new SpringWebSocketHandler(); }
	 */
	 @RequestMapping("/login")
	 @ResponseBody
	    public  Map<String,Object> login(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        String username = request.getParameter("username");
	        System.out.println(username+"��¼");
	        if(request.getSession(false) != null) {
                System.out.println("ÿ�ε�¼�ɹ��ı�SessionID��");
                //��ȫ������ÿ�ε�½�ɹ��ı� Session ID��ԭ��ԭ����sessionע�������������Խ����µ�session����
            }
	        HttpSession session = request.getSession();
	        Map<String,Object> data = new HashMap<String,Object>();
	        data.put("code",200);
			data.put("data", "��¼�ɹ���"); 
			session.setAttribute("SESSION_USERNAME", username); //һ��ֱ�ӱ���userʵ��
	        return data;
	    }
	 
	/*
	 * @RequestMapping("/send")
	 * 
	 * @ResponseBody public String send(HttpServletRequest request) { String
	 * username = request.getParameter("username");
	 * infoHandler().sendMessageToUser(username, new TextMessage("��ã����ԣ�������"));
	 * return null; }
	 */

	

}
