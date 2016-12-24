package org.edu.persistence.dto;

/**
 * Maps the data from DB to Java data transfer objects.
 * 
 * @author shivam.maharshi
 */
public class Movie {

	private String title;
	private int year;
	private String description;
	private String mmpaRating;
	private String dustinRating;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMmpaRating() {
		return mmpaRating;
	}

	public void setMmpaRating(String mmpaRating) {
		this.mmpaRating = mmpaRating;
	}

	public String getDustinRating() {
		return dustinRating;
	}

	public void setDustinRating(String dustinRating) {
		this.dustinRating = dustinRating;
	}

	public Movie() {
		super();
	}

	public Movie(String title, int year, String description, String mmpaRating, String dustinRating) {
		super();
		this.title = title;
		this.year = year;
		this.description = description;
		this.mmpaRating = mmpaRating;
		this.dustinRating = dustinRating;
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + ", description=" + description + ", mmpaRating="
				+ mmpaRating + ", dustinRating=" + dustinRating + "]";
	}

}
