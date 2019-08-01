package com.kakaopay.assignment.service

import com.kakaopay.assignment.config.Configuration
import com.kakaopay.assignment.config.FinanceConfig
import com.kakaopay.assignment.converter.FinanceConverter
import com.kakaopay.assignment.repo.mapper.FinanceMapper
import com.kakaopay.assignment.repo.model.FinanceStatistcsVo
import com.kakaopay.assignment.repo.model.FinanceVo
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FinanceServiceTest {

    lateinit var financeService: FinanceService
    lateinit var financeMapper: FinanceMapper
    lateinit var financeConfig: FinanceConfig
    lateinit var financeConverter: FinanceConverter
    val configuration = Configuration()
    fun <T> anyObject(): T {
        return Mockito.anyObject<T>()
    }


    @Before
    fun init() {
        financeMapper = mock {
            on { insertOrUpdate(anyObject()) }.thenReturn(1)
            on { deleteAll() }.thenReturn(1)
        }

        configuration.removeOldHistory = false

        financeConfig = mock {
            on { configuration }.thenReturn(configuration)
        }
        financeConverter = FinanceConverter()
        financeService = FinanceService(financeMapper, financeConfig, financeConverter)
    }

    @Test
    fun saveFinanceLstTest1() {
        //given
        val financeLst = listOf<FinanceVo>(FinanceVo(1987, 8,"01", 100))


        //when
        financeService.saveFinaceLst(financeLst)

        //then
        verify(financeMapper, times(0)).deleteAll()
        verify(financeMapper, times(1)).insertOrUpdate(anyObject())
    }

    @Test
    fun saveFinanceLstTest2() {
        //given
        val financeLst = listOf<FinanceVo>(FinanceVo(1987, 8,"01", 100))
        configuration.removeOldHistory = true

        //when
        financeService.saveFinaceLst(financeLst)

        //then
        verify(financeMapper, times(1)).deleteAll()
        verify(financeMapper, times(1)).insertOrUpdate(anyObject())
    }

    @Test
    fun getLargestTest() {
        //given
        val code = "01"
        `when`(financeMapper.getLargest(ArgumentMatchers.anyString()))
                .thenReturn(FinanceStatistcsVo(1987, "한국은행", 100))

        //when
        val response = financeService.getLargest(code)

        //then
        Assert.assertTrue(response.body!!.year == 1987)
        Assert.assertTrue(response.body!!.amount == null)
    }

}