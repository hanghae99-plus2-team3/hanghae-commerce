package hanghae99.plus2.team3.hanghaelikecart.acceptance

import com.google.common.base.CaseFormat
import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Profile("test")
@Component
class DatabaseCleaner(
    @PersistenceContext
    private val entityManager: EntityManager
) : InitializingBean {

    private var tableNames = listOf<String>()

    override fun afterPropertiesSet() {
        tableNames = entityManager.metamodel.entities
            .filter { e ->
                e.javaType.getAnnotation(
                    Entity::class.java
                ) != null
            }
            .map { e ->
                CaseFormat.UPPER_CAMEL.to(
                    CaseFormat.LOWER_UNDERSCORE,
                    e.name
                )
            }
    }

    @Transactional
    fun execute() {
        entityManager.flush()
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        for (tableName in tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $tableName ALTER COLUMN ID RESTART WITH 1").executeUpdate()
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}
