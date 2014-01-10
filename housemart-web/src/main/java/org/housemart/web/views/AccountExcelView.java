package org.housemart.web.views;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.housemart.dao.entities.AccountExtEntity;
import org.housemart.dao.entities.AccountResidenceEntity;
import org.housemart.dao.entities.RegionEntity;

import org.springframework.web.servlet.view.document.AbstractExcelView;

public class AccountExcelView extends AbstractExcelView {  
	
	@Override
    protected void buildExcelDocument(Map<String, Object> obj,  
    		HSSFWorkbook  workbook, HttpServletRequest request, HttpServletResponse response)  
            throws Exception {  
    	
    	HSSFSheet sheet = workbook.createSheet("账号列表");  
        sheet.setDefaultColumnWidth((short) 20);
        
        HSSFRow sheetRow = sheet.createRow(0);
        //ID；姓名；性别；类型；身份证号；联系方式1；联系方式2；身份；所属公司；备注；工作小区；注册时间，注销时间，身份证文件(有/无)，名片文件(有/无)，
        sheetRow.createCell(0).setCellValue("编号");
        sheetRow.createCell(1).setCellValue("ID"); 
        sheetRow.createCell(2).setCellValue("姓名");
        sheetRow.createCell(3).setCellValue("性别");
        sheetRow.createCell(4).setCellValue("类型");
        sheetRow.createCell(5).setCellValue("身份证号");
        sheetRow.createCell(6).setCellValue("联系方式1");
        sheetRow.createCell(7).setCellValue("联系方式2");
        sheetRow.createCell(8).setCellValue("身份");
        sheetRow.createCell(9).setCellValue("所属公司");
        sheetRow.createCell(10).setCellValue("备注");
        sheetRow.createCell(11).setCellValue("工作小区");
        sheetRow.createCell(12).setCellValue("注册时间");
        sheetRow.createCell(13).setCellValue("注销时间");
        sheetRow.createCell(14).setCellValue("身份证文件");
        sheetRow.createCell(15).setCellValue("名片文件");
        
        List<AccountExtEntity> list = (List<AccountExtEntity>)obj.get("list");
        
        for (int i = 0; i < list.size(); i++)
        {
	        sheetRow = sheet.createRow(i + 1);  
	        AccountExtEntity account = list.get(i);
	        sheetRow.createCell(0).setCellValue(account.getId()); 
	        sheetRow.createCell(1).setCellValue(account.getLoginName() == null ? "" : account.getLoginName()); 
	        sheetRow.createCell(2).setCellValue(account.getName() == null ? "" : account.getName());
	        sheetRow.createCell(3).setCellValue(account.getGender() == null ? "" : (account.getGender() == 1 ? "男" : "女"));
	        sheetRow.createCell(4).setCellValue(account.getTypeLabel());
	        sheetRow.createCell(5).setCellValue(account.getIdentityID() == null ? "" : account.getIdentityID());
	        sheetRow.createCell(6).setCellValue(account.getContactInfo1() == null ? "" : account.getContactInfo1());
	        sheetRow.createCell(7).setCellValue(account.getContactInfo2() == null ? "" : account.getContactInfo2());
	        sheetRow.createCell(8).setCellValue(account.getPositionType() == null ? "" : account.getPositionType());
	        sheetRow.createCell(9).setCellValue(account.getCompany() == null ? "" : account.getCompany());
	        sheetRow.createCell(10).setCellValue(account.getNote() == null ? "" : account.getNote());
	        
	        if (account.getStatus() == 1)
	        {
		        String regions = "";
		        List<AccountResidenceEntity> residenceList = (List<AccountResidenceEntity>)account.findRegionsByAccount();
		        for(AccountResidenceEntity item: residenceList)
		        {
		        	regions += (regions.isEmpty() ? "" : ", ") + item.getResidenceName();
		        }
		        
		        String residences = "";
		        residenceList = (List<AccountResidenceEntity>)account.findResidencesByAccount();
		        for(AccountResidenceEntity item: residenceList)
		        {
		        	residences += (residences.isEmpty() ? "" : ", ") + item.getResidenceName();
		        }
		        
		        if (!residences.isEmpty() && !regions.isEmpty())
		        {
		        	regions += ", ";
		        }
		        
		        if ((regions + residences).length() > 16000)
		        {
		        	sheetRow.createCell(11).setCellValue(regions + residenceList.size() + "个小区");
		        }
		        else
		        {
		        	sheetRow.createCell(11).setCellValue(regions + residences);
		        }
	        }
	        else
	        {
	        	sheetRow.createCell(11).setCellValue("");
	        }
	        
	        sheetRow.createCell(12).setCellValue(account.getAddTimeLabel());
	        sheetRow.createCell(13).setCellValue(account.getRevokeTimeLabel());
	        sheetRow.createCell(14).setCellValue(StringUtils.isEmpty(account.getIdURL()) ? "无" : "有");
	        sheetRow.createCell(15).setCellValue(StringUtils.isEmpty(account.getCardURL()) ? "无" : "有");
        }
        
        String filename = "AccountList.xls";
        response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + filename);     
        OutputStream ouputStream = response.getOutputStream();     
        workbook.write(ouputStream);     
        ouputStream.flush();     
        ouputStream.close();     
    }  
}  