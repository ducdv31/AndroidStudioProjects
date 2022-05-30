package vn.dv.myvideo.view.main.fragment

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import vn.dv.myvideo.utils.UtilsFT


class PopularVideoFragmentTest {
    private lateinit var SUT: UtilsFT

    @Before
    fun setUp() {
        SUT = UtilsFT()
    }

    @Test
    fun test3() {

    }

    @Test
    fun test1() {
        val result = SUT.isPositive(-5)
        assertThat(
            result,
            CoreMatchers.`is`(false)
        )
    }

    @Test
    fun test2() {
        val result = SUT.isPositive(0)
        assertThat(
            result,
            CoreMatchers.`is`(false)
        )
    }
}