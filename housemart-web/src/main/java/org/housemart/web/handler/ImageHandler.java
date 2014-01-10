/** 
 * @Title: ImageHandler.java
 * @Package org.housemart.web.handler
 * @Description: TODO
 * @author Pie.Li
 * @date 2013-2-20 下午5:31:26
 * @version V1.0 
 */
package org.housemart.web.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ImageHandler
 * @Description: TODO
 * @date 2013-2-20 下午5:31:26
 * 
 */
public class ImageHandler extends HttpServlet {
	
	/** **/
	private static final Pattern pattern = Pattern.compile("/upload/([^\\.]+)\\.([a-zA-Z]+)");

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -9000999404827746231L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		final String URL = req.getRequestURI();
		if(StringUtils.isEmpty(URL)){
			return;
		}
		
		Matcher matcher = pattern.matcher(URL);
		while(matcher.find()){
			String filePath = matcher.group(1);
			filePath = java.net.URLDecoder.decode(filePath, "UTF-8");
			String fileSuffix = matcher.group(2);

			BufferedImage image = ImageIO.read(new File("/mnt/upload/" + filePath + "." + fileSuffix));
			OutputStream outputStream = resp.getOutputStream();
			resp.setContentType("image/" + fileSuffix);
			ImageIO.write(image, fileSuffix, outputStream);
		}
		
	}

}