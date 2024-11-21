package net.yazin.fashion.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException
import java.lang.IndexOutOfBoundsException
import java.net.MalformedURLException

@ControllerAdvice
class ExceptionHandler {

    class ErrorMessage(val message: String)

    @ExceptionHandler()
    fun handler(ex:MalformedURLException):ResponseEntity<ErrorMessage>{
        val error = ErrorMessage("Please enter a valid URL!")
        return ResponseEntity(error,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler()
    fun handler(ex:IOException):ResponseEntity<ErrorMessage>{
        val error = ErrorMessage("Error reading file.")
        return ResponseEntity(error,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler()
    fun handler(ex:IndexOutOfBoundsException):ResponseEntity<ErrorMessage>{
        val error = ErrorMessage("Error reading file. Check your file for formatting errors.")
        return ResponseEntity(error,HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler()
    fun handler(ex:RequestParamNotRecognizedException):ResponseEntity<ErrorMessage>{
        val error = ErrorMessage("Invalid request parameter.")
        return ResponseEntity(error,HttpStatus.BAD_REQUEST)
    }
}