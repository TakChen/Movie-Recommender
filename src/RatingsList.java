
/**
 * A class that stores movie ratings for a user in a singly linked list of
 * MovieRatingNode objects. Has various methods to manipulate the list. Stores
 * only the head of the list (no tail!). The list should be sorted by
 * rating (from highest to smallest).
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RatingsList implements Iterable<MovieRatingNode> {

	private MovieRatingNode head;

	public MovieRatingNode head() {
		return head;
	}

	/**
	 * Returns the reference to the node that contains the given movie title or
	 * null if such node does not exit.
	 * 
	 * @param movieTitle
	 * @return a node with given title or NULL
	 */
	public MovieRatingNode find(String movieTitle) {
		// travel whole list found/not found
		MovieRatingNode temp = head;

		while (temp != null) {
			if (temp.getMovieTitle().compareTo(movieTitle) == 0)
				return temp;
			temp = temp.next();
		}
		return temp;
	}

	/**
	 * Changes the rating for a given movie to newRating.The position of the
	 * node within the list should be changed accordingly, so that the list
	 * remains sorted by rating (from largest to smallest).
	 * 
	 * @param movieTitle
	 * @param newRating
	 */
	public void setRating(String movieTitle, int newRating) {
		/*
		 * travel whole list to find certain node need previous pointer 
		 * use find logic
		 * @tested
		 */
		MovieRatingNode temp = head;
		MovieRatingNode previous = null;
		while (temp != null) {
			if (temp.getMovieTitle().compareTo(movieTitle) == 0)
				break;
			previous = temp;
			temp = temp.next();
		}
		/*
		 * if we found the node-> temp!=null we can set rating and reorder
		 */
		if (temp != null) {
			// if we found in the head;
			if (previous == null && temp == head) {
				head = temp.next();
				temp.setNext(null);
				insertByRating(movieTitle, newRating);
			}
			// if we found in the end
			else if (temp.next() == null && temp != head) {
				previous.setNext(null);
				insertByRating(movieTitle, newRating);
			}
			// found in the middle
			else {
				previous.setNext(temp.next());
				temp.setNext(null);
				insertByRating(movieTitle, newRating);
			}
		}

	}

	/**
	 * Returns the rating for a given movie. If the movie is not in the list,
	 * returns -1.
	 */
	// @tested
	public int getRating(String movieTitle) {
		// use find method
		MovieRatingNode temp = this.find(movieTitle);
		if (temp != null)
			return temp.getMovieRating();
		return -1;

	}

	/**
	 * Inserts a new node (with a given movie and a given rating) into the list.
	 * Inserts it in the right place based on the value of the rating. Assume
	 * the list is sorted by the value of ratings, from highest to smallest. The
	 * list should remain sorted after this insert operation.
	 */
	/*
	 * travel whole list, compare rating, until...
	 * 
	 * @tested
	 */
	public void insertByRating(String movie, int rating) {
		MovieRatingNode newnode = new MovieRatingNode(movie, rating);
		// if it's empty list
		if (head == null) {
			head = newnode;
		}
		// none empty list
		else {
			MovieRatingNode previous = null;
			MovieRatingNode temp = head;
			while (temp != null) {
				if (newnode.getMovieRating() >= temp.getMovieRating()){
					newnode.setNext(temp);
					if (previous == null)// insert before head
						head = newnode;
					else 				 // insert after head
						previous.setNext(newnode);
				break;
				}
				if (temp.next() == null){
					temp.setNext(newnode);
					break;
				}
				previous = temp;
				temp = temp.next();
			}
		}
	}

	/**
	 * Computes the cosine similarity between two lists of ratings. Note: You
	 * are allowed to use a HashMap for this method.
	 */
	// @tested
	public double computeSimilarity(RatingsList otherList) {
		double similarity = 0;
		MovieRatingNode temp = head;
		double localLen = 0;
		HashMap<String, Integer> compareList = new HashMap<>();
		
		while (temp != null){
			localLen += temp.getMovieRating() * temp.getMovieRating();
			compareList.put(temp.getMovieTitle(), temp.getMovieRating());
			temp = temp.next();
		}
		temp = otherList.head();
		double remoteLen = 0;
		double dotProduct = 0;
		
		while (temp != null){
			remoteLen += temp.getMovieRating() * temp.getMovieRating();
			if (compareList.containsKey(temp.getMovieTitle()))
				dotProduct += compareList.get(temp.getMovieTitle()) * temp.getMovieRating();
			temp = temp.next();
		}
		
//		System.out.println("localLen = " + localLen);
//		System.out.println("remoteLen = " + remoteLen);
		localLen = Math.sqrt(localLen);
		remoteLen= Math.sqrt(remoteLen);
		similarity = dotProduct / (localLen * remoteLen);
		return similarity;

	}

	/**
	 * Returns a sublist of this list where the rating values are in the range
	 * from begRating to endRating, inclusive.
	 */
	// @tested
	public RatingsList sublist(int begRating, int endRating) {
		RatingsList res = new RatingsList();
		MovieRatingNode temp = head;
		
		while (temp != null){
			if (begRating > endRating) {
				if (begRating >= temp.getMovieRating() && temp.getMovieRating()>= endRating)
					res.insertByRating(temp.getMovieTitle(), temp.getMovieRating());
			}
			if (begRating < endRating){
				if (endRating >= temp.getMovieRating() && temp.getMovieRating()>= begRating)
					res.insertByRating(temp.getMovieTitle(), temp.getMovieRating());
			}
			if (begRating == endRating)
				if (begRating == temp.getMovieRating())
					res.insertByRating(temp.getMovieTitle(), temp.getMovieRating());
			temp = temp.next();
		}
		
		return res;
	}

	/**
	 * Returns the ArrayList of movie titles from this ratings list. The movies
	 * title should be in the ArrayList in the same order in which they were in
	 * the ratings list. (ordered by ratings, not titles)
	 */
	public ArrayList<String> getTitles() {
		ArrayList<String> titles = new ArrayList<String>();
		MovieRatingNode temp = head;
		
		while (temp != null){
			titles.add(temp.getMovieTitle());
			temp =temp.next();
		}
		return titles;
	}

	/** Traverses the list and prints each node */
	public void print() {
		MovieRatingNode temp = head;
		while (temp != null) {
			System.out.println(temp);
			temp = temp.next();
		}
	}

	/**
	 * Returns the middle node in the list - the one half way into the list.
	 * Needs to have the running time O(n), should be done in one pass. Hint:
	 * Use slow and fast pointers.
	 * 
	 * @return
	 */
	public MovieRatingNode getMiddleNode() {
		// talked about in class, use two pointer
		MovieRatingNode slow = head;
		MovieRatingNode fast = head;
		while (fast != null && fast.next() != null){
			fast = fast.next().next();
			slow = slow.next();
		}

		return slow; 

	}

	/**
	 * Returns the median rating (the number that is halfway into the sorted
	 * list). To compute it, find the middle node and return it's rating. If the
	 * middle node is null, return -1.
	 */
	public int getMedianRating() {
		// just get middle node
		MovieRatingNode temp = getMiddleNode();
		if (temp != null)
			return temp.getMovieRating();
		else 
			return -1;
	}

	/**
	 * Returns a RatingsList that contains n best rated movies. These are
	 * essentially first n movies from the beginning of the list. If the list is
	 * shorter than size n, it will return the whole list.
	 */
	// @Tested
	public RatingsList getNBestRankedMovies(int n) {
		// take 1st n node
		RatingsList result = new RatingsList();
		MovieRatingNode temp = head;
		int i =0;
		
		while (temp != null && i < n){
			result.insertByRating(temp.getMovieTitle(), temp.getMovieRating());
			temp = temp.next();
			i++;
		}

		return result;

	}

	/**
	 * Returns a RatingsList that contains n worst rated movies for this user.
	 * Essentially, these are the last n movies from the end of the list. Use
	 * slow & fast pointers to find the n-th node from the end (required). Note:
	 * This method should compute the result in one pass. Do NOT use reverse().
	 */
	// @Tested
	public RatingsList getNWorstRankedMovies(int n) {
		// use two pointer
		RatingsList result = new RatingsList();
		int i = 0;
		MovieRatingNode temp = head;
		MovieRatingNode slow = head;;
		
		while (temp != null){
			if (i >= n)
				slow = slow.next();
			temp = temp.next();
			i++;
		}
		
		while (slow != null){
			result.insertByRating(slow.getMovieTitle(), slow.getMovieRating());
			slow = slow.next();
		}
		
		return result;
	}

	/**
	 * Returns a new list that is the reverse of the original list. The returned
	 * list is sorted from lowest ranked movies to the highest rated movies
	 */
	// @Tested
	public RatingsList reverse() {
		RatingsList r = new RatingsList();
		MovieRatingNode temp = head;
		
		while (temp != null){
			r.insertHead(temp.getMovieTitle(), temp.getMovieRating());
			temp = temp.next();
		}
		
		return r;
	}
	/**
	 * A helper method for reverse(). this will simply insert node before
	 * head, and pushing head to left.
	 * @param title
	 * @param rating
	 */
	public void insertHead(String title, int rating){
		MovieRatingNode newnode = new MovieRatingNode(title, rating);
		// if it's empty list
		if (head == null)
			head = newnode;
		// none empty list
		else {
			newnode.setNext(head);
			head = newnode;
		}
	}

	@Override
	public Iterator<MovieRatingNode> iterator() {
		return new RatingsListIterator(0);
	}

	/**
	 * The iterator for the ratings list. Iterates over the MovieRatingNode-s of
	 * the list.
	 */
	private class RatingsListIterator implements Iterator<MovieRatingNode> {

		private MovieRatingNode curr;

		public RatingsListIterator(int index) {
			curr = head;
		}

		@Override
		public boolean hasNext() {
			if (curr != null)
				return true;
			return false;
		}

		@Override
		public MovieRatingNode next() {
			MovieRatingNode temp;
			if (curr == head){
				curr = curr.next();
				return head;
			}
			else{ // not head
				temp = curr;
				curr = curr.next();
				return temp;
			}
		}

	}

}
