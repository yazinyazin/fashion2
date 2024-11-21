package net.yazin.fashion.controller

import net.yazin.fashion.exception.RequestParamNotRecognizedException
import net.yazin.fashion.model.Result
import net.yazin.fashion.model.Speech
import net.yazin.fashion.service.CsvService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("evaluation")
class Controller (private val csvService: CsvService) {

    @GetMapping("")
    fun read(@RequestParam urls:Map<String,String>):ResponseEntity<Result>{

        urls.forEach{if (!it.key.startsWith("url", ignoreCase = true)) throw RequestParamNotRecognizedException() }

        val speeches = mutableListOf<Speech>()

        urls.forEach { speeches.addAll(csvService.parse(csvService.readFromUrl(it.value))) }

        return ResponseEntity.ok(csvService.getResult(speeches))

    }
}