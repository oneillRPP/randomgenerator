package io.ytree.randomgenerator


fun main() {
    val randomNums = intArrayOf(-1, 0, 1, 2, 3)
    val probabilities = floatArrayOf(0.01F, 0.3F, 0.58F, 0.1F, 0.01F)
    val randomGen = RandomGen(randomNums, probabilities)

    randomGen.nextNum()
}