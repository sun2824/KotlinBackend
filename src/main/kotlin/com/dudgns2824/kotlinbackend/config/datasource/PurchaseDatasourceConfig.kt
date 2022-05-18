package com.dudgns2824.kotlinbackend.config.datasource

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement //트랜 잭션 사용 annotation
@EnableJpaRepositories(
    entityManagerFactoryRef = "purchaseEntityManagerFactory",
    transactionManagerRef = "purchaseTransactionManager",
    basePackages = ["com.dudgns2824.kotlinbackend.purchase.repository"]
)
class PurchaseDatasourceConfig {

    @Bean(name = ["purchaseDataSource"])
    @ConfigurationProperties(prefix = "purchase.datasource.hikari")
    fun purchaseDatasource() : DataSource {
        println("데이터 소스 설정")
        return DataSourceBuilder
            .create()
            .type(HikariDataSource::class.java)
            .build()
    }

}