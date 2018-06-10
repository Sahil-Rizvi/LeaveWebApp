package com.sahil.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sahil.entities.EmployeeEntity;
import com.sahil.entities.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	
	    VerificationToken findByToken(String token);

	    VerificationToken findByUser(EmployeeEntity user);

	    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

	    void deleteByExpiryDateLessThan(Date now);

	    @Modifying
	    @Query("delete from verification_token t where t.expiryDate <= ?1")
	    void deleteAllExpiredSince(Date now);
	
}
