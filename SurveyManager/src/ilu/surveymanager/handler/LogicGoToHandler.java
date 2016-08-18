package ilu.surveymanager.handler;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveytool.databasemanager.LogicGoToDB;
import ilu.surveytool.databasemanager.DataObject.LogicGoTo;

public class LogicGoToHandler {

	public LogicGoToHandler() {
		super();
	}
	
	public boolean insertLogic(int qid, int ogid, int oid, int qdestid)
	{
		LogicGoToDB logicGoToDB = new LogicGoToDB();
		boolean response = false;
		LogicGoTo logicGoTo = new LogicGoTo(qid, qdestid,"", ogid, oid,"");
		
		try {
			System.out.println("Ready to insert goto");
			if(!logicGoToDB.existLogicGoToByQuestionId_OgId_OId(qid, ogid, oid)){
				System.out.println("Inside if");
				response = logicGoToDB.insertLogicGoTo(logicGoTo);
			}
			else{
				System.out.println("Inside else");
				response = logicGoToDB.updateLogicGoTo(logicGoTo);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
	public boolean removeLogicGoTo(int qid, int ogid, int oid){
		boolean response = false;
		LogicGoToDB logicGoToDB = new LogicGoToDB();
		
		try {
			response = logicGoToDB.removeLogicGoTo(new LogicGoTo(qid, 0, "", ogid, oid, ""));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public boolean removeAllLogicGoTo(int qid){
		boolean response = false;
		LogicGoToDB logicGoToDB = new LogicGoToDB();
		
		try {
			System.out.println("qid: "+qid);
			response = logicGoToDB.removeAllLogicGoTo(new LogicGoTo(qid, 0, "", 0, 0, ""));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
}
