package hk.com.mtr.pcis.web.servlet;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.web.faces.util.Constant;
import hk.com.mtr.pcis.web.faces.util.FileUtil;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApplicationFormServlet extends HttpServlet {

	
	private static final long serialVersionUID = 8753957875601440550L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, java.io.FileNotFoundException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, java.io.FileNotFoundException {
		request.setCharacterEncoding(Constant.CHARSET_UTF8_VALUE);
		response.setCharacterEncoding(Constant.CHARSET_UTF8_VALUE);
		String fileName = request.getParameter("fileName");
		String mime = request.getParameter("mime");
		String fileId = request.getParameter("fileId");
		response.setHeader("Content-type", mime);		
		byte[] bytes = null;
		if (StringUtil.isNotEmpty(fileName)) {
			
			bytes = FileUtil.getResourceByte(fileName);
		} else {
			HttpSession session = request.getSession();
			if(Constant.CARD_IMAGE.equals(fileId)){
				bytes = (byte[]) session.getAttribute(Constant.APP_FORM_DATA);				
			}else if(Constant.PHOTO_IMAGE.equals(fileId)){
				bytes = (byte[]) session.getAttribute(Constant.PHOTO_IMAGE);	
			}else if(Constant.PROOF_IMAGE.equals(fileId)){
				bytes = (byte[]) session.getAttribute(Constant.PROOF_IMAGE);
			}
		}
		ServletOutputStream out = response.getOutputStream();
		if (bytes != null)
			out.write(bytes);
		out.flush();
		out.close();
	}

}
