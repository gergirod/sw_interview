package ger.girod.interview

import android.app.Application
import ger.girod.interview.data.api.networkModule
import ger.girod.interview.data.database.databaseModule
import ger.girod.interview.data.utils.internetModule
import ger.girod.interview.data.repository.repositoryModule
import ger.girod.interview.domain.use_cases.useCasesModules
import ger.girod.interview.ui.utils.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class UsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@UsersApplication)
            modules(listOf(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
                useCasesModules,
                internetModule
            ))
        }
    }
}