package com.springboot.microservices.gitactivities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.microservices.gitactivities.serviceImpl.GitAPIService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class GitActivitiyController {

	@Autowired
	GitAPIService gitAPIService;

	@ApiOperation(value = "Creates repos with the statistics of the given user related reponame", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved  repos", response = Object.class) })
	@RequestMapping(value = "value = /gitrepositories/user/{user-name}/repos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAndPublishGitRepositoryStatisticsInDataBase(
			@ApiParam(value = "owner-name") @PathVariable(name = "user-name") String ownerName,
			@ApiParam(value = "clientTocken", defaultValue = "") @RequestParam(name = "token", defaultValue = "12345") String token)
			throws Exception {

		ResponseEntity<Object> responseEntity = null;

		try {

			gitAPIService.getAndPublishGitRepositoryStatisticsInDataBase(ownerName, token);
			responseEntity = new ResponseEntity<>("Successfully retrieved  repos and saved into Data base",
					HttpStatus.OK);

		} catch (Exception ex) {

		}
		return responseEntity;

	}

	@ApiOperation(value = "get all repos", response = String.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved  repos", response = Object.class) })
	@RequestMapping(value = "value = /gitAllRepositories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAllRepos() {

		ResponseEntity<Object> responseEntity = null;

		try {
			responseEntity = new ResponseEntity<>(gitAPIService.getAllRepos(), HttpStatus.OK);

		} catch (Exception ex) {

		}
		return responseEntity;

	}

}
