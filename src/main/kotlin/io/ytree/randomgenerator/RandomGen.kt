package io.ytree.randomgenerator

import java.util.Random
import mu.KotlinLogging

// Suppressed for brevity of submitted solution
@Suppress("MagicNumber", "ThrowsCount")
class RandomGen (
    // Values that may be returned by nextNum() private
    val randomNums: IntArray,

    // Probability of the occurrence of randomNums private
    val probabilities: FloatArray
) {

    private val logger = KotlinLogging.logger {}

    /**
    Returns one of the randomNums. When this method is called
    multiple times over a long period, it should return the
    numbers roughly with the initialized probabilities.
     */
    fun nextNum(): Int {
        /**
         Check Inputs
         1. Must be same number of randomNums and probabilities
         2. randomNums should contain unique entries
         3. Check probabilities add to 1
         */
        if (randomNums.size != probabilities.size) {
            logger.error { "ERROR: Number of randomNums and probabilities not equal" }
            throw IllegalArgumentException ("There should be an equal number of randomNums and probabilities")
        }
        if (randomNums.distinct().size != randomNums.size) {
            logger.error { "ERROR: randomNums containing non-unique entries" }
            throw IllegalArgumentException ("randomNums should only contain unique entries")
        }
        if (probabilities.sum() != 1F) {
            logger.error { "ERROR: probabilities entries do not sum to 1" }
            throw IllegalArgumentException ("probabilities should sum to 1")
        }

        // Construct a map of each number and its associated range
        val numbersAndRangesMap = generateNumbersAndRangesMap()
        for (key in numbersAndRangesMap.keys) {
            logger.info { "For number: $key, range is: ${numbersAndRangesMap[key]}" }
        }


        // Generate random number between 0 and 99 (inclusive)
        val random = Random()
        val randomInt = random.nextInt(0,100)

        // Check which range the random number falls into & return number associated with that range
        for ((num, range) in numbersAndRangesMap) {
            if (randomInt in range) {
                logger.debug { "Generated: $randomInt, got result: $num" }
                return num
            }
        }

        logger.error { "ERROR: Couldn't generate number, returning error case" }
        return 1000
    }


    /**
    Constructs a map of each number and its associated range
     */
    private fun generateNumbersAndRangesMap(): MutableMap<Int, IntRange> {
        // Multiply each decimal probability by 100 to get its percentage probability
        val percentageProbabilities = mutableListOf<Int>()
        for (probability in probabilities) {
            val percentageProbability = (probability * 100).toInt()
            percentageProbabilities.add(percentageProbability)
        }

        /**
        Construct list of ranges using percentage probability
        (total range 0-99, otherwise we end up with 101 numbers)
         */
        var lowerBound = 0
        var upperBound: Int
        val ranges = mutableListOf<IntRange>()
        for (percentageProbability in percentageProbabilities) {
            upperBound = ((lowerBound+percentageProbability)-1)
            val range = (lowerBound..upperBound)
            ranges.add(range)
            lowerBound = (upperBound+1)
        }


        // Build map of <number, range> entries
        val numbersAndRangesMap = mutableMapOf<Int, IntRange>()
        for (randomNum in randomNums) {
            val randomNumIndex = randomNums.indexOf(randomNum)
            val randomNumRange = ranges[randomNumIndex]
            numbersAndRangesMap[randomNum] = randomNumRange
        }

        return numbersAndRangesMap
    }
}