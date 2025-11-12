package com.mike.data

import org.junit.Assert.*
import org.junit.Test

class ProblemSolvingTest {
    @Test
    fun solution_1() {
        val solution = ProblemSolving.solution(listOf(1, 9, 9, 10, 2), 2)

        assertTrue(solution == (19/3.0 + 12/2.0))
    }

}