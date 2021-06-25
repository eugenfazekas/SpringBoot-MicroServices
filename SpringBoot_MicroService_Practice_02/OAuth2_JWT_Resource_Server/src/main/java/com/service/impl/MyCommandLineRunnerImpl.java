package com.service.impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Article;
import com.service.ArticleService;
import com.service.CategoriesService;
import com.service.MyCommandLineRunner;
import com.service.UserService;

@Service
public class MyCommandLineRunnerImpl implements MyCommandLineRunner, CommandLineRunner{

	private UserService userservice;
	private ArticleService articleService;
	private CategoriesService categoriesService;

	public MyCommandLineRunnerImpl(UserService userservice, ArticleService articleService, CategoriesService categoriesService) {
		this.userservice = userservice;
		this.articleService = articleService;
		this.categoriesService = categoriesService;
	}

	@Override
	public void run(String... args) throws Exception {
		dropCollectionUsers();
		createCollectionUsers();
	}
	
	@Override
	public void createCollectionUsers() {		
		userservice.createCollectionUsers();
	}

	@Override
	public void dropCollectionUsers() {	
		userservice.dropCollectionUsers();
	}

	@Override
	public void createCollectionArticles() {

		articleService.createArticleCollection();
	}

	@Override
	public void dropCollectionArticles() {

		articleService.dropArticleCollection();
	}
	
	@Override
	public void createCollectionCategories() {
		
		categoriesService.createCategoriesCollection();
	}

	@Override
	public void dropCollectionCategories() {
		
		categoriesService.dropCategoriesCollection();
	}

	@Override
	public void createCategory() {
		
		categoriesService.createCategory();	
	}

	@Override
	public void insertDummyCategories() {
		
		String[] categories = {"books","cars","travel","science","nature"};
		
		for(String c : categories)
			categoriesService.addCategory(c);
			
	}

	@Override
	public void importDummyJsonArticles() {

	ObjectMapper mapper = new ObjectMapper();
	TypeReference<List<Article>> typeReference = new TypeReference<List<Article>>(){};
	InputStream inputStream = TypeReference.class.getResourceAsStream("/static/json/testArticles.json");
	
		try {
			List<Article> articles = mapper.readValue(inputStream, typeReference);
			articleService.insertAllArticles(articles);
		}catch(Exception e) {
			System.out.println(e);
		}
	
	}
}
