package com.kakaopay.assignment.repo.model

import javax.persistence.*

@Entity
@Table(name = "tasur_bnk", schema = "kakao_finance_be")
data class BankVo(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val ID: Int?,

        @Column(name = "bnk_cd")
        val bankCode: String,

        @Column(name = "bnk_nm")
        val bankName: String
)