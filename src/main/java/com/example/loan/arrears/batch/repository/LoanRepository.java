package com.example.loan.arrears.batch.repository;

import com.example.loan.arrears.batch.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
}
