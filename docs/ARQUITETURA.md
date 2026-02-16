# Arquitetura do Projeto

## 🏗️ Visão Geral

A arquitetura é baseada em **microserviços desacoplados** que se comunicam através de **eventos no Kafka**, implementando o padrão de **coreografia de eventos**.

## 📊 Fluxo Arquitetural

```
┌─────────────────────────────────────────────────────────────────┐
│                          Cliente                                │
└────────────────┬────────────────────────────────────────────────┘
                 │
                 │ POST /orders
                 ▼
        ┌─────────────────┐
        │  Order Service  │◄──── (Producer)
        │                 │
        │ - REST API      │
        │ - Create Order  │
        │ - Publish Event │
        └────────┬────────┘
                 │
                 │ Publica: pedido-criado
                 ▼
        ┌──────────────────────────────────┐
        │         Apache Kafka             │
        │  (Message Broker Distribuído)    │
        │                                  │
        │ Topics:                          │
        │  - pedido-criado                 │
        │  - pedido-pago                   │
        │  - pedido-pagamento-falhou       │
        │  - pedido-cancelado              │
        │  - estoque-atualizado            │
        └──────────────────────────────────┘
         │                          │
         │ Consome:                 │
         │ pedido-criado            │
         │                          │
    ┌────▼─────────┐           ┌────▼──────────┐
    │ Payment      │           │ Inventory     │
    │ Service      │           │ Service       │
    │              │           │               │
    │ - Consumer   │           │ - Consumer    │
    │ - Processa   │           │ - Processa    │
    │   pagamento  │           │   estoque     │
    │ - Publica    │           │ - Publica     │
    │   pedido-pago│           │   estoque-    │
    │              │           │   atualizado  │
    └────┬─────────┘           └────┬──────────┘
         │ pedido-pago              │
         │                          │ estoque-atualizado
         └──────────┬───────────────┘
                    │
                    ▼
        ┌──────────────────────────┐
        │ Notification Service     │
        │                          │
        │ - Consumer Universal     │
        │ - Processa todos eventos │
        │ - Envia notificações     │
        │   (Email, Push, SMS)     │
        └──────────────────────────┘
```

## 🔄 Padrão de Comunicação: Coreografia de Eventos

**Coreografia** significa que cada serviço reage aos eventos de forma autônoma, sem orquestrador central.

```
Vantagens:
✅ Desacoplamento total
✅ Escalabilidade independente
✅ Falhas isoladas

Desvantagens:
⚠️ Fluxo global mais complexo
⚠️ Debugging mais difícil
⚠️ Precisa de observabilidade
```

## 🔗 Responsabilidades por Camada

### Controller (`OrderController`)
- Recebe requisições HTTP
- Valida entrada
- Chama serviço
- Retorna resposta

### Service (`OrderService`)
- Lógica de negócio
- Orquestra operações
- Chama producer para eventos

### Producer (`OrderProducer`)
- Envia mensagens para Kafka
- Gerencia configuração do producer
- Trata erros de envio

### Model (`Order`)
- Entidade de domínio
- Mapeada para banco de dados
- Representa estado do pedido

### Repository (`OrderRepository`)
- Persistência em banco
- CRUD básico
- Queries customizadas

---

**Última atualização**: 15 de fevereiro de 2026
