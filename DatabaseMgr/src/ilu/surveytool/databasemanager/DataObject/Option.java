package ilu.surveytool.databasemanager.DataObject;

import java.util.HashMap;

public class Option {
	
	int id;
	HashMap<String, Content> contents;
	int index;

	public Option() {
		contents = new HashMap<String, Content>();
	}
	
	public Option(int id, HashMap<String, Content> contents, int index) {
		super();
		this.id = id;
		this.contents = contents;
		this.index = index;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HashMap<String, Content> getContents() {
		System.out.println("Obtener contenidos");
		return contents;
	}

	public void setContents(HashMap<String, Content> contents) {
		this.contents = contents;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", contents=" + contents + ", index=" + index + "]";
	}
	
}
