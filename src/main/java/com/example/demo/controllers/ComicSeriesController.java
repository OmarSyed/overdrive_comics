package com.example.demo.controllers;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controllers.UsersController;
import com.example.demo.entity.ComicChapter;
import com.example.demo.entity.ComicSeries;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Rating;
import com.example.demo.entity.Users;
import com.example.demo.repository.ChapterRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.SeriesRepository;
import com.example.demo.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/series")
public class ComicSeriesController {

	@Autowired
	private SeriesRepository seriesrepository;
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private ChapterRepository chapterrepository;
	@Autowired
	private CommentRepository commentrepository;

	// Create a series, add it to mongo collection
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createSeries(@Valid @RequestBody ComicSeries series, @CookieValue("username") String username) {
		series.setAuthor(username);
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		if (check.isEmpty()) {
			HashMap<String, Double> rating = new HashMap<>();
			series.setRating(rating);
			series.setThumbnail("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAMFBMVEX////j4+Py8vLk5OTi4uL29vbu7u76+vrq6urm5ub+/v7l5eXn5+f9/f3h4eHr6+uo6oKiAAADM0lEQVR4nO3Yy67jIBREUY4PfuBH/P9/2zhOrrDUE6S+UkFvBiFJMYkVF3iFkCwOIYSXxTmEMdorf5iiLXnqIgrpONYh7Us41jOlaT3CtqdxXee09xEFO0IY3Pf8c0/3KU+22RjCnKc+orjmd+Ypp4vZtSj6e1H0TqKh95HvSLf8uuZffd2s5vG6We2+WXuIQtjd87sj/3Pfiza7FrmfoZMo/4H3NKzHkdtnXtcx7VsupimlMxdTF1G4dpAh2n2zxuneXN43q3US3a26PQrXy8JtPnq36vcy2OMydBLN57kM8zwv5zLO85in/GFYlilPXUQ/rWpuReG6F4XbdlRsKeUi88du03IUxmmahnG8pvw6/Eyf79qPPudSexztYnm0az6SK/d/Hn12xu26DLHcNF/WS7R9H5Nf5WNyXHqJ8gl1Kw+v/ji89hBdnlE+gMTHA0gPUdCo9F+MHorhJQSYdxJZucgfizqJ7rPqeeZpWv52jG09+srpGh+oagWqNh7Jlfuv7BZSCI/qo/qofodDDuFRfVQf1RdgFVS/OlJDeFQf1Uf1BRAe1a+O9BAe1Uf1UX25tkf1UX1Uv/1IrtxRfVQf1RdAeFS/dsghPKqP6qP6AqyC6ldHagiP6qP6qL4AwqP61ZEewqP6qD6qL9f2qD6qj+q3H8mVO6qP6qP6AgiP6tcOOYRH9VF9VF+AVVD96kgN4VF9VB/VF0B4VL860kN4VB/VR/Xl2h7VR/VR/fYjuXJH9VF9VF8A4VH92iGH8Kg+qo/qC7AKql8dqSE8qo/qo/oCCI/qV0d6CI/qo/qovlzbo/qoPqrffiRX7qg+qo/qCyA8ql875BAe1Uf1UX0BVkH1qyM1hEf1UX1UXwDhUf3qSA/hUX1UH9WXa3tUH9VH9duP5Mod1Uf1UX0BhEf1a4ccwqP6qD6qL8AqqH51pIbwqD6qj+oLIDyqXx3pITyqj+qj+nJtj+qj+qh++5FcuaP6qD6qL4DwqH7tkEN4VB/VR/UFWAXVr47UEB7VR/VRfQGER/WrIz2ER/VRfVRfru1RfVQf1W8/kit3VB/VR/UFEB7Vrx1yCI/qo/r/oer/AfStKxKIUGaSAAAAAElFTkSuQmCC");
			LocalDate today = LocalDate.now();
			series.setDate(today);
			seriesrepository.save(series);
			String id = seriesrepository.save(series).getSeriesId();
			Users user = userrepository.findByUsername(username);
			List<String> ids = user.getProducedSeries();
			ids.add(id);
			user.setProducedSeries(ids);
			userrepository.save(user);
			return "success";
		}
		for (int i = 0; i < check.size(); i++) {
			//System.out.println(check.get(i).getComicSeriesName());
			//System.out.println(series.getComicSeriesName());
			if (check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				return "failure";
			}
		}
		HashMap<String, Double> rating = new HashMap<>();
		series.setRating(rating);
		series.setThumbnail("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAMFBMVEX////j4+Py8vLk5OTi4uL29vbu7u76+vrq6urm5ub+/v7l5eXn5+f9/f3h4eHr6+uo6oKiAAADM0lEQVR4nO3Yy67jIBREUY4PfuBH/P9/2zhOrrDUE6S+UkFvBiFJMYkVF3iFkCwOIYSXxTmEMdorf5iiLXnqIgrpONYh7Us41jOlaT3CtqdxXee09xEFO0IY3Pf8c0/3KU+22RjCnKc+orjmd+Ypp4vZtSj6e1H0TqKh95HvSLf8uuZffd2s5vG6We2+WXuIQtjd87sj/3Pfiza7FrmfoZMo/4H3NKzHkdtnXtcx7VsupimlMxdTF1G4dpAh2n2zxuneXN43q3US3a26PQrXy8JtPnq36vcy2OMydBLN57kM8zwv5zLO85in/GFYlilPXUQ/rWpuReG6F4XbdlRsKeUi88du03IUxmmahnG8pvw6/Eyf79qPPudSexztYnm0az6SK/d/Hn12xu26DLHcNF/WS7R9H5Nf5WNyXHqJ8gl1Kw+v/ji89hBdnlE+gMTHA0gPUdCo9F+MHorhJQSYdxJZucgfizqJ7rPqeeZpWv52jG09+srpGh+oagWqNh7Jlfuv7BZSCI/qo/qofodDDuFRfVQf1RdgFVS/OlJDeFQf1Uf1BRAe1a+O9BAe1Uf1UX25tkf1UX1Uv/1IrtxRfVQf1RdAeFS/dsghPKqP6qP6AqyC6ldHagiP6qP6qL4AwqP61ZEewqP6qD6qL9f2qD6qj+q3H8mVO6qP6qP6AgiP6tcOOYRH9VF9VF+AVVD96kgN4VF9VB/VF0B4VL860kN4VB/VR/Xl2h7VR/VR/fYjuXJH9VF9VF8A4VH92iGH8Kg+qo/qC7AKql8dqSE8qo/qo/oCCI/qV0d6CI/qo/qovlzbo/qoPqrffiRX7qg+qo/qCyA8ql875BAe1Uf1UX0BVkH1qyM1hEf1UX1UXwDhUf3qSA/hUX1UH9WXa3tUH9VH9duP5Mod1Uf1UX0BhEf1a4ccwqP6qD6qL8AqqH51pIbwqD6qj+oLIDyqXx3pITyqj+qj+nJtj+qj+qh++5FcuaP6qD6qL4DwqH7tkEN4VB/VR/UFWAXVr47UEB7VR/VRfQGER/WrIz2ER/VRfVRfru1RfVQf1W8/kit3VB/VR/UFEB7Vrx1yCI/qo/r/oer/AfStKxKIUGaSAAAAAElFTkSuQmCC");
		LocalDate today = LocalDate.now();
		series.setDate(today);
		String id = seriesrepository.save(series).getSeriesId();
		Users user = userrepository.findByUsername(username);
		List<String> ids = user.getProducedSeries();
		ids.add(id);
		user.setProducedSeries(ids);
		userrepository.save(user);
		return "success";
	}

	// publish or unpublish a series
	@RequestMapping(value = "/publish", method = RequestMethod.POST)
	public ComicSeries publishSeries(@Valid @RequestBody ComicSeries series) {
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		// System.out.println(series.getAuthor());
		System.out.println(series.getAuthor());
		for (int i = 0; i < check.size(); i++) {
			if (check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				// System.out.println(series.getAuthor());
				if (check.get(i).isPublished()) {
					check.get(i).setPublished(false);
					seriesrepository.save(check.get(i));
					return check.get(i);
				} else {
					check.get(i).setPublished(true);
					seriesrepository.save(check.get(i));
					return check.get(i);
				}
			}
		}
		return null;
	}

	// return the series under a user
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<ComicSeries> userSeries(@CookieValue("username") String username) {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// System.out.println(auth.getName());
//		UsersController user = new UsersController();
		return seriesrepository.findByAuthor(username);
	}

	// return series under a author
	@RequestMapping(value = "/author", method = RequestMethod.GET)
	public List<ComicSeries> authorSeries(@RequestParam String username) {
		return seriesrepository.findByAuthor(username);
	}

	// return a specific series a user selected
	@RequestMapping(value = "/title", method = RequestMethod.GET)
	public ComicSeries specficSeries(@RequestParam String title, @RequestParam String author) {
		List<ComicSeries> check = seriesrepository.findByAuthor(author);
		for (int i = 0; i < check.size(); i++) {
			if (check.get(i).getComicSeriesName().equals(title)) {
				return check.get(i);
			}
		}
		return null;
		// return seriesrepository.findById(series.getSeriesId());
	}

	// show series of specific genre
	@RequestMapping(value = "genre", method = RequestMethod.GET)
	public List<ComicSeries> genreSeries(@RequestParam String genre) {
		List<ComicSeries> genres = seriesrepository.findByGenre(genre);
		Collections.reverse(genres);
		if (!UsersController.curUser.equals("")) {
			Users user = userrepository.findByUsername(UsersController.curUser);
			List<String> followed = user.getFollowedSeries();
			for (int i = 0; i < genres.size(); i++) {
				if (followed.contains(genres.get(i).getSeriesId())) {
					genres.get(i).setFollowed(true);
				}
			}
			if (genres.size() > 20) {
				List<ComicSeries> second = new ArrayList<ComicSeries>(genres.subList(0, 20));
				return second;
			} else {
				return genres;
			}

		} else {
			if (genres.size() > 20) {
				List<ComicSeries> second = new ArrayList<ComicSeries>(genres.subList(0, 20));
				return second;
			} else {
				return genres;
			}
		}
	}

	// Users follows a series
	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	public ComicSeries followSeries(@Valid @RequestBody ComicSeries series, @CookieValue("username") String username) {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Users user = userrepository.findByUsername(auth.getName());
		// UsersController curUser = new UsersController();
//		String checkUser = UsersController.getCurUser();
		Users user = userrepository.findByUsername(username);
		// System.out.println(curUser.getCurUser());
		List<String> followed = user.getFollowedSeries();
		System.out.println(series.getAuthor());
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		for (int i = 0; i < check.size(); i++) {
			// System.out.println(check.get(i).getComicSeriesName());
			if (check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				// System.out.println(series.getComicSeriesName());
				System.out.println(check.get(i).getFollowers());

				if (followed.contains(check.get(i).getSeriesId())) {
					check.get(i).setFollowers(check.get(i).getFollowers() - 1);
					System.out.println(check.get(i).getFollowers());
					followed.remove(check.get(i).getSeriesId());
					user.setFollowedSeries(followed);
					check.get(i).setFollowed(false);
					seriesrepository.save(check.get(i));
					userrepository.save(user);
					return check.get(i);
				} else {
					check.get(i).setFollowers(check.get(i).getFollowers() + 1);
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
		}
		return null;
	}

	// edit comic series description
	@RequestMapping(value = "/description", method = RequestMethod.POST)
	public ComicSeries editDescription(@Valid @RequestBody ComicSeries series) {
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		for (int i = 0; i < check.size(); i++) {
			if (check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				check.get(i).setDescription(series.getDescription());
				seriesrepository.save(check.get(i));
				return check.get(i);
			}
		}
		return null;
	}

	// rate or update rating for series
	@RequestMapping(value = "/rating", method = RequestMethod.POST)
	public ComicSeries editRating(@Valid @RequestBody ComicSeries series, @CookieValue("username") String username) {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String curUser = UsersController.curUser;
		Users user = userrepository.findByUsername(username);
		// System.out.println(auth.getName());
		List<ComicSeries> check = seriesrepository.findByAuthor(series.getAuthor());
		ComicSeries update = new ComicSeries();
		for (int i = 0; i < check.size(); i++) {
			if (check.get(i).getComicSeriesName().equals(series.getComicSeriesName())) {
				update = check.get(i);
				break;
			}
		}
		HashMap<String, Double> newRating;
		if (update.getRating() == null) {
			newRating = new HashMap<>();
			update.setRating(newRating);
		} else {
			newRating = update.getRating();
		}
		// System.out.println(auth.getName());
		if (newRating.containsKey(user.getUsername())) {
			newRating.replace(username, series.getScore());
		} else {
			newRating.put(username, series.getScore());
		}
		// System.out.println(auth.getName());
		double sum = 0;
		double counter = 0;
		for (Double f : newRating.values()) {
			sum += f;
			counter += 1;
		}
		// System.out.println(auth.getName());
		double avg = sum / counter;
		update.setScore(avg);
		System.out.println(avg);
		seriesrepository.save(update);
		return update;
	}

	// display what the author follows
	@CrossOrigin
	@RequestMapping(value = "/displayfollows", method = RequestMethod.GET)
	public Iterable<ComicSeries> displaySeriesFollows(@CookieValue("username") String username) {
		try {
//			String checkUser = UsersController.getCurUser();
			Users user = userrepository.findByUsername(username);
			return seriesrepository.findAllById(user.getFollowedSeries());
		} catch (NullPointerException ex) {
			System.out.println("No followed series found");
			return null;
		}
	}

	// add like to a chapter
	@RequestMapping(value = "/chapter/like", method = RequestMethod.POST)
	public ComicChapter likeChapter(@Valid @RequestBody ComicChapter chapter, @CookieValue("username") String username) {
		System.out.println(chapter.get_id());
		Optional<ComicChapter> chap = chapterrepository.findById(chapter.get_id());
		Users currentUser  = userrepository.findByUsername(username);
		List<String> chapterId = currentUser.getLikedChapters();
		List<String> users = chap.get().getLikedUsers();
		Optional<ComicSeries> series = seriesrepository.findById(chap.get().getSeriesId());
		
		if(users.contains(username)) {
			chapterId.remove(chapter.get_id());
			users.remove(username);
			chap.get().setLikedUsers(users);
			chap.get().setLikes(chap.get().getLikes()-1);
			chapterrepository.save(chap.get());
			currentUser.setLikedChapters(chapterId);
			userrepository.save(currentUser);
			series.get().setLikes(series.get().getLikes()-1);
			seriesrepository.save(series.get());
			return chap.get();
		}else {
			chapterId.add(chapter.get_id());
			users.add(username);
			chap.get().setLikedUsers(users);
			chap.get().setLikes(chap.get().getLikes()+1);
			chapterrepository.save(chap.get());
			currentUser.setLikedChapters(chapterId);
			userrepository.save(currentUser);
			series.get().setLikes(series.get().getLikes()+1);
			seriesrepository.save(series.get());
			return chap.get();
		}

	}

	// delete like to a chapter
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

	// get all the likes a user has
	@CrossOrigin
	@RequestMapping(value = "/totallikes", method = RequestMethod.GET)
	public int totalLikes(@CookieValue("username") String username) {
		int total = 0;
		List<ComicChapter> chapter = chapterrepository.findByAuthor(username);
		for (int i = 0; i < chapter.size(); i++) {
			total = total + chapter.get(i).getLikes();
		}
		return total;
	}
	
	//get all the followers a user has
	@CrossOrigin
	@RequestMapping(value="/totalfollowers", method = RequestMethod.GET)
	public int totalFollowers(@CookieValue("username") String username) {
		int total  = 0;
		List<ComicSeries> series = seriesrepository.findByAuthor(username);
		for(int i = 0; i < series.size(); i++) {
			total = total + series.get(i).getFollowers();
		}
		return total;
	}
	
	//get number of comics made
	@CrossOrigin
	@RequestMapping(value="/totalcomics", method = RequestMethod.GET)
	public int totalComics(@CookieValue("username") String username) {
		List<ComicSeries> series = seriesrepository.findByAuthor(username);
		return series.size();
	}
	
	//get number of follows
	@CrossOrigin
	@RequestMapping(value="/totalfollows", method = RequestMethod.GET)
	public int totalFollowing(@CookieValue("username") String username) {
		Users user  = userrepository.findByUsername(username);
		return user.getFollowedSeries().size();
	}

	// create a chapter
	@RequestMapping(value = "/chapter/create", method = RequestMethod.POST)
	public ComicChapter createChapter(@Valid @RequestBody ComicChapter chapter, @CookieValue("username") String username) {
		// List<ComicSeries> series =
		// seriesrepository.findByAuthor(UsersController.curUser);
		String seriesId = "";
		LocalDate today = LocalDate.now();
		// for(int i = 0; i<series.size(); i++) {
		// if(series.get(i).getComicSeriesName().equals(chapter.getSeriesTitle())) {
		// chapter.setSeriesId(series.get(i).getSeriesId());
		List<String> likedUsers = new ArrayList<String>();
		List<String> imgUrls = new ArrayList<String>();
		// List<String> pages = new ArrayList<String>();
		chapter.setAuthor(username);
		chapter.setSeriesId(chapter.getSeriesId());
		chapter.setLikedUsers(likedUsers);
		chapter.setImgUrls(imgUrls);
		chapter.setPublished(false);
		chapter.setCreated(today);
		chapterrepository.save(chapter);

		return chapter;
	}

	// save a chapter
	// check if current user is author
	@RequestMapping(value = "/chapter/save", method = RequestMethod.POST)
	public ComicChapter saveChapter(@Valid @RequestBody ComicChapter chapter) throws JSONException {
		Optional<ComicChapter> chap = chapterrepository.findById(chapter.get_id());
		// JSONObject obj = new JSONObject(chapter.getPages());
//		JSONArray arr = obj.getJSONArray("posts");

		System.out.println("sdfds" + " " + chapter.getPages());
		LocalDate today = LocalDate.now();
		chap.get().setPages(chapter.getPages());
//		JSONArray arr = new JSONArray(chapter.getPages());
//		List<String> list = new ArrayList<String>();
//		for(int i = 0; i < arr.length(); i++){
//		    list.add(arr.getJSONObject(i).toString());
//		}
//		chap.get().setPages(list);
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		Date date = new Date();
		chap.get().setChapterTitle(chapter.getChapterTitle());
		chap.get().setLastModified(today);
		chapterrepository.save(chap.get());
		return chap.get();
		// return null;
	}

	// view a chapter
	// id is series id
	@RequestMapping(value = "/chapter/view/{id}", method = RequestMethod.GET)
	public String viewChapter(@PathVariable String id) {
		Optional<ComicChapter> chap = chapterrepository.findById(id);
		String check = chap.get().getPages();
		if (check == null) {
			return "error";
		} else {
			return check;
		}
	}

	// view published chapter
	@RequestMapping(value = "/chapter/view/publish/{series_id}/{chapter}", method = RequestMethod.GET)
	public ComicChapter viewChapterPublished(@PathVariable String series_id, @PathVariable int chapter) {
		List<ComicChapter>  chapters = chapterrepository.findBySeriesId(series_id);
		if (chapters.size() == 0 || chapter - 1 > chapters.size() - 1 || chapter == 0) {
			return null;
		} else {
			return chapters.get(chapter - 1);
		}
	}

	
	//publish a chapter
	@RequestMapping(value="/chapter/publish", method=RequestMethod.POST)
	public String publishChapter(@Valid @RequestBody ComicChapter chapter, @CookieValue("username") String username) {
		//System.out.println(chapter.get_id() + " " + UsersController.curUser);
//		String user = UsersController.curUser;
		String user = "johnsmith";
		LocalDate today = LocalDate.now();
		Optional<ComicChapter> chap = chapterrepository.findById(chapter.get_id());
		List<String> imgurls = new ArrayList<String>();
		JSONArray arr;
		try {
			arr = new JSONArray(chapter.getImages());
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < arr.length(); i++) {			    //list.add(arr.getJSONObject(i).toString());
					String filename = "../"+ "overdrive_frontend/src/assets/" + username +"/" + chap.get().getSeriesId() + "/" + chapter.get_id()+ "/"+"image_" + i + ".png";
					//File userdirectory = new File("user"+"/" + chapter.getSeriesTitle() + "/" + chapter.get_id()+"/"+filename);
					//File userdirectory = new File(user+"/" + chapter.getSeriesTitle() + "/" + chapter.get_id()+"/"+filename);
					String base64Image = arr.getString(i);
					byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
					File outputfile = new File(filename);
					outputfile.getParentFile().mkdirs();
					System.out.println(filename);
					String addfile = "assets/" + username +"/" + chap.get().getSeriesId() + "/" + chapter.get_id()+ "/"+"image_" + i + ".png";
					ImageIO.write(img, "png", outputfile);
					imgurls.add(addfile);
					
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chap.get().setImgUrls(imgurls);
		chap.get().setPublished(true);
		chap.get().setLastModified(today);
		chapterrepository.save(chap.get());
		return chapter.getImages();
	}
	
	// return chapters of a series
	// seriesid
	@RequestMapping(value = "chapter/{id}", method = RequestMethod.GET)
	public List<ComicChapter> getChapters(@PathVariable String id) {
		List<ComicChapter> chapters = chapterrepository.findBySeriesId(id);
		return chapters;
	}

	// add comment to chapter
	@RequestMapping(value = "chapter/addComment", method = RequestMethod.POST)
	public Comment addComment(@Valid @RequestBody Comment comment, @CookieValue("username") String username) {
		// List<Comment> com =
		// commentrepository.findByChapterId(comment.getChapterId());
		Date date = new Date();
		comment.setUsername(username);
		comment.setDate(date);
		commentrepository.save(comment);
		return comment;
	}

	// delete comment to chapter
	@RequestMapping(value = "chapter/deleteComment", method = RequestMethod.POST)
	public String deleteComment(@Valid @RequestBody Comment comment) {
		commentrepository.deleteById(comment.get_id());
		return "success";
	}

	// list comments for a chapter
	@RequestMapping(value = "chapter/listcomments/{chapterId}", method = RequestMethod.GET)
	public List<Comment> listComments(@PathVariable String chapterId) {
		return commentrepository.findByChapterId(chapterId);
	}	
	
	//get total likes for a series
	@RequestMapping(value="likes", method=RequestMethod.GET)
	public int seriesLikes(@PathVariable String seriesId) {
		List<ComicChapter> chapter = chapterrepository.findBySeriesId(seriesId);
		int likes = 0;
		for(int i = 0; i<chapter.size(); i++) {
			likes = likes + chapter.get(i).getLikes();
		}
		return likes;
	}
	
	//check if current user liked chapter
	@RequestMapping(value="chapter/liked/{chapterId}", method=RequestMethod.GET)
	public boolean checkLiked(@PathVariable String chapterId, @CookieValue("username") String username) throws NullPointerException {
		try {
			Optional<ComicChapter> chap = chapterrepository.findById(chapterId);
			if(chap.get().getLikedUsers().contains(username)) {
				return true;
			}else {
				return false;
			}
		}catch(NullPointerException e) {
			return false;
		}
	}

	//change series settings
	@RequestMapping(value="/settings", method=RequestMethod.POST)
	public ComicSeries changeSettings(@Valid @RequestBody ComicSeries series) {
		Optional<ComicSeries> comic = seriesrepository.findById(series.getSeriesId());
		if(!series.getDay().isEmpty()) {
			comic.get().setDay(series.getDay());
		}
		if(!series.getGenre().isEmpty()) {
			comic.get().setGenre(series.getGenre());
		}
		if(!series.getDescription().isEmpty()) {
			comic.get().setDescription(series.getDescription());
		}
		return seriesrepository.save(comic.get());		
	}
	
	@RequestMapping(value="/thumbnail/pic/{id}", method = RequestMethod.POST)
	public String editPic(@RequestParam("pic") MultipartFile imagefile, @PathVariable String id, 
			@CookieValue("username") String username) throws IllegalStateException, IOException {
		Users user = userrepository.findByUsername(username); 
		Optional<ComicSeries> series = seriesrepository.findById(id);
	
		String message = "";
		String filename = "";
        //MultipartFile file = imagefile;
        try {
            byte[] bytes = imagefile.getBytes();

            // Creating the directory to store file
            //String rootPath = System.getProperty("catalina.home");
            File dir = new File("../" + "overdrive_frontend/src/assets/" + username + "/" 
            + id);
            if (!dir.exists())
                dir.mkdirs();
            filename = "assets/" + username + "/" 
                    + id +"/" + "image0.png";
            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + "image0.png");
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

//            logger.info("Server File Location="
//                    + serverFile.getAbsolutePath());

            message = message + "You successfully uploaded file=" + "image"
                    + "<br />";
        } catch (Exception e) {
            return "You failed to upload " + "image" + " => " + e.getMessage();
        }
        series.get().setThumbnail(filename);
        seriesrepository.save(series.get());
        return message;
	}
	
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public boolean uploadPic(@RequestParam("pic") MultipartFile imagefile, 
			@CookieValue("username") String username) throws IllegalStateException, IOException {
		Users user = userrepository.findByUsername(username); 
		//Optional<ComicSeries> series = seriesrepository.findById(id);
		List<String> editorPics = user.getEditorPics();
		int index = editorPics.size();
		String message = "";
		String filename = "";
        //MultipartFile file = imagefile;
        try {
            byte[] bytes = imagefile.getBytes();

            // Creating the directory to store file
            //String rootPath = System.getProperty("catalina.home");
            File dir = new File("../" + "overdrive_frontend/src/assets/" + username + "/pictures");
            if (!dir.exists())
                dir.mkdirs();
            filename = "assets/" + username + "/pictures" + "/"  + "image" + index + ".png";
            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + "image" + index + ".png");
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

//            logger.info("Server File Location="
//                    + serverFile.getAbsolutePath());

            message = message + "You successfully uploaded file=" + "image"
                    + "<br />";
        } catch (Exception e) {
            return false;
        }
        editorPics.add(filename);
        user.setEditorPics(editorPics);
        userrepository.save(user);
        return true;
	}
	
	//get chapter object
	@RequestMapping(value="/getchapter", method=RequestMethod.POST)
	public ComicChapter getChapter(@Valid @RequestBody ComicChapter chapter) {
		Optional<ComicChapter> chap = chapterrepository.findById(chapter.get_id());
		return chap.get();
	}
	
	//add chapter title
	@RequestMapping(value="chapter/title", method=RequestMethod.POST)
	public ComicChapter titleChapter(@Valid @RequestBody ComicChapter chapter) {
		Optional<ComicChapter> chap = chapterrepository.findById(chapter.get_id());
		chap.get().setChapterTitle(chapter.getChapterTitle());
		chapterrepository.save(chap.get());
		return chap.get();
	}
	
	@RequestMapping(value = "/popular/{option}", method = RequestMethod.GET)
	public List<ComicSeries> getPopular(@PathVariable String option) {
		//System.out.println("stuff");
		List<ComicSeries> all_popular_followers = seriesrepository.findByGenreOrderByFollowersDesc(option);
		List<ComicSeries> all_popular_likes = seriesrepository.findByOrderByLikesDesc();
		//Set<ComicSeries> s = new HashSet<ComicSeries>();
		//s.addAll(all_popular_likes);
		//s.addAll(all_popular_followers);
		//return s;
		//all_popular_followers.addAll(all_popular_likes);
		//List<ComicSeries> listWithoutDuplicates = all_popular_followers.stream().distinct().collect(Collectors.toList());
		if (all_popular_followers.size() > 5) {
			List<ComicSeries> second = new ArrayList<ComicSeries>(all_popular_followers.subList(0, 5));
			return second;
		} else {
			return all_popular_followers;
		}
		//return all_popular_followers;

	}

	@RequestMapping(value = "/discover", method = RequestMethod.GET)
	public Set<ComicSeries> discover(@CookieValue("username") String username) {
		//String check = (String)securityContext.getContext().getAuthentication().getPrincipal();
		System.out.println("In discover");
//		String checkUser = UsersController.getCurUser();
		Users user = userrepository.findByUsername(username);
		List<String> followed = user.getFollowedSeries();
		int size = followed.size();
		if(size==0) {
			System.out.println("In discover, with user");
			List<ComicSeries> popular_series = seriesrepository.findByOrderByFollowersDesc();
			Set<ComicSeries> noUser = new HashSet<ComicSeries>();
			if (popular_series.size() > 30) {
				List<ComicSeries> second = new ArrayList<ComicSeries>(popular_series.subList(0, 30));
				//noUser.addAll(second);
				Collections.shuffle(second);
				List<ComicSeries> third = new ArrayList<ComicSeries>(popular_series.subList(0, 16));
				noUser.addAll(third);
				return noUser;
			} else {
				Collections.shuffle(popular_series);
				if(popular_series.size()>16) {
					List<ComicSeries> second = new ArrayList<ComicSeries>(popular_series.subList(0, 16));
					noUser.addAll(second);
					return noUser;
				}else {
					noUser.addAll(popular_series);
					return noUser;
				}
			}
		}
		Set<ComicSeries> suggested = new HashSet<ComicSeries>();
		List<String> authors = new ArrayList<String>();
		//Set<String> authorList = new LinkedHashSet<String>();
		for (int i = 0; i < followed.size(); i++) {
			Optional<ComicSeries> series = seriesrepository.findById(followed.get(i));
			authors.add(series.get().getAuthor());
		}
		if(authors.size()>5) {
			 authors =  authors.subList(0, 5);
		}
		List<ComicSeries> series = new ArrayList<ComicSeries>();
		for(int i=0; i<authors.size(); i++) {
			List<ComicSeries> authorseries = new ArrayList<ComicSeries>();
			authorseries = seriesrepository.findByAuthor(authors.get(i));
			series.addAll(authorseries);
		}
		for(int i = 0; i<series.size(); i++) {
			if(followed.contains(series.get(i).getSeriesId())) {
				series.remove(i);
			}
		}
		if(series.size()>16) {
			 series =  series.subList(0, 16);
		}
		suggested.addAll(series);
		return suggested;
	}
	
	@RequestMapping(value = "/discover/nouser", method = RequestMethod.GET)
	public Set<ComicSeries> discoverNoUser(){
		System.out.println("In discover, with no user");
		List<ComicSeries> popular_series = seriesrepository.findByOrderByFollowersDesc();
		Set<ComicSeries> noUser = new HashSet<ComicSeries>();
		if (popular_series.size() > 30) {
			List<ComicSeries> second = new ArrayList<ComicSeries>(popular_series.subList(0, 30));
			//noUser.addAll(second);
			Collections.shuffle(second);
			List<ComicSeries> third = new ArrayList<ComicSeries>(popular_series.subList(0, 16));
			noUser.addAll(third);
			return noUser;
		} else {
			Collections.shuffle(popular_series);
			if(popular_series.size()>16) {
				List<ComicSeries> second = new ArrayList<ComicSeries>(popular_series.subList(0, 16));
				noUser.addAll(second);
				return noUser;
			}else {
				noUser.addAll(popular_series);
				return noUser;
			}
		}
	}
	
	@RequestMapping(value = "/search/{query}", method = RequestMethod.GET)
	public List<ComicSeries> search(@PathVariable String query) {
		List<ComicSeries> series = seriesrepository.findByComicSeriesNameIgnoreCaseLikeOrderByFollowersDesc(query);
		List<String> ids = new ArrayList<String>();
		
		for(int i = 0; i<series.size(); i++) {
			ids.add(series.get(i).getSeriesId());
		}
		List<ComicSeries> des = seriesrepository.findByDescriptionIgnoreCaseLikeOrderByFollowersDesc(query);
		//series.addAll(des);
		for(int i = 0; i<des.size(); i++) {
			if(ids.contains(des.get(i).getSeriesId())) {
				System.out.println("duplicate");
			}else {
				series.add(des.get(i));
			}
		}
		return series;
	}
}

