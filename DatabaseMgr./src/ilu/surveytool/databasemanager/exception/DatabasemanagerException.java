package ilu.surveytool.databasemanager.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

public class DatabasemanagerException extends Exception {

	private static final long serialVersionUID = -3730923823271186475L;
	private Exception encapsulatedException;

	public DatabasemanagerException(Exception ex) {
		encapsulatedException = ex;
	}

	public String getMessage() {
		return encapsulatedException.getMessage();
	}

	public Exception getEncapsulatedException() {
		return encapsulatedException;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream printStream) {
		super.printStackTrace(printStream);
		printStream.println("***Information about encapsulated exception***");
		encapsulatedException.printStackTrace(printStream);
	}

	public void printStackTrace(PrintWriter printWriter) {
		super.printStackTrace(printWriter);
		printWriter.println("***Information about encapsulated exception***");
		encapsulatedException.printStackTrace(printWriter);
	}
}
