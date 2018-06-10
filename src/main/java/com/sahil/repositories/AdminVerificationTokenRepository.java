package com.sahil.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sahil.entities.AdminEntity;
import com.sahil.entities.VerificationTokenAdmin;

@Repository
public interface AdminVerificationTokenRepository extends JpaRepository<VerificationTokenAdmin, Long>{
	
	VerificationTokenAdmin findByToken(String token);

	VerificationTokenAdmin findByUser(AdminEntity user);

	Stream<VerificationTokenAdmin> findAllByExpiryDateLessThan(Date now);

	void deleteByExpiryDateLessThan(Date now);

	@Modifying
	@Query("delete from verification_token_admin t where t.expiryDate <= ?1")
	void deleteAllExpiredSince(Date now);
	
}
