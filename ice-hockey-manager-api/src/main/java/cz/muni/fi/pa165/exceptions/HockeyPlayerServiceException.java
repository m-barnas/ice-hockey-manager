package cz.muni.fi.pa165.exceptions;

/**
 * Exception thrown when any error occurs in Hockey Player service layer.
 *
 * @author Jakub Hruska jhruska@mail.muni.cz
 */
public class HockeyPlayerServiceException extends Exception {

	public HockeyPlayerServiceException(String msg, Throwable t) {
		super(msg, t);
	}

	public HockeyPlayerServiceException(String msg) {
		super(msg);
	}
}
