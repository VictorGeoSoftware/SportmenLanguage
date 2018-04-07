package com.victor.test.sport.presenters.reviews

import com.victor.test.sport.data.TypeDto
import com.victor.test.sport.network.SportmenRepository
import com.victor.test.sport.presenters.Presenter
import com.victor.test.sport.utils.trace
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by victorpalmacarrasco on 29/3/18.
 * ${APP_NAME}
 */
class SportMenPresenter(
        private val androidSchedulers: Scheduler,
        private val subscriberSchedulers: Scheduler,
        private val sportMenRepository: SportmenRepository): Presenter<SportMenPresenter.SportMenView>() {

    private lateinit var disposable: Disposable


    // ----------------------------------------------------------------------------------------------------------
    // --------------------------------------------- VIEW INTERFACE ---------------------------------------------
    interface SportMenView {
        fun onSportMenListReceived(sportMenResponse: ArrayList<TypeDto>) {}
        fun onReviewsListError() { }
    }


    // -------------------------------------------------------------------------------------------------------------
    // --------------------------------------------- PRESENTER METHODS ---------------------------------------------
    /**
        In this funcion, we call to web service and aside list of Reviews, we also receive the total
        number of available reviews, which we keep on a variable.
        With this value, we can call to getAllReviews methods, and retrieve all availabe reviews.
     */
    fun getSportMenList() {
        disposable = sportMenRepository.getSportMenList()
                .observeOn(androidSchedulers)
                .subscribeOn(subscriberSchedulers)
                .subscribe(
                        {
                            trace("getSportMenList - response :: $it")
                            view?.onSportMenListReceived(it)
                        },
                        {
                            trace("getSportMenList - Error :: ${it.localizedMessage}")
                            it.printStackTrace()
                            view?.onReviewsListError()
                        },
                        {
                            trace("getSportMenList - finish!")
                        }
                )
    }


    override fun destroy() {
        if (disposable.isDisposed) {
            disposable.dispose()
        }
    }


}