package vn.dv.myhome.utils.bus

import com.squareup.otto.Bus

class GlobalBus : Bus() {

    companion object {

        private val bus = GlobalBus()

        fun getBus(): Bus = bus
    }
}