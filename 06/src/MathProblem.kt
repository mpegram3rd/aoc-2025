class MathProblem {
    val nums = mutableListOf<Long>()
    var calculation:  ((Long, Long) -> Long)? = null

    fun include(value: Long) {
        nums.add(value)
    }

    fun runCalculation(): Long {
        var tally: Long = nums[0]
        val tmpList = nums.drop(1)
        tmpList.forEach { num ->
            tally = calculate(tally, num)
        }
        return tally
    }

    fun calculate(num1: Long, num2: Long): Long {
        return calculation?.invoke(num1, num2)
            ?: throw IllegalStateException("Calculation Operator has not been set")
    }
}