package com.kakaopay.assignment.repo.provider

import com.kakaopay.assignment.repo.model.UserVo
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.jdbc.SQL

class UserProvider {

    fun insertUser(@Param("vo") vo: UserVo): String {
        return SQL()
            .INSERT_INTO(USER_TABLE)
            .INTO_COLUMNS("user_id", "enc_password")
            .INTO_VALUES("#{vo.userId}", "#{vo.encPassword}")
            .toString()
    }

    fun getUser(@Param("user_id") userId: String): String {
        return SQL().SELECT("user_id userId", "enc_password encPassword")
            .FROM(USER_TABLE)
            .WHERE("user_id = #{user_id}")
            .LIMIT(1)
            .toString()
    }

    companion object {
        const val USER_TABLE = "kakao_finance_be.tasur_usr"
        const val METHOD_INSERT = "insertUser"
        const val METHOD_GET = "getUser"
    }
}