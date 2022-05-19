package com.dudgns2824.kotlinbackend.config.datasource

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
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

    @Bean(name = ["purchaseDataSource"]) // 데이터 소스 빈 등록
    @ConfigurationProperties(prefix = "purchase.datasource.hikari") // purchase.yml에서 purchase:datasource:hikari 내부의 설정 가져옴
    fun purchaseDatasource(): DataSource {
        return DataSourceBuilder
            .create()
            .type(HikariDataSource::class.java)
            .build()
    }

    @Primary
    @Bean(name = ["purchaseEntityManagerFactory"])
    fun purchaseEntityManager(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("purchaseDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages("com.dudgns2824.kotlinbackend.purchase.entity")
            .persistenceUnit("purchase")
            .build()
    }

}