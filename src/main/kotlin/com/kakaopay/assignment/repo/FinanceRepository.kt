package com.kakaopay.assignment.repo

import com.kakaopay.assignment.repo.model.FinanceVo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface FinanceRepository: JpaRepository<FinanceVo, Long> {

    fun findByYearAndMonth(year: Int, month: Int): List<FinanceVo>

    /*
    INSERT INTO kakao_finance_be.tasur_hist(
	year, month, bank_type, asur_price)
	VALUES (2017, 9, '08', 1)
	ON CONFLICT ON CONSTRAINT tasure_hist_uq01
	DO UPDATE
		SET asur_price = EXCLUDED.asur_price;
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO kakao_finance_be.tasur_hist(year, month, bank_type, asur_price) " +
            "VALUES (:year, :month, :bank_type, :asur_price) " +
            "ON CONFLICT ON CONSTRAINT tasure_hist_uq01 " +
            "DO UPDATE " +
            "SET asur_price = :asur_price",
            nativeQuery = true)
    fun merge(@Param("year") year: Int,
              @Param("month") month: Int,
              @Param("bank_type") bankType: String,
              @Param("asur_price") assurancePrice: Int): Int


}