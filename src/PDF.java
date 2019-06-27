import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PDF {

    public void CreatePDF(Map studentInformationMap,Map failingCourseMap) {
        int cellNameCount=0;
        String majorAttribute=String.valueOf(studentInformationMap.get("专业属性"));
        double demand=0;
        if(majorAttribute.equals("文学")||majorAttribute.equals("法学")||majorAttribute.equals("教育学")||majorAttribute.equals("管理学")||majorAttribute.equals("艺术学")||majorAttribute.equals("历史学")){
            demand=2.0;
        }else {
            demand=1.8;
        }
        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYMMdd");//设置当前时间的格式，为年-月-日
        String No=dateFormat.format(date)+String.valueOf(studentInformationMap.get("学号"));
        String name=String.valueOf(studentInformationMap.get("姓名"))+"学生预警信息表";
        // 1:建立Document对象实例
        Document document = new Document();
        try {
            // 2:建立一个PDF 写入器与document对象关联通过书写器(Writer)可以将文档写入到磁盘中
            StringBuilder filename = new StringBuilder();
            filename.append(name).append(new SimpleDateFormat(No).format(new Date())).append(".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(filename.toString()));

            // 3:打开文档
            document.open();

            //解决中文不显示问题
            BaseFont bfChinese = BaseFont.createFont("c://windows//fonts//SIMFANG.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
//            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font fontChina18 = new Font(bfChinese, 18);
            Font fontChina12 = new Font(bfChinese, 12);

            // 4:向文档添加内容
            // 标题
            Paragraph titleParagraph = new Paragraph("山西大同大学学生预警信息表", fontChina18);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);// 居中
            document.add(titleParagraph);

            // 空格
            Paragraph blank1 = new Paragraph(" ");
            document.add(blank1);

            // 编号

            Chunk c1 = new Chunk("编号：", fontChina12);
            Chunk c2 = new Chunk(No, fontChina12);
            Paragraph snoParagraph = new Paragraph();
            snoParagraph.add(c1);
            snoParagraph.add(c2);
            snoParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(snoParagraph);

            // 空格
//            document.add(blank1);

            String schoolName=String.valueOf(studentInformationMap.get("学院"));
            Chunk c3 = new Chunk("学院：", fontChina12);
            Chunk c4 = new Chunk(schoolName, fontChina12);
            Paragraph dkswjgParagraph = new Paragraph();
            dkswjgParagraph.add(c3);
            dkswjgParagraph.add(c4);
            dkswjgParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(dkswjgParagraph);

            // 提醒
            String alert = "此表根据山西大同大学教务系统成绩预警系统自动生成，此表生成代表该学生专业课成绩已低于山西大同大学学士学位授予最低要求，特此通知！";
            Paragraph alertParagraph = new Paragraph(alert, fontChina12);
            alertParagraph.setFirstLineIndent(24);// 首行缩进个2字符
            document.add(alertParagraph);

            alert = "山西大同大学学士学位授予规定：学位课程的平均学分绩点要求：文学、法学、教育学、管理学、艺术学、历史学学位课程的平均学分绩点为 2.0；理学、工学、农学、医学、经济学学位课程的平均学分绩点为 1.8。";
            alertParagraph = new Paragraph(alert, fontChina12);
            alertParagraph.setFirstLineIndent(24);// 首行缩进个2字符
            document.add(alertParagraph);

            alert = "根据此项规定，预警等级分为 3 级，第一级为通过，表现为学生成绩不及格数低于 4 门且绩点高于毕业要求绩点的 95%；第二级黄色预警，表现为学生成绩 4 门不及格或绩点低于毕业要求绩点的 95%；第三级为红色预警，表现为学生成绩 6 门不及格或绩点低于毕业要求绩点的 85%。";
            alertParagraph = new Paragraph(alert, fontChina12);
            alertParagraph.setFirstLineIndent(24);// 首行缩进个2字符
            document.add(alertParagraph);

            // 填开日期
            SimpleDateFormat dateFormat_min=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//设置当前时间的格式，为年-月-日 时-分-秒
            Chunk c5 = new Chunk("日期："+dateFormat_min.format(date), fontChina12);
            Paragraph tkrqParagraph = new Paragraph();
            tkrqParagraph.add(c5);
            tkrqParagraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(tkrqParagraph);

            // 空格
            document.add(blank1);

            // 表格处理
            PdfPTable table = new PdfPTable(4);// 四列
            table.setWidthPercentage(100);// 表格宽度为100%

            PdfPCell cellName = new PdfPCell();
            PdfPCell cellData = new PdfPCell();

            ArrayList<String> attributeName=new ArrayList();
            attributeName.add("姓名");
            attributeName.add("学号");
            attributeName.add("学院");
            attributeName.add("专业");
            attributeName.add("专业属性");
            attributeName.add("电子邮件");
            attributeName.add("父亲姓名与联系方式");
            attributeName.add("母亲姓名与联系方式");
            attributeName.add("班主任姓名");
            attributeName.add("班主任编号");

            //上方学生信息填入
            for(int count=0;count<10;count=count+1){
                cellName.setPhrase(new Paragraph(String.valueOf(attributeName.get(count)), fontChina12));
                cellName.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellName.setExtraParagraphSpace(10);
                table.addCell(cellName);

                cellData.setPhrase(new Paragraph(String.valueOf(studentInformationMap.get(String.valueOf(attributeName.get(count)))), fontChina12));
                cellData.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellData.setExtraParagraphSpace(10);
                table.addCell(cellData);
            }

            cellName.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cellName.setPhrase(new Paragraph("绩点", fontChina12));
            cellName.setExtraParagraphSpace(10);
            table.addCell(cellName);

            cellData.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cellData.setPhrase(new Paragraph(String.valueOf(failingCourseMap.get("绩点")), fontChina12));
            cellData.setExtraParagraphSpace(10);
            table.addCell(cellData);

            cellName.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cellName.setPhrase(new Paragraph("预警等级", fontChina12));
            cellName.setExtraParagraphSpace(10);
            table.addCell(cellName);

            cellData.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cellData.setPhrase(new Paragraph(String.valueOf(failingCourseMap.get("预警等级")), fontChina12));
            cellData.setExtraParagraphSpace(10);
            table.addCell(cellData);

            PdfPCell cell13 = new PdfPCell();
            cell13.setColspan(4);// 跨四列
            cell13.setPhrase(new Paragraph("学生不及格科目列表", fontChina12));
            cell13.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell13.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            cell13.setExtraParagraphSpace(10);
            table.addCell(cell13);


            ArrayList<String > failingCourseattributeName=new ArrayList();

            for (int count=1;count<failingCourseMap.size()-2;count=count+1){
                failingCourseattributeName.add("不及格科目"+count);
                cellName.setPhrase(new Paragraph(String.valueOf(failingCourseattributeName.get(count-1)), fontChina12));
                cellName.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellName.setExtraParagraphSpace(10);
                table.addCell(cellName);
                cellNameCount=cellNameCount+1;

                cellData.setPhrase(new Paragraph(String.valueOf(failingCourseMap.get(failingCourseattributeName.get(count-1))), fontChina12));
                cellData.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellData.setExtraParagraphSpace(10);
                table.addCell(cellData);

            }

            while (cellNameCount<11){
                cellName.setPhrase(new Paragraph("不及格科目"+(cellNameCount+1), fontChina12));
                cellName.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellName.setExtraParagraphSpace(10);
                table.addCell(cellName);
                cellNameCount=cellNameCount+1;

                cellData.setPhrase(new Paragraph(" ", fontChina12));
                cellData.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
                cellData.setExtraParagraphSpace(10);
                table.addCell(cellData);
            }

            cellName.setPhrase(new Paragraph("不及格科目数", fontChina12));
            cellName.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cellName.setExtraParagraphSpace(10);
            table.addCell(cellName);

            cellData.setPhrase(new Paragraph(String.valueOf(failingCourseMap.get("不及格科目数")), fontChina12));
            cellData.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cellData.setExtraParagraphSpace(10);
            table.addCell(cellData);


            // 备注
            PdfPCell cell34 = new PdfPCell();
            cell34.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell34.setPhrase(new Paragraph("备注", fontChina12));
            cell34.setExtraParagraphSpace(10);
            table.addCell(cell34);

            PdfPCell cell35 = new PdfPCell();
            cell35.setColspan(3);
            cell35.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            cell35.setPhrase(new Paragraph("该学生专业为："+studentInformationMap.get("专业")+",专业属性为"+studentInformationMap.get("专业属性")+",故该学生学士学位授予要求绩点应达到"+demand, fontChina12));
            cell35.setExtraParagraphSpace(10);
            table.addCell(cell35);

            document.add(table);

            document.add(blank1);
            document.add(blank1);

            // 5:关闭文档
            document.close();
            System.out.println("生成PDF成功");
        } catch (FileNotFoundException e) {
            System.out.println("生成PDF失败");
            e.printStackTrace();
        } catch (DocumentException e) {
            System.out.println("生成PDF失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("生成PDF失败");
            e.printStackTrace();
        }
    }
}
