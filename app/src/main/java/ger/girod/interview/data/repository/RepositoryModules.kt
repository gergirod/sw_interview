package ger.girod.interview.data.repository

import org.koin.dsl.module

val repositoryModule = module {
    factory {UsersRepository(get(), get())}
    factory { UserDetailRepository(get()) }
}