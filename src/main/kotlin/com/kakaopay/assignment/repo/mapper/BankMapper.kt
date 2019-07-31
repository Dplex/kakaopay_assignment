package com.kakaopay.assignment.repo.mapper

import com.kakaopay.assignment.repo.model.BankVo
import com.kakaopay.assignment.repo.provider.BankProvider
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.SelectProvider
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface BankMapper {

    @SelectProvider(type = BankProvider::class, method = BankProvider.METHOD_FINDALL)
    fun findAll(): List<BankVo>
}