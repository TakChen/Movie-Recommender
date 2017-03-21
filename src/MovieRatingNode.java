/** The class that represents a single node in the RatingsList */

public class MovieRatingNode {
	
	private String movieTitle; // the title of the movie
	private int movieRating; // ranges from 1 to 5
	private MovieRatingNode next; 

	MovieRatingNode(String title, int rating) {
		movieTitle = title;
		next = null;

		if (rating < 1 || rating > 5) {
			System.out.println("Invalid rating. Using a default value of 3.");
			movieRating = 3;
		}
		else	
			movieRating = rating;
	}

	MovieRatingNode(String title, int rating, MovieRatingNode next) {
		this(title, rating);
		this.next = next;
	}
	
	public MovieRatingNode next() {
		return next;
	}

	public void setNext(MovieRatingNode anotherNode) {
		this.next = anotherNode;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String s) {
		movieTitle = s;
	}

	public int getMovieRating() {
		return movieRating;
	}

	public void setMovieRating(int newrating) {
		movieRating = newrating;
	}
	
	public String toString() {
		return movieTitle + ", " + movieRating;
	}
}
