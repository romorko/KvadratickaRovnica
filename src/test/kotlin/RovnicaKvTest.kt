import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RovnicaKvTest
{

    @BeforeEach
    fun setUp()
    {
    }

    @AfterEach
    fun tearDown()
    {
    }

    private val skusam:RovnicaKv(1,4,4)

    @Test
    fun dajKoreneTest()
    {
        assertEquals(1,dajKorene())
    }
}