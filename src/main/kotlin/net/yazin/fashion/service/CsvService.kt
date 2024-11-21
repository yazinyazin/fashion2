package net.yazin.fashion.service

import net.yazin.fashion.model.Result
import net.yazin.fashion.model.Speech
import java.io.BufferedReader

interface CsvService {
    fun readFromFile(file: String): BufferedReader
    fun readFromUrl(url: String): BufferedReader
    fun parse(reader: BufferedReader): List<Speech>
    fun findPolitician(politicians: Map<String, Int>, isMax: Boolean): String?
    fun getSpeechesCountByYear(year: Int, speeches: List<Speech>): Map<String, Int>
    fun getSpeechesCountByTopic(topic: String, speeches: List<Speech>): Map<String, Int>
    fun sumWordCountsBySpeaker(speeches: List<Speech>): Map<String, Int>
    fun getResult(speeches: List<Speech>): Result
}