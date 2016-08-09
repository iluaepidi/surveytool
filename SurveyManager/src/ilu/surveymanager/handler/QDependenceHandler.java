package ilu.surveymanager.handler;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ilu.surveymanager.data.QDependence;
import ilu.surveymanager.data.QDependenceValue;
import ilu.surveytool.databasemanager.QDependenceDB;

public class QDependenceHandler {

	public QDependenceHandler() {
		super();
	}
	
	public String saveQDependence(QDependence qdependence)
	{
		QDependenceDB qDependenceDB= new QDependenceDB();
		JSONObject response = new JSONObject();
		QDependenceValue qdepvale = qdependence.getqdepval().get(0);
		
		try {
			
			if(qdepvale.getOid()>0){
			
				if(qdependence.getId() == 0)
				{
					int qdependenceId = qDependenceDB.insertQDependence(qdependence.getQuestionId(), qdependence.getDependenceType());
					qdependence.setId(qdependenceId);
					int qDependeceItemId = qDependenceDB.insertQDependenceValue(qdependenceId, qdepvale.getQid(), qdepvale.getOgid(), String.valueOf(qdepvale.getOid()));
					response.put("qdependenceId", String.valueOf(qdependenceId));
					response.put("qDependeceItemId", String.valueOf(qDependeceItemId));
				}
				else if(qdepvale.getItemId() >= 0)
				{
					qDependenceDB.updateQDependence(qdependence.getId(), qdepvale.getQid(), qdepvale.getOgid(), qdepvale.getOid(), qdependence.getDependenceType());
				}
				else
				{
					int qDependeceItemId = qDependenceDB.insertQDependenceValue(qdependence.getId(), qdepvale.getQid(), qdepvale.getOgid(), String.valueOf(qdepvale.getOid()));
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
			qDependenceDB.updateQDependenceType(id, dependenceType);
		}
	}
	
	public void removeQDependence(int qDependenceId)
	{
		QDependenceDB qDependenceDB = new QDependenceDB();
		qDependenceDB.removeQDependenceValue(qDependenceId);
		qDependenceDB.removeQDependence(qDependenceId);
	}
	
	public void removeAllQDependences(int questionId)
	{
		try {
			QDependenceDB qDependenceDB= new QDependenceDB();
			int qDependenceId = qDependenceDB.getQDependenceByQuestionId(questionId).getId();
			qDependenceDB.removeQDependenceValue(qDependenceId);
			qDependenceDB.removeQDependence(qDependenceId);
		}
		catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
