package com.song.bootmonodb.export;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.SmartTagSetting;
import com.aspose.cells.Workbook;
import com.aspose.cells.WorkbookDesigner;
import com.aspose.cells.Worksheet;

/**
 *  使用aspose.cell导出excel
 * @author song
 *
 */
@RestController
@RequestMapping(value = "/export")
public class ExportExcel {
	
	/**
	 *  导出简易数据execl ，不用模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcel" , method = RequestMethod.GET)
	public String exportExcelTest(HttpServletRequest request , HttpServletResponse response) throws Exception {
		
		Workbook workbook = new Workbook();  //工作簿
		Worksheet sheet = workbook.getWorksheets().get(0);
		sheet.setName("测试Sheet名字");
		sheet.autoFitColumns();
		sheet.autoFitRows();
		Cells cells = sheet.getCells();
		for (int i= 0 ; i < 20 ; i++) {
			for (int j= 0 ; j < 20 ; j++) {
				Cell cell = cells.get(i , j);
				cell.putValue(i+"行，第"+j +"列");
			}
		}
		String fileName = "C:\\Users\\song\\Desktop\\aspose导出文件1.xlsx" ; 
		workbook.save(fileName);
		
		return "success";
		
	}
	/**
	 *  根据xlsx模板，导出excel
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportExcel1" , method = RequestMethod.GET)
	public String ExportExcel1(HttpServletRequest request , HttpServletResponse response) throws Exception {
		
		//模板路径
		String templetPath = "C:\\Users\\song\\Desktop\\aspose导出模板.xlsx" ;
		Workbook workbook = new Workbook(templetPath);
		Worksheet worksheet = workbook.getWorksheets().get(0);
		
		WorkbookDesigner workbookDesigner = new WorkbookDesigner();
		workbookDesigner.setWorkbook(workbook);
		SmartTagSetting smartTagSetting = worksheet.getSmartTagSetting();
		workbookDesigner.setDataSource("userName", "宋迎辉");
		workbookDesigner.setDataSource("className", "计科F1402");
		workbookDesigner.setDataSource("sex","男");
		String[] smartMarkers = workbookDesigner.getSmartMarkers();
		for (String s : smartMarkers) {
			System.out.println(s);
		}
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String,Object> map= new HashMap<>();
		map.put("userName","宋迎辉");
		map.put("className", "计科F1402") ; 
		list.add(map);
		
		workbookDesigner.process(0,false);
		
		String fileName = "C:\\Users\\song\\Desktop\\aspose导出文件2.xlsx" ; 
		workbook.save(fileName);
		return "success";
	}
	
	/**
	 *  Servlet方式 下载文件
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/download" , method = RequestMethod.GET)
	public void downLoad(HttpServletResponse response) throws IOException {
		
		String path = "C:\\Users\\song\\Desktop\\微信小程序ID.txt";
		String fileName = URLEncoder.encode("微信小程序ID.txt","utf-8");
		File file = new File (path);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
		byte[] buffer = new byte[1024] ; 
		BufferedInputStream bis = null ;
		OutputStream os = null ; 
		
		try {
			os = response.getOutputStream();
			bis= new BufferedInputStream(new FileInputStream(file));
			int i = 0 ;
			while ((i = bis.read(buffer)) != -1) {
				os.write(buffer , 0 , i);
				os.flush();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if (bis != null ) {
				try {
					bis.close();
				} catch (IOException e) {
					System.out.println("关闭bis流错误");
					e.printStackTrace();
				}
			}
		}
	}
}
