package net.yazin.fashion

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import net.yazin.fashion.model.Result
import net.yazin.fashion.model.Speech
import net.yazin.fashion.service.CsvService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.time.LocalDate

@WebMvcTest
class ControllerUnitTest(@Autowired val mockMvc:MockMvc) {
    @MockkBean
    lateinit var csvService: CsvService

    @Test
    fun check(){
        //every {csvService.getResult(any())}.returns(Result(null,null,null))
        every {csvService.readFromUrl(any())}.returns(File("src/main/resources/speeches_normal.csv").bufferedReader())
        every {csvService.parse(any())}.returns(listOf(Speech("Person","Topic", LocalDate.now(),100)))
        every {csvService.getResult(any())}.returns(Result(null,null,null))

        mockMvc.perform(get("/evaluation?url1=haha"))
            .andExpect(status().is2xxSuccessful);

        mockMvc.perform(get("/evaluation?url1=haha&url2=hihihi"))
            .andExpect(status().is2xxSuccessful);

        mockMvc.perform(get("/evaluation?url1=haha&url2=hihihi&url99=hohoho"))
            .andExpect(status().is2xxSuccessful);

        mockMvc.perform(get("/evaluation?url1=haha&zzz=hihihi"))
            .andExpect(status().isBadRequest);

    }
}