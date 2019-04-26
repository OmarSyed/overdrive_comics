package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.ComicChapter;

public interface ChapterRepository extends MongoRepository<ComicChapter, String>{
	List<ComicChapter> findBySeriesId(String seriesId);
	List<ComicChapter> findByAuthor(String author);
}
