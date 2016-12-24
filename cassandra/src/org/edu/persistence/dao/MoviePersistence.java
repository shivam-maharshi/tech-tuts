package org.edu.persistence.dao;

import java.util.Optional;

import org.edu.persistence.CassandraConnector;
import org.edu.persistence.DataAccess;
import org.edu.persistence.dto.Movie;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

/**
 * Performs DB operations on the Movie type objects in the DB.
 * 
 * @author shivam.maharshi
 */
public class MoviePersistence implements Persistence<Movie> {

	private DataAccess dao;

	public MoviePersistence(DataAccess dao) {
		super();
		this.dao = dao;
	}

	@Override
	public ResultSet create(Movie movie) {
		ResultSet rs = dao.getSession().execute(
				"INSERT INTO movies_keyspace.movies (title, year, description, mmpa_rating, dustin_rating) VALUES (?, ?, ?, ?, ?)",
				movie.getTitle(), movie.getYear(), movie.getDescription(), movie.getMmpaRating(),
				movie.getDustinRating());
		if (rs.isExhausted())
			System.out.println("Successfully created movie.");
		return rs;
	}

	@Override
	public Optional<Movie> read(Movie movie) {
		ResultSet rs = dao.getSession().execute("SELECT * from movies_keyspace.movies WHERE title = ? AND year = ?",
				movie.getTitle(), movie.getYear());
		Row movieRow = rs.one();
		Optional<Movie> movieRes = movieRow != null ? Optional
				.of(new Movie(movieRow.getString("title"), movieRow.getInt("year"), movieRow.getString("description"),
						movieRow.getString("mmpa_rating"), movieRow.getString("dustin_rating")))
				: Optional.empty();
		return movieRes;
	}

	@Override
	public ResultSet update(Movie movie) {
		ResultSet rs = dao.getSession().execute("UPDATE movies_keyspace.movies SET description = ? WHERE title = ? and year = ?",
				movie.getDescription(), movie.getTitle(), movie.getYear());
		if (rs.isExhausted())
			System.out.println("Successfully updated movie.");
		return rs;
	}

	@Override
	public ResultSet delete(Movie movie) {
		ResultSet rs = dao.getSession().execute("DELETE FROM movies_keyspace.movies WHERE title = ? and year = ?",
				movie.getTitle(), movie.getYear());
		if (rs.isExhausted())
			System.out.println("Successfully deleted movie.");
		return rs;
	}

}
