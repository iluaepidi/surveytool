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
import ilu.surveytool.databasemanager.DataObject.Option;
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
	
	public File exportSurveyResponses(int surveyId, List<Question> questions, HashMap<String, HashMap<Integer, HashMap<Integer, List<String>>>> responses, String resourceFolder)
	{
		String folderPath = resourceFolder + "/surveyExport";
		System.out.println("Resource folder: " + folderPath);
		File folder = new File(folderPath);
		if(!folder.exists()) folder.mkdirs();
		

		Calendar cal = Calendar.getInstance();
		String filePath = folderPath + "/export" + surveyId + "_" + cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + cal.get(Calendar.DAY_OF_MONTH) + ".xls";
		
		File fileXLS = new File(filePath);
		
		List<Integer> questionsWithOther = new ArrayList<Integer>();
		
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
									Option option = optionGroup.getOptions().get(o);
									cell3 = row3.createCell(desp);
									String optionLabel = "";
									if(option.getContents().containsKey(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) && optionGroup.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText() != null && !optionGroup.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText().isEmpty())
									{
										optionLabel = option.getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
									}
									else
									{
										optionLabel = option.getResources().get(0).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText();
									}
									System.out.println("option label excel: " + optionLabel);
									cell3.setCellValue(optionLabel);
									sheet.autoSizeColumn(desp);
									desp++;
									
									if(option.isOther())
									{
										cell3 = row3.createCell(desp);
										cell3.setCellValue("Other answer");
										sheet.autoSizeColumn(desp);
										desp++;
										questionsWithOther.add(questions.get(c).getQuestionId());
									}
								}
							}
							else if(optionGroup.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_RADIO))
							{
								cell3 = row3.createCell(desp);
								cell3.setCellValue("Option selected");
								sheet.autoSizeColumn(desp);
								desp++;
								
								int osize = optionGroup.getOptions().size();
								boolean exist = false;
								for(int o = 0; o < osize; o++)
								{
									Option option = optionGroup.getOptions().get(o);
									if(option.isOther() && !exist)
									{
										cell3 = row3.createCell(desp);
										cell3.setCellValue("Other answer");
										sheet.autoSizeColumn(desp);
										desp++;
										questionsWithOther.add(questions.get(c).getQuestionId());
										exist = true;
									}
								}								
							}
						}
						
					}
					else
					{
						desp++;
					}
				}	
			}
			
			List<String> userList = new ArrayList<String>();
			userList.addAll(responses.keySet());
			Collections.sort(userList);
			
			int rowIndex = 3;
			for(String user : userList)
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
								//List<String> values = null;
								//if(optionGroups != null) values = optionGroups.get(ogItem.getId());
								List<String> values = (optionGroups != null && optionGroups.get(ogItem.getId()) != null) ? optionGroups.get(ogItem.getId()) : new ArrayList<String>();
								if(ogItem.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_RADIO))
								{
									String val = "";
									if(optionGroups != null && values != null && !values.isEmpty()) val = values.get(0);
									String[] vals = val.split(DBConstants.s_VALUE_TOKEN);
									
									cell = row.createCell(desp);
									if(vals.length > 0 && !vals[0].isEmpty()) cell.setCellValue(vals[0]);							
									desp++;
									
									if(questionsWithOther.contains(questions.get(c).getQuestionId()))
									{
										cell = row.createCell(desp);
										if(val.indexOf(DBConstants.s_VALUE_TOKEN) > -1) cell.setCellValue(vals[1]);							
										desp++;
									}
								}
								else if(ogItem.getOptionType().equals(DBConstants.s_VALUE_OPTIONTYPE_CHECKBOX))
								{
									String otherText = "";
									for(int o = 0; o < ogItem.getOptions().size(); o++)
									{
										if(optionGroups != null)
										{	
											int optionId = ogItem.getOptions().get(o).getId();
											if(/*(ogItem.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null && values.contains(ogItem.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText()))
													||*/ (ogItem.getOptions().get(o).getResources() != null && !ogItem.getOptions().get(o).getResources().isEmpty() && ogItem.getOptions().get(o).getResources().get(0).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE) != null && values.contains(ogItem.getOptions().get(o).getResources().get(0).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText()))
													|| (values != null && values.contains(Integer.toString(optionId)))
													|| (values != null && values.contains(Integer.toString(ogItem.getOptions().get(o).getIndex()))))
											{
												cell = row.createCell(desp);
												cell.setCellValue("1");
												desp++;
											}
											else
											{
												String val = "0";
												
												for(String value : values)
												{
													if(value.contains(ogItem.getOptions().get(o).getContents().get(DBConstants.s_VALUE_CONTENTTYPE_NAME_TITLE).getText()) && value.contains(DBConstants.s_VALUE_TOKEN))
													{
														val = "1";
														otherText = value.split(DBConstants.s_VALUE_TOKEN)[1];
													}
												}
												
												cell = row.createCell(desp);
												cell.setCellValue(val);
												desp++;
											}
										}
										else
										{
											cell = row.createCell(desp);
											cell.setCellValue("0");
											desp++;
										}
									}
									
									if(questionsWithOther.contains(questions.get(c).getQuestionId()))
									{
										if(!otherText.isEmpty())
										{
											cell = row.createCell(desp);
											cell.setCellValue(otherText);
										}
										desp++;
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