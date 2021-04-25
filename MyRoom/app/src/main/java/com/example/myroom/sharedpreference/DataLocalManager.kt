package com.example.myroom.sharedpreference

import android.content.Context

class DataLocalManager() {
    constructor(context: Context) : this() {

    }

    private var mySharedPreference: MySharedPreference? = null

    companion object {
        private val PRE_FIRST = "PRE_FIRST"
        private val NUM_LED = "NUM_LED"
        private val TOPIC_LED = "TOPIC_LED"
        private val HOST_SAVE = "Host share preference"
        private val PORT_SAVE = "Port share preference"
        private val USERNAME_SAVE = "User name share preference"
        private val PASSWORD_SAVE = "Password share preference"
        private val TOPIC_SUB_SAVE = "Topic sub share preference"
        private val QOS_SUB_SAVE = "Qos sub share preference"
        private val TOPIC_PUB_SAVE = "Topic publish share preference"
        private val CONTENT_PUB_SAVE = "Content publish share preference"

        /* for calendar */
        private val DAY_SAVE = "day calendar share preference"
        private val MONTH_SAVE = "month calendar share preference"
        private val YEAR_SAVE = "year calendar share preference"

        /* ******************** */
        private var instance: DataLocalManager? = null


        fun init(context: Context?) {
            instance = DataLocalManager()
            instance!!.mySharedPreference = MySharedPreference(context!!)
        }


        fun getInstance(): DataLocalManager? {
            if (instance == null) {
                instance = DataLocalManager()
            }
            return instance
        }

        fun setFirstInstall(isfirst: Boolean) {
            DataLocalManager.getInstance()?.mySharedPreference?.putBooleanValue(PRE_FIRST, isfirst)
        }

        fun getFirstInstall(): Boolean {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getBooleanValue(PRE_FIRST)
        }

        fun setNumLed(num: Int) {
            DataLocalManager.getInstance()?.mySharedPreference?.putIntValue(NUM_LED, num)
        }

        fun getNumLed(): Int {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getIntValue(NUM_LED)
        }

        fun setTopicLed(topic: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(TOPIC_LED, topic)
        }

        fun getTopicLed(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(TOPIC_LED)
        }

        fun setHostMQTT(hostMQTT: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(HOST_SAVE, hostMQTT)
        }

        fun getHostMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(HOST_SAVE)
        }

        fun setPortMQTT(portMQTT: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(PORT_SAVE, portMQTT)
        }

        fun getPortMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(PORT_SAVE)
        }

        fun setUsernameMQTT(usernameMQTT: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(
                USERNAME_SAVE,
                usernameMQTT
            )
        }

        fun getUsernameMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(USERNAME_SAVE)
        }

        fun setPasswordMQTT(passwordMQTT: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(
                PASSWORD_SAVE,
                passwordMQTT
            )
        }

        fun getPasswordMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(PASSWORD_SAVE)
        }

        fun setTopicSubMQTT(topicSubMQTT: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(
                TOPIC_SUB_SAVE,
                topicSubMQTT
            )
        }

        fun getTopicSubMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(
                TOPIC_SUB_SAVE
            )
        }

        fun setQosSubMQTT(qosSubMQTT: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(
                QOS_SUB_SAVE,
                qosSubMQTT
            )
        }

        fun getQosSubMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(QOS_SUB_SAVE)
        }

        fun setTopicPubMQTT(topicPubMQTT: String?) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(
                TOPIC_PUB_SAVE,
                topicPubMQTT
            )
        }

        fun getTopicPubMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(
                TOPIC_PUB_SAVE
            )
        }

        fun setContentPubMQTT(contentPubMQTT: String) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putStringValue(
                CONTENT_PUB_SAVE,
                contentPubMQTT
            )
        }

        fun getContentPubMQTT(): String? {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getStringValue(
                CONTENT_PUB_SAVE
            )
        }

        /* day calendar */
        fun setDayCalendar(dayOfWeek: Int) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putIntValue(
                DAY_SAVE,
                dayOfWeek
            )
        }

        fun getDayCalendar(): Int {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getIntValue(
                DAY_SAVE
            )
        }

        /* month calendar */
        fun setMonthCalendar(month: Int) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putIntValue(
                MONTH_SAVE,
                month
            )
        }

        fun getMonthCalendar(): Int {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getIntValue(
                MONTH_SAVE
            )
        }

        /* year calendar */
        fun setYearCalendar(year: Int) {
            DataLocalManager.getInstance()?.mySharedPreference!!.putIntValue(
                YEAR_SAVE,
                year
            )
        }

        fun getYearCalendar(): Int {
            return DataLocalManager.getInstance()?.mySharedPreference!!.getIntValue(
                YEAR_SAVE
            )
        }
    }
}