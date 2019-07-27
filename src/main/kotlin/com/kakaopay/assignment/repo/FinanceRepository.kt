package com.kakaopay.assignment.repo

import com.kakaopay.assignment.repo.model.FinanceVo
import org.springframework.data.repository.CrudRepository

interface FinanceRepository: CrudRepository<FinanceVo, Long> {

    fun findByYearAndMonth(year: Int, month: Int): List<FinanceVo>

}