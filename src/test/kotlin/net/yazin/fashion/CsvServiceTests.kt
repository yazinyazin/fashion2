package net.yazin.fashion

import net.yazin.fashion.service.CsvService
import net.yazin.fashion.service.CsvServiceImp
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test


class CsvServiceTests() {

    private val csvService = CsvServiceImp();

    @Test
    fun readFile_thenCountSpeeches(){
        val speechesNormal = csvService.parse(csvService.readFromFile("src/main/resources/speeches_normal.csv"))
        assertEquals(6,speechesNormal.size)
    }

    @Test
    fun readFile_thenGetPoliticianWithMostSpeechesInAYear(){

        val speechesNormal = csvService.parse(csvService.readFromFile("src/main/resources/speeches_normal.csv"))
        assertEquals("James Hetfield",csvService.findPolitician(csvService.getSpeechesCountByYear(2013,speechesNormal),true))
        assertNull(csvService.findPolitician(csvService.getSpeechesCountByYear(2010,speechesNormal),true))

        val speechesTie = csvService.parse(csvService.readFromFile("src/main/resources/speeches_tie_2013.csv"))
        assertNull(csvService.findPolitician(csvService.getSpeechesCountByYear(2013,speechesTie),true))

        val speechesNone = csvService.parse(csvService.readFromFile("src/main/resources/speeches_none_2013.csv"))
        assertNull(csvService.findPolitician(csvService.getSpeechesCountByYear(2013,speechesNone),true))

        val speechesEmpty = csvService.parse(csvService.readFromFile("src/main/resources/speeches_empty.csv"))
        assertNull(csvService.findPolitician(csvService.getSpeechesCountByYear(2013,speechesEmpty),true))

        val speechesDate = csvService.parse(csvService.readFromFile("src/main/resources/speeches_date.csv"))
        assertEquals("Lars Ulrich",csvService.findPolitician(csvService.getSpeechesCountByYear(2013,speechesDate),true))
    }

    @Test
    fun readFile_thenGetPoliticianWithMostHomelandSecurity(){
        val speechesNormal = csvService.parse(csvService.readFromFile("src/main/resources/speeches_normal.csv"))
        assertEquals("Lars Ulrich",csvService.findPolitician(csvService.getSpeechesCountByTopic("Homeland Security",speechesNormal),true))
        assertEquals("Dave Mustaine",csvService.findPolitician(csvService.getSpeechesCountByTopic("Nuclear Disarmament",speechesNormal),true))
        assertNull(csvService.findPolitician(csvService.getSpeechesCountByTopic("Panda Bears",speechesNormal),true))
    }

    @Test
    fun readFile_thenGetLeastWordyPolitician(){
        val speechesWordy = csvService.parse(csvService.readFromFile("src/main/resources/speeches_wordy.csv"))
        assertEquals("Dave Mustaine",csvService.findPolitician(csvService.sumWordCountsBySpeaker(speechesWordy),false))
        assertNull(csvService.findPolitician(csvService.sumWordCountsBySpeaker(speechesWordy),true))

        val speechesWordy2 = csvService.parse(csvService.readFromFile("src/main/resources/speeches_wordy_2.csv"))
        assertEquals("James Hetfield",csvService.findPolitician(csvService.sumWordCountsBySpeaker(speechesWordy2),true))

    }


}