package net.yazin.fashion

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import net.yazin.fashion.exception.ExceptionHandler
import net.yazin.fashion.model.Result
import net.yazin.fashion.model.Speech
import net.yazin.fashion.service.CsvService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.time.LocalDate

@SpringBootTest(
    classes = arrayOf(FashionApplication::class),
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)class IntegrationTest {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun whenCalled_returnJames() {
        val result = restTemplate.getForEntity("/evaluation?url1=https://yazin.net/csv1.csv",Result::class.java);

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals("James Hetfield", result.getBody()?.mostSpeeches)
    }

    @Test
    fun whenCalled_returnDave() {
        val result = restTemplate.getForEntity("/evaluation?url1=https://yazin.net/csv1.csv&url2=https://yazin.net/csv2.csv",Result::class.java);

        assertNotNull(result)
        assertEquals(HttpStatus.OK, result?.statusCode)
        assertEquals("Dave Mustaine", result.getBody()?.mostSpeeches)
    }

    @Test
    fun whenCalled_fail_parameter() {
        val result = restTemplate.getForEntity("/evaluation?url1=https://yazin.net/csv1.csv&zzz=https://yazin.net/csv2.csv",
            ExceptionHandler.ErrorMessage::class.java);

        assertNotNull(result)
        assertEquals(HttpStatus.BAD_REQUEST, result?.statusCode)
        assertEquals("Invalid request parameter.", result.getBody()?.message)

    }

    @Test
    fun whenCalled_fail_file() {
        val result = restTemplate.getForEntity("/evaluation?url1=https://yazin.net/csv1.csv&url2=https://yazin.net/csv3.csv",
            ExceptionHandler.ErrorMessage::class.java);

        assertNotNull(result)
        assertEquals(HttpStatus.BAD_REQUEST, result?.statusCode)
        assertEquals("Error reading file. Check your file for formatting errors.", result.getBody()?.message)

    }
}