package com.infinite.common.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * 
* @ClassName: ExportUtil
* @Description:导入导出工具类 
* @author chenliqiao
* @date 2018年7月9日 下午2:35:50
*
 */
public class ExportUtil {
    
    private static final String EXCEL_2003_FORMAT="^.+\\.(?i)(xls)$";
    private static final String EXCEL_2007_FORMAT="^.+\\.(?i)(xlsx)$";
    public static final String EXCEL_FILE_TYPE_XLS="xls";
    public static final String EXCEL_FILE_TYPE_XLSX="xlsx";
    public static final DecimalFormat df=new DecimalFormat("0");

    /**
     * 导入数据
     * startRowIndex:从第几行开始导入数据（约定从1开始）
     * 返回结果：List<Map<Integer,String>>，每个Map代表一行，key为第几列，value为值，（约定key从1开始）
     */
    public static List<Map<Integer,String>> importDatas(MultipartFile file,int startRowIndex) throws RuntimeException{
        //返回的结果集
        List<Map<Integer,String>> datas=null;
        try {
            //校验格式
            boolean isExcel2003=file.getOriginalFilename().matches(EXCEL_2003_FORMAT);
            boolean isExcel2007=file.getOriginalFilename().matches(EXCEL_2007_FORMAT);
            if (!isExcel2003 && !isExcel2007) {
                throw new Exception("上传文件格式不正确");
            }
            //获取表格对象Sheet
            Workbook wb=isExcel2003?new HSSFWorkbook(file.getInputStream()):new XSSFWorkbook(file.getInputStream());
            Sheet sheet=wb.getSheetAt(0);
            if(sheet==null){
                throw new Exception("上传表格不存在!");
            }
            //公式对象
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
            
            //返回的结果集
            datas=new ArrayList<>();
            
            //循环遍历表格获取数据
            for (Row row : sheet) {
                //从第几行开始取数,row.getRowNum()从0开始，而startRowIndex从1开始
                if(row.getRowNum()<(startRowIndex-1)){
                    continue;
                }
                Map<Integer,String> data=new HashMap<>();
                
                int cellSize=row.getLastCellNum();
                for(int cellIndex=0;cellIndex<cellSize;cellIndex++){
                    //key从1开始
                    data.put(cellIndex+1, ExportUtil.getValue(row.getCell(cellIndex),formulaEvaluator));
                }
                
                //data为空 或者 data的所有value都是空串则不返回（即该行对应的所有列都没有值）
                if(data.isEmpty()||data.values().stream().allMatch(value ->StringUtils.isEmpty(value))){
                    continue;
                }
                
                datas.add(data);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datas;
    }   
    
    /**
     * 导出单个报表(初版)
     */
    public static Workbook exportDatas(ExportConfig config){
          Assert.notNull(config, "config不能为空!");
          Assert.hasText(config.getSheetName(), "表名不能为空!");
          Assert.hasText(config.getFileType(), "文件类型不能为空!");
          Assert.notEmpty(config.getTitles(), "标题不能为空!");
          Assert.notEmpty(config.getDatas(), "列表数据不能为空!");
          boolean isExcel2003=Objects.equals(config.getFileType(), "xls");
          boolean isExcel2007=Objects.equals(config.getFileType(), "xlsx");
          Assert.isTrue(isExcel2003||isExcel2007, "上传文件格式不正确!");
          
          //获取表格对象Sheet
          Workbook wb=isExcel2003?new HSSFWorkbook():new XSSFWorkbook();
          Sheet sheet=wb.createSheet(config.getSheetName());
          // 设置表格默认列宽度为20个字节  
          sheet.setDefaultColumnWidth(20);  
          // 生成一个样式
          CellStyle style=createCellStyle(wb);

          //添加标题
          Row titleRow=sheet.createRow(0);
          Iterator<String> iterator=config.getTitles().keySet().iterator();
          int titleCellIndex=0;
          while(iterator.hasNext()){
              Cell cell=titleRow.createCell(titleCellIndex);
              cell.setCellStyle(style);
              cell.setCellValue(config.getTitles().get(iterator.next()));
              titleCellIndex++;
          }
          
          //添加数据
          int dataRowIndex=1;
          for (JSONObject data : config.getDatas()) {
            Row dataRow=sheet.createRow(dataRowIndex);
            
            Iterator<String> columnIterator=config.getTitles().keySet().iterator();
            int dataCellIndex=0;
            while(columnIterator.hasNext()){
                Cell cell=dataRow.createCell(dataCellIndex);
                cell.setCellValue(toString(data.get(columnIterator.next())));
                dataCellIndex++;
            }
            dataRowIndex++;
          }
        return wb;  
    }
    
    /**
     * 批量导出报表，即同时导出多张报表
     */
    public static Workbook batchExportDatas(List<ExportConfig> configList){
        Assert.notEmpty(configList, "导出配置列表不能为空");
        List<String> fileTypeList=configList.stream().map(ExportConfig::getFileType).distinct().collect(Collectors.toList());
        Assert.notEmpty(fileTypeList, "文件类型不能为空!");
        Assert.isTrue(fileTypeList.size()==1, "文件类型不统一!");
        String fileType=fileTypeList.get(0);
        boolean isExcel2003=Objects.equals(fileType, "xls");
        boolean isExcel2007=Objects.equals(fileType, "xlsx");
        Assert.isTrue(isExcel2003||isExcel2007, "上传文件格式不正确!");
        
        //获取Workbook对象
        Workbook workbook=isExcel2003?new HSSFWorkbook():new XSSFWorkbook();
        int sheetNumber=0;
        for (ExportConfig config : configList) {
            Sheet sheet=workbook.createSheet();
            workbook.setSheetName(sheetNumber++, config.getSheetName());
            // 设置表格默认列宽度为20个字节  
            sheet.setDefaultColumnWidth(20);  
            // 生成一个样式
            CellStyle style=createCellStyle(workbook);

            //添加标题
            Row titleRow=sheet.createRow(0);
            Iterator<String> iterator=config.getTitles().keySet().iterator();
            int titleCellIndex=0;
            while(iterator.hasNext()){
                Cell cell=titleRow.createCell(titleCellIndex);
                cell.setCellStyle(style);
                cell.setCellValue(config.getTitles().get(iterator.next()));
                titleCellIndex++;
            }
            
            //添加数据
            int dataRowIndex=1;
            for (JSONObject data : config.getDatas()) {
              Row dataRow=sheet.createRow(dataRowIndex);
              
              Iterator<String> columnIterator=config.getTitles().keySet().iterator();
              int dataCellIndex=0;
              while(columnIterator.hasNext()){
                  Cell cell=dataRow.createCell(dataCellIndex);
                  cell.setCellValue(toString(data.get(columnIterator.next())));
                  dataCellIndex++;
              }
              dataRowIndex++;
            }
        }
        return workbook;
    }
    
    /**
     * 构建CellStyle样式
     */
    private static CellStyle createCellStyle(Workbook wb){
        // 生成一个样式，并设置样式  
        CellStyle style = wb.createCellStyle();  
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);  
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(CellStyle.BORDER_THIN);  
        style.setBorderLeft(CellStyle.BORDER_THIN);  
        style.setBorderRight(CellStyle.BORDER_THIN);  
        style.setBorderTop(CellStyle.BORDER_THIN);  
        style.setAlignment(CellStyle.ALIGN_CENTER);  
        // 生成一个字体  
        Font font = wb.createFont();  
        font.setColor(HSSFColor.BLACK.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 指定当单元格内容显示不下时自动换行  
        style.setWrapText(true);  
        return style;
    }
    
    /**
     * 转换值
     */
    private static String getValue(Cell cell,FormulaEvaluator formulaEvaluator) {
        if(cell==null)
            return "";
               
        switch (cell.getCellType()) { 
            case Cell.CELL_TYPE_STRING:
                return toString(cell.getStringCellValue());
            case Cell.CELL_TYPE_NUMERIC:
                if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
                    return DateUtil.dateToString(cell.getDateCellValue(), DateUtil.yrMonDay_);
                }
                return df.format(cell.getNumericCellValue());
            case Cell.CELL_TYPE_BOOLEAN:
                return toString(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA://处理公式
                return toString(formulaEvaluator.evaluate(cell).getNumberValue());
            case Cell.CELL_TYPE_BLANK:
                return toString(cell.getStringCellValue());
            default:
                return "";
        }
    }
    
    /**
     * 转换string,不输出null
     */
    private static String toString(Object obj){
        return (obj == null) ? "" : obj.toString();
    }
    
    /**
     * 
    * @ClassName: ExportConfig
    * @Description:导出配置类 
    * @author chenliqiao
    * @date 2018年7月11日 下午5:21:15
    *
     */
    public static class ExportConfig{       
        
        /**表名**/
        private String sheetName;
        
        /**列标题（key:取值参数；value:列值）**/
        private LinkedHashMap<String, String> titles;
        
        /**列表数据**/
        private List<JSONObject> datas;
        
        /**文件类型**/
        private String fileType=ExportUtil.EXCEL_FILE_TYPE_XLS;
        
        /**文件名称**/
        private String fileName;
        
        public ExportConfig(){}
        
        public ExportConfig(String sheetName,LinkedHashMap<String, String> titles,List<JSONObject> datas){
            this.sheetName=sheetName;
            this.titles=titles;
            this.datas=datas;
        }

        public String getSheetName() {
            return sheetName;
        }

        public void setSheetName(String sheetName) {
            this.sheetName = sheetName;
        }

        public LinkedHashMap<String, String> getTitles() {
            return titles;
        }

        public void setTitles(LinkedHashMap<String, String> titles) {
            this.titles = titles;
        }

        public List<JSONObject> getDatas() {
            return datas;
        }

        public void setDatas(List<JSONObject> datas) {
            this.datas = datas;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
        
    }
}
