package com.example.mars

import com.example.mars.data.repository.MainRepository
import com.example.mars.ui.rover.RoverViewModel
import com.example.mars.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.inject.Inject

class RoverViewModelTest {
    lateinit var roverViewModel:RoverViewModel
    lateinit var repository: MainRepository
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setup(){
        repository = mock(MainRepository::class.java)
        roverViewModel = RoverViewModel(repository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun getRoverNameTest(){
        Assert.assertEquals(roverViewModel.getRoverName(R.id.radioButtonCuriosity), Constants.Curiosity)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}