import java.util.ArrayList;

public class Driver {
	public static void main(String[] args) {
		
//		Driver.testRatingList();
		
		
		
		
		
		// Test movie recommender @Tested
		MovieRecommender recommender = new MovieRecommender();
		recommender.readUserRatings("movieRatings");
//		User u = recommender.findMostSimilarUser(0);
//		System.out.println(u.getId());
		
		
		// Compute recommendations for user = 0 @tested
		recommender.recommend(0, 5, "recommendations");
//		System.out.println();
		// Compute anti-recommendations for user = 0 @Tested
		recommender.antiRecommend(0, 5, "antiRecommendations");
		
	}
	public static void testRatingList(){
		//test insert by rating()
		RatingsList list = new RatingsList();
		list.insertByRating("A", 2);
		list.insertByRating("B", 3);
		list.insertByRating("C", 5);
		list.insertByRating("E", 1);
		list.insertByRating("F", 4);
		
//		list.print();
//		System.out.println("##########");
		//-----------------------------
		
		// test reverse()
//		RatingsList l = list.reverse();
		
		//------------------------------
		
		//test Find()
//		MovieRatingNode tmp;
//		tmp = list.find("A");
//		System.out.println(tmp);
//		tmp = list.find("E");
//		System.out.println(tmp);
		
		//------------------------------
		
		//test setRating()
//		list.setRating("A", 5);
//		list.setRating("E", 2);
//		list.setRating("C", 3);
//		list.setRating("F", 1);
//		list.print();
		
		//------------------------------
		
		//test getRating()
//		System.out.println(list.getRating("A")+ "/"+list.getRating("B")+"/"+list.getRating("C"));
		
		//------------------------------
		
		//test computeSimilarity()
		RatingsList list2= new RatingsList();
		list2.insertByRating("E", 3);
		list2.insertByRating("F", 4);
		list2.insertByRating("J", 5);
		list2.insertByRating("G", 1);
		list2.insertByRating("H", 5);
		list2.insertByRating("I", 2);
		list2.print();
		System.out.println("#############");
//		double i = list.computeSimilarity(list2);
//		System.out.println(i);
		//-------------------------------
		
		//test sublist()
//		RatingsList sub2 = list2.sublist(5, 4);
//		sub2.print();
//		System.out.println("#############");
		
		//-------------------------------
		
		//test getMiddleNode()
//		MovieRatingNode midnode = list2.getMiddleNode();
//		System.out.println(midnode);
		
		//-------------------------------

		//test getMedianRating()
		
//		System.out.println(list2.getMedianRating());
		
		//-------------------------------
		
		//test getNBestRankedMovies()
//		list=list2.getNBestRankedMovies(0);
//		list.print();
//		System.out.println("#############");
		
		
		//-------------------------------
		
		//test getNWorstRankedMovies()
//		list =list2.getNWorstRankedMovies(0);
//		list.print();
//		System.out.println("#############");
		
		//-------------------------------
		
		//test getTitles()
//		printArrayList(list2.getTitles());
//		System.out.println("####################");
	}
	
	
	public static void printArrayList(ArrayList<String> lst){
		for (int i = 0; i < lst.size(); i++) {
			System.out.println(lst.get(i));
		}
	}
}
