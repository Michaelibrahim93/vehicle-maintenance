package com.mike.data

object ProblemSolving {
    fun stepsCount(numberOfSteps: Int): Long {
        val memo = Array<Long>(numberOfSteps + 1, { _ -> -1 })
        memo[0] = 1
        memo[1] = 1
        return stepsCount(numberOfSteps, memo)

        val list = arrayListOf<String>()
        list.remo
    }

    private fun stepsCount(numberOfSteps: Int, memo: Array<Long>): Long {
        if (memo[numberOfSteps] > -1) return memo[numberOfSteps]
        val stepsPossibilities = if (numberOfSteps <= 1)
            numberOfSteps.toLong()
        else
            stepsCount(numberOfSteps - 1) + stepsCount(numberOfSteps - 2)

        memo[numberOfSteps] = stepsPossibilities
        return stepsPossibilities
    }
}