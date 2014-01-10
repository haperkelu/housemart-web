/**
 * 
 */
package org.housemart.web.controller;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.housemart.web.context.SpringContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pai.li
 *
 */
@Controller
public class AjaxController {
	
	//private Cache ehCache 
	private Cache ehCache = SpringContextHolder.getBean("ehCache");
	
	@RequestMapping(value = "ajax/menu/get.do")
	public ModelAndView getMenu(@RequestParam int menuId, Model model) {
		
		Element element = ehCache.get(String.valueOf(menuId));
		/**
		Menu jsonObj = null;
		if(element == null){
			jsonObj = new Menu();
			jsonObj.setMenuId(menuId);
			List<MenuItem> list = new ArrayList<MenuItem>();
			MenuItem item1 = new MenuItem();
			item1.setName("item1");
			list.add(item1);
			jsonObj.setChilds(list);
			Element tempObj = new Element(String.valueOf(menuId), jsonObj);
			ehCache.put(tempObj);
		}else{
			jsonObj = (Menu)element.getObjectValue();
		}
		**/		
		return new ModelAndView("jsonView", "json", new Object());
		
	}
	
}
