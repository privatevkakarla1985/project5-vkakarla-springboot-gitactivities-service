package com.springboot.microservices.gitactivities.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.microservices.gitactivities.entities.RepoDetails;

@Repository
public interface GITReposRepository extends JpaRepository<RepoDetails, Long>{

}
