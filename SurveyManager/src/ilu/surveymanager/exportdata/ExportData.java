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

import com.sun.javafx.collections.MappingChange.Map;

import ilu.surveytool.databasemanager.DataObject.Content;
import ilu.surveytool.databasemanager.DataObject.OptionsGroup;
import ilu.surveytool.databasemanager.DataObject.Question;
import ilu.surveytool.databasemanager.DataObject.Response;
import ilu.surveytool.databasemanager.DataObject.ResponseSimple;
import ilu.surveytool.databasemanager.constants.DBConstants;
import ilu.surveytool.databasemanager.constants.DBFieldNames;

public class ExportData {
	
	public ExportData() {
		super();
	}
	
	public File exportSurveyResponses(int surveyId, List<Question> questions, HashMap<Integer, HashMap<Integer, HashMap<Integer, List<String>>>> responses)
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
			
			Row row1 = sheet.createRow(0);
			Row row2 = sheet.createRow(1);
			Row row3 = sheet.createRow(2);
			Cell cell1 = row1.createCell(0);
			Cell cell2 = row2.createCell(0);
			Cell cell3 = row3.createCell(0);
			cell1.setCellValue("User Id");
			sheet.autoSizeColumn(0);
			int desp = 0;
			for(int c = 0; c < questions.size(); c++)
			{
				System.out.println("Question " + c + ": " + questions.get(c));
				cell1 = row1.createCell(c + 1 + desp);
				cell1.setCellValue(questions.get(c).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
				sheet.autoSizeColumn(c + 1);

				int size = questions.get(c).getOptionsGroups().size();
				if(size > 0)
				{
					boolean isRadio = false;
					int numCheckbox = 0;
					int ogdesp = 0;
					for(int og = 0; og < size; og++)
					{
						int ogsize = questions.get(c).getOptionsGroups().get(0).getOptions().size();
						OptionsGroup optionGroup = questions.get(c).getOptionsGroups().get(og);

						Content ogcontent = optionGroup.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
						if(ogcontent != null)
						{
							cell2 = row2.createCell(c + og + 1 + desp + ogdesp);
							cell2.setCellValue(ogcontent.getText());
							sheet.autoSizeColumn(c + 1);
						}
						
						//System.out.println("Type: " + optionGroup.getOptionType());
												
						if(optionGroup.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_CHECKBOX))
						{
							int osize = optionGroup.getOptions().size();
							for(int o = 0; o < osize; o++)
							{
								cell3 = row3.createCell(c + og + o + 1 + desp + ogdesp);
								cell3.setCellValue(optionGroup.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
								sheet.autoSizeColumn(c + 1);
							}
							numCheckbox += osize;
							ogdesp = numCheckbox - 1;
						}
						else if(optionGroup.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_RADIO))
						{
							isRadio = true;
						}
					}
					
					if(isRadio)
					{
						desp += questions.get(c).getOptionsGroups().size() - 1;
					}
					
					if(numCheckbox > 0) desp += numCheckbox - 1;
				}
				
				
			}
			
			List<Integer> userList = new ArrayList<Integer>();
			userList.addAll(responses.keySet());
			Collections.sort(userList);
			
			int rowIndex = 3;
			for(Integer user : userList)
			{
				System.out.println("user " + user + ": " + responses.get(user).toString());
				Row row = sheet.createRow(rowIndex);
				Cell cell = row.createCell(0);
				cell.setCellValue(user);
				desp = 0;
				for(int c = 0; c < questions.size(); c++)
				{
					HashMap<Integer, List<String>> optionGroups = responses.get(user).get(questions.get(c).getQuestionId());
					List<OptionsGroup> ogList = questions.get(c).getOptionsGroups();
					if(ogList.isEmpty())
					{
						cell = row.createCell(c + 1 + desp);
						cell.setCellValue(optionGroups.get(0).get(0));
					}
					else
					{
						boolean isRadio = false;
						int numCheckbox = 0;
						int ogdesp = 0;
						for(int og = 0; og < ogList.size(); og++)
						{
							OptionsGroup ogItem = ogList.get(og);
							List<String> values = optionGroups.get(ogItem.getId());
							if(ogItem.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_RADIO))
							{
								cell = row.createCell(c + 1 + desp + og);
								cell.setCellValue(values.get(0));
								isRadio = true;
							}
							else if(ogItem.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_CHECKBOX))
							{
								for(int o = 0; o < ogItem.getOptions().size(); o++)
								{
									if(values.contains(ogItem.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText()))
									{
										cell = row.createCell(c + 1 + desp + ogdesp + o);
										cell.setCellValue("yes");
										//desp++;
									}
									else
									{
										cell = row.createCell(c + 1 + desp + ogdesp + o);
										cell.setCellValue("no");
										//desp++;
									}
								}
								//desp = ogItem.getOptions().size() - 1;
								numCheckbox += ogItem.getOptions().size();
								ogdesp = numCheckbox;
							} 
						}

						if(isRadio)
						{
							desp += ogList.size() - 1;
						}
						
						if(numCheckbox > 0) desp += numCheckbox - 1;
						
					}
					
					
					/*else if(optionGroup.size() == 1)
					{
						for()
						cell.setCellValue(optionGroup.get(0).get(0));
					}*/
					//cell.setCellValue();
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