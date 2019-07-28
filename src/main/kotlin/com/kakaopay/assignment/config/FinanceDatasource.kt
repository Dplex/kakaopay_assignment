package com.kakaopay.assignment.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
        basePackages = arrayOf("com.kakaopay.assignment.repo"),
        entityManagerFactoryRef = "financeEntityManager",
        transactionManagerRef = "financeTransactionManager"
)
class FinanceDatasource(@Autowired val financeConfig: FinanceConfig) {
    @Primary
    @Bean
    fun dataSource(): DataSource {
        return DriverManagerDataSource().also {
            it.url = financeConfig.dbUrl
            it.username = financeConfig.dbUser
            it.password = financeConfig.dbPassword
        }
    }

    @Primary
    @Bean
    fun financeEntityManager(): LocalContainerEntityManagerFactoryBean {
        return LocalContainerEntityManagerFactoryBean().also {
            it.dataSource = dataSource()
            it.setPackagesToScan("com.kakaopay.assignment.repo.model")

            it.jpaVendorAdapter = HibernateJpaVendorAdapter()
            it.setJpaPropertyMap(
                    HashMap<String, Any>().also { map ->
//                        map.put("hibernate.hbm2ddl.auto", "true")
                        map.put("hibernate.temp.use_jdbc_metadata_defaults", false)
                        map.put("hibernate.jdbc.lob.non_contextual_creation", true)
                        map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect")
                    })
        }
    }

    @Primary
    @Bean
    fun financeTransactionManager(): PlatformTransactionManager {
        return JpaTransactionManager().also {
            it.entityManagerFactory = financeEntityManager().`object`
        }
    }

}