package com.example.demo.entity;

import java.sql.Blob;

import java.util.ArrayList;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class ComicSeries {
	
	@Id
	private ObjectId _id;
	
	private boolean isPublished;
	private String genre;
	private String comicSeriesName;
	private String author;
	private Blob thumbnail;
	private HashMap<String, Integer> rating; 
	private double score;
	private String description;
	private int followers;
	private ArrayList<String> chapters;	//Change type of ArrayList to comic chapter
	private boolean isFollowed;
	
	
	public String getSeriesId() {
		return _id.toHexString();
	}
	public void setSeriesId(ObjectId _id) {
		this._id = _id;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Blob getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Blob thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public HashMap<String, Integer> getRating() {
		return rating;
	}
	public void setRating(HashMap<String, Integer> rating) {
		this.rating = rating;
	}
	
	
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
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
	public boolean isFollowed() {
		return isFollowed;
	}
	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
	
}
