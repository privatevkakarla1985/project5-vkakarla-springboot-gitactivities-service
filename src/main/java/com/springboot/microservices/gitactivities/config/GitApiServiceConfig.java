package com.springboot.microservices.gitactivities.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GitApiServiceConfig {

	@Value("${git.api.repos.url}")
	private String gitRepos;

	public String getGitRepos() {
		return gitRepos;
	}

	public void setGitRepos(String gitRepos) {
		this.gitRepos = gitRepos;
	}
	
}
