package ilu.surveytool.databasemanager.DataObject;

import java.sql.Timestamp;

public class PollTableInfo {
	
	int pollId;
	String title = "";
	String publicUrl = "";
	Timestamp deadLineDate;

	public PollTableInfo() {
		super();
	}

	public PollTableInfo(int pollId, String title, Timestamp deadLineDate, String publicUrl) {
		super();
		this.pollId = pollId;
		this.title = title;
		this.publicUrl = publicUrl;
		this.deadLineDate = deadLineDate;
	}

	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPublicUrl() {
		return publicUrl;
	}

	public void setPublicUrl(String publicUrl) {
		this.publicUrl = publicUrl;
	}

	public Timestamp getDeadLineDate() {
		return deadLineDate;
	}

	public void setDeadLineDate(Timestamp deadLineDate) {
		this.deadLineDate = deadLineDate;
	}

	@Override
	public String toString() {
		return "PollTableInfo [pollId=" + pollId + ", title=" + title + ", deadLineDate=" + deadLineDate + "]";
	}
	
}
