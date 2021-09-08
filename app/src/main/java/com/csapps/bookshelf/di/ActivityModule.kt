package com.csapps.bookshelf.di

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.csapps.bookshelf.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @ActivityScoped
    @Provides
    fun provideRecyclerViewVerticalDivider(
        @ApplicationContext context: Context): DividerItemDecoration {
        var decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.transprent_view)!!)
        return decoration
    }

    @ActivityScoped
    @Provides
    fun provideBookNames(@ApplicationContext context: Context) = context.resources.getStringArray(R.array.book_names)

}