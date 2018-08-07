package com.spm.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spm.back.mapping.Keyword;
import com.spm.back.mapping.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long>{

}
