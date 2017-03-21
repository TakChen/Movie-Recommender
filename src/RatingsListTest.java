import java.util.Iterator;

public class RatingsListTest {

	public static void main(String[] args) {

		// First, test methods of RatingsList separately
		// Note: the tests rely on the iterator working. If it does not work,
		// the program will exit
		RatingsList ratings = initalizeListWithValues();

		System.out.println("------------------Testing iterator ---------------------------");
		if (testIterator(ratings))
			System.out.println(">>>Good news: iterator test passed.\n");
		else {
			System.out.println("Exiting the test: since iterator is not working, other tests will fail too..");
		}

		System.out.println("------------------Testing insertByRating ---------------------");
		testInsertByRating(ratings);

		System.out.println("------------------Testing setRating ---------------------");
		testSetRating(ratings);

		System.out.println("------------------Testing find ---------------------");
		if (testFind(ratings, "Harry Potter") && testFind(ratings, "Twilight"))
			System.out.println(">>>Good news: find test passed.\n");

		System.out.println("------------------Testing computeSimilarity ---------------------");
		testComputeSimilarity();

		// sublist
		System.out.println("\n------------------Testing subslist ---------------------");
		testSublist(2, 4);

		System.out.println("\n------------------Testing getMedianRating ---------------------");
		testMedianRating(ratings);

		// reverse
		System.out.println("\n------------------Testing reverse -----------------------");
		testReverse();

		// getNBestRankedMovies
		System.out.println("\n------------------Testing getNBestRankedMovies -----------------------");
		testNBestRankedMovies();

		// getNWorstRankedMovies
		System.out.println("\n------------------Testing getNWorstRankedMovies -----------------------");
		testNWorstRankedMovies();
	}

	// Iterate over ratings and return true if the list is sorted by rating from
	// largest to smallest
	public static boolean isSortedDescending(RatingsList ratings) {

		Iterator<MovieRatingNode> it = ratings.iterator();
		int prevMovieRating = 6;
		while (it.hasNext()) {
			MovieRatingNode curr = it.next();
			// System.out.println(curr);
			if (!(curr.getMovieRating() <= prevMovieRating)) {
				return false;
			}
		}
		return true;
	}

	public static int numNodes(RatingsList ratings) {
		int count = 0;
		Iterator<MovieRatingNode> it = ratings.iterator();
		while (it.hasNext()) {
			it.next();
			count++;
		}
		return count;
	}

	public static boolean testIterator(RatingsList ratings) {
		MovieRatingNode curr = ratings.head();
		Iterator<MovieRatingNode> it = ratings.iterator();
		while (it.hasNext()) {
			if (curr != it.next()) {
				System.out.println(">>>Test failed in testIterator: Iterator in RatingsList does not work correctly. ");
				return false;
			}
			curr = curr.next();
		}
		if (curr == null)
			return true;
		else {
			System.out.println(">>>Test failed in testIterator: hasNext() returns false when we still have nodes. ");
			return false; // hasNext returned false, but we still have nodes
		}
	}

	public static void testInsertByRating(RatingsList ratings) {
		if ((numNodes(ratings) == 7) && isSortedDescending(ratings))
			System.out.println(">>>Good news: insertByRating test passed.\n");
		else if (numNodes(ratings) != 7)
			System.out.println(">>>Test failed in insertByRating: number of nodes is incorrect after insertion. ");
		else
			System.out.println(
					">>>Test failed in insertByRating: nodes are not sorted by rating from largest to smallest. ");

	}

	public static void testSetRating(RatingsList ratings) {
		ratings.setRating("Harry Potter", 2);
		ratings.setRating("Ocean twelve", 5);
		ratings.setRating("Matrix", 1);
		if ((numNodes(ratings) == 7) && isSortedDescending(ratings))
			System.out.println(">>>Good news: setRating test passed.\n");
		else if (numNodes(ratings) != 7)
			System.out.println(">>>Test failed in setRating: number of nodes is incorrect after changing the rating ");
		else
			System.out
					.println(">>>Test failed in setRating: nodes are not sorted by rating from largest to smallest. ");
	}

	public static boolean testFind(RatingsList ratings, String title) {
		MovieRatingNode node = ratings.find(title);
		if (node == null || (!node.getMovieTitle().equals(title))) {
			System.out.println(">>>Test failed in find: could not find a given title: " + title + " in the list.");
			return false;
		} else
			return true;
	}

	public static void testComputeSimilarity() {
		RatingsList list1 = new RatingsList();
		list1.insertByRating("Harry Potter", 5);
		list1.insertByRating("Matrix", 5);
		list1.insertByRating("Twilight", 2);
		list1.insertByRating("Gone with the wind", 5);
		list1.insertByRating("Ocean twelve", 3);

		RatingsList list2 = new RatingsList();
		list2.insertByRating("Hunger Games", 1);
		list2.insertByRating("Matrix", 4);
		list2.insertByRating("The Lord of the Rings", 5);
		list2.insertByRating("Gone with the wind", 2);
		list2.insertByRating("Love Actually ", 4);
		list2.insertByRating("Ocean twelve", 5);

		double a = list1.computeSimilarity(list2);
		if (Math.abs(a - 0.514) > 0.005) {
			System.out.println(">>>Test failed: in computeSimilarity.");
			System.out.println("In computeSimilarity: expected value: " + 0.514 + ",  Your code returned: " + a);
		} else
			System.out.println(">>>Good news: passed computeSimilarity test");
		/*
		 *  Matrix: 5 * 4 + Gone with the wind: 5*2 + Ocean twelve: 3 * 5 = 45
		 *  v1 = 5, 5, 2, 5, 3,  length(v1) = sqrt(25 + 25 + 4 + 25 + 9) =
		 *  sqrt(75 + 13) = sqrt(88) = 9.38
		 *  v2 = 1, 4, 5, 2, 4, 5 .length(v2) = sqrt(1+16+25+4+16+25) = sqrt(46+41) =sqrt(87) = 9.33
		 *  total = 45 / length (v1) * length(v2) = 45 / (9.38*9.33) = 45/87.51 = 0.514 
		 */
	}

	public static void testSublist(int i, int j) {
		RatingsList ratings = initalizeListWithValues();
		RatingsList newList = ratings.sublist(i, j);
		Iterator<MovieRatingNode> it = newList.iterator();
		int prevMovieRating = 6;
		while (it.hasNext()) {
			MovieRatingNode m = it.next();
			// System.out.println(m);
			if (m.getMovieRating() < i || m.getMovieRating() > j) {
				System.out.println(">>>Test failed: in testSublist ");
				System.out.println("Expected: ");
				System.out.println("Got: ");
				newList.print();
				return;
			}
			if (!(m.getMovieRating() <= prevMovieRating)) {
				System.out.println(">>>Test failed: in testSublist ");
				System.out.println("The sublist is not sorted.");
				newList.print();
				return;
			}

		} // while
		System.out.println(">>>Good news: passed testSublist test");

	}

	// Builds a list with some default hard coded values.
	// should ideally take a list of strings/ratings as a parameter
	public static RatingsList initalizeListWithValues() {
		RatingsList ratings = new RatingsList();
		ratings.insertByRating("Harry Potter", 5);
		ratings.insertByRating("Matrix", 5);
		ratings.insertByRating("Twilight", 2);
		ratings.insertByRating("Gone with the wind", 5);
		ratings.insertByRating("Ocean eleven", 4);
		ratings.insertByRating("Ocean twelve", 3);
		ratings.insertByRating("Casablanca", 4);
		ratings.print();
		return ratings;
	}

	public static void testMedianRating(RatingsList list) {
		int i = list.getMedianRating();
		if (i != 4) {
			System.out.println(">>>Test failed: in testMedianRating. ");
			System.out.println("Expected 4, got: " + i);
			list.print();
		} else {
			System.out.println(">>>Good news: passed testMedianRating test");
		}
	}

	public static void testReverse() {
		RatingsList ratings = new RatingsList();
		ratings.insertByRating("Gone with the wind", 5);
		ratings.insertByRating("Ocean eleven", 4);
		ratings.insertByRating("Ocean twelve", 3);
		ratings.insertByRating("Casablanca", 1);

		RatingsList reversed = ratings.reverse();
		if (numNodes(reversed) != 4) {
			System.out.println(
					">>> Test failed: in testReverse. The number of nodes in a reversed list is not the same as in the original list.");
			return;
		}
		Iterator<MovieRatingNode> it = reversed.iterator();
		if (!(it.next().getMovieTitle()).equals("Casablanca") || !(it.next().getMovieTitle()).equals("Ocean twelve")
				|| !(it.next().getMovieTitle()).equals("Ocean eleven")
				|| !(it.next().getMovieTitle()).equals("Gone with the wind")) {

			System.out.println(">>> Test failed: in testReverse.");
			return;

		}
		System.out.println(">>>Good news: passed testReverse");
	}

	public static void testNBestRankedMovies() {
		RatingsList list = new RatingsList();
		list.insertByRating("Harry Potter", 5);
		list.insertByRating("Ocean eleven", 4);
		list.insertByRating("Matrix", 5);
		list.insertByRating("Casablanca", 2);
		RatingsList res = list.getNBestRankedMovies(2);
		if (numNodes(res) != 2) {
			System.out.println(
					">>> Test failed: in testNBestRankedMovies. It was supposed to return a list with 2 nodes");
			return;
		}
		boolean b1 = res.head().getMovieTitle().equals("Matrix");
		boolean b2 = res.head().next().getMovieTitle().equals("Harry Potter");
		boolean b3 = res.head().getMovieTitle().equals("Harry Potter");
		boolean b4 = res.head().next().getMovieTitle().equals("Matrix");
		if ((b1 && b2) || (b3 && b4))
			System.out.println(">>>Good news: testNBestRankedMovies passed");
		else {
			System.out.println(">>> Test failed: in testNBestRankedMovies. ");
			System.out.println("The test list was: ");
			list.print();
			System.out.println("For the provided test, expected Matrix and HarryPotter, but got:");
			res.print();

		}
	}
	
	public static void testNWorstRankedMovies() {
		RatingsList list = new RatingsList();
		list.insertByRating("Harry Potter", 5);
		list.insertByRating("Ocean eleven", 4);
		list.insertByRating("Matrix", 5);
		list.insertByRating("Casablanca", 2);
		RatingsList res = list.getNWorstRankedMovies(2);
		if (numNodes(res) != 2) {
			System.out.println(
					">>> Test failed: in testNWorstRankedMovies. It was supposed to return a list with 2 nodes");
			return;
		}
		boolean b1 = res.head().getMovieTitle().equals("Ocean eleven");
		boolean b2 = res.head().next().getMovieTitle().equals("Casablanca");
		if (b1 && b2)
			System.out.println(">>>Good news: testNWorstRankedMovies passed");
		else {
			System.out.println(">>> Test failed: in testNWorstRankedMovies. ");
			System.out.println("The test list was: ");
			list.print();
			System.out.println("For the provided test, expected Ocean eleven and Casablanca, but got:");
			res.print();
			
		}

	}
}
