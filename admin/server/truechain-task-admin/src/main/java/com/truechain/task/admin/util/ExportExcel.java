package com.truechain.task.admin.util;

import com.truechain.task.admin.model.dto.UserDTO;
import com.truechain.task.admin.model.viewPojo.UserProfilePagePojo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.data.domain.Page;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;


/**
 * Created by NancySmall
 * on 2018/10/19
 **/
public class ExportExcel {
    public static UserDTO getUserDTO(String startDate,String endDate,String name,String wxNickName,Integer auditStatus,String level,int pageSize,int pageIndex){
        UserDTO user=new UserDTO();
        user.setName(name);
        user.setLevel(level);
        user.setPageIndex(pageIndex);
        user.setAuditStatus(auditStatus);
        user.setStartDate(startDate);
        user.setEndDate(endDate);
        user.setWxNickName(wxNickName);
        user.setPageSize(pageSize);
        return user;

    }
    public static void exportData(Page<UserProfilePagePojo> userProfilePagePojos, HttpServletResponse response){
        try {
            ServletOutputStream out = response.getOutputStream();

            //设置文件头：最后一个参数是设置下载文件名(这里我们叫：张三.pdf)
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("导出" + ".xls", "UTF-8"));

            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("sheet1");

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short


            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
            hssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
            //表头
            HSSFRow headerRow = hssfSheet.createRow(0);
            String[] headers = {"姓名", "微信昵称", "微信号", "抢任务数", "完成任务数", "进行中任务数", "true数量", "ttr数量", "rmb数量", "用户"};
            for (int i = 0; i < headers.length; i++) {
                HSSFCell headCell = headerRow.createCell(i);
                headCell.setCellValue(headers[i]);
                headCell.setCellStyle(hssfCellStyle);
            }

            for (int rowNum = 0;rowNum < userProfilePagePojos.getContent().size();rowNum++){
                UserProfilePagePojo userProfilePagePojo = userProfilePagePojos.getContent().get(rowNum);
                HSSFRow row = hssfSheet.createRow(rowNum + 1);
                //姓名
                HSSFCell[] hssfCellArray = new HSSFCell[10];
                for (int i = 0; i < hssfCellArray.length; i++) {
                    hssfCellArray[i] = row.createCell(i);
                    hssfCellArray[i].setCellStyle(hssfCellStyle);
                }
                hssfCellArray[0].setCellValue(userProfilePagePojo.getSysUser().getPersonName());
                hssfCellArray[1].setCellValue(userProfilePagePojo.getSysUser().getWxNickName());
                hssfCellArray[2].setCellValue(userProfilePagePojo.getSysUser().getWxNum());
                hssfCellArray[3].setCellValue(userProfilePagePojo.getTaskCount());
                hssfCellArray[4].setCellValue(userProfilePagePojo.getTaskDoneCount());
                hssfCellArray[5].setCellValue(userProfilePagePojo.getTaskDoingCount());
                hssfCellArray[6].setCellValue(userProfilePagePojo.getTrueValue());
                hssfCellArray[7].setCellValue(userProfilePagePojo.getTtrValue());
                hssfCellArray[8].setCellValue(userProfilePagePojo.getRmbValue());
                hssfCellArray[9].setCellValue(userProfilePagePojo.getRecommendCount());
            }

            // 第七步，将文件输出到客户端浏览器
            try {
                workbook.write(out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
      }




}
