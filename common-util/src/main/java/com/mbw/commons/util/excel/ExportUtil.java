package com.mbw.commons.util.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.mbw.commons.lang.constants.ExcelConstants;
import com.mbw.commons.lang.exception.ServiceException;
import com.mbw.commons.util.date.DateUtil;
import com.mbw.commons.util.io.FileUtil;
import com.mbw.commons.util.validate.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author Mabowen
 * @date 2020-01-04 19:25
 */
@Slf4j
public class ExportUtil {

    /**
     * 默认导出excel方法
     *
     * @author Mabowen
     * @date 17:26 2020-01-15
     * @param list
     * @param fileName
     * @param response
     */
    public static void defaultExportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.XSSF);
            if (null != workbook) {
                exportExcel(workbook, fileName, response);
            }
        } catch (Exception e) {
            log.error("导出excel异常：" + e.getMessage(), e);
            throw new ServiceException("导出excel异常：" + e.getMessage(), e);
        }
    }

    /**
     * 导出动态表头的excel表格
     * @author Mabowen
     * @date 2020/02/18 16:07
     * @param params 导出参数
     * @param colList 表头list
     * @param dataList 数据list
     * @param fileName
     * @param response
     * @return
     */
    public static void dynamicTableHeadExportExcel(ExportParams params, List<ExcelExportEntity> colList, List<Map<String, Object>> dataList, String fileName, HttpServletResponse response) {
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(params, colList, dataList);
            if (null != workbook) {
                exportExcel(workbook, fileName, response);
            }
        } catch (Exception e) {
            log.error("导出动态表头excel异常：" + e.getMessage(), e);
            throw new ServiceException("导出动态表头excel异常：" + e.getMessage(), e);
        }
    }

    /**
     * 导出大数据量excel
     * @author Mabowen
     * @date 15:51 2020-04-18
     * @param
     * @return
     */
    public static void exportBigDataExcel(ExportParams params, Class<?> pojoClass, List<?> dataList, String fileName, HttpServletResponse response) {
        try {
            Workbook workbook = ExcelExportUtil.exportBigExcel(params, pojoClass, dataList);
            if (null != workbook) {
                exportExcel(workbook, fileName, response);
            }
        } catch (Exception e) {
            log.error("导出大数据量Excel异常：" + e.getMessage(), e);
            throw new ServiceException("导出大数据量Excel异常：" + e.getMessage(), e);
        }
    }

    /**
     * 导出多个excel并压缩层zip下载
     * @author Mabowen
     * @date 2020/02/18 18:29
     * @param workbooks
     * @param excelNames excel文件名集合
     * @param zipName zip压缩文件名
     * @param response
     * @param rootPath 跟目录
     * @return
     */
    public static void exportExcelToZip(List<Workbook> workbooks, List<String> excelNames, String zipName, HttpServletResponse response, String rootPath) {
        try {
            if (workbooks.isEmpty()) {
                return;
            }
            if (workbooks.size() == 1 && excelNames.size() == 1) {
                exportExcel(workbooks.get(0), excelNames.get(0), response);
            } else {
                String currentTimestamp = DateUtil.getCurrentTimestamp();
                String excelPath = rootPath + ExcelConstants.EXCEL_PATH + File.separator + currentTimestamp + File.separator;
                String zipPath = rootPath + ExcelConstants.ZIP_PATH + File.separator + currentTimestamp + File.separator;

                // 首先将excel导出到临时文件夹下
                File excelDir = new File(excelPath);
                if (!excelDir.exists() && !excelDir.isDirectory()) {
                    excelDir.mkdirs();
                }
                for (int i = 0; i < workbooks.size(); i++) {
                    FileOutputStream out = new FileOutputStream(excelPath + excelNames.get(i) + ExcelConstants.XLSX);
                    Workbook workbook = workbooks.get(i);
                    workbook.write(out);
                    out.flush();
                    out.close();
                }

                // 将临时文件夹下的excel压缩成zip
                File zipDir = new File(zipPath);
                if (!zipDir.exists() && !zipDir.isDirectory()) {
                    zipDir.mkdirs();
                }
                File[] files = FileUtil.getFiles(excelPath);
                String zipFilePath = zipPath + zipName + ExcelConstants.ZIP;
                File zipFile = new File(zipFilePath);
                //将文件压缩成zip
                FileUtil.zipFiles(files, zipFile);

                // 下载zip
                response.setCharacterEncoding("UTF-8");
                response.setHeader("content-Type", "application/octet-stream");
                response.setContentType("application/zip");
                response.setHeader("Location", zipFile.getName());
                response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName(zipName) + ExcelConstants.ZIP);

                OutputStream outputStream = response.getOutputStream();
                InputStream inputStream = new FileInputStream(zipFilePath);

                byte[] buffer = new byte[2048];
                int i;
                while ((i = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, i);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();

                //删除临时文件夹下的excel
                FileUtil.delDirectoryAndFile(excelPath);
                //删除zip目录下的zip文件
                FileUtil.delDirectoryAndFile(zipPath);
            }
        } catch (IOException e) {
            log.error("导出多个excel并压缩层zip下载异常: " + e.getMessage(), e);
            throw new ServiceException("导出多个excel并压缩层zip下载异常: " + e.getMessage(), e);
        }
    }

    /**
     * excel导出工具类---apache poi导出
     * @param sheetName  工作表
     * @param titleName   表头
     * @param fileName     导出的文件名
     * @param columnNumber   列数
     * @param columnWidth     列宽
     * @param columnName      列名
     * @param dataList        数据
     * @param response        返回浏览器的响应
     * @throws Exception
     */
    public static void exportWithResponse(String sheetName, String titleName,
                                          String fileName, int columnNumber, int[] columnWidth,
                                          String[] columnName, String[][] dataList,
                                          HttpServletResponse response) throws Exception {
        if (columnNumber == columnWidth.length && columnWidth.length == columnName.length) {
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            // sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < columnNumber; i++) {
                for (int j = 0; j <= i; j++) {
                    if (i == j) {
                        sheet.setColumnWidth(i, columnWidth[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            // 创建第0行 也就是标题
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// 设备标题的高度
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setVerticalAlignment(VerticalAlignment.CENTER);
            style2.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_TURQUOISE.getIndex());
            style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont1.setBold(true); // 字体加粗
            headerFont1.setFontName("黑体"); // 设置字体类型
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
            style2.setFont(headerFont1); // 为标题样式设置字体样式

            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNumber - 1)); // 合并列标题
            cell1.setCellValue(titleName); // 设置值标题
            cell1.setCellStyle(style2); // 设置标题样式

            // 创建第1行 也就是表头
            HSSFRow row = sheet.createRow((int) 1);
            row.setHeightInPoints(37);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// 设置自动换行
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER); // 创建一个居中格式

            style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont.setBold(true); // 字体加粗
            headerFont.setFontName("黑体"); // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
            style.setFont(headerFont); // 为标题样式设置字体样式

            // 第四.一步，创建表头的列
            for (int i = 0; i < columnNumber; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(style);
            }

            // 第五步，创建单元格，并设置值
            for (int i = 0; i < dataList.length; i++) {
                row = sheet.createRow((int) i + 2);
                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// 设置自动换行
                zidonghuanhang.setVerticalAlignment(VerticalAlignment.CENTER); // 创建一个居中格式

                // 设置边框
                zidonghuanhang.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
                zidonghuanhang.setBorderBottom(BorderStyle.THIN);
                zidonghuanhang.setBorderLeft(BorderStyle.THIN);
                zidonghuanhang.setBorderRight(BorderStyle.THIN);
                zidonghuanhang.setBorderTop(BorderStyle.THIN);

                // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// 设置自动换行
                zidonghuanhang2.setVerticalAlignment(VerticalAlignment.CENTER); // 创建一个上下居中格式
                zidonghuanhang2.setAlignment(HorizontalAlignment.CENTER);// 左右居中

                // 设置边框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
                zidonghuanhang2.setBorderBottom(BorderStyle.THIN);
                zidonghuanhang2.setBorderLeft(BorderStyle.THIN);
                zidonghuanhang2.setBorderRight(BorderStyle.THIN);
                zidonghuanhang2.setBorderTop(BorderStyle.THIN);
                HSSFCell datacell = null;
                for (int j = 0; j < columnNumber; j++) {
                    datacell = row.createCell(j);
                    datacell.setCellValue(dataList[i][j]);
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }

            // 第六步，将文件存到浏览器设置的下载位置
            String filename = fileName + ".xls";
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(filename, "GBK"))));
            try (OutputStream out = response.getOutputStream()) {
                wb.write(out);// 将数据写出去
                String str = "导出" + fileName + "成功！";
                System.out.println(str);
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "导出" + fileName + "失败！";
                System.out.println(str1);
            }
        } else {
            System.out.println("列数目长度名称三个数组长度要一致");
        }
    }

    /**
     * 导出工作薄excel表格
     *
     * @author Mabowen
     * @date 19:28 2020-01-04
     * @param workbook 工作薄
     * @param fileName excel文件名
     * @param response
     */
    private static void exportExcel(Workbook workbook, String fileName, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + encodeFileName(fileName) + ExcelConstants.XLSX);

        try {
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("导出" + fileName + "失败, 失败原因: %s", e.getMessage()), e);
            throw new ServiceException(String.format("导出" + fileName + "失败, 失败原因: %s", e.getMessage()), e);
        } finally {
            try {
                workbook.close();
            } catch (IOException ignore) {

            }
        }
    }

    /**
     * 文件名转码
     *
     * @author Mabowen
     * @date 15:16 2020-01-15
     * @param fileName
     * @return
     */
    private static String encodeFileName(String fileName) {
        AssertUtil.assertNotNull(fileName, "转码文件名不能为空");

        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(String.format("转码失败，失败原因: %s", e.getMessage()), e);
            throw new ServiceException(String.format("转码失败，失败原因: %s", e.getMessage()), e);
        }

        return fileName;
    }
}
