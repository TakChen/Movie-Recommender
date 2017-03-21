
/** A class that stores the user id and the the user's movie ratings */
import java.util.ArrayList;

public class User {
	private int userId;
	private RatingsList movieRatings;

	public User(int id, RatingsList list) {
		userId = id;
		movieRatings = list;
	}

	public int getId() {
		return userId;
	}

	public void print() {
		System.out.println(userId + "\n ");
		movieRatings.print();

	}

	/**
	 * Returns the Arraylist of all the movie titles from the movieRatings list.
	 * These are all the movies that the user has seen.
	 */
	public ArrayList<String> getMovies() {
		return movieRatings.getTitles();

	}

	/**
	 * Returns the list of the user's favorite movies (up to n). These are the
	 * movies that this user gave the rating of 5.
	 * 
	 * @param n
	 * @return
	 */
	public ArrayList<String> getFavoriteMovies(int n) {
		RatingsList best = movieRatings.getNBestRankedMovies(n).sublist(5, 5);
		// best.print();
		if (best != null)
			return best.getTitles();
		else
			return null;
	}

	/**
	 * Returns the list of the movies the user likes the least (up to n). These
	 * are the movies that this user gave the rating of 1.
	 * 
	 * @param n
	 * @return
	 */
	public ArrayList<String> getLeastFavoriteMovies(int n) {
		RatingsList worst = movieRatings.getNWorstRankedMovies(n).sublist(1, 1);
		// worst.print();
		if (worst != null)
			return worst.getTitles();
		else
			return null;
	}

	/**
	 * Computes the cosine similarity of this user with the given "other" user.
	 */
	public double computeSimilarity(User otherUser) {
		return movieRatings.computeSimilarity(otherUser.movieRatings);
	}

	/** Changes the rating for the given movie to newRating */
	public void setRating(String movieTitle, int newRating) {
		movieRatings.setRating(movieTitle, newRating);
	}

	/** Returns this user's rating for a given movie */
	public int getRating(String movieTitle) {
		return movieRatings.getRating(movieTitle);
	}
}
