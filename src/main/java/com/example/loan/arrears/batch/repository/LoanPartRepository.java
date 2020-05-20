package com.example.loan.arrears.batch.repository;

import com.example.loan.arrears.batch.domain.LoanPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanPartRepository extends JpaRepository<LoanPart,Long> {
}
