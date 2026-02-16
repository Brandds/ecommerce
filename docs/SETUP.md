# Setup do Projeto

## 🚀 Pré-requisitos

### Software Necessário
- **Java 17+** ([Download](https://www.oracle.com/java/technologies/downloads/#java17))
- **Maven 3.8+** ([Download](https://maven.apache.org/))
- **Docker & Docker Compose** ([Download](https://www.docker.com/products/docker-desktop))
- **Git** (opcional, para clonar)

### Verificar Instalação

```bash
# Java
java -version

# Maven
mvn -version

# Docker
docker --version
docker-compose --version
```

---

## 📥 Instalação

### 1. Clonar Repositório (ou copiar projeto)

```bash
# Se estiver versionado
git clone https://github.com/seu-usuario/ecommerce-kafka.git
cd ecommerce-kafka

# Ou simplesmente estar na pasta do projeto
cd ~/Área\ de\ trabalho/ecommerce
```

### 2. Estrutura de Pastas Esperada

```
ecommerce/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── enums/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── request/
│   │   │   └── service/
│   │   └── resources/
│   │       └── application.yaml
│   └── test/
├── docs/
│   ├── README.md
│   ├── ARQUITETURA.md
│   ├── FLUXO_EVENTOS.md
│   ├── TOPICOS_KAFKA.md
│   ├── RESILIENCIA.md
│   ├── MICROSSERVICOS.md
│   └── SETUP.md
├── docker-compose.yml
├── pom.xml
└── mvnw
```

---

## 🐳 Iniciar Kafka com Docker Compose

### Modo 1: Comando Direto

```bash
# Iniciar containers
docker-compose up -d

# Verificar status
docker-compose ps

# Parar containers
docker-compose down
```

### Modo 2: Iniciar Apenas Containers Parados

```bash
# Se já existem containers criados
docker-compose start

# Parar sem remover
docker-compose stop

# Remover containers
docker-compose down
```

### Modo 3: Rebuildar Imagens

```bash
# Se fez mudanças no docker-compose.yml
docker-compose up -d --build

# Ou com cache limpo
docker-compose up -d --build --no-cache
```

### Verificar Logs

```bash
# Todos os serviços
docker-compose logs -f

# Serviço específico
docker-compose logs -f kafka
docker-compose logs -f zookeeper
docker-compose logs -f kafka-ui
```

### Acessar Kafka UI

```
http://localhost:8080
```

Visualizar tópicos, partições, consumer groups e mensagens em tempo real.

---

## 🛠️ Compilar Projeto

### Build Completo

```bash
# Limpar builds anteriores + compilar
mvn clean install

# Resultado: target/classes/ contém .class files
```

### Build Rápido (sem testes)

```bash
mvn clean install -DskipTests
```

### Build com Dependências Atualizadas

```bash
# Forçar download de dependências
mvn clean install -U
```

---

## ▶️ Executar Order Service

### Com Maven

```bash
# Terminal 1: Executar aplicação
mvn spring-boot:run

# Output esperado:
# 2026-02-15 10:30:00 ... Started EcommerceApplication in 3.5 seconds
```

### Com IDE (VS Code / IntelliJ)

1. Abrir arquivo `EcommerceApplication.java`
2. Clicar em "Run" ou `Shift + F10`
3. Aguardar inicialização

### JAR Executável

```bash
# Gerar JAR
mvn clean package

# Executar
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar

# Com parâmetros
java -Dserver.port=8001 -jar target/ecommerce-0.0.1-SNAPSHOT.jar
```

### Verificar se está rodando

```bash
# Deve retornar 200 OK
curl http://localhost:8080/actuator/health

# Response:
# {"status":"UP"}
```

---

## 🧪 Testar Endpoints

### 1. Criar Pedido

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "550e8400-e29b-41d4-a716-446655440000",
    "total": 150.00,
    "items": [
      {
        "productId": "PROD-001",
        "quantity": 2,
        "price": 75.00
      }
    ]
  }'
```

### 2. Obter Pedido

```bash
curl http://localhost:8080/orders/{orderId}
```

### 3. Listar Pedidos do Usuário

```bash
curl "http://localhost:8080/orders?userId=550e8400-e29b-41d4-a716-446655440000"
```

### Com Postman

1. Importar arquivo `postman_collection.json` (se existir)
2. Ou criar manualmente:

| Método | URL | Body |
|--------|-----|------|
| POST | `http://localhost:8080/orders` | JSON do pedido |
| GET | `http://localhost:8080/orders/{id}` | - |
| GET | `http://localhost:8080/orders` | - |

---

## 📊 Monitorar Kafka

### Via CLI (Dentro do Container)

```bash
# Entrar no container Kafka
docker exec -it kafka bash

# Listar tópicos
kafka-topics --list --bootstrap-server localhost:9092

# Descrever tópico
kafka-topics --describe --topic pedido-criado --bootstrap-server localhost:9092

# Consumir mensagens (debug)
kafka-console-consumer --topic pedido-criado \
  --from-beginning \
  --bootstrap-server localhost:9092

# Ver consumer groups
kafka-consumer-groups --list --bootstrap-server localhost:9092

# Ver lag de um consumer group
kafka-consumer-groups --describe \
  --group order-service \
  --bootstrap-server localhost:9092
```

### Via Kafka UI (Recomendado)

```
http://localhost:8080
```

- **Clusters**: Ver estado do Kafka
- **Topics**: Criar, deletar, visualizar mensagens
- **Consumer Groups**: Monitorar lag e offset
- **Brokers**: Status de cada broker

---

## 🔧 Configuração application.yaml

### Exemplo Mínimo

```yaml
spring:
  application:
    name: order-service
  
  jpa:
    hibernate.ddl-auto: create-drop
  
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    consumer:
      group-id: order-service
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: "com.ecommerce.dto"

server:
  port: 8080
```

### Com Database Externo

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce
    username: root
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate.ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect
```

---

## 🐛 Troubleshooting

### Problema: "Connection refused" ao Kafka

```
org.apache.kafka.common.errors.TimeoutException: 
  Timed out waiting for a node assignment
```

**Solução:**
```bash
# Verificar se Kafka está rodando
docker-compose ps

# Se não, iniciar
docker-compose up -d

# Verificar logs
docker-compose logs kafka
```

### Problema: Porta já em uso

```
Address already in use: bind
```

**Solução:**
```bash
# Encontrar processo na porta 8080
lsof -i :8080

# Matar processo
kill -9 <PID>

# Ou mudar porta em application.yaml
server:
  port: 8081
```

### Problema: Classes não encontradas

```
ClassNotFoundException: com.ecommerce.dto
```

**Solução:**
```bash
# Limpar cache Maven
mvn clean install -U

# Reconstruir projeto
mvn clean package
```

### Problema: Consumer não consome mensagens

**Checklist:**
1. ✅ Kafka está rodando? `docker-compose ps`
2. ✅ Tópico existe? `docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092`
3. ✅ Consumer está ativo? Verificar logs da aplicação
4. ✅ Group ID correto? Verificar em `application.yaml`

---

## 📈 Performance

### Aumentar Partições (Paralelismo)

```bash
# Criar tópico com mais partições
kafka-topics --create \
  --topic pedido-criado \
  --partitions 6 \
  --replication-factor 1 \
  --bootstrap-server localhost:9092

# Ou aumentar tópico existente
kafka-topics --alter \
  --topic pedido-criado \
  --partitions 6 \
  --bootstrap-server localhost:9092
```

### Aumentar Threads de Consumo

```yaml
spring:
  kafka:
    listener:
      concurrency: 3  # 3 threads por partição
```

---

## 🧹 Limpeza

### Deletar Dados

```bash
# Parar containers
docker-compose down

# Deletar volumes (banco de dados, logs)
docker-compose down -v

# Deletar tudo (incluindo imagens)
docker-compose down -v --rmi all
```

### Limpar Local

```bash
# Cache Maven
rm -rf ~/.m2/repository

# Build local
mvn clean

# Target folder
rm -rf target/
```

---

## 📝 Checklist de Setup

- [ ] Java 17+ instalado
- [ ] Maven 3.8+ instalado
- [ ] Docker e Docker Compose instalados
- [ ] `docker-compose.yml` criado na raiz do projeto
- [ ] Containers iniciados: `docker-compose up -d`
- [ ] `pom.xml` com dependências corretas
- [ ] `application.yaml` configurado
- [ ] Order Service compilado: `mvn clean install`
- [ ] Aplicação rodando: `mvn spring-boot:run`
- [ ] Kafka UI acessível: `http://localhost:8080`
- [ ] Endpoint testado: `curl http://localhost:8080/actuator/health`

---

## 🚀 Próximos Passos

1. **Payment Service**: Implementar consumer de `pedido-criado`
2. **Inventory Service**: Implementar consumer de `pedido-pago`
3. **Notification Service**: Implementar consumer universal
4. **Testes**: Adicionar testes unitários e de integração
5. **CI/CD**: Configurar GitHub Actions ou GitLab CI

---

**Última atualização**: 15 de fevereiro de 2026
