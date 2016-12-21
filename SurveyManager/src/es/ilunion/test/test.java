package es.ilunion.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class test {
	
	public static void main(String[] args)
	{
		String folderPath = "/surveyExport";
		File folder = new File(folderPath);
		if(!folder.exists()) folder.mkdirs();
		
		try {
			
			Calendar cal = Calendar.getInstance();
			String filePath = folderPath + "/export" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + ".xls";
			
			File fileXLS = new File(filePath);
			
			int i = 1;
			while(fileXLS.exists())
			{
				filePath = folderPath + "/export" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + "_" + i + ".xls";
				fileXLS = new File(filePath);
				i++;
			}
							
			fileXLS.createNewFile();
			
			Workbook book = new HSSFWorkbook();
			
			FileOutputStream file = new FileOutputStream(fileXLS);
			
			Sheet sheet = book.createSheet("My first survey");
			
			for(int f = 0; f < 3; f++)
			{
				Row row = sheet.createRow(f);
				for(int c=0;c<5;c++){
				   Cell cell = row.createCell(c);	
				   if(f==0){
					   cell.setCellValue("Encabezado #"+c);
				   }else{
					   cell.setCellValue("Valor celda "+c+","+f);
				   }
				}
			}
			
			book.write(file);
			file.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void uuidtal()
	{
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		
		SecureRandom random = new SecureRandom();
		System.out.println(new BigInteger(50, random).toString(32));
	}
}