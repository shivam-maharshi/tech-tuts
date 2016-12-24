package org.edu.persistence;

import java.util.Optional;

import org.edu.persistence.dao.MoviePersistence;
import org.edu.persistence.dao.Persistence;
import org.edu.persistence.dto.Movie;

/**
 * Test client that performs operations using other data access and persistence
 * classes.
 * 
 * @author shivam.maharshi
 */
public class TestClient {

	public static void main(String[] args) {
		DataAccess da = new DataAccess("127.0.0.1", 9042);
//		dbo.createKeyspace("movies_keyspace");
//		String createMovieCql = "CREATE TABLE movies_keyspace.movies (title varchar, year int, description varchar, "
//				+ "mmpa_rating varchar, dustin_rating varchar, PRIMARY KEY (title, year))";
//		dbo.executeQuery(createMovieCql);
//		Movie movie = new Movie("Inception", 2010, "Dream within a Dream.", "4.5", "3.9");
//		Persistence<Movie> mp = new MoviePersistence(dbo);
//		mp.create(movie);
//		Optional<Movie> movieRes = mp.read(new Movie("Inception", 2010, "Taco inside a Taco", null, null));
//		if (movieRes.isPresent())
//			System.out.println("Movie fetched from DB: " + movieRes.get());
//		mp.update(new Movie("Inception", 2010, "Taco inside", null, null));
//		mp.delete(new Movie("Inception", 2010, null, null, null));
//		da.describe("movies_keyspace");
		// Add a list type column into table. Similarly Maps & Sets can be added too.
//		da.executeQuery("ALTER TABLE movies_keyspace.movies ADD email list<text>");
//		da.executeQuery("CREATE INDEX my_rating ON movies_keyspace.movies (my_rating)");
		// Executing queries in batch.
//		da.executeQuery("BEGIN BATCH INSERT INTO movies_keyspace.movies (title, year) VALUES ('1', 2000); INSERT INTO movies_keyspace.movies (title, year) VALUES ('2', 2000); APPLY BATCH;");
		// Insert data into a list column.
//		da.executeQuery("BEGIN BATCH INSERT INTO movies_keyspace.movies (title, year, email) VALUES ('3', 2000, ['shivam.maharshi@gmail.com','shivam1@vt.edu']); APPLY BATCH;");
		da.close();
	}

}
