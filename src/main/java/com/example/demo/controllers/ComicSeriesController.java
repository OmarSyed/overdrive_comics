package com.example.demo.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.DateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ComicChapter;
import com.example.demo.entity.ComicSeries;
import com.example.demo.entity.Comment;
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
				if(users.contains(UsersController.getCurUser())) {
					users.remove(UsersController.getCurUser());
					chap.get(i).setLikedUsers(users);
					chap.get(i).setLikes(chap.get(i).getLikes()-1);
					chapterrepository.save(chap.get(i));
					return chap.get(i);
				}else {
					users.add(UsersController.getCurUser());
					chap.get(i).setLikedUsers(users);
					chap.get(i).setLikes(chap.get(i).getLikes()+1);
					chapterrepository.save(chap.get(i));
					return chap.get(i);
				}
			}
		}return null;
	}
	
	//delete like to a chapter
//	@RequestMapping(value="/chapter/unlike", method = RequestMethod.POST)
//	public ComicChapter unlikeChapter(ComicChapter chapter) {
//		List<ComicChapter> chap = chapterrepository.findBySeriesId(chapter.getSeriesId());
//		ComicChapter comicChapter;
//		for(int i = 0; i<chap.size(); i++) {
//			if(chap.get(i).getChapterTitle().equals(chapter.getChapterTitle())) {
//				//comicChapter = chap.get(i);
//				List<String> users = chap.get(i).getLikedUsers();
//				users.remove(UsersController.getCurUser());
//				chap.get(i).setLikedUsers(users);
//				chap.get(i).setLikes(chap.get(i).getLikes()-1);
//				chapterrepository.save(chap.get(i));
//				return chap.get(i);
//			}
//		}return null;
//	}
	
	//get all the likes a user has
	@RequestMapping(value="/totallikes", method = RequestMethod.GET)
	public int totalLikes() {
		int total = 0;
		List<ComicChapter> chapter = chapterrepository.findByAuthor(UsersController.curUser);
		for(int i = 0;i<chapter.size(); i++) {
			total = total + chapter.get(i).getLikes();
		}
		return total;
	}
	
	//create a chapter
	@RequestMapping(value="/chapter/create", method = RequestMethod.POST)
	public ComicChapter createChapter(@Valid @RequestBody ComicChapter chapter) {
		//List<ComicSeries> series = seriesrepository.findByAuthor(UsersController.curUser);
		String seriesId = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		//for(int i = 0; i<series.size(); i++) {
			//if(series.get(i).getComicSeriesName().equals(chapter.getSeriesTitle())) {
				//chapter.setSeriesId(series.get(i).getSeriesId());
		List<String> likedUsers  = new ArrayList<String>();
		List<Comment> comments  = new ArrayList<Comment>();
		//List<String> pages = new ArrayList<String>();
		chapter.setAuthor(UsersController.curUser);
		chapter.setLikedUsers(likedUsers);
		chapter.setComments(comments);
		//chapter.setPages(pages);
		chapter.setCreated(date);
		chapterrepository.save(chapter);
		//return chapter;
			//}
			//}
		return chapter;
	}
	
	//save a chapter
	//check if current user is author
	@RequestMapping(value="/chapter/save", method = RequestMethod.POST)
	public ComicChapter saveChapter(@Valid @RequestBody ComicChapter chapter) throws JSONException {
		Optional<ComicChapter> chap = chapterrepository.findById(chapter.get_id());
		//JSONObject obj = new JSONObject(chapter.getPages());
//		JSONArray arr = obj.getJSONArray("posts");
		
		System.out.println("sdfds" + " " + chapter.getPages());
		chap.get().setPages(chapter.getPages());
//		JSONArray arr = new JSONArray(chapter.getPages());
//		List<String> list = new ArrayList<String>();
//		for(int i = 0; i < arr.length(); i++){
//		    list.add(arr.getJSONObject(i).toString());
//		}
//		chap.get().setPages(list);
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		Date date = new Date();
//		chap.get().setLastModified(date);
		chapterrepository.save(chap.get());
		return chap.get();
		//return null;
	}
	
	//view a chapter
	//id is series id
	@RequestMapping(value="/chapter/view/{id}", method = RequestMethod.GET)
	public String viewChapter(@PathVariable String id) {
		Optional<ComicChapter> chap = chapterrepository.findById(id);
		return chap.get().getPages();
	}
	
	
	//publish a chapter
	@RequestMapping(value="/chapter/publish", method=RequestMethod.POST)
	public String publishChapter(@Valid @RequestBody ComicChapter chapter) {
		System.out.println(chapter.get_id());
		String user = UsersController.curUser;
		JSONArray arr;
		try {
			File userdirectory = new File(user+"/" + chapter.getSeriesTitle() + "/" + chapter.get_id());
			arr = new JSONArray(chapter.getImages());
			List<String> list = new ArrayList<String>();
			for(int i = 0; i < arr.length(); i++){
//			    //list.add(arr.getJSONObject(i).toString());
					String filename = chapter.get_id() + i + ".png";
					String base64Image = arr.getString(i);
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
					File outputfile = new File(filename);
					ImageIO.write(img, "png", outputfile);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return chapter.getImages();
	}
	
	//return chapters of a series
	//seriesid
	@RequestMapping(value="series/chapter{id}", method=RequestMethod.GET)
	public List<ComicChapter> getChapters(@PathVariable String id){
		List<ComicChapter> chapters  = chapterrepository.findBySeriesId(id);
		return chapters;
	}
	
	//retrieve chapter images
	//chapterid
	@RequestMapping(value="chapter/retrieve{id}", method=RequestMethod.POST)
	public String retrieveChapter(@PathVariable String id) {
		
		
		
		
		return "";
	}
	
	
	
	
	
}
