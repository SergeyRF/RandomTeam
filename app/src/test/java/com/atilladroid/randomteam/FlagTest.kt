package com.atilladroid.randomteam

import org.junit.Test

/**
 * Created by sergey on 5/30/18.
 */
class FlagTest {

    val a = 2
    val b = 4
    val c = 1.shl(25)

    @Test
    fun testFlags() {
        var flag = 0
        flag = flag.or(2)
        flag = flag.or(4)
        flag = flag.or(8)
        flag = flag.or(16)
        flag = flag.xor(4)
        var b :Byte = 0

        println(1.shl(1))


        /*println(flag.and(2)==2)
        println(flag.and(8)==8)
        println(flag.and(4)==4)

        println(flag.and(16)==16)
        println(flag)*/
    }
}