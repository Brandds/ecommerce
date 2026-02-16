# E-commerce Orientado a Eventos com Kafka

## 📋 Visão Geral

Projeto intermediário de e-commerce que utiliza **Apache Kafka** como backbone de comunicação entre microserviços. Implementa padrões de **coreografia de eventos** para garantir consistência eventual em um ambiente distribuído.

Este projeto é excelente para aprender Kafka na prática e está pronto para adicionar ao portfólio no GitHub.

## 🎯 Objetivos

- ✅ Dominar Kafka com microserviços
- ✅ Implementar comunicação assíncrona
- ✅ Aplicar padrões de resiliência (Retry, DLQ, Idempotência)
- ✅ Garantir consistência eventual
- ✅ Criar arquitetura orientada a eventos

## 🧱 Arquitetura Geral

```
┌─────────────┐
│ Order API   │
└─────┬───────┘
      │ evento: pedido-criado
      ▼
   ┌─────── Kafka ───────┐
   │                     │
   ▼                     ▼
Payment Service     Inventory Service
   │                     │
   │ evento: pedido-pago │
   ▼                     ▼
       ───── Kafka ─────
              │
              ▼
     Notification Service
```

## 📦 Microserviços

### 1. Order Service (Producer)
- **Responsabilidade**: Criar pedidos e publicar eventos
- **Endpoints**: 
  - `POST /orders` - Criar novo pedido
  - `GET /orders/{id}` - Obter detalhes do pedido
- **Evento Publicado**: `pedido-criado`

### 2. Payment Service (Consumer + Producer)
- **Responsabilidade**: Processar pagamentos
- **Consome**: `pedido-criado`
- **Publica**: `pedido-pago` ou `pedido-pagamento-falhou`

### 3. Inventory Service (Consumer + Producer)
- **Responsabilidade**: Gerenciar estoque
- **Consome**: `pedido-pago`
- **Publica**: `estoque-atualizado` ou `pedido-cancelado`

### 4. Notification Service (Consumer)
- **Responsabilidade**: Notificar eventos
- **Consome**: Todos os eventos
- **Ações**: Email, Push, SMS (logs)

## 🧵 Tópicos Kafka

| Tópico | Producer | Consumers |
|--------|----------|-----------|
| `pedido-criado` | Order Service | Payment Service |
| `pedido-pago` | Payment Service | Inventory Service, Notification Service |
| `pedido-pagamento-falhou` | Payment Service | Notification Service |
| `pedido-cancelado` | Inventory Service | Notification Service |
| `estoque-atualizado` | Inventory Service | Notification Service |

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 3.x**
- **Spring Kafka**
- **Maven**
- **Kafka 7.5.0**
- **Docker & Docker Compose**
- **H2 Database** (desenvolvimento)

## 🚀 Quick Start

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 17+
- Maven 3.8+

### Como rodar

```bash
# 1. Iniciar containers (Kafka, Zookeeper, Kafka UI)
docker-compose start

# 2. Compilar projeto
mvn clean install

# 3. Executar Order Service
mvn spring-boot:run

# 4. Acessar Kafka UI (opcional)
http://localhost:8080
```

## 📚 Documentação Completa

- [Arquitetura](./ARQUITETURA.md) - Diagrama e detalhes arquiteturais
- [Fluxo de Eventos](./FLUXO_EVENTOS.md) - Passo a passo do fluxo completo
- [Tópicos Kafka](./TOPICOS_KAFKA.md) - Detalhes de cada tópico
- [Microserviços](./MICROSSERVICOS.md) - Responsabilidades de cada serviço
- [Resiliência](./RESILIENCIA.md) - Padrões de retry, DLQ e idempotência
- [Setup](./SETUP.md) - Configuração do ambiente

## 🧪 Exemplos de Payloads

### Criar Pedido
```bash
POST /orders
Content-Type: application/json

{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "total": 150.00,
  "items": [
    {
      "productId": "p1",
      "quantity": 2
    }
  ]
}
```

### Evento: pedido-criado
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "total": 150.00,
  "items": [
    {
      "productId": "p1",
      "quantity": 2
    }
  ],
  "status": "CREATED"
}
```

## 📋 Checklist de Implementação

- [x] Estrutura base do Order Service
- [x] Docker Compose com Kafka
- [ ] Modelo de Pedido (Order)
- [ ] Controller REST
- [ ] Producer Kafka
- [ ] Payment Service
- [ ] Inventory Service
- [ ] Notification Service
- [ ] Padrões de Resiliência
- [ ] Testes unitários

## 🤝 Próximos Passos

1. Revisar estrutura de pacotes do Order Service
2. Implementar modelo de Pedido
3. Criar Controller REST
4. Configurar Producer Kafka
5. Implementar Payment Service com Consumer
6. Adicionar padrões de resiliência

## 📞 Autor

Projeto de estudo - E-commerce com Kafka

---

**Última atualização**: 15 de fevereiro de 2026
