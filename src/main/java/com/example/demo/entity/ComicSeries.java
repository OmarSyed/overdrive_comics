package com.example.demo.entity;

import java.sql.Blob;
import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class ComicSeries {
	
	@Id
	private ObjectId seriesId;
	private boolean isPublished;
	private String genre;
	private String comicSeriesName;
	private Users author;
	private Blob thumbnail;
	private double rating;
	private String description;
	private int followers;
	private ArrayList<String> chapters;	//Change type of ArrayList to comic chapter
	
	public ObjectId getSeriesId() {
		return seriesId;
	}
	public void setSeriesId(ObjectId seriesId) {
		this.seriesId = seriesId;
	}
	public boolean isPublished() {
		return isPublished;
	}
	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getComicSeriesName() {
		return comicSeriesName;
	}
	public void setComicSeriesName(String comicSeriesName) {
		this.comicSeriesName = comicSeriesName;
	}
	public Users getAuthor() {
		return author;
	}
	public void setAuthor(Users author) {
		this.author = author;
	}
	public Blob getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Blob thumbnail) {
		this.thumbnail = thumbnail;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFollowers() {
		return followers;
	}
	public void setFollowers(int followers) {
		this.followers = followers;
	}
	public ArrayList<String> getChapters() {
		return chapters;
	}
	public void setChapters(ArrayList<String> chapters) {
		this.chapters = chapters;
	}
	
}
