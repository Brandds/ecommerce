# Demanda 03 — Notification Service

## Contexto
O Notification Service consome todos os eventos para notificar (Email, Push, SMS via logs).

## Objetivo
Implementar o Notification Service como consumidor universal.

## Escopo mínimo
- Consumir `pedido-pago`, `pedido-pagamento-falhou`, `pedido-cancelado`, `estoque-atualizado`.
- Logar a “notificação” simulada.

## Referências
- docs/README.md (Microserviços, Tópicos Kafka)
- docs/ARQUITETURA.md
