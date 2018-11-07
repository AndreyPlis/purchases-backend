package com.purchases.backend

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.purchases.backend.model.Category
import com.purchases.backend.model.Product
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)


    /* val categories = categories()
     products(categories)
     var count = 0
     categories.forEach { category ->
         category.subCategories.forEach {
             val c = it.products.count()
             System.out.println(c)
             count += c
         }
     }

     System.out.println(count)*/
}

fun products(categories: List<Category>) {
    /* val restTemplate = RestTemplate()
     restTemplate.getMessageConverters()
             .add(0, StringHttpMessageConverter(Charset.forName("UTF-8")));
     categories.forEach {
         it.subCategories.forEach {
             val fooResourceUrl = "https://wsmacp.globus.ru/api/catalog/categories/${it.id}/products?store_id=81"
             val response = restTemplate.getForEntity(fooResourceUrl, String::class.java)
             saveFile("${it.id}.txt", response.body!!)
         }
     }*/

    categories.forEach { category ->
        category.subCategories.forEach {
            val str = String(Files.readAllBytes(Paths.get("./src/main/resources/data/${it.id}.txt")))
            val mapper = ObjectMapper()
            val root = mapper.readTree(str)
            buildProducts(root[0].path("products"), it)
        }
    }
}

fun buildProducts(products: JsonNode, category: Category) {

    products.forEach {
        val id = it.path("id").longValue()
        val name = it.path("name").textValue()
        val price = it.path("price").floatValue()

        val begin = it.path("price_begins_on")?.textValue()
        val end = it.path("price_ends_on")?.textValue()

        var priceBeginsOn: Date? = null
        if (begin != null)
            priceBeginsOn = Date.from(LocalDateTime.parse(begin, DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant())
        var priceEndsOn: Date? = null
        if (end != null)
            priceEndsOn = Date.from(LocalDateTime.parse(end, DateTimeFormatter.ISO_DATE_TIME).atZone(ZoneId.systemDefault()).toInstant())
        category.products.add(Product(id, name, price, priceBeginsOn, priceEndsOn, category))
    }
}

fun categories(): List<Category> {
    /* val restTemplate = RestTemplate()
    restTemplate.getMessageConverters()
            .add(0, StringHttpMessageConverter(Charset.forName("UTF-8")));
    val fooResourceUrl = "https://wsmacp.globus.ru/api/catalog/categories?store_id=81"
    val response = restTemplate.getForEntity(fooResourceUrl, String::class.java)*/


    val str = String(Files.readAllBytes(Paths.get("./src/main/resources/data/categories.txt")))
    val mapper = ObjectMapper()
    val root = mapper.readTree(str)

    val list = root.map {
        buildCategory(it, null)
    }

    return list
}


fun saveFile(path: String, source: String) {
    val file = File("./src/main/resources/data/$path")
    val out = BufferedWriter(FileWriter(file))
    out.write(source)
    out.close()
}

fun buildCategory(category: JsonNode, parent: Category?): Category {
    val id = category.path("id").longValue()
    val level = category.path("level").intValue()
    val name = category.path("name").textValue()
    val image = category.path("image").textValue()
    val cat = Category(id, name, level, parent, HashSet(), null)
    category.path("categories").forEach {
        cat.subCategories.add(buildCategory(it, cat))
    }
    return cat;
}




