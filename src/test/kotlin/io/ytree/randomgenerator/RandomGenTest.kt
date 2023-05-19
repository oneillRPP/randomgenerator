package io.ytree.randomgenerator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test

internal class RandomGenTest {

    // Accepted test inputs
    private val randomNums = intArrayOf(-1, 0, 1, 2, 3)
    private val probabilities = floatArrayOf(0.01F, 0.3F, 0.58F, 0.1F, 0.01F)

    // Exception test inputs
    private val randomNumsNonUnique = intArrayOf(-1, 0, 1, 2, 2)
    private val probabilitiesIncorrectNumber = floatArrayOf(0.01F, 0.3F, 0.58F, 0.1F)
    private val probabilitiesIncorrectSum = floatArrayOf(0.01F, 0.3F, 0.58F, 0.1F, 0.02F)

    // For repeated test for probability checking
//    val results = mutableListOf<Int>()

    @Test
    fun `expect generated num to be one of inputs`() {
        val randomGen = RandomGen(randomNums, probabilities)
        val result = randomGen.nextNum()
        val isResultInInputs = randomNums.contains(result)

        assertEquals(true, isResultInInputs)
    }

    @Test
    fun `expect different number of randomNums and probabilities throws IllegalArgumentException with message`() {
        val randomGen = RandomGen(randomNums, probabilitiesIncorrectNumber)

        val exception = assertThrows(
            IllegalArgumentException::class.java
        ) { randomGen.nextNum() }

        assertEquals(exception.message, "There should be an equal number of randomNums and probabilities")
    }

    @Test
    fun `expect randomNums containing non-unique values throws IllegalArgumentException with message`() {
        val randomGen = RandomGen(randomNumsNonUnique, probabilities)

        val exception = assertThrows(
            IllegalArgumentException::class.java
        ) { randomGen.nextNum() }

        assertEquals(exception.message, "randomNums should only contain unique entries")
    }

    @Test
    fun `expect probabilities that don't sum to 1 to throw IllegalArgumentException with message`() {
        val randomGen = RandomGen(randomNums, probabilitiesIncorrectSum)

        val exception = assertThrows(
            IllegalArgumentException::class.java
        ) { randomGen.nextNum() }

        assertEquals(exception.message, "probabilities should sum to 1")
    }

    /*
    Attempted to write a test that would check the probability convergence
    Runs the nextNum() method 100 times and collects the results
    Results are output, unsure how to verify (also, might be expensive to run)
     */
    @Test
    fun `expect generated nums to converge on probability`() {
        val numberOfExecutions = 100
        val results = mutableListOf<Int>()
        val randomGen = RandomGen(randomNums, probabilities)

        // Repeat method 100 times and store result each time
        for (i in 0 until numberOfExecutions) {
            val result = randomGen.nextNum()
            results.add(result)
        }

        // For each unique randomNum input, calculate occurrence
        val resultsMap = results.groupingBy { it }.eachCount()
        println("Results of running nextNum() $numberOfExecutions times: ${resultsMap.toString()}")
    }
}