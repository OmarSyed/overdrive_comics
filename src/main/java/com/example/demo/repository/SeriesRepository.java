package com.example.demo.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.ComicSeries;
import com.example.demo.entity.Users;


public interface SeriesRepository extends MongoRepository<ComicSeries, String> {
	List<ComicSeries> findByGenre(String genre);
	List<ComicSeries> findByAuthor(String author);
	List<ComicSeries> findByComicSeriesName(String comicSeriesName);
	
}

