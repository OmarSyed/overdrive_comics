package com.example.demo.entity;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class ComicSeries {
	
	@Id
	private ObjectId _id;
	
	private boolean isPublished;
	private String genre;
	private String comicSeriesName;
	private String author;
	private String thumbnail;
	private HashMap<String, Double> rating; 
	private int likes;
	private double score;
	private String description;
	private int followers;
	private String day;
	private LocalDate date;
	
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
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public HashMap<String, Double> getRating() {
		return rating;
	}
	public void setRating(HashMap<String, Double> rating) {
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
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
