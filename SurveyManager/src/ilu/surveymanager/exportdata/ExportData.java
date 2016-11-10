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
			int desp = 1;
			for(int c = 0; c < questions.size(); c++)
			{
				String qtype = questions.get(c).getQuestionType();
				//System.out.println("question " + c + ": " + qtype);
				if(!qtype.equals(DBConstants.s_VALUE_QUESTIONTYPE_BCONTENT))
				{
					//System.out.println("Question " + c + ": " + questions.get(c));
					cell1 = row1.createCell(desp);
					cell1.setCellValue(questions.get(c).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
					sheet.autoSizeColumn(desp);
	
					int size = questions.get(c).getOptionsGroups().size();
					if(size > 0)
					{
						for(int og = 0; og < size; og++)
						{
							int ogsize = questions.get(c).getOptionsGroups().get(0).getOptions().size();
							OptionsGroup optionGroup = questions.get(c).getOptionsGroups().get(og);
	
							Content ogcontent = optionGroup.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE);
							if(ogcontent != null)
							{
								cell2 = row2.createCell(desp);
								cell2.setCellValue(ogcontent.getText());
								sheet.autoSizeColumn(desp);
							}
							
							//System.out.println("Type: " + optionGroup.getOptionType());
													
							if(optionGroup.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_CHECKBOX))
							{
								int osize = optionGroup.getOptions().size();
								for(int o = 0; o < osize; o++)
								{
									cell3 = row3.createCell(desp);
									cell3.setCellValue(optionGroup.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText());
									sheet.autoSizeColumn(desp);
									desp++;
								}
							}
							else if(optionGroup.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_RADIO))
							{
								desp++;
							}
						}
						
					}
					else
					{
						desp++;
					}
				}	
			}
			
			List<Integer> userList = new ArrayList<Integer>();
			userList.addAll(responses.keySet());
			Collections.sort(userList);
			
			int rowIndex = 3;
			for(Integer user : userList)
			{
				//System.out.println("user " + user + ": " + responses.get(user).toString());
				Row row = sheet.createRow(rowIndex);
				Cell cell = row.createCell(0);
				cell.setCellValue(user);
				desp = 1;
				for(int c = 0; c < questions.size(); c++)
				{
					String qtype = questions.get(c).getQuestionType();
					//System.out.println("question " + c + ": " + qtype);
					if(!qtype.equals(DBConstants.s_VALUE_QUESTIONTYPE_BCONTENT))
					{
						HashMap<Integer, List<String>> optionGroups = responses.get(user).get(questions.get(c).getQuestionId());
						List<OptionsGroup> ogList = questions.get(c).getOptionsGroups();
						if(ogList.isEmpty())
						{
							cell = row.createCell(desp);
							if(optionGroups != null) cell.setCellValue(optionGroups.get(0).get(0));
							desp++;
						}
						else
						{
							for(int og = 0; og < ogList.size(); og++)
							{
								OptionsGroup ogItem = ogList.get(og);
								List<String> values = null;
								if(optionGroups != null) values = optionGroups.get(ogItem.getId());
								if(ogItem.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_RADIO))
								{
									cell = row.createCell(desp);
									if(optionGroups != null && values != null && !values.isEmpty()) cell.setCellValue(values.get(0));							
									desp++;				
								}
								else if(ogItem.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_CHECKBOX))
								{
									for(int o = 0; o < ogItem.getOptions().size(); o++)
									{
										if(optionGroups != null)
										{
											if(values.contains(ogItem.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText()))
											{
												cell = row.createCell(desp);
												cell.setCellValue("yes");
												desp++;
											}
											else
											{
												cell = row.createCell(desp);
												cell.setCellValue("no");
												desp++;
											}
										}
										else
										{
											cell = row.createCell(desp);
											desp++;
										}
									}
								} 
							}
	
						}
						
					}
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