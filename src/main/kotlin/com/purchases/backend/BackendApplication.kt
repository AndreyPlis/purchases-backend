package com.purchases.backend

import com.purchases.backend.repository.GoodRepository
import com.purchases.backend.repository.MeasureRepository
import com.purchases.backend.repository.PurchaseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaRepositories(basePackages = arrayOf("com.purchases.backend.repository"))
@EntityScan(basePackages = arrayOf("com.purchases.backend.model"))
@EnableTransactionManagement
class BackendApplication : CommandLineRunner {

    @Autowired
    private lateinit var goodRepository: GoodRepository

    @Autowired
    private lateinit var purchaseRepository: PurchaseRepository

    @Autowired
    private lateinit var measureRepository: MeasureRepository


    override fun run(vararg args: String?) {
        /*  purchaseRepository.deleteAllInBatch()
          goodRepository.deleteAllInBatch()


          val good = Good("Milk")

          val m = arrayOf("мл", "кг", "л", "шт", "гр")
          for (measure in m) {
              val measure2 = Measure(measure)
              measureRepository.save(measure2)
          }*/

        /*val purchase = Purchase(1,1.5f,good,null,null,null)

        purchaseRepository.save(purchase) */
    }
}


fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
