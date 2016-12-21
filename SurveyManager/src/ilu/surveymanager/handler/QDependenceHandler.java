package ilu.surveymanager.handler;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.QDependence;
import ilu.surveymanager.data.QDependenceValue;
import ilu.surveytool.databasemanager.LogicGoToDB;
import ilu.surveytool.databasemanager.QDependenceDB;
import ilu.surveytool.databasemanager.DataObject.LogicGoTo;

public class QDependenceHandler {

	public QDependenceHandler() {
		super();
	}
	
	public String saveQDependence(QDependence qdependence)
	{
		QDependenceDB qDependenceDB= new QDependenceDB();
		JSONObject response = new JSONObject();
		QDependenceValue qdepvalue = qdependence.getqdepval().get(0);
		
		try {
			System.out.println("qdepvalue.getOid(): "+qdepvalue.getOid()+", qdependence.getId(): "+qdependence.getId()+", qdepvalue.getItemId(): "+qdepvalue.getItemId());
			if(qdepvalue.getOid()>0){
				System.out.println("Dentro de 'if(qdepvalue.getOid()>0)'");
				if((qdependence.getId() == 0) || (qDependenceDB.getCountQDependenceValue(qdependence.getId()) == 0))
				{
					System.out.println("Dentro de 'if(qdependence.getId() == 0)'");
					int qdependenceId = qDependenceDB.insertQDependence(qdependence.getQuestionId(), qdependence.getDependenceType());
					qdependence.setId(qdependenceId);
					int qDependeceItemId = qDependenceDB.insertQDependenceValue(qdependenceId, qdepvalue.getQid(), qdepvalue.getOgid(), String.valueOf(qdepvalue.getOid()));
					response.put("qdependenceId", String.valueOf(qdependenceId));
					response.put("qDependeceItemId", String.valueOf(qDependeceItemId));
				}
				else if(qdepvalue.getItemId() >= 0)
				{
					System.out.println("Dentro de 'if(qdepvalue.getItemId() >= 0)'");
					qDependenceDB.updateQDependence(qdependence.getId(), qdepvalue.getItemId(), qdepvalue.getQid(), qdepvalue.getOgid(), qdepvalue.getOid(), qdependence.getDependenceType());
				}
				else
				{
					System.out.println("Dentro del else");
					int qDependeceItemId = qDependenceDB.insertQDependenceValue(qdependence.getId(), qdepvalue.getQid(), qdepvalue.getOgid(), String.valueOf(qdepvalue.getOid()));
					if(!qdependence.getDependenceType().equals(""))
						qDependenceDB.updateQDependenceType(qdependence.getId(), qdependence.getDependenceType());
					response.put("qDependeceItemId", String.valueOf(qDependeceItemId));
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.toString();
	}
	
	public void saveQDependenceType (int id, String dependenceType)
	{
		QDependenceDB qDependenceDB= new QDependenceDB();

		if(id > 0)
		{
			System.out.println(id+", "+dependenceType);
			qDependenceDB.updateQDependenceType(id, dependenceType);
		}
	}
	
	public boolean removeQDependence(int qDependenceId)
	{
		QDependenceDB qDependenceDB = new QDependenceDB();
		boolean response = qDependenceDB.removeAllQDependenceValue(qDependenceId);
		if(response)
			response = qDependenceDB.removeQDependence(qDependenceId);
		
		return response;
	}
	
	public boolean removeQDependenceValue(int qItemDependenceId)
	{
		QDependenceDB qDependenceDB = new QDependenceDB();
		return qDependenceDB.removeQDependenceValue(qItemDependenceId);
	}
	
	public boolean removeAllQDependences(int questionId)
	{
		boolean response = false;
		try {
			QDependenceDB qDependenceDB= new QDependenceDB();
			int qDependenceId = qDependenceDB.getQDependenceIdByQuestionId(questionId);
			response = qDependenceDB.removeQDependenceValue(qDependenceId);
			if(response)
				response = qDependenceDB.removeQDependence(qDependenceId);
		}
		catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
	}
	
}
