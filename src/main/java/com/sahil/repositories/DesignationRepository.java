package com.sahil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sahil.entities.DesignationEntity;

@Repository
public interface DesignationRepository extends JpaRepository<DesignationEntity,Integer>{

	public DesignationEntity findByName(String name);
}
