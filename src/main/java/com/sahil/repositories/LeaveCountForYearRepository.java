package com.sahil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.LeaveCountForYearEntity;

@Repository
public interface LeaveCountForYearRepository extends JpaRepository<LeaveCountForYearEntity,Integer>{

}
