package org.housemart.web.views;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.housemart.util.PinyinTranslator;
import org.housemart.util.ResidenceHelper.EXCEL_COL;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ResidenceExcelView extends AbstractExcelView {
  
  @SuppressWarnings("unchecked")
  @Override
  protected void buildExcelDocument(Map<String,Object> obj,
      HSSFWorkbook workbook, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    HSSFSheet sheet = workbook.createSheet("小区信息列表");
    sheet.setDefaultColumnWidth(20);
    
    HSSFRow sheetRow = sheet.createRow(0);
    sheetRow.createCell(0).setCellValue(EXCEL_COL.ID.getVal());
    sheetRow.createCell(1).setCellValue(EXCEL_COL.REGION.getVal());
    sheetRow.createCell(2).setCellValue(EXCEL_COL.PLATE.getVal());
    sheetRow.createCell(3).setCellValue(EXCEL_COL.NAME.getVal());
    sheetRow.createCell(4).setCellValue(EXCEL_COL.YEAR.getVal());
    sheetRow.createCell(5).setCellValue(EXCEL_COL.MONTH.getVal());
    sheetRow.createCell(6).setCellValue(
        EXCEL_COL.ANNUAL_PRICE_INCREMENT.getVal());
    sheetRow.createCell(7).setCellValue(
        EXCEL_COL.ANNUAL_TURNOVER_PERCENT.getVal());
    sheetRow.createCell(8)
        .setCellValue(EXCEL_COL.ANNUAL_TURNOVER_RATE.getVal());
    sheetRow.createCell(9).setCellValue(EXCEL_COL.RENT_REVENUE.getVal());
    sheetRow.createCell(10).setCellValue(EXCEL_COL.AVERAGE_PRICE.getVal());
    sheetRow.createCell(11).setCellValue(EXCEL_COL.MIN_RENT_PRICE.getVal());
    sheetRow.createCell(12).setCellValue(EXCEL_COL.MAX_RENT_PRICE.getVal());
    sheetRow.createCell(13).setCellValue(EXCEL_COL.HEADCOUNT.getVal());
    
    List<Map<Object,Object>> list = (List<Map<Object,Object>>) obj.get("list");
    String regionName = (String) obj.get("regionName");
    HSSFDataFormat df4 = workbook.createDataFormat();
    short dataFormat4 = df4.getFormat("0.0000");
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setDataFormat(dataFormat4);
    
    for (int i = 0; i < list.size(); i++) {
      sheetRow = sheet.createRow(i + 1);
      
      HSSFCell cell;
      Map<Object,Object> map = list.get(i);
      
      sheetRow.createCell(0).setCellValue(
          Double.valueOf(map.get("residenceId").toString()));
      sheetRow.createCell(1).setCellValue(map.get("region").toString());
      sheetRow.createCell(2).setCellValue(map.get("plate").toString());
      sheetRow.createCell(3).setCellValue(map.get("residenceName").toString());
      sheetRow.createCell(4).setCellValue(
          Double.valueOf(map.get("year").toString()));
      sheetRow.createCell(5).setCellValue(
          Double.valueOf(map.get("month").toString()));
      (cell = sheetRow.createCell(6)).setCellValue(Double.valueOf(map.get(
          "annualPriceIncrement").toString()));
      cell.setCellStyle(cellStyle);
      (cell = sheetRow.createCell(7)).setCellValue(Double.valueOf(map.get(
          "annualTurnoverPercent").toString()));
      cell.setCellStyle(cellStyle);
      sheetRow.createCell(8).setCellValue(
          map.get("annualTurnoverRate").toString());
      (cell = sheetRow.createCell(9)).setCellValue(Double.valueOf(map.get(
          "rentRevenue").toString()));
      cell.setCellStyle(cellStyle);
      (cell = sheetRow.createCell(10)).setCellValue(Double.valueOf(map.get(
          "averagePrice").toString()));
      cell.setCellStyle(cellStyle);
      (cell = sheetRow.createCell(11)).setCellValue(Double.valueOf(map.get(
          "minRentPrice").toString()));
      cell.setCellStyle(cellStyle);
      (cell = sheetRow.createCell(12)).setCellValue(Double.valueOf(map.get(
          "maxRentPrice").toString()));
      cell.setCellStyle(cellStyle);
      sheetRow.createCell(13).setCellValue(
          Double.valueOf(map.get("headCount").toString()));
    }
    
    PinyinTranslator pinyin = new PinyinTranslator();
    String regionPinyinName = regionName != null ? pinyin.toPinyin(regionName)
        : "";
    String filename = "ResidenceList_" + regionPinyinName + ".xls";
    response.setContentType("application/vnd.ms-excel");
    response
        .setHeader("Content-disposition", "attachment;filename=" + filename);
    OutputStream ouputStream = response.getOutputStream();
    workbook.write(ouputStream);
    ouputStream.flush();
    ouputStream.close();
  }
}