package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ComicSeries;
import com.example.demo.entity.Users;
import com.example.demo.repository.SeriesRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/series")
public class ComicSeriesController {
	
	@Autowired
	private SeriesRepository seriesrepository;
	@Autowired
	private UserRepository userrepository;
	
	
	//Create a series, add it to mongo collection
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String createSeries(@Valid @RequestBody ComicSeries series) {
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		if(check.isEmpty()){
			seriesrepository.save(series);
			return "success";
		}
		for (int i = 0; i < check.size(); i++) {
			if(check.get(i).getComicSeriesName().equals(series.getAuthor())) {
				return "failure";
			}
		}
		seriesrepository.save(series);
		return "success";
	}
	
	//publish or unpublish a series
	@RequestMapping(value="/publish", method = RequestMethod.POST)
	public ComicSeries publishSeries(@Valid @RequestBody ComicSeries series) {
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		//System.out.println(series.getAuthor());
		System.out.println(series.getAuthor());
		for(int i = 0; i<check.size(); i++) {
			if(check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				//System.out.println(series.getAuthor());
				if(check.get(i).isPublished()) {
					check.get(i).setPublished(false);
					seriesrepository.save(check.get(i));
					return check.get(i);
				}else {
					check.get(i).setPublished(true);
					seriesrepository.save(check.get(i));
					return check.get(i);
				}
			}
		}return null;
	}
	
	//return the series under a user
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public List<ComicSeries> userSeries(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getName());
		return seriesrepository.findByAuthor(auth.getName());
	}
	
	//return series under a author
	@RequestMapping(value="/author", method = RequestMethod.GET)
	public List<ComicSeries> authorSeries(@RequestParam String username){
		return seriesrepository.findByAuthor(username);
	}
	
	//return a specific series a user selected
	@RequestMapping(value="/title", method = RequestMethod.GET)
	public ComicSeries specficSeries(@RequestParam String title, @RequestParam String author) {
		List<ComicSeries> check = seriesrepository.findByAuthor(author);
		for(int i = 0; i<check.size(); i++) {
			if(check.get(i).getComicSeriesName().equals(title)) {
				return check.get(i);
			}
		}return null;
		//return seriesrepository.findById(series.getSeriesId());
	}
	
	//show series of specific genre
	@RequestMapping(value="genre", method = RequestMethod.GET)
	public List<ComicSeries> genreSeries(){
		List<ComicSeries> genres = seriesrepository.findByGenre("Action");
		genres.addAll(seriesrepository.findByGenre("Fantasy"));
		genres.addAll(seriesrepository.findByGenre("Comedy"));
		genres.addAll(seriesrepository.findByGenre("Drama"));
		genres.addAll(seriesrepository.findByGenre("Sports"));
		//genres.addAll(seriesrepository.findByGenre("Thriller"));
		//genres.addAll(seriesrepository.findByGenre("Adventure"));
		return genres;
	}
	
	//Users follows a series
	@RequestMapping(value="/follow", method = RequestMethod.POST)
	public ComicSeries followSeries(@Valid @RequestBody ComicSeries series) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Users user = userrepository.findByUsername(auth.getName());
		System.out.println(auth.getName());
		List<String> followed = user.getFollowedSeries();
		
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		for(int i = 0; i<check.size(); i++) {
			//System.out.println("test");
			if(check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				//System.out.println(series.getComicSeriesName());
				System.out.println(check.get(i).getFollowers());
				check.get(i).setFollowers(check.get(i).getFollowers()+1);
				System.out.println(check.get(i).getFollowers());
				followed.add(check.get(i).getSeriesId());
				user.setFollowedSeries(followed);
				seriesrepository.save(check.get(i));
				userrepository.save(user);
				return check.get(i);
			}
		}return null;
	}
	
	//edit comic series description
	@RequestMapping(value="/description", method = RequestMethod.POST)
	public ComicSeries editDescription(@Valid @RequestBody ComicSeries series) {
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		for(int i = 0; i<check.size(); i++) {
			if(check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				check.get(i).setDescription(series.getDescription());
				seriesrepository.save(check.get(i));
				return check.get(i);
			}
		}
		return null;
	}
	
	
	
	//rate/update rating for series
//	@RequestMapping(value="/rating", method = RequestMethod.POST)
//	public ComicSeries editRating(@Valid @RequestBody Rating rating) {
//		
//	}
}
