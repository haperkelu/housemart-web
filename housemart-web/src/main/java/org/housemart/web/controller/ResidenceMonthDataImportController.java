/**
 * Created on 2013-11-29
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.dao.entities.ResidenceMonthDataEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.util.ResidenceHelper.EXCEL_COL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResidenceMonthDataImportController {
  private static final Logger logger = LoggerFactory
      .getLogger(ResidenceMonthDataImportController.class);
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceDao;
  
  @SuppressWarnings("rawtypes")
  @Autowired
  GenericDao residenceMonthDataDao;
  
  @RequestMapping(value = "importResidenceMonthDataById.controller")
  public String importDataByIdInit(Model model) {
    model.addAttribute("year", Calendar.getInstance().get(Calendar.YEAR));
    model.addAttribute("month", Calendar.getInstance().get(Calendar.MONTH) + 1);
    return "report/importResidenceMonthDataById";
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value = "importResidenceMonthDataByIdSubmit.controller")
  public String importDataByMatchingId(
      @RequestParam("file") MultipartFile file, Model model,
      @RequestParam("colName") String colName, @RequestParam("year") int year,
      @RequestParam("month") int month) {
    
    Workbook book = null;
    logger.info("Begin:" + file.getOriginalFilename() + ":"
        + Calendar.getInstance().getTime());
    try {
      book = Workbook.getWorkbook(file.getInputStream());
    } catch (Exception e) {
      model.addAttribute("message", "excel格式有误");
      logger.error(e.getMessage(), e);
      if (book != null) {
        book.close();
      }
      model.addAttribute("year", year);
      model.addAttribute("month", month);
      return "report/importResidenceMonthDataById";
    }
    
    Sheet sheet = book.getSheet(0);
    if (sheet == null || sheet.getRows() <= 1) {
      model.addAttribute("message", "excel数据为空");
      model.addAttribute("year", year);
      model.addAttribute("month", month);
      return "report/importResidenceMonthDataById";
    }
    
    if (StringUtils.isBlank(colName)) {
      model.addAttribute("message", "选择导入哪一列");
      model.addAttribute("year", year);
      model.addAttribute("month", month);
      return "report/importResidenceMonthDataById";
    }
    
    if (year < 0 || month < 0) {
      model.addAttribute("message", "选择年/月");
      model.addAttribute("year", year);
      model.addAttribute("month", month);
      return "report/importResidenceMonthDataById";
    }
    
    int colIndex = findColIndex(colName, sheet);
    if (colIndex == -1 && !colName.equals(EXCEL_COL.ALL.getVal())) {
      model.addAttribute("message", "excel没有找到" + colName + "列");
      model.addAttribute("year", year);
      model.addAttribute("month", month);
      return "report/importResidenceMonthDataById";
    }
    
    int total = sheet.getRows() - 1;
    int affectedCount = 0;
    
    model.addAttribute("total", total);
    for (int r = 1; r < sheet.getRows(); r++) {
      
      if (r % 10 == 0) {
        logger.info(file.getOriginalFilename() + ":" + r + ":"
            + Calendar.getInstance().getTime());
      }
      // A
      String id = sheet.getCell(0, r).getContents().toString();
      try {
        if (StringUtils.isEmpty(id)) {
          continue;
        }
        
        ResidenceEntity residence = (ResidenceEntity) residenceDao.load(
            "loadResidenceById", Integer.valueOf(id));
        if (residence != null) {
          int residenceId = residence.getResidenceId();
          String residenceName = residence.getResidenceName();
          Map<String,Object> paraMonthData = new HashMap<String,Object>();
          paraMonthData.put("residenceId", residenceId);
          paraMonthData.put("year", year);
          paraMonthData.put("month", month);
          List<ResidenceMonthDataEntity> monthDataList = residenceMonthDataDao
              .select("findMonthData", paraMonthData);
          
          EXCEL_COL col = EXCEL_COL.getValueOf(colName);
          Cell cell;
          if (CollectionUtils.isNotEmpty(monthDataList)) {
            // updateRawData
            ResidenceMonthDataEntity preData = monthDataList.get(0);
            preData.setUpdateTime(new Date());
            
            switch (col) {
              case ALL:
                int i;
                i = findColIndex(EXCEL_COL.ANNUAL_PRICE_INCREMENT.getVal(),
                    sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setAnnualPriceIncrement(Double.valueOf(cell
                      .getContents()));
                }
                i = findColIndex(EXCEL_COL.ANNUAL_TURNOVER_PERCENT.getVal(),
                    sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setAnnualTurnoverPercent(Double.valueOf(cell
                      .getContents()));
                }
                i = findColIndex(EXCEL_COL.ANNUAL_TURNOVER_RATE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null) {
                  preData.setAnnualTurnoverRate(cell.getContents());
                }
                i = findColIndex(EXCEL_COL.RENT_REVENUE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setRentRevenue(Double.valueOf(cell.getContents()));
                }
                i = findColIndex(EXCEL_COL.AVERAGE_PRICE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setAveragePrice(Double.valueOf(cell.getContents()));
                }
                i = findColIndex(EXCEL_COL.MIN_RENT_PRICE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setMinRentPrice(Double.valueOf(cell.getContents()));
                }
                i = findColIndex(EXCEL_COL.MAX_RENT_PRICE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setMaxRentPrice(Double.valueOf(cell.getContents()));
                }
                break;
              case ANNUAL_PRICE_INCREMENT:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setAnnualPriceIncrement(Double.valueOf(cell
                      .getContents()));
                }
                break;
              case ANNUAL_TURNOVER_PERCENT:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setAnnualTurnoverPercent(Double.valueOf(cell
                      .getContents()));
                }
                break;
              case ANNUAL_TURNOVER_RATE:
                if ((cell = sheet.getCell(colIndex, r)) != null) {
                  preData.setAnnualTurnoverRate(cell.getContents());
                }
                break;
              case RENT_REVENUE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setRentRevenue(Double.valueOf(cell.getContents()));
                }
                break;
              case AVERAGE_PRICE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setAveragePrice(Double.valueOf(cell.getContents()));
                }
                break;
              case MIN_RENT_PRICE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setMinRentPrice(Double.valueOf(cell.getContents()));
                }
                break;
              case MAX_RENT_PRICE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  preData.setMaxRentPrice(Double.valueOf(cell.getContents()));
                }
                break;
              case HEADCOUNT:
                // TODO
                break;
              default:
                // TODO
            }
            
            residenceMonthDataDao.update("updateResidenceMonthData", preData);
          } else {
            // insertRawData
            ResidenceMonthDataEntity newData = new ResidenceMonthDataEntity();
            newData.setYear(year);
            newData.setMonth(month);
            newData.setResidenceId(residenceId);
            newData.setResidenceName(residenceName);
            newData.setAddTime(new Date());
            newData.setUpdateTime(new Date());
            
            switch (col) {
              case ALL:
                int i;
                i = findColIndex(EXCEL_COL.ANNUAL_PRICE_INCREMENT.getVal(),
                    sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setAnnualPriceIncrement(Double.valueOf(cell
                      .getContents()));
                }
                i = findColIndex(EXCEL_COL.ANNUAL_TURNOVER_PERCENT.getVal(),
                    sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setAnnualTurnoverPercent(Double.valueOf(cell
                      .getContents()));
                }
                i = findColIndex(EXCEL_COL.ANNUAL_TURNOVER_RATE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null) {
                  newData.setAnnualTurnoverRate(cell.getContents());
                }
                i = findColIndex(EXCEL_COL.RENT_REVENUE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setRentRevenue(Double.valueOf(cell.getContents()));
                }
                i = findColIndex(EXCEL_COL.AVERAGE_PRICE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setAveragePrice(Double.valueOf(cell.getContents()));
                }
                i = findColIndex(EXCEL_COL.MIN_RENT_PRICE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setMinRentPrice(Double.valueOf(cell.getContents()));
                }
                i = findColIndex(EXCEL_COL.MAX_RENT_PRICE.getVal(), sheet);
                if (i > -1 && (cell = sheet.getCell(i, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setMaxRentPrice(Double.valueOf(cell.getContents()));
                }
                break;
              case ANNUAL_PRICE_INCREMENT:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setAnnualPriceIncrement(Double.valueOf(cell
                      .getContents()));
                }
                break;
              case ANNUAL_TURNOVER_PERCENT:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setAnnualTurnoverPercent(Double.valueOf(cell
                      .getContents()));
                }
                break;
              case ANNUAL_TURNOVER_RATE:
                if ((cell = sheet.getCell(colIndex, r)) != null) {
                  newData.setAnnualTurnoverRate(cell.getContents());
                }
                break;
              case RENT_REVENUE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setRentRevenue(Double.valueOf(cell.getContents()));
                }
                break;
              case AVERAGE_PRICE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setAveragePrice(Double.valueOf(cell.getContents()));
                }
                break;
              case MIN_RENT_PRICE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setMinRentPrice(Double.valueOf(cell.getContents()));
                }
                break;
              case MAX_RENT_PRICE:
                if ((cell = sheet.getCell(colIndex, r)) != null
                    && StringUtils.isNotBlank(cell.getContents())) {
                  newData.setMaxRentPrice(Double.valueOf(cell.getContents()));
                }
                break;
              case HEADCOUNT:
                // TODO
                break;
              default:
                // TODO
            }
            
            residenceMonthDataDao.add("addResidenceMonthData", newData);
          }
        } else {
          logger.warn(id + " residence is null");
        }
        
        affectedCount++;
      } catch (Exception e) {
        logger.error(id + ":" + e.getMessage(), e);
      }
      if (r % 10 == 0) {
        logger.info(file.getOriginalFilename() + ":" + r + ":"
            + Calendar.getInstance().getTime());
      }
    }
    logger.info("End:" + file.getOriginalFilename() + ":"
        + Calendar.getInstance().getTime());
    model.addAttribute("affectedCount", affectedCount);
    model.addAttribute("message", year + "年" + month + "月" + colName);
    model.addAttribute("year", year);
    model.addAttribute("month", month);
    return "report/importResidenceMonthDataById";
  }
  
  public int findColIndex(String colName, Sheet sheet) {
    int colIndex = -1;
    for (int c = 0; c <= sheet.getColumns() - 1; c++) {
      Cell cell;
      if ((cell = sheet.getCell(c, 0)) != null
          && cell.getContents().equals(colName)) {
        colIndex = c;
        break;
      }
    }
    return colIndex;
  }
}
