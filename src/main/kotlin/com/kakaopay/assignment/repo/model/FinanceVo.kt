package com.kakaopay.assignment.repo.model

import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor
import javax.persistence.*

@Entity
@Table(name = "tasur_hist", schema = "kakao_finance_be")
data class FinanceVo(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        val ID: Int?,

        @Column(name = "year")
        val year: Int,

        @Column(name = "month")
        val month: Int,

        @Column(name = "bank_type")
        val bankType: String,

        @Column(name = "asur_price")
        val assurancePrice: Int
)