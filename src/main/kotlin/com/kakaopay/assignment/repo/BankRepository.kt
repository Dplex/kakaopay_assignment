package com.kakaopay.assignment.repo

import com.kakaopay.assignment.repo.model.BankVo
import org.springframework.data.jpa.repository.JpaRepository

interface BankRepository: JpaRepository<BankVo, Long> {


}