# UserFeed - Kotlin Multiplatform Mobile

Kotlin Multiplatform (KMP) asosidagi Android va iOS ilova. JSONPlaceholder API dan foydalanuvchilar, postlar va commentlarni yuklab, local bazada saqlaydi. Offline rejimni qo'llab-quvvatlaydi.

## Texnologiyalar

| Texnologiya | Versiya | Vazifasi |
|---|---|---|
| Kotlin Multiplatform | 2.3.20 | Android + iOS shared code |
| Jetpack Compose Multiplatform | 1.10.3 | UI framework |
| Ktor Client | 3.1.3 | HTTP so'rovlar |
| SQLDelight | 2.0.2 | Local database |
| Koin | 4.0.4 | Dependency Injection |
| Voyager | 1.1.0-beta03 | Navigation |
| Kotlinx Coroutines | 1.10.2 | Asinxron ishlash |
| Kotlinx Serialization | 1.8.1 | JSON parsing |

## API

[JSONPlaceholder](https://jsonplaceholder.typicode.com) - public REST API.

| Endpoint | Tavsif |
|---|---|
| `GET /users` | Barcha foydalanuvchilar |
| `GET /posts?userId={id}` | Foydalanuvchi postlari |
| `GET /comments?postId={id}` | Post commentlari |

## Arxitektura

**Clean Architecture + MVVM** - 3 qatlam:

```
presentation  →  domain  ←  data
(UI, ScreenModel)  (UseCase, Model, Repository interface)  (API, DB, Repository impl)
```

### SOLID printsiplari

- **S** - Har bir UseCase 1 vazifa (9 ta alohida UseCase)
- **O** - `Resource<T>` sealed class `fold`/`map`/`flatMap` bilan kengaytirilgan
- **L** - Barcha Repository interface/implementation to'g'ri almashtiriladi
- **I** - `PostRepository` va `CommentRepository` alohida interface
- **D** - Domain layer hech qanday framework ga bog'liq emas, DI orqali inject

### DRY printsiplari

- `safeApiCall` / `safeDbCall` - barcha repository'larda yagona error handling
- `UiStateScaffold` - Scaffold + Loading/Error/Success boilerplate 1 widget
- `PostCard` - PostList va Favorites da qayta ishlatiladi
- `AppTextStyle` / `AppColor` - `MaterialTheme` wrapper, Flutter dagi `context.textTheme` kabi

## Loyiha tuzilmasi

```
composeApp/src/commonMain/kotlin/com/example/userfeed/
├── core/
│   ├── constants/         ApiConstants (BASE_URL, timeout)
│   ├── database/          DatabaseDriverFactory (expect/actual)
│   ├── di/                Koin modullar (core, repository, usecase, screenmodel)
│   ├── network/           HttpClientFactory (Ktor + timeout + logging)
│   └── utils/             Resource, UiState, SafeCall, DispatcherProvider
├── data/
│   ├── mapper/            DTO <-> Domain, Entity <-> Domain mapperlar
│   ├── remote/
│   │   ├── dto/           UserDto, PostDto, CommentDto
│   │   └── ApiService     Ktor API chaqiruvlar
│   └── repository/        UserRepositoryImpl, PostRepositoryImpl,
│                          CommentRepositoryImpl, FavoriteRepositoryImpl
├── domain/
│   ├── model/             User, Post, Comment (data class)
│   ├── repository/        UserRepository, PostRepository,
│   │                      CommentRepository, FavoriteRepository (interface)
│   └── usecase/           GetUsersUseCase, RefreshUsersUseCase,
│                          HasUsersUseCase, GetPostsByUserUseCase,
│                          RefreshPostsUseCase, GetPostByIdUseCase,
│                          GetCommentsUseCase, GetFavoritesUseCase,
│                          ToggleFavoriteUseCase
└── presentation/
    ├── components/        AppTopBar, LoadingContent, ErrorContent,
    │                      PostCard, UiStateScaffold
    ├── screens/
    │   ├── splash/        SplashScreen + SplashScreenModel
    │   ├── userlist/      UserListScreen + UserListScreenModel
    │   ├── postlist/      PostListScreen + PostListScreenModel
    │   ├── postdetail/    PostDetailScreen + PostDetailScreenModel
    │   └── favorites/     FavoritesScreen + FavoritesScreenModel
    └── theme/             Color, Type (AppTextStyle, AppColor), Theme
```

## Screenlar

### 1. Splash Screen
- Ilova ochilganda ko'rsatiladi
- DB da user bor bo'lsa -> User List
- Yo'q bo'lsa API dan yuklaydi
- Xatolik bo'lsa -> Retry tugmasi

### 2. User List Screen
- API dan userlarni yuklaydi, DB ga saqlaydi
- LazyColumn da user name + email ko'rsatadi
- Pull-to-refresh qo'llab-quvvatlaydi
- User bosilsa -> Post List Screen
- Favorites tugmasi (yulduzcha)

### 3. Post List Screen
- Tanlangan user postlarini yuklaydi
- DB cache + pull-to-refresh
- Favorite yulduzcha bosilsa -> local DB ga saqlaydi (real-time yangilanadi)
- Post bosilsa -> Post Detail Screen

### 4. Post Detail Screen
- Post body va commentlar ro'yxati
- Loading / Error state bilan

### 5. Favorites Screen
- Faqat local DB dan
- Offline ham ishlaydi
- Yulduzcha bosilsa -> favoritdan o'chiradi

## Database

SQLDelight bilan 3 ta jadval:

| Jadval | Vazifasi |
|---|---|
| `UserEntity` | Foydalanuvchilar (id, name, username, email, phone, website) |
| `PostEntity` | Postlar (id, userId, title, body) |
| `FavoritePostEntity` | Sevimli postlar (postId, userId, title, body) |

## State Management

Har bir screen 3 ta holatni boshqaradi:

```kotlin
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}
```

Error handling `Resource<T>` sealed class orqali:

```kotlin
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Failure(val error: AppError) : Resource<Nothing>()
}
```

## Ishga tushirish

### Talablar
- JDK 17
- Android Studio / Fleet
- Xcode (iOS uchun)

### Android
```bash
./gradlew :composeApp:assembleDebug
```

### iOS
Xcode orqali `iosApp/iosApp.xcodeproj` ochib run qiling.

### Eslatma
`gradle.properties` da JDK 17 path ko'rsatilgan. Agar boshqa path bo'lsa o'zgartiring:
```properties
org.gradle.java.home=/path/to/jdk17
```

## Dark Mode

`UserFeedTheme` `isSystemInDarkTheme()` orqali avtomatik dark/light mode ni qo'llab-quvvatlaydi.
# user-feed
