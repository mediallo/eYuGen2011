package dataStructures;

/**
 * @author Albert
 *
 */
public class Event {
	/**
	 * Number of events (messages) for the interest.
	 */
	public double numOfMessages;
	
	/**
	 * A set of categories for the interest. The number of path names is equal to
	 * the number of attributes in the event space.
	 */
	public Category[] categories;
	
	/**
	 * Constructor
	 * @param numOfAttri
	 */
	public Event(int numOfAttri) {
		numOfMessages = 0;
		categories = new Category[numOfAttri];
	}
}
