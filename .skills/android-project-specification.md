---
skill_metadata:
  name: android-project-specification
  version: 1.0.0
  description: |
    Android 项目开发规范 - 基于 Jetpack Compose + MVI + Clean Architecture + Hilt
    包含代码规范、架构设计、命名约定、最佳实践等完整开发指南。
  author: Leo (leovp)
  created: 2026-04-01
  tags:
    - android
    - specification
    - jetpack-compose
    - mvi
    - clean-architecture
    - hilt
    - best-practices
  compatibility:
    - Minimax
    - Claude Code
    - 通义灵码
    - Cursor
    - GitHub Copilot
  based_on: AndroidShowcase Project
  min_android_studio: "Panda 2"
  min_kotlin: "2.3.10"
  min_agp: "9.1.0"
  min_gradle: "9.4.0"
---

# Android 项目开发规范

基于成熟的 AndroidShowcase 项目模板，适用于所有使用 Jetpack Compose + MVI + Clean Architecture + Hilt 的 Android 项目。

## 📋 目录

1. [技术栈要求](#技术栈要求)
2. [项目结构规范](#项目结构规范)
3. [代码规范](#代码规范)
4. [架构规范](#架构规范)
5. [命名约定](#命名约定)
6. [注释规范](#注释规范)
7. [测试规范](#测试规范)
8. [版本控制](#版本控制)
9. [构建配置](#构建配置)
10. [最佳实践](#最佳实践)

---

## 技术栈要求

### 核心框架与库

**必须使用的技术栈：**

| 类别 | 技术 | 最低版本 | 说明 |
|------|------|----------|------|
| **UI 框架** | Jetpack Compose | 2026.02.01 (BOM) | 声明式 UI |
| **架构模式** | MVI | - | Model-View-Intent |
| **依赖注入** | Hilt | 2.59.2 | Dagger 简化版 |
| **异步处理** | Kotlin Coroutines | 1.10.2 | 协程 + Flow |
| **网络请求** | [Net](https://github.com/liangjingkanji/Net)（基于 OkHttp） + Lib-Network | 4.12.0 | HTTP 客户端 |
| **本地存储** | DataStore / SharedPreferences | - | 键值对存储 |
| **图片加载** | Coil | 3.3.0 | Kotlin 图片库 |
| **序列化** | Kotlinx Serialization | 1.10.0 | JSON 序列化 |

**构建工具版本：**

```toml
AGP = "9.1.0"
Kotlin = "2.3.10"
Gradle = "9.4.0"
JDK = "17"
minSdk = "25" (Android 7.1.1)
targetSdk = "36" (Android 16)
compileSdk = "36"
```

### 代码质量工具

**必须集成的工具：**

1. **ktlint** - 代码格式化
   - 版本：1.6.0
   - 自动格式化：`./gradlew ktlintFormat`
   - 检查：`./gradlew ktlintCheck`

2. **detekt** - 静态代码分析
   - 版本：1.23.8
   - 配置：使用 `config/detekt.yml`
   - 检查：`./gradlew detekt`

3. **Jacoco** - 代码覆盖率
   - 版本：0.8.9
   - 报告：`./gradlew jacocoTestReport`

4. **SonarQube** - 代码质量管理
   - 版本：7.2.3.7755
   - 集成 CI/CD 流程

---

## 项目结构规范

### 标准模块划分

```
project-root/
├── app/                              # 主应用模块（必需）
│   ├── src/main/kotlin/com/your/package/
│   │   ├── data/                     # 数据层
│   │   │   ├── datasource/           # 数据源
│   │   │   │   └── api/              # API 接口定义
│   │   │   │       ├── model/        # 数据传输对象
│   │   │   │       └── response/     # 响应封装
│   │   │   └── repository/           # 仓储实现
│   │   ├── domain/                   # 领域层
│   │   │   ├── model/                # 领域模型
│   │   │   ├── repository/           # 仓储接口
│   │   │   └── usecase/              # 用例
│   │   ├── presentation/             # 表现层 (MVI)
│   │   │   └── [Feature]/
│   │   │        ├── composable/      # Composable 组件
│   │   │        ├── [Feature]ViewModel.kt
│   │   │        ├── [Feature]Screen.kt
│   │   │        ├── [Feature]UiState.kt
│   │   │        ├── [Feature]Action.kt
│   │   │        └── [Feature]Event.kt
│   │   ├── ui/                       # UI 层
│   │   │   ├── theme/                # 主题样式
│   │   │   └── components/           # 通用组件
│   │   ├── testdata/                 # 测试数据
│   │   │   ├── local/                # 本地数据
│   │   │   ├── mock/                 # 模拟数据
│   │   │   └── [Feature]Module.kt    # DI 测试模块
│   │   └── utils/                    # 工具类
│   ├── src/main/res/                 # 资源文件
│   ├── src/test/kotlin/              # 单元测试
│   ├── src/androidTest/kotlin/       # UI 测试
│   └── build.gradle.kts
│
├── feature_base/                     # 基础模块（必需）
│   ├── src/main/kotlin/com/leovp/feature/base/
│   │   ├── composable/               # 全局 Composable
│   │   ├── event/                    # UI 事件管理
│   │   ├── http/                     # HTTP 组件
│   │   ├── log/                      # 日志工具
│   │   ├── ui/                       # UI 基类
│   │   ├── extensions/               # 扩展函数
│   │   └── utils/                    # 工具类
│   ├── src/main/kotlin/com/leovp/ui/theme/  # 全局主题
│   ├── src/main/res/
│   └── build.gradle.kts
│
├── feature_[name]/                   # 功能模块（按需）
│   ├── src/main/kotlin/com/your/package/[feature]/
│   │   ├── data/
│   │   ├── domain/
│   │   ├── presentation/
│   │   ├── testdata/
│   │   └── utils/
│   ├── src/main/res/
│   ├── src/main/AndroidManifest.xml
│   └── build.gradle.kts
│
├── config/
│   ├── sign/                         # 签名配置
│   │   ├── debug.keystore
│   │   └── keystore.properties
│   ├── detekt.yml                    # Detekt 配置
│   └── jacoco.gradle.kts             # Jacoco 配置
│
├── gradle/
│   ├── wrapper/
│   └── libs.versions.toml            # 版本目录（必需）
│
├── build.gradle.kts                  # 根构建配置
├── settings.gradle.kts               # 项目设置
├── gradle.properties                 # Gradle 属性
├── .editorconfig                     # 编辑器配置
└── .gitignore
```

### 模块依赖规则

**强制依赖方向：**

```
app → feature_* → feature_base
domain → (无依赖)
data → domain
presentation → domain + data
```

**禁止的依赖：**
- ❌ 上层模块不能依赖下层模块（如 presentation 不能直接依赖 data）
- ❌ 同级模块不能循环依赖
- ❌ feature 模块之间不能直接依赖（通过 interface 通信）

---

## 代码规范

### Kotlin 编码规范

#### 1. 文件格式

```kotlin
// ✅ 正确：文件头部空行
package com.example.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// 顶级常量
private const val TAG = "MyClass"

// 类定义
class MyClass {
    // 实现
}
```

#### 2. 缩进与空格

```kotlin
// ✅ 正确：4 个空格缩进
class MyClass {
    fun myFunction() {
        if (condition) {
            doSomething()
        }
    }
}

// ✅ 正确：操作符前后空格
val sum = a + b
val result = x * y

// ❌ 错误：缺少空格
val sum=a+b
```

#### 3. 行宽限制

- **最大行宽**：110 字符（detekt）
- **推荐行宽**：90 字符（.editorconfig）
- **超长换行**：使用链式调用或参数换行

```kotlin
// ✅ 正确：参数换行
fun myFunction(
    parameter1: String,
    parameter2: Int,
    parameter3: Boolean,
) {
    // 实现
}

// ✅ 正确：链式调用换行
val result = list
    .filter { it.isActive }
    .map { it.toModel() }
    .firstOrNull()
```

#### 4. 大括号规则

```kotlin
// ✅ 正确：控制流语句必须使用大括号
if (condition) {
    doSomething()
}

for (item in items) {
    process(item)
}

// ❌ 错误：省略大括号
if (condition) doSomething()
```

#### 5. 空行规则

```kotlin
// ✅ 正确：类成员之间空一行
class MyClass {
    private val field1 = "value1"
    
    private val field2 = "value2"
    
    fun method1() {
        // 实现
    }
    
    fun method2() {
        // 实现
    }
}

// ❌ 错误：连续多个空行
```

#### 6. 单文件行数规则

每个 Kotlin 文件，最大行数不要超过 800 行。若该文件是工具类或扩展类，则不受该限制。

### Compose 特定规范

#### 1. @Composable 函数命名

```kotlin
// ✅ 正确：@Composable 函数大写开头
@Composable
fun MyScreenContent() { }

@Composable
fun UserListItem() { }

// ✅ 正确：普通函数小写开头
fun calculateSum(a: Int, b: Int): Int { }
```

#### 2. Composable 函数结构

```kotlin
// ✅ 正确：Preview 注解的 Preview 前缀
@Composable
@Preview(showBackground = true)
fun PreviewMyScreen() {
    MyScreenContent()
}

// ✅ 正确：所有带默认值的参数必须放到参数列表的最后，并且 modifier 必须是第一个带默认值的参数。
@Composable
fun MyComponent(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier, // modifier 必须是第一个带默认值的参数
    subtitle: String = "",  // 其它默认参数需要在 modifier 之后
) { }
```

#### 3. State Hoisting

```kotlin
// ✅ 正确：状态提升模式
@Composable
fun ParentScreen() {
    var count by remember { mutableStateOf(0) }
    
    ChildCounter(
        count = count,
        onCountChange = { count = it }
    )
}

@Composable
fun ChildCounter(
    count: Int,
    onCountChange: (Int) -> Unit,
) {
    Button(onClick = { onCountChange(count + 1) }) {
        Text("Count: $count")
    }
}
```

#### 4. Modifier 使用规范

```kotlin
// ✅ 正确：Modifier 作为第一个参数
@Composable
fun MyComponent(
    modifier: Modifier = Modifier,
    // 其他参数
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) { }
}

// ❌ 错误：在内部创建新 Modifier
@Composable
fun MyComponent() {
    Box(modifier = Modifier.fillMaxSize()) { }
}
```

---

## 架构规范

### MVI 架构模式

#### 1. 基本结构

```kotlin
// UiState - 界面状态
sealed interface UiState : com.leovp.mvvm.BaseState {
    data class Content(
        val unreadList: List<UnreadModel> = emptyList(),
        val isLoading: Boolean = false,
    ) : UiState
}

// Action - 用户意图
sealed interface Action : com.leovp.mvvm.BaseAction.Simple<UiState> {
    data object ShowLoading : Action {
        override fun reduce(state: UiState): UiState {
            val uiState = state as Content
            return uiState.copy(isLoading = true)
        }
    }

    data class LoadSuccess(val unreadList: List<UnreadModel>) : Action {
        override fun reduce(state: UiState): UiState =
            Content(
                unreadList = unreadList,
                isLoading = false,
            )
    }
}

// UiEvent - 界面事件（用户触控操作等）
sealed interface MainUiEvent {
    sealed interface TopAppBarEvent : MainUiEvent {
        data object MenuClick : TopAppBarEvent

        data object RecordingClick : TopAppBarEvent
    }

    sealed interface SearchEvent : MainUiEvent {
        data object SearchClick : SearchEvent

        data object ScanClick : SearchEvent
    }

    data object Refresh : MainUiEvent
}

// Event - 副作用事件
所有导航及副作用，都需要通过 ViewModel 处理，或者以事件的形式分发出去。可以通过 ViewModel 中的 UiEventManager 实现事件分发。不能直接在 UI 界面处理。
```

#### 2. ViewModel 实现

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    uiEventManager: UiEventManager,
    private val useCase: MainUseCase,
) : com.leovp.mvvm.BaseViewModel<UiState, Action>(
    initialState = Content(),
    uiEventManager = uiEventManager,
) {
    
    override fun getTagName() = "MainVM"
    
    init {
        onEvent(MainUiEvent.Refresh)
    }
    
    fun onEvent(event: MainUiEvent) {
        viewModelScope.launch {
            when (event) {
                is SearchEvent -> handleSearchEvent(event)
                is TopAppBarEvent -> handleAppBarEvent(event)
                MainUiEvent.Refresh -> loadData(forceRefresh = true)
            }
        }
    }
    
    @Suppress("SameParameterValue")
    private fun loadData(forceRefresh: Boolean = false) {
        val uiState = uiStateFlow.value as Content
        i(tag) {
            "loadData(forceRefresh=$forceRefresh) uiState.isLoading=${uiState.isLoading}"
        }
        if (uiState.isLoading && !forceRefresh) {
            w(tag) { "The data is loading now. Ignore loading." }
            return
        }
        sendAction(Action.ShowLoading)
        viewModelScope.launch {
            val unreadListBizResult = useCase.getUnreadList("1")
            dispatchBizResult(
                uiEventManager = uiEventManager,
                bizResult = unreadListBizResult,
                onSuccess = { unreadList, _ ->
                    i(tag) { "unreadList=${unreadList.toJsonString()}" }
                    sendAction(Action.LoadSuccess(unreadList))
                },
            )
        }
    }

    private fun handleAppBarEvent(event: TopAppBarEvent) {
        when (event) {
            TopAppBarEvent.RecordingClick -> {
                showToast("Recording is not yet implemented.")
            }

            TopAppBarEvent.MenuClick -> {
                error("MenuClick should be implemented in your Screen.")
            }
        }
    }

    private fun handleSearchEvent(event: SearchEvent) {
        when (event) {
            SearchEvent.SearchClick -> {
                navigate(Screen.Search)
            }

            SearchEvent.ScanClick -> {
                showToast("Scan is not yet implemented.")
            }
        }
    }
}
```

#### 3. Screen 实现

```kotlin
@Composable
fun MainScreen(
    widthSize: WindowWidthSizeClass,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>(),
) {
    d(TAG) { "=> Enter MainScreen <=" }
    val coroutineScope = rememberCoroutineScope()
    val isExpandedScreen by remember {
        mutableStateOf(widthSize == WindowWidthSizeClass.Expanded)
    }
    val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)
    val navController = LocalNavigationActions.current
    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                currentRoute = Screen.None,
                onCloseDrawer = {
                    coroutineScope.launch { sizeAwareDrawerState.close() }
                },
                modifier = Modifier.requiredWidth(300.dp),
            )
        },
        drawerState = sizeAwareDrawerState,
        // Only enable opening the drawer via gestures if the screen is not expanded
        gesturesEnabled = !isExpandedScreen,
    ) {
        val snackbarHostState = remember { SnackbarHostState() }
        CustomEventHandler(
            events = viewModel.requireUiEvents,
            navController = navController,
            snackbarHostState = snackbarHostState,
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val mainUiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
            var unreadList: List<UnreadModel>
            mainUiState.let {
                when (it) {
                    is UiState.Content -> unreadList = it.unreadList
                }
            }
            MainContentScaffold(
                snackbarHostState = snackbarHostState,
                unreadList = unreadList,
                onEvent = { event ->
                    when (event) {
                        TopAppBarEvent.MenuClick ->
                            coroutineScope.launch {
                                sizeAwareDrawerState.open()
                            }

                        else -> viewModel.onEvent(event)
                    }
                },
            )
        }
    }
}

@Composable
private fun MainContentScaffold(
    snackbarHostState: SnackbarHostState,
    unreadList: List<UnreadModel>,
    onEvent: (MainUiEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerScreenValues = MainTabs.entries.toTypedArray()
    val pagerState =
        rememberPagerState(
            initialPage = MainTabs.DISCOVERY.ordinal,
            initialPageOffsetFraction = 0f,
            pageCount = { pagerScreenValues.size },
        )
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            HomeTopAppBar(
                unread =
                    unreadList
                        .firstOrNull { it.key == Screen.Message }
                        ?.value,
                pagerState = pagerState,
                onEvent = onEvent,
            ) {
                HomeTopAppBarContent(
                    // listState = listState,
                    pagerState = pagerState,
                    onEvent = onEvent,
                )
            }
        },
        bottomBar = { CustomBottomBar(pagerState, coroutineScope, unreadList) },
    ) { contentPadding ->
        MainScreenContent(
            pagerState = pagerState,
            pagerScreenValues = pagerScreenValues,
            onMainRefresh = { onEvent(MainUiEvent.Refresh) },
            modifier = Modifier.padding(contentPadding),
        )
    } // end of Scaffold
    // // The gradient box will significantly impact display performance.
    // LinearGradientBox(listState)
} // end of Box

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeTopAppBarContent(
    // listState: LazyListState,
    pagerState: PagerState,
    onEvent: (SearchEvent) -> Unit,
) {
    d(TAG) { "=> Enter HomeTopAppBarContent <=" }

    // val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    // val firstVisibleItemScrollOffset by remember {
    //     derivedStateOf { listState.firstVisibleItemScrollOffset }
    // }
    // val scrolled = firstVisibleItemIndex != 0 || firstVisibleItemScrollOffset != 0

    when (pagerState.currentPage) {
        MainTabs.DISCOVERY.ordinal -> {
            SearchBar(
                searchText = "Wellerman Nathan Evans",
                border = BorderStroke(width = 0.5.dp, brush = defaultLinearGradient),
                backgroundBrush = defaultLinearGradient,
                modifier =
                    Modifier
                        .height(48.dp)
                        .padding(vertical = 6.dp),
                searchIndicatorIcon = painterResource(id = R.drawable.app_search),
                actionIcon = painterResource(id = R.drawable.app_qr_code),
                onClick = { onEvent(SearchEvent.SearchClick) },
                onActionClick = { onEvent(SearchEvent.ScanClick) },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomBar(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    unreadList: List<UnreadModel> = emptyList(),
) {
    d(TAG) { "=> Enter CustomBottomBar <=" }
    NavigationBar {
        MainTabs.entries.forEachIndexed { index, bottomItemData ->
            val badgeNum =
                unreadList.firstOrNull { it.key == bottomItemData.screen }?.value
                    ?: 0
            NavigationBarItem(
                icon = {
                    if (badgeNum > 0) {
                        val badgeNumber = badgeNum.toCounterBadgeText(999)
                        val unreadContentDescription =
                            stringResource(
                                R.string.app_tab_unread_count,
                                badgeNumber,
                            )
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text(
                                        text = badgeNumber,
                                        modifier =
                                            Modifier.semantics {
                                                contentDescription =
                                                    unreadContentDescription
                                            },
                                    )
                                }
                            },
                        ) {
                            TabIcon(bottomItemData.screen)
                        }
                    } else {
                        TabIcon(bottomItemData.screen)
                    }
                },
                label = {
                    Text(
                        stringResource(bottomItemData.screen.nameResId),
                    )
                },
                // Here's the trick. The selected tab is based on HorizontalPager state.
                selected = index == pagerState.currentPage,
                onClick = {
                    LogContext.log.i(
                        TAG,
                        "Selected: ${bottomItemData.screen}",
                    )
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = bottomItemData.ordinal,
                            animationSpec = tween(TAB_SWITCH_ANIM_DURATION),
                        )
                    }
                },
            ) // end NavigationBarItem
        } // end AppBottomNavigationItems
    } // end NavigationBar
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    pagerState: PagerState,
    pagerScreenValues: Array<MainTabs>,
    onMainRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    d(TAG) { "=> Enter MainScreenContent <=" }
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        key = { index -> pagerScreenValues[index].ordinal },
    ) { page ->
        when (pagerScreenValues[page]) {
            MainTabs.DISCOVERY ->
                DiscoveryScreen(onRefresh = onMainRefresh)

            MainTabs.MY -> MyScreen()
            MainTabs.COMMUNITY -> CommunityScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    unread: Int? = 0,
    pagerState: PagerState,
    onEvent: (TopAppBarEvent) -> Unit,
    content: @Composable () -> Unit,
) {
    d(TAG) { "=> Enter HomeTopAppBar <=" }
    val topBarHeight = 54.dp

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
        )
        Row(
            modifier =
                modifier
                    .fillMaxWidth()
                    .heightIn(topBarHeight),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val badgeNum = unread ?: 0
            if (badgeNum > 0) {
                Box {
                    HomeTopMenu(onClick = { onEvent(TopAppBarEvent.MenuClick) })
                    Badge(
                        modifier =
                            Modifier
                                .align(Alignment.BottomEnd)
                                .padding(6.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = CircleShape,
                                ),
                    ) {
                        Text(text = badgeNum.toCounterBadgeText())
                    }
                }
            } else {
                HomeTopMenu(onClick = { onEvent(TopAppBarEvent.MenuClick) })
            }
            Row(modifier = modifier.weight(1f)) {
                content()
            }
            if (pagerState.currentPage == MainTabs.DISCOVERY.ordinal) {
                IconButton(onClick = { onEvent(TopAppBarEvent.RecordingClick) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.app_mic),
                        contentDescription = null,
                    )
                }
            }
        } // end row
    } // end Column
}

@Preview
@Composable
fun PreviewMainScreen() {
    PreviewWrapperNoTheme {
        val viewModel: MainViewModel =
            viewModel(
                factory =
                    viewModelProviderFactoryOf {
                        MainViewModel(
                            PreviewMainModule.previewMainUseCase,
                            UiEventManager(),
                        )
                    },
            )

        viewModel<DiscoveryViewModel>(
            factory =
                viewModelProviderFactoryOf {
                    DiscoveryViewModel(
                        useCase = PreviewDiscoveryModule.previewDiscoveryListUseCase,
                        uiEventManager = UiEventManager(),
                    )
                },
        )

        AppTheme(dynamicColor = false) {
            MainScreen(
                widthSize = WindowWidthSizeClass.Compact,
                viewModel = viewModel,
            )
        }
    }
}
```

### Clean Architecture 分层

#### 1. Domain 层（核心业务逻辑）

```kotlin
// 领域模型
@Keep
@Immutable
data class UnreadModel(
    val key: Screen,
    val value: Int,
)

// 仓储接口
interface MainRepository {
    suspend fun getUnreadList(uid: String): Result<ApiResponseModel<List<UnreadModel>>>
}

// 用例
@Singleton
class MainUseCase
    @Inject
    constructor(private val repository: MainRepository) {
        suspend fun getUnreadList(uid: String): ResultBiz<List<UnreadModel>> =
            processApiResponseResult(repository.getUnreadList(uid))
    }
```

#### 2. Data 层（数据实现）

```kotlin
// 数据模型。根据需要创建 DTO
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
)

// 映射扩展。若创建了 DTO，通常需要设置映射扩展，将请求返回的 DTO 转换成业务需要的 Model。
fun UserDto.toDomain() = UserModel(
    id = id,
    name = name,
    email = email
)

// 仓储实现
@Singleton
class MainRepositoryImpl
    @Inject
    constructor(private val dataSource: MainDataSource) : MainRepository {
        override suspend fun getUnreadList(
            uid: String,
        ): Result<ApiResponseModel<List<UnreadModel>>> =
            result {
                delay(1000)
                ApiResponseModel(
                    code = 200,
                    message = "",
                    result = dataSource.getUnreadList("1"),
                )
            }
    }

// Hilt Module 定义
@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {
    @Singleton
    // @MainRepositoryImplement
    @Binds
    abstract fun bindRepository(repository: MainRepositoryImpl): MainRepository
}

```

#### 3. Presentation 层（UI 展示）

参考上方 MVI 实现示例。

---

## 命名约定

### 通用命名规则

#### 1. 变量与属性

```kotlin
// ✅ 正确：驼峰命名
val userName = "Leo"
private val itemCount = 10
var isLoading = false

// ❌ 错误：下划线命名（常量除外）
val user_name = "Leo"
```

#### 2. 常量命名

```kotlin
// ✅ 正确：全大写下划线分隔
private const val MAX_RETRY_COUNT = 3
const val API_BASE_URL = "https://api.example.com"

// 伴生对象中的常量
companion object {
    private const val TAG = "MainActivity"
    const val REQUEST_CODE = 1001
}
```

#### 3. 类与接口

```kotlin
// ✅ 正确：大驼峰命名
class UserRepository { }
interface NavigationListener { }
data class UserModel { }

// ✅ 正确：接口命名以形容词结尾
interface Loadable { }
interface Clickable { }
```

#### 4. 函数与方法

```kotlin
// ✅ 正确：小驼峰命名，动词开头
fun calculateTotal() { }
fun getUserById(id: String) { }
suspend fun loadData() { }

// ✅ 正确：布尔返回值使用 is/has/can 前缀
fun isValid() = true
fun hasPermission() = false
fun canExecute() = true
```

#### 5. Resource 命名

**drawable 资源：**

```
类型_模块_描述_state.xml

✅ 正确：
ic_home_normal.xml
ic_home_pressed.xml
bg_login_button.xml
img_user_avatar.png

❌ 错误：
home_icon.xml  # 缺少类型前缀
button_bg.xml   # 顺序错误
```

**layout 资源：**

```
类型_模块_描述.xml

✅ 正确：
activity_main.xml
fragment_home.xml
item_user_list.xml
dialog_confirm.xml
view_toolbar.xml
```

**values 资源：**

```xml
<!-- ✅ 正确：有意义的命名 -->
<string name="login_button_text">登录</string>
<string name="error_network_failed">网络失败</string>
<dimen name="spacing_large">16dp</dimen>
<color name="primary_blue">#007AFF</color>

<!-- ❌ 错误：无意义命名 -->
<string name="text1">登录</string>
<string name="error1">网络失败</string>
```

### 包名规范

```kotlin
// ✅ 正确：全小写，单词间用点分隔
package com.example.app.data.repository
package com.example.app.presentation.home

// ❌ 错误：大写字母
package com.example.App.Data
```

---

## 注释规范

### 文件头部注释

```kotlin
/**
 * Author: Your Name
 * Date: 2024/01/01
 * Description: 类的功能描述
 */
class MyClass {
    // 实现
}
```

### 函数注释

```kotlin
/**
 * 根据用户 ID 获取用户信息
 *
 * @param userId 用户的唯一标识符
 * @return 返回用户信息的结果封装
 * @throws IOException 当网络请求失败时抛出
 */
suspend fun getUserById(userId: String): Result<UserModel> {
    // 实现
}
```

### TODO/FIXME 注释

```kotlin
// TODO: 后续需要优化此处的性能
fun complexCalculation() { }

// FIXME: 此处有内存泄漏风险
fun riskyOperation() { }

// NOTE: 注意：此方法必须在主线程调用
@MainThread
fun updateUI() { }
```

**禁止的注释：**

```kotlin
// ❌ 禁止：无意义的注释
// 增加计数
count++

// ❌ 禁止：过时的注释
// 2023 年添加的功能
// 已废弃，不要使用
```

---

## 测试规范

### 单元测试

```kotlin
// ✅ 正确：使用 JUnit5
@ExtendWith(AndroidJUnit5::class)
class UserRepositoryTest {
    
    @Test
    fun `get user by id returns success when user exists`() = runTest {
        // Given
        val expectedUser = UserModel(id = "1", name = "Leo")
        every { mockRepository.getUserById("1") } returns Result.success(expectedUser)
        
        // When
        val result = repository.getUserById("1")
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedUser, result.getOrNull())
    }
    
    @Test
    fun `get user by id returns failure when user not found`() = runTest {
        // Given
        every { mockRepository.getUserById("999") } returns Result.failure(Exception("Not found"))
        
        // When & Then
        val result = repository.getUserById("999")
        assertTrue(result.isFailure)
    }
}
```

### UI 测试

```kotlin
@RunWith(AndroidJUnit4::class)
class MyScreenTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun loading_showsProgressBar() {
        composeTestRule.setContent {
            MyScreenContent(state = MyUiState.Loading)
        }
        
        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertIsDisplayed()
    }
    
    @Test
    fun content_showsDataList() {
        val testData = listOf(
            DataModel(id = "1", title = "Item 1"),
            DataModel(id = "2", title = "Item 2")
        )
        
        composeTestRule.setContent {
            MyScreenContent(
                state = MyUiState.Content(dataList = testData)
            )
        }
        
        composeTestRule
            .onNodeWithText("Item 1")
            .assertIsDisplayed()
    }
}
```

### 测试命名规范

```kotlin
// ✅ 正确：使用反引号描述测试场景
@Test
fun `login with valid credentials should navigate to home`() { }

@Test
fun `login with invalid credentials should show error`() { }

// ❌ 错误：无意义命名
@Test
fun testLogin() { }

@Test
fun test1() { }
```

---

## 版本控制

### Git 提交规范

```bash
# 格式：<type>(<scope>): <subject>

# type 类型
feat:     新功能
fix:      Bug 修复
docs:     文档更新
style:    代码格式调整
refactor: 重构
test:     测试相关
chore:    构建/工具配置

# 示例
feat(login): 添加用户登录功能
fix(network): 修复网络连接超时问题
docs(readme): 更新 README 文档
style(ui): 调整按钮样式
refactor(auth): 重构认证模块
test(user): 添加用户测试用例
chore(gradle): 升级 Gradle 版本
```

### Branch 命名规范

```bash
# 功能分支
feature/login-feature
feature/user-profile

# 修复分支
fix/network-issue
fix/crash-on-startup

# 发布分支
release/v1.0.0
release/v2.1.0-beta

# 热修复分支
hotfix/critical-bug-fix
```

---

## 构建配置

### 多 Flavor 配置

```kotlin
android {
    flavorDimensions += listOf("version")
    
    productFlavors {
        create("dev") {
            dimension = "version"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "API_URL", "\"https://dev-api.example.com\"")
        }
        
        create("prod") {
            dimension = "version"
            buildConfigField("String", "API_URL", "\"https://api.example.com\"")
        }
    }
    
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
        
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

### APK 命名规范

```kotlin
androidComponents {
    onVariants { variant ->
        val isConsoleLogOpen = variant.name != "prodRelease"
        variant.buildConfigFields?.put(
            "CONSOLE_LOG_OPEN",
            BuildConfigField("boolean", isConsoleLogOpen.toString(), null),
        )

        // Disable lintVital for variants with K2 compatibility issues
        if (variant.name == "devRelease") {
            tasks.configureEach {
                if (name.contains("lintVital") && name.contains("DevRelease")) {
                    enabled = false
                }
            }
        }

        // Rename APK output files
        val appName = "LeoAndroidShowcase"
        // Example: dev, prod
        val flavorName = variant.flavorName.orEmpty()
        // Example: debug, release
        val buildTypeName = variant.buildType.orEmpty()
        // Example: DevDebug, ProdRelease
        val capitalizedName = variant.name.replaceFirstChar { it.uppercase() }
        val mainOutput = variant.outputs.firstOrNull()
        val versionName = mainOutput?.versionName?.getOrElse("") ?: ""
        val versionCode = mainOutput?.versionCode?.getOrElse(0) ?: 0
        // val versionName = android.defaultConfig.versionName ?: "NA"
        // val versionCode = android.defaultConfig.versionCode ?: 0
        // Example: 20260325_104413(CST)
        val timestamp =
            ZonedDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss(z)"),
            )
        // Example: 18cc7c4
        val gitTag = gitVersionTag()
        // Example: 148
        val gitCount = gitCommitCount()
        println(
            "buildTypeName=$buildTypeName flavorName=$flavorName capitalizedName=$capitalizedName gitTag=$gitTag gitCount=$gitCount",
        )
        // Example: LeoAndroidShowcase-dev-debug-v1.0.5-dev(105)-20260325_104413(CST)-18cc7c4-148.apk
        val apkName =
            "$appName${("-$flavorName").takeIf {
                it != "-"
            } ?: ""}-$buildTypeName" +
                "-v$versionName($versionCode)" +
                "-$timestamp" +
                "-$gitTag-$gitCount" +
                ".apk"

        tasks.register("rename${capitalizedName}Apk") {
            val apkDir =
                variant.artifacts.get(
                    com.android.build.api.artifact.SingleArtifact.APK,
                )
            inputs.dir(apkDir)
            doLast {
                val dir = apkDir.get().asFile
                dir.listFiles()?.filter { it.extension == "apk" }?.forEach { srcFile ->
                    val finalName =
                        if ("unsigned" in srcFile.name) {
                            apkName.replace(".apk", "-unsigned.apk")
                        } else {
                            apkName
                        }
                    srcFile.copyTo(File(dir, finalName), overwrite = true)
                }
            }
        }
        afterEvaluate {
            tasks.named("assemble$capitalizedName") {
                finalizedBy("rename${capitalizedName}Apk")
            }
        }
    }
}

// 获取当前分支的提交总次数
fun gitCommitCount(): String {
//    val cmd = 'git rev-list HEAD --first-parent --count'
    val cmd = "git rev-list HEAD --count"

    return runCatching {
        // You must trim() the result. Because the result of command has a suffix '\n'.
        providers
            .exec {
                commandLine = cmd.trim().split(' ')
            }.standardOutput.asText
            .get()
            .trim()
    }.getOrDefault("NA")
}

// 使用commit的哈希值作为版本号也是可以的，获取最新的一次提交的哈希值的前七个字符
// $ git rev-list HEAD --abbrev-commit --max-count=1
// a935b078

/*
 * 获取最新的一个tag信息
 * $ git describe --tags
 * 4.0.4-9-ga935b078
 * 说明：
 * 4.0.4        : tag名
 * 9            : 打tag之后又有四次提交
 * ga935b078    ：开头 g 为 git 的缩写，在多种管理工具并存的环境中很有用处
 * a935b078     ：当前分支最新的 commitID 前几位
 */
fun gitVersionTag(): String {
    // https://stackoverflow.com/a/4916591/1685062
//    val cmd = "git describe --tags"
    val cmd = "git describe --always"

    val versionTag =
        runCatching {
            // You must trim() the result. Because the result of command has a suffix '\n'.
            providers
                .exec {
                    commandLine = cmd.trim().split(' ')
                }.standardOutput.asText
                .get()
                .trim()
        }.getOrDefault("NA")

    val regex = "-(\\d+)-g".toRegex()
    val matcher: MatchResult? = regex.matchEntire(versionTag)

    val matcherGroup0: MatchGroup? = matcher?.groups?.get(0)
    return if (matcher?.value?.isNotBlank() == true &&
        matcherGroup0?.value?.isNotBlank() == true
    ) {
        versionTag.substring(0, matcherGroup0.range.first) + "." +
            matcherGroup0.value
    } else {
        versionTag
    }
}
```

---

## 最佳实践

### 1. 性能优化

**列表优化：**

```kotlin
// ✅ 正确：使用 key 参数
LazyColumn {
    items(
        items = dataList,
        key = { it.id }  // 使用稳定的 key
    ) { item ->
        MyItem(item)
    }
}

// ✅ 正确：避免在 Composable 中创建对象
@Composable
fun MyItem(item: DataModel) {
    // ❌ 错误：每次重组都创建新对象
    // val formatter = DecimalFormat("#.##")
    
    // ✅ 正确：使用 remember
    val formatter = remember { DecimalFormat("#.##") }
    Text(formatter.format(item.value))
}
```

**图片加载优化：**

```kotlin
// ✅ 正确：使用 Coil 加载图片
AsyncImage(
    model = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .build(),
    contentDescription = "Image",
    modifier = Modifier.size(100.dp)
)
```

### 2. 内存管理

**协程作用域：**

```kotlin
// ✅ 正确：使用合适的作用域
class MyViewModel : ViewModel() {
    // 使用 viewModelScope
    fun loadData() {
        viewModelScope.launch {
            // 自动在 ViewModel clear 时取消
        }
    }
}

// ✅ 正确：在 Composable 中使用 rememberCoroutineScope
@Composable
fun MyScreen() {
    val scope = rememberCoroutineScope()
    
    Button(onClick = {
        scope.launch {
            // 在重组时自动取消
        }
    }) { }
}
```

**资源释放：**

```kotlin
// ✅ 正确：使用 DisposableEffect 清理资源
@Composable
fun LocationObserver(
    onLocationChanged: (Location) -> Unit
) {
    val context = LocalContext.current
    
    DisposableEffect(context) {
        val locationClient = LocationClient(context)
        locationClient.startListening(onLocationChanged)
        
        onDispose {
            locationClient.stopListening()
        }
    }
}
```

### 3. 错误处理

**统一错误处理：**

```kotlin
// ✅ 正确：使用 sealed class 表示错误
sealed class AppError {
    data class NetworkError(val message: String) : AppError()
    data class DatabaseError(val cause: Throwable) : AppError()
    object Unauthorized : AppError()
}

// 在 ViewModel 中处理
suspend fun loadData() {
    try {
        val result = repository.getData()
        result.fold(
            onSuccess = { data ->
                setState { Content(data) }
            },
            onFailure = { error ->
                val appError = when (error) {
                    is IOException -> AppError.NetworkError("网络错误")
                    is UnauthorizedException -> AppError.Unauthorized
                    else -> AppError.DatabaseError(error)
                }
                sendEvent(ShowError(appError))
            }
        )
    } catch (e: Exception) {
        // 兜底错误处理
        sendEvent(ShowError(AppError.NetworkError("未知错误")))
    }
}
```

### 4. 代码复用

**提取公共 Composable：**

```kotlin
// ✅ 正确：提取可复用的组件
@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null
        )
        Text(text = message)
        
        if (actionLabel != null && onAction != null) {
            Button(onClick = onAction) {
                Text(actionLabel)
            }
        }
    }
}

// 在 Screen 中使用
@Composable
fun MyScreenContent(state: MyUiState) {
    when {
        state.dataList.isEmpty() -> EmptyState(
            message = "暂无数据",
            actionLabel = "刷新",
            onAction = { /* 刷新逻辑 */ }
        )
        else -> { /* 正常内容 */ }
    }
}
```

### 5. 依赖注入最佳实践

**Hilt 模块组织：**

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
    
    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    
    @Provides
    @Singleton
    fun provideUserApi(
        @BaseUrl("https://api.example.com")
        client: OkHttpClient
    ): UserApi {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(UserApi::class.java)
    }
}
```

---

## 附录

### 常用 Gradle 命令

```bash
# 构建相关
./gradlew clean                          # 清理构建
./gradlew assembleDebug                  # 构建 Debug 包
./gradlew assembleRelease                # 构建 Release 包
./gradlew assembleDevDebug              # 构建 Dev 环境 Debug 包

# 测试相关
./gradlew test                           # 运行所有测试
./gradlew testDebugUnitTest             # 运行 Debug 单元测试
./gradlew connectedAndroidTest          # 运行 UI 测试
./gradlew jacocoTestReport              # 生成代码覆盖率报告

# 代码质量
./gradlew ktlintCheck                   # 检查代码格式
./gradlew ktlintFormat                  # 格式化代码
./gradlew detekt                        # 静态代码分析
./gradlew staticCheck                   # 运行所有检查

# 依赖管理
./gradlew dependencies                  # 查看依赖树
./gradlew dependencyUpdates             # 检查依赖更新
```

### 推荐工具和插件

**Android Studio 插件：**
- Android WiFi ADB
- GsonFormatPlus
- Key Promoter X
- Rainbow Brackets
- Translation

**调试工具：**
- Stetho
- LeakCanary
- BlockCanary

### 学习资源

**官方文档：**
- [Android Developers](https://developer.android.com/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)

**开源项目：**
- [AndroidShowcase](https://github.com/yhz61010/AndroidShowcase)
- [Now in Android](https://github.com/android/nowinandroid)
- [Compose Samples](https://github.com/android/compose-samples)

---

## 版本历史

| 版本 | 日期 | 更新内容 | 作者 |
|------|------|----------|------|
| 1.0.0 | 2026-04-01 | 初始版本 | Leo |

---

## 联系方式

如有问题或建议，请联系：
- Email: leovp@example.com
- GitHub: [@yhz61010](https://github.com/yhz61010)
