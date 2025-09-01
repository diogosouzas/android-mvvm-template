# Exemplo de Migração para MVI Puro

## 1. Definir Intents/Actions

```kotlin
sealed class HomeIntent {
    object LoadUsers : HomeIntent()
    object RefreshUsers : HomeIntent()
    object ClearError : HomeIntent()
}
```

## 2. ViewModel com Reducer

```kotlin
class HomeViewModel(
    private val userUseCases: UserUseCases
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadUsers -> loadUsers()
            is HomeIntent.RefreshUsers -> refreshUsers()
            is HomeIntent.ClearError -> clearError()
        }
    }
    
    private fun reduce(currentState: HomeUiState, result: HomeResult): HomeUiState {
        return when (result) {
            is HomeResult.Loading -> currentState.copy(isLoading = true)
            is HomeResult.UsersLoaded -> currentState.copy(
                users = result.users,
                isLoading = false,
                errorMessage = null
            )
            is HomeResult.Error -> currentState.copy(
                isLoading = false,
                errorMessage = result.message
            )
        }
    }
}
```

## 3. UI com Intents

```kotlin
@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeIntent.LoadUsers)
    }
    
    // UI components
    IconButton(onClick = { 
        viewModel.handleIntent(HomeIntent.RefreshUsers) 
    }) {
        Icon(Icons.Default.Refresh, "Refresh")
    }
}
```

## Vantagens do MVI Puro:
- ✅ Fluxo unidirecional rigoroso
- ✅ Estado completamente previsível
- ✅ Fácil debugging (todos os intents são trackáveis)
- ✅ Time-travel debugging possível
- ✅ Testabilidade máxima

## Desvantagens:
- ❌ Mais boilerplate code
- ❌ Curva de aprendizado maior
- ❌ Pode ser overkill para apps simples
