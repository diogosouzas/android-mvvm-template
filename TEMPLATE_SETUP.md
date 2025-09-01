# Configuração do Template

Este guia te ajudará a personalizar o template para seu projeto específico.

## 📝 Checklist de Configuração

### 1. Configuração Básica
- [ ] Renomear pacote de `com.arch.template` para seu pacote
- [ ] Alterar `applicationId` no `app/build.gradle.kts`
- [ ] Atualizar `namespace` no `app/build.gradle.kts`
- [ ] Modificar nome do app em `app/src/main/res/values/strings.xml`
- [ ] Personalizar ícone do app em `app/src/main/res/mipmap-*/`

### 2. API e Backend
- [ ] Substituir URL base no `NetworkModule.kt`
- [ ] Adaptar modelos de dados para sua API
- [ ] Configurar autenticação se necessário
- [ ] Adicionar interceptors específicos

### 3. Design e Tema
- [ ] Personalizar cores em `ui/theme/Color.kt`
- [ ] Ajustar tipografia em `ui/theme/Type.kt`
- [ ] Configurar ícones e recursos específicos
- [ ] Implementar dark theme personalizado

### 4. Funcionalidades Específicas
- [ ] Adicionar novos modelos de dados
- [ ] Criar use cases específicos
- [ ] Implementar telas necessárias
- [ ] Configurar navegação entre telas

### 5. Configurações de Build
- [ ] Configurar flavor dimensions se necessário
- [ ] Ajustar configurações de ProGuard
- [ ] Configurar signing configs para release
- [ ] Adicionar variáveis de ambiente

### 6. Integração Externa
- [ ] Configurar Firebase (se necessário)
- [ ] Adicionar Analytics
- [ ] Configurar Crash Reporting
- [ ] Integrar Push Notifications

## 🔧 Comandos Úteis

### Renomear Pacote (MacOS/Linux)
```bash
# Renomear de com.arch.template para com.seudominio.seuapp
find . -name "*.kt" -exec sed -i '' 's/com\.arch\.template/com.seudominio.seuapp/g' {} +
find . -name "*.xml" -exec sed -i '' 's/com\.arch\.template/com.seudominio.seuapp/g' {} +
find . -name "*.gradle.kts" -exec sed -i '' 's/com\.arch\.template/com.seudominio.seuapp/g' {} +
```

### Construir o Projeto
```bash
# Build debug
./gradlew assembleDebug

# Build release
./gradlew assembleRelease

# Executar testes
./gradlew test

# Verificar lint
./gradlew lint
```

## 📦 Estrutura de Pacotes Recomendada

Após renomear o pacote, sua estrutura ficará assim:

```
com.seudominio.seuapp/
├── core/              # Utilitários compartilhados
├── data/              # Camada de dados
│   ├── local/         # Banco de dados local
│   ├── remote/        # APIs e serviços remotos
│   ├── repository/    # Implementações de repositório
│   └── model/         # Modelos de dados
├── domain/            # Camada de domínio
│   └── usecase/       # Casos de uso
├── di/                # Injeção de dependência
└── ui/                # Camada de interface
    ├── feature1/      # Funcionalidade 1
    ├── feature2/      # Funcionalidade 2
    ├── navigation/    # Navegação
    ├── theme/         # Tema e estilo
    └── components/    # Componentes reutilizáveis
```

## 🎯 Próximos Passos Recomendados

1. **Configure a API**: Adapte os endpoints para sua API real
2. **Implemente Autenticação**: Adicione sistema de login se necessário
3. **Adicione Mais Telas**: Crie as funcionalidades específicas do seu app
4. **Configure CI/CD**: Personalize o workflow do GitHub Actions
5. **Adicione Testes**: Expanda a cobertura de testes
6. **Otimize Performance**: Implemente estratégias de cache avançadas

## ❓ Dúvidas Frequentes

**Q: Como adicionar uma nova funcionalidade?**
A: Siga o padrão: Model → Repository → UseCase → ViewModel → Screen

**Q: Como configurar diferentes ambientes (dev, prod)?**
A: Use build variants ou configure flavor dimensions no build.gradle.kts

**Q: Como adicionar autenticação?**
A: Crie um AuthRepository, implemente interceptors no AppModule e gerencie tokens no DataStore

**Q: Como modularizar o projeto?**
A: Quando o projeto crescer, separe funcionalidades em módulos distintos seguindo o guia de modularização do Android

## 📚 Recursos Adicionais

- [Documentação Oficial Android](https://developer.android.com/docs)
- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- [Material 3 Guidelines](https://m3.material.io/)
- [Koin Documentation](https://insert-koin.io/)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
