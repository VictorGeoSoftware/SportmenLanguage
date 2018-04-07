package com.victor.test.sport.di

import com.victor.test.sport.network.SportmenRepository
import com.victor.test.sport.presenters.reviews.SportMenPresenter
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */

@Module(includes = [AppModule::class])
class PresenterModule {

    @Provides
    fun provideReviewsPresenter(sportmenRepository: SportmenRepository): SportMenPresenter =
            SportMenPresenter(AndroidSchedulers.mainThread(), Schedulers.newThread(), sportmenRepository)
}