# Android Architecture Template

[![CI/CD Pipeline](https://github.com/diogosouzas/android-mvvm-template/actions/workflows/ci.yml/badge.svg)](https://github.com/diogosouzas/android-mvvm-template/actions/workflows/ci.yml)
[![Security Scan](https://github.com/diogosouzas/android-mvvm-template/actions/workflows/security.yml/badge.svg)](https://github.com/diogosouzas/android-mvvm-template/actions/workflows/security.yml)
[![GitHub release (latest by date)](https://img.shields.io/github/v/release/diogosouzas/android-mvvm-template)](https://github.com/diogosouzas/android-mvvm-template/releases)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/kotlin-2.0.21-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.12.2-blue.svg?logo=gradle)](https://gradle.org)

Um template de projeto Android moderno que segue as [recomendações oficiais de arquitetura do Google](https://developer.android.com/topic/architecture/recommendations) e implementa as melhores práticas para desenvolvimento Android.

## 🏗️ Arquitetura

Este template implementa a arquitetura em camadas recomendada pelo Google:

- **UI Layer (Camada de Interface)**: Telas compostas com Jetpack Compose e ViewModels
- **Domain Layer (Camada de Domínio)**: Use cases e lógica de negócio
- **Data Layer (Camada de Dados)**: Repositórios, fontes de dados locais e remotas

### Diagrama da Arquitetura

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ UI Layer    │ -> │ Domain      │ -> │ Data Layer  │
│             │    │ Layer       │    │             │
│ - Compose   │    │             │    │ - Room DB   │
│ - ViewModel │    │ - Use Cases │    │ - Retrofit  │
│ - Navigation│    │             │    │ - Repository│
└─────────────┘    └─────────────┘    └─────────────┘
```

## 🛠️ Tecnologias Utilizadas

### Core
- **Kotlin** - Linguagem principal
- **Jetpack Compose** - UI moderna e declarativa
- **Material 3** - Design system atualizado
- **Coroutines & Flow** - Programação assíncrona e reativa

### Arquitetura
- **Koin** - Injeção de dependência
- **ViewModel** - Gerenciamento de estado da UI
- **Navigation Component** - Navegação entre telas
- **Repository Pattern** - Abstração de fontes de dados

### Persistência
- **Room** - Banco de dados local
- **DataStore** - Armazenamento de preferências
- **Offline-first** - Funciona sem conexão

### Rede
- **Retrofit** - Cliente HTTP
- **OkHttp** - Interceptors e logging
- **Kotlinx Serialization** - Serialização JSON

### Testes
- **JUnit** - Framework de testes
- **MockK** - Mocking para Kotlin
- **Turbine** - Testes de Flow
- **Kotest** - Assertions avançadas

### Utilitários
- **Timber** - Logging estruturado
- **Coil** - Carregamento de imagens
- **Lottie** - Animações

## 📁 Estrutura do Projeto

```
app/src/main/java/com/arch/template/
├── core/
│   └── util/                 # Utilitários gerais (Result, extensões)
├── data/
│   ├── local/
│   │   ├── dao/             # Data Access Objects
│   │   └── database/        # Configuração do Room
│   ├── remote/
│   │   ├── api/             # Serviços de API
│   │   └── dto/             # Data Transfer Objects
│   ├── repository/          # Implementações de repositório
│   └── model/               # Modelos de dados
├── di/                      # Módulos de injeção de dependência
├── domain/
│   └── usecase/             # Casos de uso
└── ui/
    ├── home/                # Tela principal
    ├── navigation/          # Configuração de navegação
    ├── theme/               # Tema e cores
    └── model/               # Modelos de UI State
```

## 🚀 Como Usar Este Template

### 1. Clone o projeto
```bash
git clone <repository-url>
cd ArchitectureAndroidTemplate
```

### 2. Renomeie o pacote
- Altere `com.arch.template` para o pacote da sua aplicação
- Atualize o `namespace` e `applicationId` no `build.gradle.kts`
- Renomeie as pastas correspondentes

### 3. Configure a API
- Substitua a URL base no `NetworkModule.kt`
- Adapte os modelos de dados para sua API
- Implemente os endpoints necessários

### 4. Personalize o tema
- Modifique as cores em `ui/theme/Color.kt`
- Ajuste a tipografia em `ui/theme/Type.kt`
- Customize o tema em `ui/theme/Theme.kt`

### 5. Adicione suas funcionalidades
- Crie novos modelos de dados
- Implemente novos use cases
- Desenvolva novas telas com Compose

## 📋 Funcionalidades Incluídas

### ✅ Implementado
- [x] Arquitetura em camadas (UI, Domain, Data)
- [x] Injeção de dependência com Koin
- [x] Navigation Component
- [x] Offline-first com Room
- [x] Testes unitários
- [x] Pull-to-refresh
- [x] Tratamento de erro
- [x] Loading states
- [x] Material 3 theming
- [x] Edge-to-edge support

### 🔄 Configurações Recomendadas

#### Build Variants
O template inclui configurações para diferentes ambientes:
- **Debug**: Com logs e debugging habilitado
- **Release**: Otimizado para produção

#### ProGuard/R8
Configurações de ofuscação incluídas no `proguard-rules.pro`

#### Testes
Estrutura completa de testes com:
- Testes unitários para ViewModels
- Testes de repositório
- Fakes para testes
- Cobertura de casos de sucesso e erro

## 🧪 Executando Testes

```bash
# Executar todos os testes
./gradlew test

# Executar testes específicos
./gradlew testDebugUnitTest

# Gerar relatório de cobertura
./gradlew jacocoTestReport
```

## 📚 Boas Práticas Implementadas

### 1. Nomenclatura
- **Métodos**: Verbos (ex: `getUsersStream()`)
- **Propriedades**: Substantivos (ex: `isLoading`)
- **Flows**: Sufixo `Stream` (ex: `getUsersStream()`)
- **Implementações**: Prefixo `Default` (ex: `DefaultUserRepository`)

### 2. Tratamento de Erros
- Uso da classe `Result` para encapsular sucesso/erro
- Estados de loading explícitos
- Mensagens de erro amigáveis

### 3. Offline-First
- Dados sempre vêm do banco local
- Sincronização em background
- Funciona sem conexão de rede

### 4. Testes
- Use fakes ao invés de mocks
- Teste StateFlows corretamente
- Cobertura de casos de erro

### 5. Performance
- ViewModels com escopo apropriado
- Lazy loading onde aplicável
- Otimizações de renderização

## 🔧 Próximos Passos

Após configurar o template, considere adicionar:

1. **Analytics** - Firebase Analytics ou similar
2. **Crash Reporting** - Firebase Crashlytics
3. **CI/CD** - GitHub Actions ou similar
4. **Flavor Dimensions** - Para diferentes ambientes
5. **Modularização** - Conforme o projeto cresce
6. **Deep Links** - Para navegação externa
7. **Notifications** - Push notifications
8. **Caching** - Estratégias avançadas de cache

## 📖 Referências

- [Recomendações de Arquitetura Android](https://developer.android.com/topic/architecture/recommendations)
- [Guia de Arquitetura Android](https://developer.android.com/topic/architecture)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material 3](https://m3.material.io/)
- [Koin Documentation](https://insert-koin.io/)

## 📄 Licença

Este template está disponível sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.
