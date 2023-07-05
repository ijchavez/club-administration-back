package uy.com.club.administration.reports;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uy.com.club.administration.dto.request.ContributionListDTO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ContributionReport {
    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<ContributionListDTO> listContributions;


    public ContributionReport(List<ContributionListDTO> listContributions) {
        this.listContributions = listContributions;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Contributions");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, translateToSpanish("Category.Name"), style);
        createCell(row, 1, translateToSpanish("Amount"), style);
        createCell(row, 2, translateToSpanish("Date"), style);
        createCell(row, 3, translateToSpanish("Payment.Method.Name"), style);
        createCell(row, 4, translateToSpanish("Issuer.Name"), style);
        createCell(row, 5, translateToSpanish("Partner.Name"), style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(String.valueOf(value));
        } else if (value != null) {
            cell.setCellValue(String.valueOf(value));
        } else {
            cell.setCellValue((String) null);
        }
        cell.setCellStyle(style);
    }

    public String formatDate(Date date) {
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        return targetFormat.format(date);
    }

    private void writeDataLines() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (ContributionListDTO contribution : listContributions) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, contribution.getCategoryTypeName(), style);
            createCell(row, columnCount++, contribution.getAmount(), style);
            createCell(row, columnCount++, formatDate(contribution.getDate()), style);
            createCell(row, columnCount++, contribution.getPaymentMethodName(), style);
            createCell(row, columnCount++, contribution.getIssuerName(), style);
            createCell(row, columnCount, contribution.getPartnerName(), style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public String translateToSpanish(String word) {
        String language = "es";
        String country = "US";
        var locale = new Locale(language, country);
        var message = ResourceBundle.getBundle("message_es", locale);
        return message.getString(word);
    }
}