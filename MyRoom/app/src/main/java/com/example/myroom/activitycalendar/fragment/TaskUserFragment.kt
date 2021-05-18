package com.example.myroom.activitycalendar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.example.myroom.R
import com.example.myroom.activitycalendar.ActivityCalendar
import com.example.myroom.activitycalendar.model.TaskUser
import com.example.myroom.activitycalendar.model.TaskView
import com.example.myroom.activitycalendar.rcvadapter.RcvTaskAdapter
import com.example.myroom.activitymain.MyApplication
import com.example.myroom.components.dialog.EditTaskDialog
import com.example.myroom.components.dialog.InputTaskDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaskUserFragment : Fragment() {

    companion object {
        var fabAddTask: FloatingActionButton? = null

        @Volatile
        var swipeAble: Boolean = false
    }

    private lateinit var activityCalendar: ActivityCalendar
    private lateinit var linearLayoutComplete: LinearLayout
    private lateinit var rcvTask: RecyclerView
    private lateinit var rcvTaskComplete: RecyclerView
    private lateinit var count: TextView
    private lateinit var name: TextView
    private lateinit var dateTask: TextView

    /* adapter & list */
    private lateinit var rcvTaskAdapter: RcvTaskAdapter
    private lateinit var rcvTaskAdapterCompleted: RcvTaskAdapter
    private var listTask: MutableList<TaskView> = mutableListOf()
    private var listTaskCompleted: MutableList<TaskView> = mutableListOf()

    /* ************** */

    /* data from previous fragment */
    lateinit var taskUser: TaskUser
    lateinit var date: String
    /* *************************** */

    /* dialog data */
    lateinit var inputTaskDialog: InputTaskDialog
    lateinit var editTaskDialog: EditTaskDialog

    /* *********** */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragView = inflater.inflate(R.layout.fragment_task_user, container, false)
        activityCalendar = activity as ActivityCalendar
        activityCalendar.actionBar?.title = "Task"
        /* init variable */
        initVar(fragView)
        val linearLayoutManager =
            LinearLayoutManager(activityCalendar, RecyclerView.VERTICAL, false)
        val linearLayoutManager1 =
            LinearLayoutManager(activityCalendar, RecyclerView.VERTICAL, false)
        rcvTask.layoutManager = linearLayoutManager
        rcvTask.adapter = rcvTaskAdapter
        rcvTask.isFocusable = false
        rcvTask.isNestedScrollingEnabled = false

        rcvTaskComplete.layoutManager = linearLayoutManager1
        rcvTaskComplete.adapter = rcvTaskAdapterCompleted
        /* ************* */

        /* get TaskUser and date form transaction */
        val bundle: Bundle = arguments as Bundle
        taskUser = bundle.get(ActivityCalendar.TAG_TASKUSER) as TaskUser
        date = bundle.getString(ActivityCalendar.TAG_DATE).toString()
        name.text = taskUser.Name
        dateTask.text = date
        /* ************************************** */

        /* get task from firestore -> set data to 2 adapter + update count*/
        getFirestore(taskUser.ID, date)
        inputTaskDialog = InputTaskDialog(taskUser.ID, date)
        editTaskDialog = EditTaskDialog(taskUser.ID, date)
        /* ************************************************************** */

        return fragView
    }

    private fun setFirestore(collections: String, doc: String, data: Any) {
        Firebase.firestore.collection(collections)
            .document(doc)
            .set(data)
    }

    private fun getFirestore(collections: String, doc: String) {
        Firebase.firestore.collection(collections)
            .document(doc)
            .addSnapshotListener { value, error ->
                if (value?.data != null && error == null) {
                    listTask = mutableListOf()
                    listTaskCompleted = mutableListOf()
                    for (user in value.data!!) {
                        /* check value true/false to add to listTask/listTaskCompleted */
                        if (user.value.toString() == "false") {
                            listTask.add(TaskView(user.key.toString(), false))
                        } else if (user.value.toString() == "true") {
                            listTaskCompleted.add(TaskView(user.key.toString(), true))
                        }
                    }
                } else {
                    listTask = mutableListOf()
                    listTaskCompleted = mutableListOf()
                }
                rcvTaskAdapter.setData(listTask)
                rcvTaskAdapterCompleted.setData(listTaskCompleted)
                count.text = listTaskCompleted.size.toString()
            }
    }

    private fun initVar(view: View) {
        name = view.findViewById(R.id.tv_name_in_task)
        dateTask = view.findViewById(R.id.tv_date_in_task)
        count = view.findViewById(R.id.count_complete)
        linearLayoutComplete = view.findViewById(R.id.layout_show_complete)
        rcvTask = view.findViewById(R.id.rcv_task)
        rcvTaskComplete = view.findViewById(R.id.rcv_task_complete)
        fabAddTask = view.findViewById(R.id.fab_add_task)

        fabAddTask?.visibility = View.INVISIBLE


        rcvTaskAdapter = RcvTaskAdapter(requireContext(), object : RcvTaskAdapter.IClickCheck {
            override fun onClickCheck(taskView: TaskView, position: Int) {
                /* move from task to complete task */
                listTask.remove(taskView)
                rcvTaskAdapter.notifyItemRemoved(position)
                rcvTaskAdapter.notifyItemRangeRemoved(position, listTask.size)
                /* add to completed task */
                taskView.completed = true
                listTaskCompleted.add(taskView)
                rcvTaskAdapterCompleted.notifyDataSetChanged()
                /* update count */
                count.text = listTaskCompleted.size.toString()

                /* set data to firestore */
                val data: MutableMap<String, Boolean> = mutableMapOf()
                for (user in listTask) {
                    data[user.task] = user.completed
                }
                for (user in listTaskCompleted) {
                    data[user.task] = user.completed
                }
                setFirestore(taskUser.ID, date, data)
                /* ********************* */
            }

            override fun setOnDeleteTask(taskView: TaskView, position: Int) {
                listTask.remove(taskView)
                rcvTaskAdapter.notifyItemRemoved(position)
                rcvTaskAdapter.notifyItemRangeRemoved(position, listTask.size)
                /* set data to firestore */
                val data: MutableMap<String, Boolean> = mutableMapOf()
                for (user in listTask) {
                    data[user.task] = user.completed
                }
                for (user in listTaskCompleted) {
                    data[user.task] = user.completed
                }
                setFirestore(taskUser.ID, date, data)
                /* ********************* */
            }

            override fun setOnEditTask(
                taskView: TaskView,
                position: Int,
                oldData: String,
                swipeRevealLayout: SwipeRevealLayout
            ) {
                /* delete old task and set new task */
            }

            override fun onSwipeAble(swipeRevealLayout: SwipeRevealLayout) {
                swipeRevealLayout.setLockDrag(!swipeAble)
            }

        })
        rcvTaskAdapterCompleted =
            RcvTaskAdapter(requireContext(), object : RcvTaskAdapter.IClickCheck {
                override fun onClickCheck(taskView: TaskView, position: Int) {
                    /* move from complete task to task */
                    listTaskCompleted.remove(taskView)
                    rcvTaskAdapterCompleted.notifyItemRemoved(position)
                    rcvTaskAdapterCompleted.notifyItemRangeRemoved(position, listTaskCompleted.size)
                    /* add to task */
                    taskView.completed = false
                    listTask.add(taskView)
                    rcvTaskAdapter.notifyDataSetChanged()
                    /* update count */
                    count.text = listTaskCompleted.size.toString()

                    /* set data to firestore */
                    val data: MutableMap<String, Boolean> = mutableMapOf()
                    for (user in listTask) {
                        data[user.task] = user.completed
                    }
                    for (user in listTaskCompleted) {
                        data[user.task] = user.completed
                    }
                    setFirestore(taskUser.ID, date, data)
                    /* ********************* */
                }

                override fun setOnDeleteTask(taskView: TaskView, position: Int) {

                    listTaskCompleted.remove(taskView)
                    rcvTaskAdapterCompleted.notifyItemRemoved(position)
                    rcvTaskAdapterCompleted.notifyItemRangeRemoved(position, listTaskCompleted.size)
                    count.text = listTaskCompleted.size.toString()
                    /* set data to firestore */
                    val data: MutableMap<String, Boolean> = mutableMapOf()
                    for (user in listTask) {
                        data[user.task] = user.completed
                    }
                    for (user in listTaskCompleted) {
                        data[user.task] = user.completed
                    }
                    setFirestore(taskUser.ID, date, data)
                    /* ********************* */
                }

                override fun setOnEditTask(
                    taskView: TaskView,
                    position: Int,
                    oldData: String,
                    swipeRevealLayout: SwipeRevealLayout
                ) {
                    /* delete old task and set new task */
                    editTaskDialog = EditTaskDialog(taskUser.ID, date, oldData)
                    editTaskDialog.show(activityCalendar.supportFragmentManager, "Edit task")
                    if (swipeRevealLayout.isOpened){
                        swipeRevealLayout.close(true)
                    }
                }

                override fun onSwipeAble(swipeRevealLayout: SwipeRevealLayout) {
                    swipeRevealLayout.setLockDrag(!swipeAble)
                }

            })

        fabAddTask!!.setOnClickListener {
            /* open input Task Dialog and set Data */
            if (!inputTaskDialog.isAdded) {
                inputTaskDialog.show(activityCalendar.supportFragmentManager, "Input dialog")
            }
        }

        MyApplication.getUIDPermission(activityCalendar)

    }


}