package com.autobahn.challenge.repo;

import org.springframework.data.repository.CrudRepository;

import com.autobahn.challenge.model.Record;

public interface RecordRepo extends CrudRepository<Record, Long>{

}
