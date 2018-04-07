package com.victor.test.sport

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.victor.test.sport.data.TypeDto
import com.victor.test.sport.network.SportmenRepository
import com.victor.test.sport.presenters.reviews.SportMenPresenter
import com.victor.test.sport.utils.getMockedSportMenResponse
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.SocketTimeoutException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SportMenPresenterTest {
    @Mock private lateinit var mockSportMenView: SportMenPresenter.SportMenView
    @Mock private lateinit var sportMenRepository: SportmenRepository
    private lateinit var sportMenPresenter: SportMenPresenter
    private lateinit var testScheduler: TestScheduler


    private lateinit var mockedSportMenResponse: ArrayList<TypeDto>



    private fun createMockedPresenter(): SportMenPresenter {
        testScheduler = TestScheduler()
        val reviewsPresenter = SportMenPresenter(testScheduler, testScheduler, sportMenRepository)
        reviewsPresenter.view = mockSportMenView

        return reviewsPresenter
    }

    private fun createMockedResponse(): ArrayList<TypeDto> {
        val gson = Gson()
        val typeToken = object: TypeToken<List<TypeDto>>(){}.type
        return gson.fromJson(getMockedSportMenResponse(), typeToken)
    }


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sportMenPresenter = createMockedPresenter()
        mockedSportMenResponse = createMockedResponse()
    }


    @Test
    fun `should make a request a retrieve some response`() {
        whenever(sportMenRepository.getSportMenList()).thenReturn(Observable.just(mockedSportMenResponse))

        sportMenPresenter.getSportMenList()
        testScheduler.triggerActions()

        verify(sportMenRepository, times(1)).getSportMenList()
    }

    @Test
    fun `should make a request and communicate to view the list of received reviews`() {
        whenever(sportMenRepository.getSportMenList()).thenReturn(Observable.just(mockedSportMenResponse))

        sportMenPresenter.getSportMenList()
        testScheduler.triggerActions()

        verify(mockSportMenView).onSportMenListReceived(mockedSportMenResponse)
    }


    @Test
    fun `should make a request and handle a time out error`() {
        val timeOutException = SocketTimeoutException("TIME_OUT_ERROR")
        whenever(sportMenRepository.getSportMenList()).thenReturn(Observable.error(timeOutException))

        sportMenPresenter.getSportMenList()
        testScheduler.triggerActions()

        verify(mockSportMenView).onReviewsListError()
    }
}
