
/** The MovieRecommender class stores the ArrayList of all the users from the dataset,
 *  and has methods to read user data from the file, to compute recommendations and 
 *  anti-recommendations.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MovieRecommender {
	private ArrayList<User> users = new ArrayList<User>();

	/**
	 * Reads user ratings from the file and saves data for each user in the
	 * ArrayList called users. For each user, the ratings list will be sorted by
	 * rating (from largest to smallest).
	 */
	// @ somehow Tested
	public void readUserRatings(String filename) {
		String line = null;
		try (BufferedReader bf = new BufferedReader(new FileReader(filename));){
			while ((line = bf.readLine()) != null) {
				if (line.matches("\\d+ \\d+")){
					String[] idNumber = line.split(" ");
					RatingsList list = new RatingsList();
					User user = new User(Integer.parseInt(idNumber[0]), list);

					for (int i = 0; i < Integer.parseInt(idNumber[1]); i++) {
						line = bf.readLine();
						String[] titleRating =line.split("; ");
						list.insertByRating(titleRating[0], Integer.parseInt(titleRating[1]));
					}
//					user.print();
					users.add(user);
				}
			}

		} catch (IOException e) {
			System.out.println("readUserRatings has IOException");
			System.out.println(line);
		}
	}

	/**
	 * The method computes the similarity between the user with the given userid
	 * and all the other users. Finds the maximum similarity and returns the
	 * "most similar user".
	 */
	public User findMostSimilarUser(int userid) {
		// call computesimilarity() in Ratingslist
		User mostSimilarUser = null;
		double result = 0;
		User currUser = users.get(userid);
		
		for (int i = 0; i < users.size(); i++) {
			if (i != userid){
				double r = currUser.computeSimilarity(users.get(i));
				if (r >= result){
					result = r;
					mostSimilarUser = users.get(i);
				}
			}
		}
		
		
		return mostSimilarUser;

	}

	/**
	 * Computes up to num movie recommendations for the user with the given user
	 * id and prints these movie titles to the given file. First calls
	 * findMostSimilarUser and then getFavoriteMovies(num) method on the
	 * "most similar user" to get up to num recommendations. Filter them so that you only
	 * recommend movies the user with the given userid hasn't seen.
	 */
	/*
	 * NOTE: num will only get top5 of the favorite Movie
	 * the MOive is arrange by rating, not character
	 * thus the favorite movie list will be in random order
	 * everyone will get different result
	 */
	public void recommend(int userid, int num, String filename) {
		ArrayList<String> remote = findMostSimilarUser(userid).getFavoriteMovies(num);
		ArrayList<String> local = users.get(userid).getMovies();
		ArrayList<String> finalist = new ArrayList<>();
		
		
		for (int i = 0; i < remote.size(); i++) {
			boolean repeated = false;
			for (int j = 0; j < local.size(); j++) {
				if (remote.get(i).compareTo(local.get(j)) == 0){
					repeated = true;
				}
			}
			if (repeated == false)
				finalist.add(remote.get(i));
		}
		// write to file
		printToFile(finalist, filename);
	}

	/**
	 * Computes up to num movie anti-recommendations for the user with the given
	 * user id and prints these movie titles to the given file. Thre the
	 * movies the user should avoid. First calls findMostSimilarUser and then
	 * getLeastFavoriteMovies(num) method on the "most similar user" toese a get up
	 * to num movies the most similar user strongly disliked. Filter them so that you only
	 * list movies the user with the given userid hasn't seen.
	 */
	public void antiRecommend(int userid, int num, String filename) {
		ArrayList<String> remote = findMostSimilarUser(userid).getLeastFavoriteMovies(num);
		ArrayList<String> local = users.get(userid).getMovies();
		ArrayList<String> finalist = new ArrayList<>();
		
		
		for (int i = 0; i < remote.size(); i++) {
			boolean repeated = false;
			for (int j = 0; j < local.size(); j++) {
				if (remote.get(i).compareTo(local.get(j)) == 0){
					repeated = true;
				}
			}
			if (repeated == false)
				finalist.add(remote.get(i));
		}
		// write to file
		printToFile(finalist, filename);
	}
	
	
	private void printToFile(ArrayList<String> input, String filename){
		try (PrintWriter pw = new PrintWriter(filename);){
			for (int i = 0; i < input.size(); i++) {
				pw.println(input.get(i));
			}
			pw.flush();
			
		} catch (FileNotFoundException e) {
			System.out.println(this.getClass().getName() + " printToFile");
		}
	}
}
