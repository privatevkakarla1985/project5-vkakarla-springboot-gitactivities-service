package com.springboot.microservices.gitactivities.service;

import java.util.List;

import com.springboot.microservices.gitactivities.entities.RepoDetails;

public interface IGitActivityService {

	public void getAndPublishGitRepositoryStatisticsInDataBase(String userName,String token);
	
	public List<RepoDetails> getAllRepos();
	
			
	
}
