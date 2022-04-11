package com.autobahn.challenge.repo;

import org.springframework.data.repository.CrudRepository;

import com.autobahn.challenge.model.RecordsHeader;

public interface HeaderRepository extends CrudRepository<RecordsHeader, Long>{

}
