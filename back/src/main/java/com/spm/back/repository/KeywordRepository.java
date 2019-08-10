package com.spm.back.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spm.back.mapping.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long>{

}
