package org.housemart.web.views;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.AccountResidenceEntity;
import org.housemart.dao.entities.RegionEntity;
import org.housemart.dao.entities.ResidenceExtEntity;

import org.springframework.web.servlet.view.document.AbstractExcelView;

public class AccountHouseExcelView extends AbstractExcelView {  
	
	@Override
    protected void buildExcelDocument(Map<String, Object> obj,  
    		HSSFWorkbook  workbook, HttpServletRequest request, HttpServletResponse response)  
            throws Exception {  
    	
    	HSSFSheet sheet = workbook.createSheet("登盘统计列表");  
        sheet.setDefaultColumnWidth((short) 20);
        
        HSSFRow sheetRow = sheet.createRow(0);
        //工号, 姓名, 联系方式, 注册时间, 注销时间, 所属公司, 小区名, 区域, 板块, 实际登录的房源数, 内网盘源数
        sheetRow.createCell(0).setCellValue("工号");
        sheetRow.createCell(1).setCellValue("姓名"); 
        sheetRow.createCell(2).setCellValue("联系方式");
        sheetRow.createCell(3).setCellValue("注册时间");
        sheetRow.createCell(4).setCellValue("注销时间");
        sheetRow.createCell(5).setCellValue("所属公司");
        sheetRow.createCell(6).setCellValue("小区名");
        sheetRow.createCell(7).setCellValue("区域");
        sheetRow.createCell(8).setCellValue("板块");
        sheetRow.createCell(9).setCellValue("实际登盘房源数");
        sheetRow.createCell(10).setCellValue("内网盘源数");
        sheetRow.createCell(11).setCellValue("经纪人数");
        sheetRow.createCell(12).setCellValue("经纪人");
        
        
        List<Map<Object, Object>> list = (List<Map<Object, Object>>)obj.get("list");
        
        int lastID = -1;
        
        HSSFFont font = workbook.createFont();
        HSSFCellStyle style = workbook.createCellStyle();
	    style.setFont(font);
	    font.setColor(HSSFFont.COLOR_RED);
        
        for (int i = 0; i < list.size(); i++)
        {
	        sheetRow = sheet.createRow(i + 1);  
	        Map<Object, Object> map = list.get(i);
	        
	        int accountId = Integer.parseInt(map.get("accountId").toString());
	        
	        sheetRow.createCell(0).setCellValue(map.get("accountId").toString()); 
	        sheetRow.createCell(1).setCellValue(map.get("accountName").toString());
	        sheetRow.createCell(2).setCellValue(map.get("accountContactInfo").toString());
	        
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			if (map.get("accountAddTime") != null)
			{
				sheetRow.createCell(3).setCellValue(df.format(map.get("accountAddTime")));
			}
			else
			{
				sheetRow.createCell(3).setCellValue("");
			}
			
			if (map.get("accountRevokeTime") != null)
			{
				sheetRow.createCell(4).setCellValue(df.format(map.get("accountRevokeTime")));
			}
			else
			{
				sheetRow.createCell(4).setCellValue("");
			}
			
			sheetRow.createCell(5).setCellValue(map.get("accountCompany").toString());
			lastID = accountId;
	        
			HSSFCell residenceCell = sheetRow.createCell(6);
			residenceCell.setCellValue(map.get("residenceName").toString());
			
	        sheetRow.createCell(7).setCellValue(map.get("regionName").toString());
	        sheetRow.createCell(8).setCellValue(map.get("plateName").toString());
	        sheetRow.createCell(9).setCellValue(map.get("houseCount").toString());
	        sheetRow.createCell(10).setCellValue(map.get("residenceHouseCount").toString());
	        
	        HSSFCell brokerCountCell = sheetRow.createCell(11);
	        brokerCountCell.setCellValue(map.get("brokerCount").toString());
	        
	        sheetRow.createCell(12).setCellValue(map.get("brokers").toString());
	        
	        if ((Integer)map.get("brokerCount") > 5)
	        {
	 	        brokerCountCell.setCellStyle(style);
	 	        residenceCell.setCellStyle(style);
	        }
        }
        
        String houseType = obj.get("houseType").toString();
        
        String filename = "AccountList_" + houseType + ".xls";
        response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + filename);     
        OutputStream ouputStream = response.getOutputStream();     
        workbook.write(ouputStream);     
        ouputStream.flush();     
        ouputStream.close();     
    }  
}  