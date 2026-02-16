# Demanda 02 — Inventory Service

## Contexto
O Inventory Service consome `pedido-pago`, atualiza estoque e publica `estoque-atualizado` ou `pedido-cancelado`.

## Objetivo
Implementar o Inventory Service como consumidor e produtor Kafka.

## Escopo mínimo
- Consumir tópico `pedido-pago`.
- Simular atualização de estoque.
- Publicar `estoque-atualizado` ou `pedido-cancelado`.
- Logs claros de processamento.

## Referências
- docs/README.md (Microserviços, Tópicos Kafka)
- docs/ARQUITETURA.md
