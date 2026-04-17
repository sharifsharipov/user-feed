package com.example.userfeed.core.di

import com.example.userfeed.core.network.createHttpClient
import com.example.userfeed.core.utils.DefaultDispatcherProvider
import com.example.userfeed.core.utils.DispatcherProvider
import com.example.userfeed.data.remote.ApiService
import com.example.userfeed.data.repository.CommentRepositoryImpl
import com.example.userfeed.data.repository.FavoriteRepositoryImpl
import com.example.userfeed.data.repository.PostRepositoryImpl
import com.example.userfeed.data.repository.UserRepositoryImpl
import com.example.userfeed.domain.repository.CommentRepository
import com.example.userfeed.domain.repository.FavoriteRepository
import com.example.userfeed.domain.repository.PostRepository
import com.example.userfeed.domain.repository.UserRepository
import com.example.userfeed.domain.usecase.GetCommentsUseCase
import com.example.userfeed.domain.usecase.GetFavoritesUseCase
import com.example.userfeed.domain.usecase.RefreshCommentsUseCase
import com.example.userfeed.domain.usecase.GetPostByIdUseCase
import com.example.userfeed.domain.usecase.GetPostsByUserUseCase
import com.example.userfeed.domain.usecase.GetUsersUseCase
import com.example.userfeed.domain.usecase.RefreshPostsUseCase
import com.example.userfeed.domain.usecase.RefreshUsersUseCase
import com.example.userfeed.domain.usecase.ToggleFavoriteUseCase
import com.example.userfeed.presentation.screens.favorites.FavoritesScreenModel
import com.example.userfeed.presentation.screens.postdetail.PostDetailScreenModel
import com.example.userfeed.presentation.screens.postlist.PostListScreenModel
import com.example.userfeed.presentation.screens.splash.SplashScreenModel
import com.example.userfeed.presentation.screens.userlist.UserListScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
    single { createHttpClient() }
    singleOf(::ApiService)
}

val repositoryModule = module {
    singleOf(::UserRepositoryImpl) bind UserRepository::class
    singleOf(::PostRepositoryImpl) bind PostRepository::class
    singleOf(::CommentRepositoryImpl) bind CommentRepository::class
    singleOf(::FavoriteRepositoryImpl) bind FavoriteRepository::class
}

val useCaseModule = module {
    factoryOf(::GetUsersUseCase)
    factoryOf(::RefreshUsersUseCase)
    factoryOf(::GetPostsByUserUseCase)
    factoryOf(::RefreshPostsUseCase)
    factoryOf(::GetPostByIdUseCase)
    factoryOf(::GetCommentsUseCase)
    factoryOf(::RefreshCommentsUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::ToggleFavoriteUseCase)
}

val screenModelModule = module {
    factoryOf(::SplashScreenModel)
    factoryOf(::UserListScreenModel)
    factoryOf(::PostListScreenModel)
    factoryOf(::PostDetailScreenModel)
    factoryOf(::FavoritesScreenModel)
}

val sharedModules = listOf(
    coreModule,
    repositoryModule,
    useCaseModule,
    screenModelModule
)
