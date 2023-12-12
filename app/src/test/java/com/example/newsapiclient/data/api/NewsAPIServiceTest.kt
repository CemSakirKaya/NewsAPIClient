package com.example.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsAPIServiceTest {

    private lateinit var apiService: NewsAPIService
    private lateinit var server:MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsAPIService::class.java)
    }

     fun enqueueMockRespone(fileName:String){
         val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
         val source = inputStream.source().buffer()
        val  mockResponse = MockResponse()
         mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
     }


    @After
    fun tearDown() {
       server.shutdown()
    }


    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockRespone("newsresponse.json")
           val body = apiService.getTopHeadlinesResponse(1,"us").body()
            val request = server.takeRequest()

            assertThat(body).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?page=1&country=us&apiKey=b7122b5c5f8948eda9715867b6240ce6")

        }


    }


    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockRespone("newsresponse.json")
            val body = apiService.getTopHeadlinesResponse(1,"us").body()
            val articleList = body?.articles


            if (articleList != null) {
                assertThat(articleList.size).isEqualTo(20)
            }


        }

    }








}