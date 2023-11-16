package com.kbhc.www.Controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kbhc.www.Service.DataService;
import com.kbhc.www.VO.DBServerInfoVO;
import com.kbhc.www.VO.DatabaseVO;
import com.kbhc.www.VO.UserVO;

import lombok.extern.slf4j.Slf4j;

/**
 * [ 템플릿 설명 ]
 * - 해당 파일은 Restful API 형태로 URL 관리하는 파일입니다
 * - 뷰 자체만을 리턴해주거나 API 호출에 대한 처리를 해줍니다.
 * [ Annotation 설명 ]
 * - @Slf4j : 로그를 위해 사용하는 Annotation
 * - @RestController: Restful API 구조에서 JSON 타입으로 데이터를 반환 받기 위해 사용하는 Annotation
 * - @Controller: Spring MVC 패턴을 사용하기 위한 Annotation
 * - @RequestMapper: Controller URL Default
 *
 * @author lee
 * @since 2022.09.30
 */


@Slf4j
@RequestMapping("/")
@Controller
public class MainController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 필드 주입이 아닌 생성자 주입형태로 사용합니다. '생성자 주입 형태'로 사용합니다.
	private final DataService dataService;
	public MainController(DataService dataService) {
		super();
		this.dataService = dataService;
	}
	
	@GetMapping(value = {"index", ""})
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("index");
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("user") == null) {
			modelAndView.setViewName("redirect:login");
			return modelAndView;
//			return new ModelAndView("login");
		}
		
		modelAndView.addObject("list", dataService.selectEcxeption());
		modelAndView.addObject("count", dataService.selectCountData());
		
		/* Database MASTER, SLAVE1, SLAVE2 구분 */
		DBServerInfoVO serverInfo = dataService.showServerID();
		String server_name="";
		if (serverInfo.getValue().equals("1028961128")||serverInfo.getValue().equals("36407449")) {
			server_name = "MASTER";
		}
		if (serverInfo.getValue().equals("1343120162")) {
			server_name = "SLAVE1";
		}
		if (serverInfo.getValue().equals("4026358385")) {
			server_name = "SLAVE2";
		}
		System.out.println(">>>>>>"+server_name.toString());
		modelAndView.addObject("server_name", server_name);
		
		/* CPU 사용량 */
//		modelAndView.addObject("pcMonitorVO", pcMonitorVO);
		
		/* read replica 현황 */
		List<DatabaseVO> dbList = dataService.selectAllDatabase();
		modelAndView.addObject("dbList", dbList);
		
		
		return modelAndView;
	}
	
	
	@GetMapping("login")
	public ModelAndView getLogin() {
		ModelAndView modelAndView = new ModelAndView("login");
		System.out.println("Login Page");
		
		 try{
			modelAndView.addObject("hostname", InetAddress.getLocalHost().getHostName());
			modelAndView.addObject("ip", InetAddress.getLocalHost().getHostAddress());
			System.out.println( InetAddress.getLocalHost().getHostName() );
			System.out.println( InetAddress.getLocalHost().getHostAddress() );
		}
		catch( UnknownHostException e ){
		  e.printStackTrace();
		}
		
		/* CPU 사용량 */
		return modelAndView;
	}
	
	@PostMapping("login")
	public String postLogin(HttpServletRequest request, HttpServletResponse response) {
		
		UserVO user = new UserVO();

		user.setEmail(request.getParameter("inputEmail"));
		user.setPassword(request.getParameter("inputPassword"));
		
		ModelAndView modelAndView = new ModelAndView("index");
		
		/* session 등록 */
		// 세션을 생성하기 전에 기존의 세션 파기
		request.getSession().invalidate();
		HttpSession session = request.getSession(true);  // Session이 없으면 생성
		// 세션에 userId를 넣어줌
		session.setAttribute("user", user);
		session.setMaxInactiveInterval(1800); // Session이 30분동안 유지
//		return modelAndView;
		return "redirect:/index";
	}
	
	@PostMapping("deleteSession")
	@ResponseBody
	public boolean deleteSession(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@PostMapping("deleteException")
	@ResponseBody
	public String deleteException(@RequestBody String msg) {
		String jsonData = URLDecoder.decode(msg, StandardCharsets.UTF_8);
		System.out.println(jsonData);
		if(dataService.deleteExceptionData().equals("OK")) {
			return "data";
		}else {
			return "error";
		}
	}
	
	@PostMapping("deleteAllData")
	@ResponseBody
	public String deleteAllData(@RequestBody String msg) {
		String jsonData = URLDecoder.decode(msg, StandardCharsets.UTF_8);
		System.out.println(jsonData);
		if(dataService.deleteAllData().equals("OK")) {
			return "data";
		}else {
			return "error";
		}
	}
	
}
