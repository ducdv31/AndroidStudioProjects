package vn.dv.todolist.doimain.home.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import vn.dv.todolist.doimain.home.room.db.CategoryTodoDb

@Module
@InstallIn(ActivityComponent::class)
object HomeScreenModule {
    const val CategoryDbName = "category-todo-db"

    @ActivityScoped
    @Provides
    fun provideCategoryTodoDb(@ApplicationContext context: Context): CategoryTodoDb {
        return Room.databaseBuilder(
            context,
            CategoryTodoDb::class.java,
            CategoryDbName
        ).build()
    }
}