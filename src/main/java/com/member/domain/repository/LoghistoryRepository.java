package com.member.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.member.domain.entity.Loghistory;

@RepositoryRestResource
public interface LoghistoryRepository extends JpaRepository<Loghistory, Long> {
    Optional<Loghistory> findByEmail(String email);
}
