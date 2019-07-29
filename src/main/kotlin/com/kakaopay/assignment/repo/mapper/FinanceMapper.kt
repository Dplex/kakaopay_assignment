package com.kakaopay.assignment.repo.mapper

import com.kakaopay.assignment.repo.model.FinanceStatistcsVo
import com.kakaopay.assignment.repo.model.FinanceVo
import com.kakaopay.assignment.repo.provider.FinanceProvider
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface FinanceMapper {

    @DeleteProvider(type = FinanceProvider::class, method = FinanceProvider.METHOD_DELETEALL)
    fun deleteAll(): Long

    @InsertProvider(type = FinanceProvider::class, method = FinanceProvider.METHOD_INSERT_OR_UPDATE)
    fun insertOrUpdate(@Param("vo") vo: FinanceVo): Long

    @SelectProvider(type = FinanceProvider::class, method = FinanceProvider.METHOD_GET_SUMMERIZE)
    fun getSummerize(@Param("code") bankType: String): List<FinanceStatistcsVo>

    @SelectProvider(type = FinanceProvider::class, method = FinanceProvider.METHOD_GET_LARGEST)
    fun getLargest(@Param("code") bankType: String): FinanceStatistcsVo

    @SelectProvider(type = FinanceProvider::class, method = FinanceProvider.METHOD_GET_ALL)
    fun getAll(): List<FinanceVo>

}