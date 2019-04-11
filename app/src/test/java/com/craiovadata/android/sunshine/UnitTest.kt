package com.craiovadata.android.sunshine

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isCorrect_time() {
//        assertEquals(4, 2 + 2)
        val nowMillsUTC = System.currentTimeMillis()

        val timeZoneID = TimeZone.getAvailableIDs()[361]
        val tz = TimeZone.getTimeZone(timeZoneID)
//        val tz = TimeZone.getTimeZone("Australia/Sydney")

//        for (i in 0 until TimeZone.getAvailableIDs().size) {
//            val id = TimeZone.getAvailableIDs()[i]
//            print("\n $i $id")
////            TimeZone.getTimeZone(Time)
//        }


        val simpleDateFormat = SimpleDateFormat("HH.mm   dd.MMM.yyyy", Locale.getDefault())
        simpleDateFormat.setTimeZone(tz)
        val result = simpleDateFormat.format(Date())

        val date = Date()
//        date.time = nowMillsUTC


        print(timeZoneID + "  " + result)
        print("\n  date: " + date + " " + date.time + " UTC: " + System.currentTimeMillis() )
    }

    @Test
    fun showAllTimeZoneIds(){
        TimeZone.getAvailableIDs().forEach {
            println(it)
        }
    }


}
