package com.kakaopay.assignment.repo.mapper

import com.kakaopay.assignment.repo.model.UserVo
import com.kakaopay.assignment.repo.provider.UserProvider
import org.apache.ibatis.annotations.InsertProvider
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.SelectProvider
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface UserMapper {

    @InsertProvider(type = UserProvider::class, method = UserProvider.METHOD_INSERT)
    fun insertUser(@Param("vo") vo: UserVo): Long

    @SelectProvider(type = UserProvider::class, method = UserProvider.METHOD_GET)
    fun getUser(@Param("user_id") userId: String): UserVo?
}