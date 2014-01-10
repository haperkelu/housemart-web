/**
 * 
 */
package org.housemart.web.handler;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.UserAccessEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.rpc.stubs.log.UserAccessLoggerServiceStub;
import org.housemart.service.AuthenticationService;
import org.housemart.service.UserAccessLoggerService;
import org.housemart.web.concurrent.TheadServiceProvider;
import org.housemart.web.context.HouseMartContext;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author Administrator
 *
 */
public class HandlerInterceptor  extends HandlerInterceptorAdapter {
	
	protected static final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);	
	private AuthenticationService authenticationService = SpringContextHolder.getBean("authenticationService");

	private static List<String> whiteList;
	static{
	  whiteList = new ArrayList<String>();
	  whiteList.add("isBrokerViewContact.controller");
	  whiteList.add("client/appFile/download.controller");
	  whiteList.add("brokerApply.controller");
	  whiteList.add("brokerApplySave.controller");
	  whiteList.add("ajax/deleteHouseInt.controller");
	  whiteList.add("ajax/applyHouseInt.controller");
	  whiteList.add("ajax/deactiveHouseInt.controller");
	  whiteList.add("ajax/housePicUploadInt.controller");
	  whiteList.add("ajax/housePicDeleteInt.controller");
	}
	
	@SuppressWarnings("rawtypes")
	UserAccessLoggerServiceStub userAccessLoggerService = (UserAccessLoggerServiceStub)SpringContextHolder.getBean("userAccessLoggerService");
	
	//before the actual handler will be executed
	@Override
	public boolean preHandle(final HttpServletRequest request, 
		HttpServletResponse response, Object handler)
	    throws Exception {
		
		final String URL = request.getRequestURL().toString();
		TheadServiceProvider.getThreadService().execute(new Runnable(){

			public void run() {
				try {
					UserAccessEntity entity = new UserAccessEntity();
					entity.setBizTag("Page View");
					entity.setUserId(HouseMartContext.getCurrentUserId());
					entity.setUrl(URL);
					entity.setAccessText("access page ip:" + request.getRemoteAddr());
					userAccessLoggerService.access("Page View", HouseMartContext.getCurrentUserId(), URL, "access page ip:" + request.getRemoteAddr());
				} catch (Exception e) {
					logger.error("User Access Error", e);
				}				
			}				
		});

		//首页
		String serverName = request.getServerName();
    if (URL.endsWith("/")
        && serverName != null
        && !(serverName.contains("broker.housemart.cn") || serverName
            .contains("test.housemart.cn"))) {
      return true;
    }
		
		//白名单
		for(String whiteListItem : whiteList){
		  if(URL.endsWith(whiteListItem)){
		    return true;
		  }
		}
		
		//request.setAttribute("login", true);		
		//登陆则返回原有页面
		if(isLogin(request)){			
			return true;
		}
//		
		//登陆页面，避免循环调用
		if(request.getRequestURL().toString().contains("login") 
				|| request.getRequestURL().toString().contains("loginSubmit")
				|| request.getRequestURL().toString().contains("logout")
				){
			return true;
		}
		
//		//跳转登陆页面
		response.sendRedirect("/login.controller");
		return true;
	}

	/**
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		super.postHandle(request, response, handler, modelAndView);
		modelAndView.addObject("isAdmin", HouseMartContext.getCurrentUserId() == 0);
		modelAndView.addObject("isManager", HouseMartContext.isManager());
		modelAndView.addObject("type", HouseMartContext.getType());
		
		String sysMsg = HouseMartContext.getSysMsg();
		String newSysMsg = request.getParameter("sysMsg");
		
		if (newSysMsg != null && newSysMsg != "")
		{
			sysMsg = URLDecoder.decode(newSysMsg, "UTF-8");
		}
		
		modelAndView.addObject("sysMsg", sysMsg);
	}






	/**
	 * 
	 * @return
	 */
  private boolean isLogin(HttpServletRequest request) {
    
    Cookie[] cookies = request.getCookies();
    if (!ArrayUtils.isEmpty(cookies)) {
      for (Cookie item : cookies) {
        if (item.getName().equals("user")) {
          if (authenticationService.isLoggin(item.getValue())) {
            return true;
          }
        }
      }
    }
    String userCookiePara = request.getParameter("user_cookie");
    if (StringUtils.isNotBlank(userCookiePara)) {
      if (authenticationService.isLoggin(userCookiePara)) {
        return true;
      }
    }
    
    return false;
  }
		
}
