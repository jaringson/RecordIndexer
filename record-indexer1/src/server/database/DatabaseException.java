package server.database;

/**
 * This class extends Exception and makes it so when databases are switched out,
 * the program still throws the same errors.
 * 
 * @extends Exception
 * @author jaronce
 *
 */

@SuppressWarnings("serial")
public class DatabaseException extends Exception {

	public DatabaseException() {
		return;
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(Throwable cause) {
		super(cause);

	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}

