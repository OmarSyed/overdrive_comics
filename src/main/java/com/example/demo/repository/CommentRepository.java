package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.ComicChapter;
import com.example.demo.entity.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
	List<Comment> findByChapterId(String chapterId);
}
