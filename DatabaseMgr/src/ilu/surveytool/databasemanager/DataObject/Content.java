package ilu.surveytool.databasemanager.DataObject;

public class Content {

	int contentId;
	String language = "";
	String contentType = "";
	String text = "";
	int index = 0;
	
	public Content() {
		super();
	}

	public Content(int contentId, String language, String contentType, String text) {
		super();
		this.contentId = contentId;
		this.language = language;
		this.contentType = contentType;
		this.text = text;
	}

	public Content(int contentId, String language, String contentType, String text, int index) {
		super();
		this.contentId = contentId;
		this.language = language;
		this.contentType = contentType;
		this.text = text;
		this.index = index;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Content [contentId=" + contentId + ", language=" + language + ", contentType=" + contentType + ", text="
				+ text + ", index=" + index + "]";
	}
	
}
