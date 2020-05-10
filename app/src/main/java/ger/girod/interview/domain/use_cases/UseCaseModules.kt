package ger.girod.interview.domain.use_cases

import ger.girod.interview.data.repository.UserDetailRepository
import ger.girod.interview.data.repository.UsersRepository
import org.koin.dsl.module


fun provideUserUseCase(usersRepository: UsersRepository) : GetUsersUseCase {
    return GetUsersUseCaseImpl(usersRepository)
}

fun provideFavoriteUserUseCase(usersRepository: UsersRepository) : GetFavoritesUserUseCase {
    return GetFavoritesUsersUseCaseImpl(usersRepository)
}

fun provideSaveUserUseCase(detailRepository: UserDetailRepository) : SaveFavoriteUserUseCase {
    return SaveFavoriteUsersUseCaseImpl(detailRepository)
}

val useCasesModules = module {
    factory { provideUserUseCase(get()) }
    factory { provideFavoriteUserUseCase(get()) }
    factory { provideSaveUserUseCase(get()) }
}