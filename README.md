E-commerce Altice - Guia de Execução

🐳 Docker Compose

# 1. Compilar
```shell
  ./mvnw clean package
```
# 2. Executar
```
cd src/main/docker
docker-compose up -d
```

☸️ Kubernetes
```
kubectl create namespace altice-ecommerce
```
 
# 1. Build da imagem
```
./mvnw clean package
docker build -f src/main/docker/Dockerfile.jvm -t altice-ecommerce:latest .
```
# 2. Deploy PostgreSQL
```
kubectl apply -f k8s/postgres-k8s.yaml
```
# 3. Deploy da aplicação
```
kubectl apply -f k8s/altice-app-k8s-final.yaml
```
# 4. Expor acesso
```
kubectl port-forward service/altice-ecommerce-service 8091:8091 -n altice-ecommerce &
```

# 5. Verificar
```
curl http://localhost:8091/api/v1/products
```

🔗 Acessos
API: http://localhost:8091

## 🧪 Teste da API

### 1️⃣ Criar usuário
```bash
curl -X POST http://localhost:8091/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Silva",
    "email": "maria.silva@example.com",
    "password": "senha_segura_123"
  }'
```

### 2️⃣ Criar produto
```bash
curl -X POST http://localhost:8091/api/v1/products \
  -H "Content-Type: application/json" \
  -d '[{
    "code": "monitor-gaming-01",
    "category": "computing",
    "subCategory": "monitors",
    "description": "27-inch 4K Gaming Monitor",
    "acquirerId": "fornecedor-lg",
    "stockQuantity": 60,
    "priceCost": 299.99,
    "price": 449.99
  }]'
```

### 3️⃣ Criar carrinho de compras

**🔹 Carrinho vazio:**
```bash
curl -X POST http://localhost:8091/api/v1/shopping-carts \
  -H "Content-Type: application/json" \
  -d '{}'
```

**🔹 Carrinho com itens:**
```bash
# Substituir pelos IDs reais retornados nos passos anteriores
curl -X POST http://localhost:8091/api/v1/shopping-carts \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "seu-user-id-aqui",
    "items": [
      {
        "productId": "seu-product-id-aqui",
        "quantity": 1
      }
    ]
  }'
```

**🔹 Adicionar itens ao carrinho existente:**
```bash
curl -X PATCH http://localhost:8091/api/v1/shopping-carts/{cartId}/items \
  -H "Content-Type: application/json" \
  -d '[
    {
      "productId": "seu-product-id-aqui",
      "quantity": 1
    }
  ]'
```

### 4️⃣ Fazer checkout
```bash
curl -X POST http://localhost:8091/api/v1/checkout/cart/{cartId} \
  -H "Content-Type: application/json"
```

## 🚀 Funcionalidades da API

### 👤 Usuários (`/api/v1/users`)
- `POST /` - Criar usuário
- `GET /{id}` - Buscar usuário
- `PATCH /{id}` - Atualizar usuário
- `DELETE /{id}` - Remover usuário

### 📦 Produtos (`/api/v1/products`)
- `POST /` - Criar produtos (múltiplos)
- `GET /` - Listar produtos (filtros: category, subCategory)
- `GET /{id}` - Buscar produto
- `PUT /{id}` - Atualizar produto
- `DELETE /{id}` - Remover produto

**Categorias disponíveis:**
- `mobile phones`, `gaming`, `computing`, `televisions`

### 🛒 Carrinho (`/api/v1/shopping-carts`)
- `POST /` - Criar carrinho
- `GET /{id}` - Buscar carrinho
- `PUT /{id}` - Atualizar carrinho
- `DELETE /{id}` - Remover carrinho
- `PATCH /{id}/clear` - Limpar carrinho
- `PATCH /{id}/items` - Adicionar itens
- `PATCH /{id}/items/remove` - Remover itens
- `PATCH /{id}/items/quantity` - Atualizar quantidades
- `PATCH /{id}/user` - Associar usuário

### 💳 Checkout (`/api/v1/checkout`)
- `POST /cart/{cartId}` - Criar checkout
- `POST /payment` - Processar pagamento
- `GET /{id}` - Buscar checkout

**Métodos de pagamento:**

🔹 **MBWay**
- Aprovado: `912345678`
- Rejeitado: `900000000`

🔹 **Cartão**
- Aprovado: `1234567890123456`
- Rejeitado: `0000000000000000`

### 📊 Analytics (`/api/v1/analytics`)
- `GET /carts` - Estatísticas de carrinhos
- `GET /items/statistics` - Estatísticas de itens
- `GET /items/top` - Produtos mais vendidos
- `GET /dashboard` - Dashboard completo

## 🧹 Limpeza

**Docker Compose:**
```bash
docker-compose down -v
```

**Kubernetes:**
```bash
kubectl delete namespace altice-ecommerce
```
