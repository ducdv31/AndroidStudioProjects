package vn.dv.todolist.doimain.detailtodo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import vn.dv.todolist.doimain.detailtodo.room.db.TodoItemDb

@Module
@InstallIn(ActivityComponent::class)
object TodoItemModule {

    private const val TODO_DB_NAME = "TODO_DB_NAME"

    @ActivityScoped
    @Provides
    fun provideTodoDb(@ApplicationContext context: Context): TodoItemDb {
        return Room.databaseBuilder(
            context,
            TodoItemDb::class.java,
            TODO_DB_NAME
        ).build()
    }
}