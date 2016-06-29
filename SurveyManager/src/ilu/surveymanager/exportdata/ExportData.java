package ilu.surveymanager.exportdata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.ResponseSimple;
import ilu.surveytool.databasemanager.constants.DBConstants;

public class ExportData {
	
	public ExportData() {
		super();
	}
	
	public File exportSurveyResponses(int surveyId, List<Question> questions, HashMap<Integer, HashMap<Integer, String>> responses)
	{
		String folderPath = "/surveyExport";
		File folder = new File(folderPath);
		if(!folder.exists()) folder.mkdirs();
		

		Calendar cal = Calendar.getInstance();
		String filePath = folderPath + "/export" + surveyId + "_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + ".xls";
		
		File fileXLS = new File(filePath);
		
		
		try {
			
			int i = 1;
			while(fileXLS.exists())
			{
				filePath = folderPath + "/export" + surveyId + "_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + "_" + i + ".xls";
				fileXLS = new File(filePath);
				i++;
			}
							
			fileXLS.createNewFile();
			
			Workbook book = new HSSFWorkbook();
			
			FileOutputStream file = new FileOutputStream(fileXLS);
			
			Sheet sheet = book.createSheet("Survey " + surveyId);
			
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("User Id");
			sheet.autoSizeColumn(0);
			for(int c = 0; c < questions.size(); c++)
			{
				cell = row.createCell(c + 1);
				cell.setCellValue(questions.get(c).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
				sheet.autoSizeColumn(c + 1);
			}
			
			List<Integer> userList = new ArrayList<Integer>();
			userList.addAll(responses.keySet());
			Collections.sort(userList);
			
			int rowIndex = 1;
			for(Integer user : userList)
			{
				row = sheet.createRow(rowIndex);
				cell = row.createCell(0);
				cell.setCellValue(user);
				for(int c = 0; c < questions.size(); c++)
				{
					cell = row.createCell(c + 1);
					cell.setCellValue(responses.get(user).get(questions.get(c).getQuestionId()));
				}
				rowIndex++;
			}
			
			book.write(file);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileXLS;
	}

	public File exportPollResponses(int pollId, List<Question> questions, HashMap<Integer, HashMap<Integer, String>> responses)
	{
		String folderPath = "/pollExport";
		File folder = new File(folderPath);
		if(!folder.exists()) folder.mkdirs();
		

		Calendar cal = Calendar.getInstance();
		String filePath = folderPath + "/export" + pollId + "_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + ".xls";
		
		File fileXLS = new File(filePath);
		
		
		try {
			
			int i = 1;
			while(fileXLS.exists())
			{
				filePath = folderPath + "/export" + pollId + "_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + "_" + i + ".xls";
				fileXLS = new File(filePath);
				i++;
			}
							
			fileXLS.createNewFile();
			
			Workbook book = new HSSFWorkbook();
			
			FileOutputStream file = new FileOutputStream(fileXLS);
			
			Sheet sheet = book.createSheet("Poll " + pollId);
			
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("User Id");
			sheet.autoSizeColumn(0);
			for(int c = 0; c < questions.size(); c++)
			{
				cell = row.createCell(c + 1);
				cell.setCellValue(questions.get(c).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
				sheet.autoSizeColumn(c + 1);
			}
			
			List<Integer> userList = new ArrayList<Integer>();
			userList.addAll(responses.keySet());
			Collections.sort(userList);
			
			int rowIndex = 1;
			for(Integer user : userList)
			{
				row = sheet.createRow(rowIndex);
				cell = row.createCell(0);
				cell.setCellValue(user);
				for(int c = 0; c < questions.size(); c++)
				{
					cell = row.createCell(c + 1);
					cell.setCellValue(responses.get(user).get(questions.get(c).getQuestionId()));
				}
				rowIndex++;
			}
			
			book.write(file);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileXLS;
	}

}