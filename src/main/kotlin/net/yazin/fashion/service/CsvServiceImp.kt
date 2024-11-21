package net.yazin.fashion.service

import net.yazin.fashion.model.Result
import net.yazin.fashion.model.Speech
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.net.URL
import java.time.LocalDate

@Service
class CsvServiceImp : CsvService {

    override fun readFromFile(file:String):BufferedReader{
        return File(file).bufferedReader()
    }

    override fun readFromUrl(url:String):BufferedReader{
        return URL(url).openConnection().getInputStream().bufferedReader()
    }

    override fun parse(reader:BufferedReader): List<Speech> {
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (speaker, topic, date, words) = it.split(';', ignoreCase = false, limit = 4)
                Speech(speaker.trim(), topic.trim(), LocalDate.parse(date.trim()), words.toInt())
            }.toList()
    }

    override fun getSpeechesCountByYear(year:Int, speeches:List<Speech>): Map<String, Int> {

        return speeches.filter { it.date.year == year }
            .groupingBy { it.speaker }.eachCount().filter { it.value > 0 }
    }


    override fun getSpeechesCountByTopic(topic:String, speeches:List<Speech>): Map<String, Int> {

        return speeches.filter { it.topic.equals(topic, ignoreCase = true) }
            .groupingBy { it.speaker }.eachCount().filter { it.value > 0 }
    }

    override fun sumWordCountsBySpeaker (speeches:List<Speech>): Map<String, Int> {

        return speeches.groupBy { it.speaker } . mapValues { (_, speeches) -> speeches.sumOf { it.words } }
    }

    override fun findPolitician (politicians:Map<String, Int>,isMax:Boolean):String?{

        val numberOfMinOrMaxSpeeches=
            if(isMax) politicians.values.maxOrNull() ?: return null
            else politicians.values.minOrNull() ?: return null

        val listOfPoliticiansSatisfyingTheCondition = politicians.filter { it.value == numberOfMinOrMaxSpeeches }

        if (listOfPoliticiansSatisfyingTheCondition.size > 1) return null

        return listOfPoliticiansSatisfyingTheCondition.keys.first()

    }

    override fun getResult(speeches:List<Speech>):Result{
        return Result(
                        findPolitician(getSpeechesCountByYear(2013,speeches),true),
                        findPolitician(getSpeechesCountByTopic("Homeland Security",speeches),true),
                        findPolitician(sumWordCountsBySpeaker(speeches),false)
        )
    }



}