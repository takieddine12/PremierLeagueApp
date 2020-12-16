package taki.eddine.premier.league.pro.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import taki.eddine.premier.league.pro.BuildConfig
import taki.eddine.premier.league.pro.webauthentification.ApiResponse
import taki.eddine.premier.league.pro.room.GlobalDatabase
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun setUpRetrofit() : ApiResponse {
        return Retrofit.Builder().baseUrl(BuildConfig.NextFixturesApiLink)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiResponse::class.java)
    }

    @Provides
    @Singleton
    @TopScorersRetrofitCall
    fun setUpRetrofit2() : ApiResponse {
        return Retrofit.Builder().baseUrl(BuildConfig.TopScorersApiLink)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiResponse::class.java)
    }
    @Provides
    @Singleton
    fun setUpDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, GlobalDatabase::class.java,"gloabldb.db").build()


    @NewsDaoImpl
    @Provides
    @Singleton
    fun provideNewsDao(globalDatabase: GlobalDatabase) = globalDatabase.newsDao()

    @StandingsDaoImpl
    @Provides
    @Singleton
    fun provideStandingsDao(globalDatabase: GlobalDatabase) = globalDatabase.standingsDao()

    @FixturesDaoImpl
    @Provides
    @Singleton
    fun provideFixturesDao(globalDatabase: GlobalDatabase) = globalDatabase.fixturesDao()

    @LiveScoresImpl
    @Provides
    @Singleton
    fun provideLiveScoresDao(globalDatabase: GlobalDatabase) = globalDatabase.livescoresDao()

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

    @TopScorersDaoImpl
    @Provides
    @Singleton
    fun provideTopScorersDao(globalDatabase: GlobalDatabase) = globalDatabase.topscorersDao()


    @BottomStandingsDaoImpl
    @Provides
    @Singleton
    fun provideStandingsBottomDao(globalDatabase: GlobalDatabase) = globalDatabase.StandingsBottomDao()
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BottomStandingsDaoImpl

@Qualifier
@Retention(AnnotationRetention.BINARY) // Binary
annotation class NewsDaoImpl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StandingsDaoImpl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FixturesDaoImpl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LiveScoresImpl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TopScorersRetrofitCall

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TopScorersDaoImpl
