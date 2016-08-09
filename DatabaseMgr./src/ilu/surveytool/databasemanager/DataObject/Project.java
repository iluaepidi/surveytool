package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;

public class Project {
	
	int projectId;
	String name = "";
	Timestamp createDate;

	public Project() {
		super();
	}

	public Project(int projectId, String name, Timestamp createDate) {
		super();
		this.projectId = projectId;
		this.name = name;
		this.createDate = createDate;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", name=" + name + ", createDate=" + createDate + "]";
	}

}
