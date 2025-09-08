# 🛍️ Products API - Spring Boot Kotlin

Uma API RESTful completa para gerenciamento de produtos desenvolvida com **Spring Boot** e **Kotlin**, seguindo as melhores práticas de arquitetura em camadas e padrões REST.

## 🚀 Tecnologias Utilizadas

- **Kotlin** 1.9.20
- **Spring Boot** 3.2.0
- **Spring Data JPA** - Persistência de dados
- **Spring Web** - REST Controllers
- **Spring Validation** - Validação de dados
- **H2 Database** - Banco de dados em memória
- **Jackson** - Serialização JSON
- **Maven/Gradle** - Gerenciamento de dependências

## 🏗️ Arquitetura

A aplicação segue o padrão de **arquitetura em camadas**:

```
├── Controller Layer    # Endpoints REST
├── Service Layer      # Lógica de negócio
├── Repository Layer   # Acesso a dados
├── Entity Layer       # Modelos de dados
├── DTO Layer         # Objetos de transferência
└── Exception Layer   # Tratamento de erros
```

## 📦 Estrutura do Projeto

```
src/main/kotlin/com/example/api/
├── controller/
│   └── ProductController.kt
├── service/
│   └── ProductService.kt
├── repository/
│   └── ProductRepository.kt
├── entity/
│   ├── Product.kt
│   └── Category.kt (enum)
├── dto/
│   ├── ProductRequestDto.kt
│   ├── ProductResponseDto.kt
│   └── ProductUpdateDto.kt
├── mapper/
│   └── ProductMapper.kt
├── exception/
│   ├── ProductNotFoundException.kt
│   └── GlobalExceptionHandler.kt
└── ApiApplication.kt
```

## 🎯 Funcionalidades

### ✅ CRUD Completo
- ✅ Criar produto
- ✅ Buscar produto por ID
- ✅ Listar produtos com paginação
- ✅ Atualizar produto (completo e parcial)
- ✅ Deletar produto
- ✅ Desativar produto

### 🔍 Busca Avançada
- ✅ Filtrar por categoria
- ✅ Buscar por nome (contém)
- ✅ Filtrar por faixa de preço
- ✅ Filtrar apenas produtos ativos
- ✅ Ordenação customizada

### 📊 Analytics
- ✅ Estatísticas de produtos
- ✅ Contadores por categoria
- ✅ Produtos ativos/inativos

## 🌐 Endpoints da API

### Produtos - CRUD

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/v1/products` | Criar produto |
| `GET` | `/api/v1/products/{id}` | Buscar por ID |
| `GET` | `/api/v1/products` | Listar produtos |
| `PUT` | `/api/v1/products/{id}` | Atualizar produto |
| `DELETE` | `/api/v1/products/{id}` | Deletar produto |
| `PATCH` | `/api/v1/products/{id}/deactivate` | Desativar produto |

### Busca e Filtros

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/v1/products/category/{category}` | Buscar por categoria |
| `GET` | `/api/v1/products/search?name={name}` | Buscar por nome |
| `GET` | `/api/v1/products/price-range?minPrice={min}&maxPrice={max}` | Buscar por faixa de preço |

### Analytics

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/v1/products/stats` | Estatísticas dos produtos |

## 📋 Modelos de Dados

### Product
```json
{
  "id": 1,
  "name": "Smartphone Samsung Galaxy S23",
  "description": "Smartphone Android com 256GB",
  "price": 2500.99,
  "stock": 50,
  "category": "ELECTRONICS",
  "active": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Categorias Disponíveis
- `ELECTRONICS` - Eletrônicos
- `CLOTHING` - Vestuário
- `FOOD` - Alimentação
- `BOOKS` - Livros
- `HOME` - Casa e Decoração
- `SPORTS` - Esportes
- `OTHER` - Outros

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven ou Gradle

### Passos

1. **Clone o repositório**
```bash
git clone https://github.com/seu-usuario/products-api-kotlin.git
cd products-api-kotlin
```

2. **Execute a aplicação**
```bash
# Com Maven
./mvnw spring-boot:run

# Com Gradle
./gradlew bootRun
```

3. **Acesse a aplicação**
- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (vazio)

## 🧪 Testando a API

### 📱 Collection do Insomnia

Importe a collection `insomnia-collection.json` no Insomnia para testar todos os endpoints da API.

### 🔧 Exemplos de Requisições

#### Criar Produto
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone Samsung Galaxy S23",
    "description": "Smartphone Android com 256GB",
    "price": 2500.99,
    "stock": 50,
    "category": "ELECTRONICS",
    "active": true
  }'
```

#### Listar Produtos com Paginação
```bash
curl "http://localhost:8080/api/v1/products?page=0&size=10&sort=name,asc&activeOnly=true"
```

#### Buscar por Nome
```bash
curl "http://localhost:8080/api/v1/products/search?name=Samsung"
```

#### Atualização Parcial
```bash
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "price": 2300.99,
    "stock": 45
  }'
```

## 📊 Paginação e Ordenação

Todos os endpoints de listagem suportam:

- **Paginação**: `?page=0&size=10`
- **Ordenação**: `?sort=name,asc` ou `?sort=price,desc`
- **Múltiplos campos**: `?sort=category,asc&sort=name,desc`

## ⚠️ Tratamento de Erros

A API retorna erros estruturados:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Produto com ID 999 não encontrado"
}
```

### Códigos de Status
- `200` - Sucesso
- `201` - Criado
- `204` - Sem conteúdo
- `400` - Erro de validação
- `404` - Não encontrado
- `500` - Erro interno

## 🔧 Configuração

### application.yml
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password: ''
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
  h2:
    console:
      enabled: true

server:
  port: 8080
```

## 🤝 Contribuindo

1. Faça o fork do projeto
2. Crie sua feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Próximas Funcionalidades

- [ ] Autenticação JWT
- [ ] Cache com Redis
- [ ] Upload de imagens
- [ ] Auditoria de alterações
- [ ] Testes automatizados
- [ ] Documentação Swagger/OpenAPI
- [ ] Docker e Docker Compose
- [ ] CI/CD com GitHub Actions

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👤 Autor

Desenvolvido com ❤️ por **Seu Nome**

- GitHub: [@seu-usuario](https://github.com/seu-usuario)
- LinkedIn: [seu-perfil](https://linkedin.com/in/seu-perfil)
- Email: seu.email@exemplo.com

---

⭐ Se este projeto te ajudou, considere dar uma estrela!
