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
import kotlin.collections.ArrayList


@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)

/*
    val sb = StringBuilder()


    val categories = categories()
    val pr = products(categories)


    categories.forEach { category ->
        sb.append(createSql(category))
        category.subCategories.forEach {
            sb.append(createSql(it))
        }
    }

    pr.forEach {
        sb.append(createSql(it))
    }


    saveFile("data.sql", sb.toString())*/
}

fun createSql(category: Category): String {
    return "INSERT INTO CATEGORY (ID, NAME, LEVEL,IMAGE, PARENT_ID) VALUES (${category.id},'${category.name}',${category.level},${category.image},${category.parentCategory?.id});\n"
}

fun createSql(product: Product): String {
    var s = "INSERT INTO PRODUCT (ID, NAME, PRICE,PRICE_BEGINS_ON, PRICE_ENDS_ON) VALUES (${product.id},'${product.name.replace("'", "''")}',${product.price},null,null);\n"

    product.categories.forEach {
        s += "INSERT INTO PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_ID) VALUES(${product.id},${it.id});\n"
    }
    return s
}

fun products(categories: List<Category>): List<Product> {
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

    val products = ArrayList<Product>()
    categories.forEach { category ->
        category.subCategories.forEach {
            val str = String(Files.readAllBytes(Paths.get("./src/main/resources/data/${it.id}.txt")))
            val mapper = ObjectMapper()
            val root = mapper.readTree(str)
            buildProducts(root[0].path("products"), it, products)
        }
    }

    return products
}

fun buildProducts(productsNode: JsonNode, category: Category, products: MutableList<Product>) {

    productsNode.forEach { it ->
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

        val product = products.find { it.id.equals(id) }

        if (product != null) {
            product.categories.add(category)
            category.products.add(product)
        } else {
            val newProduct = Product(id, name, price, priceBeginsOn, priceEndsOn, mutableListOf(category))
            category.products.add(newProduct)
            products.add(newProduct)
        }
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
    val cat = Category(id, name, level, parent, HashSet(), null, ArrayList())
    category.path("categories").forEach {
        cat.subCategories.add(buildCategory(it, cat))
    }
    return cat;
}




