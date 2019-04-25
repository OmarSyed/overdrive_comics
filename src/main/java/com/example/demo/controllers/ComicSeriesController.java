package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ComicChapter;
import com.example.demo.entity.ComicSeries;
import com.example.demo.entity.Rating;
import com.example.demo.entity.Users;
import com.example.demo.repository.ChapterRepository;
import com.example.demo.repository.SeriesRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/series")
public class ComicSeriesController {
	
	@Autowired
	private SeriesRepository seriesrepository;
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private ChapterRepository chapterrepository;
	
	
	//Create a series, add it to mongo collection
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String createSeries(@Valid @RequestBody ComicSeries series) {
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		if(check.isEmpty()){
			HashMap<String, Double> rating = new HashMap<>();
			series.setRating(rating);
			seriesrepository.save(series);
			return "success";
		}
		for (int i = 0; i < check.size(); i++) {
			if(check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				return "failure";
			}
		}
		seriesrepository.save(series);
		HashMap<String, Double> rating = new HashMap<>();
		series.setRating(rating);
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
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println(auth.getName());
		UsersController user = new UsersController();
		return seriesrepository.findByAuthor(user.getCurUser());
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
	public List<ComicSeries> genreSeries(@RequestParam String genre){
		List<ComicSeries> genres = seriesrepository.findByGenre(genre);
		Collections.reverse(genres);
		if(!UsersController.curUser.equals("")) {
			Users user = userrepository.findByUsername(UsersController.curUser);
			List<String> followed = user.getFollowedSeries();
			for(int i = 0; i<genres.size(); i++) {
				if(followed.contains(genres.get(i).getSeriesId())) {
					genres.get(i).setFollowed(true);
				}
			}
			if(genres.size()>20) {
				List<ComicSeries> second = new ArrayList<ComicSeries>(genres.subList(0, 20));
				return second;
			}else {
				return genres;
			}
			
		}else {
			return genres;
		}
	}
	
	//Users follows a series
	@RequestMapping(value="/follow", method = RequestMethod.POST)
	public ComicSeries followSeries(@Valid @RequestBody ComicSeries series) {
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Users user = userrepository.findByUsername(auth.getName());
		//UsersController curUser = new UsersController();
		String checkUser = UsersController.getCurUser();
		Users user = userrepository.findByUsername(checkUser);
		//System.out.println(curUser.getCurUser());
		List<String> followed = user.getFollowedSeries();
		System.out.println(series.getAuthor());
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		for(int i = 0; i<check.size(); i++) {
			//System.out.println(check.get(i).getComicSeriesName());
			if(check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				//System.out.println(series.getComicSeriesName());
				System.out.println(check.get(i).getFollowers());
				
				if(followed.contains(check.get(i).getSeriesId())) {
					check.get(i).setFollowers(check.get(i).getFollowers()-1);
					System.out.println(check.get(i).getFollowers());
					followed.remove(check.get(i).getSeriesId());
					user.setFollowedSeries(followed);
					check.get(i).setFollowed(false);
					seriesrepository.save(check.get(i));
					userrepository.save(user);
					return check.get(i);
				}else {
					check.get(i).setFollowers(check.get(i).getFollowers()+1);
					System.out.println(check.get(i).getFollowers());
					followed.add(check.get(i).getSeriesId());
					user.setFollowedSeries(followed);
					check.get(i).setFollowed(false);
					seriesrepository.save(check.get(i));
					userrepository.save(user);
					check.get(i).setFollowed(true);
					return check.get(i);
				}
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
	
	//rate or update rating for series
	@RequestMapping(value="/rating", method = RequestMethod.POST)
	public ComicSeries editRating(@Valid @RequestBody ComicSeries series) {
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsersController curUser = new UsersController();
		Users user = userrepository.findByUsername(curUser.getCurUser());
		//System.out.println(auth.getName());
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		ComicSeries update = new ComicSeries();
		for(int i = 0; i<check.size(); i++) {
			if(check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				update = check.get(i);
			}
		}
		HashMap<String, Double> newRating = update.getRating();
		//System.out.println(auth.getName());
		if(newRating.containsKey(user.getUsername())) {
			newRating.replace(curUser.getCurUser(), series.getScore());
		}else {
			newRating.put(curUser.getCurUser(), series.getScore());
		}
		//System.out.println(auth.getName());
		double sum = 0;
		double counter = 0;
		for (Double f : newRating.values()) {
		    sum += f;
		    counter+=1;
		}
		//System.out.println(auth.getName());
		double avg = sum/counter;
		update.setScore(avg);
		System.out.println(avg);
		seriesrepository.save(update);
		return update;
	}
	
	//display what the author follows
	@RequestMapping(value="/displayfollows", method = RequestMethod.GET)
	public Iterable<ComicSeries> displaySeriesFollows(){
		String checkUser = UsersController.getCurUser();
		Users user = userrepository.findByUsername(checkUser);
		return seriesrepository.findAllById(user.getFollowedSeries());
	}
	
	//add like to a chapter
	@RequestMapping(value="/chapter/like", method = RequestMethod.POST)
	public ComicChapter likeChapter(ComicChapter chapter) {
		List<ComicChapter> chap = chapterrepository.findBySeriesId(chapter.getSeriesId());
		ComicChapter comicChapter;
		for(int i = 0; i<chap.size(); i++) {
			if(chap.get(i).getChapterTitle().equals(chapter.getChapterTitle())) {
				//comicChapter = chap.get(i);
				List<String> users = chap.get(i).getLikedUsers();
				users.add(UsersController.getCurUser());
				chap.get(i).setLikedUsers(users);
				chap.get(i).setLikes(chap.get(i).getLikes()+1);
				chapterrepository.save(chap.get(i));
				return chap.get(i);
			}
		}return null;
	}
	
	//delete like to a chapter
	@RequestMapping(value="/chapter/unlike", method = RequestMethod.POST)
	public ComicChapter unlikeChapter(ComicChapter chapter) {
		List<ComicChapter> chap = chapterrepository.findBySeriesId(chapter.getSeriesId());
		ComicChapter comicChapter;
		for(int i = 0; i<chap.size(); i++) {
			if(chap.get(i).getChapterTitle().equals(chapter.getChapterTitle())) {
				//comicChapter = chap.get(i);
				List<String> users = chap.get(i).getLikedUsers();
				users.remove(UsersController.getCurUser());
				chap.get(i).setLikedUsers(users);
				chap.get(i).setLikes(chap.get(i).getLikes()-1);
				chapterrepository.save(chap.get(i));
				return chap.get(i);
			}
		}return null;
	}
	
	
	
}
