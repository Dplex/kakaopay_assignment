package com.kakaopay.assignment.config

import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
// @EnableJpaRepositories(
//        basePackages = arrayOf("com.kakaopay.assignment.repo"),
//        entityManagerFactoryRef = "financeEntityManager",
//        transactionManagerRef = "financeTransactionManager"
// )
@EnableTransactionManagement
@MapperScan(basePackages = arrayOf("com.kakaopay.assignment.repo.mapper"))
class FinanceDatasource(@Autowired val financeConfig: FinanceConfig) {
    @Primary
    @Bean
    fun dataSource(): DataSource {

        return HikariDataSource().also {
            it.jdbcUrl = financeConfig.dbUrl
            it.username = financeConfig.dbUser
            it.password = financeConfig.dbPassword
        }
    }

//    @Bean
//    fun transactionManager(): DataSourceTransactionManager {
//        return DataSourceTransactionManager(dataSource())
//    }

    @Bean
    fun sqlSessionFactory(): SqlSessionFactoryBean {
        return SqlSessionFactoryBean().apply {
            this.setDataSource(dataSource())
        }
    }

    @Bean
    fun sqlSession(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }

//    @Primary
//    @Bean
//    fun financeEntityManager(): LocalContainerEntityManagerFactoryBean {
//        return LocalContainerEntityManagerFactoryBean().also {
//            it.dataSource = dataSource()
//            it.setPackagesToScan("com.kakaopay.assignment.repo.model")
//
//            it.jpaVendorAdapter = HibernateJpaVendorAdapter()
//            it.setJpaPropertyMap(
//                    HashMap<String, Any>().also { map ->
// //                        map.put("hibernate.hbm2ddl.auto", "true")
//                        map.put("hibernate.temp.use_jdbc_metadata_defaults", false)
//                        map.put("hibernate.jdbc.lob.non_contextual_creation", true)
//                        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect")
//                    })
//        }
//    }
//
//    @Primary
//    @Bean
//    fun financeTransactionManager(): PlatformTransactionManager {
//        return JpaTransactionManager().also {
//            it.entityManagerFactory = financeEntityManager().`object`
//        }
//    }
}