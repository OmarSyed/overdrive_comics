package com.example.demo.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.entity.ComicSeries;
import com.example.demo.entity.Users;


public interface SeriesRepository extends MongoRepository<ComicSeries, String> {
	List<ComicSeries> findByGenre(String genre);
	List<ComicSeries> findByAuthor(String author);
	List<ComicSeries> findByComicSeriesName(String comicSeriesName);
	List<ComicSeries> findByGenreOrderByFollowersDesc(String genre);
	List<ComicSeries> findByOrderByLikesDesc();
	@Query("{'description': {$regex: ?0}}")
	List<ComicSeries> findByDescriptionQuery(String description);
	@Query("{'title': {$regex: ?0}}")
	List<ComicSeries> findByComicSeriesNameQuery(String title);
	List<ComicSeries> findByComicSeriesNameIgnoreCaseLikeOrderByFollowersDesc(String comicSeriesName);
	List<ComicSeries> findByDescriptionIgnoreCaseLikeOrderByFollowersDesc(String description);
	List<ComicSeries> findByOrderByFollowersDesc();
}

