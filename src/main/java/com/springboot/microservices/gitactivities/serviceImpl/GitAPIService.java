package com.springboot.microservices.gitactivities.serviceImpl;

import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.springboot.microservices.gitactivities.config.GitApiServiceConfig;
import com.springboot.microservices.gitactivities.entities.RepoDetails;
import com.springboot.microservices.gitactivities.repositories.GITReposRepository;
import com.springboot.microservices.gitactivities.service.IGitActivityService;

@Service
public class GitAPIService implements IGitActivityService {

	private static final Logger logger = LoggerFactory.getLogger(GitAPIService.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	GITReposRepository gITReposRepository;

	@Autowired
	GitApiServiceConfig gitApiServiceConfig;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getAndPublishGitRepositoryStatisticsInDataBase(String userName, String token) {

		String Url = getGitApiURL(userName);
		List<LinkedHashMap> responseList = null;
		ResponseEntity<Object> gitResponse = null;
		HttpEntity<Object> httpEntity = null;
		RepoDetails repoDetails = null;
		List<RepoDetails> repoDetailsList = new ArrayList<>();

		httpEntity = new HttpEntity<Object>(getHeaders(token));
		
		
		gitResponse = restTemplate.exchange(Url, HttpMethod.GET, httpEntity, Object.class);

		if (gitResponse.getStatusCode() == HttpStatus.OK) {

			responseList = new ArrayList<>();

			if (gitResponse.getBody() instanceof List<?>) {
				responseList = (List<LinkedHashMap>) gitResponse.getBody();
			} else {
				responseList.add((LinkedHashMap) gitResponse.getBody());
			}

			logger.info("Successfully collected the details from the git api");

			for (LinkedHashMap repo : responseList) {

				repoDetails = new RepoDetails();
				repoDetails.setRepoId((Integer) repo.get("id"));
				repoDetails.setRepoName((String) repo.get("name"));
				repoDetails.setCreatedAt((String) repo.get("created_at"));
				repoDetails.setUpdatedAt((String) repo.get("updated_at"));
				repoDetails.setOwnerName(userName);
				repoDetailsList.add(repoDetails);
				
			}

			gITReposRepository.saveAll(repoDetailsList);
			logger.info("Successfully collected the details from the git api and saved into database");
		}

	}

	private HttpHeaders getHeaders(String token) {
		token = "";
		HttpHeaders headers = new HttpHeaders();
		if (!StringUtils.isEmpty(token)) {
			headers.add("Authorization", "token " + token);
		}

		headers.add("Accept", "application/json");
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;

	}

	@Override
	public List<RepoDetails> getAllRepos() {

		List<RepoDetails> list = null;
		list = gITReposRepository.findAll();
		return list;
	}

	public String getGitApiURL(String ownerName) {
		UriComponentsBuilder uriBuilder = null;

		if (!Strings.isBlank(ownerName)) {
			uriBuilder = UriComponentsBuilder.fromUriString(format(gitApiServiceConfig.getGitRepos(), ownerName));
		}

		return uriBuilder.build().encode().toUriString();

	}

}
