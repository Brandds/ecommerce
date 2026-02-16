# Demanda 01 — Payment Service

## Contexto
O projeto descreve um microserviço de pagamentos que consome eventos de `pedido-criado` e publica `pedido-pago` ou `pedido-pagamento-falhou`.

## Objetivo
Implementar o Payment Service como consumidor e produtor Kafka.

## Escopo mínimo
- Consumir tópico `pedido-criado`.
- Processar pagamento (simulado).
- Publicar `pedido-pago` em sucesso e `pedido-pagamento-falhou` em falha.
- Logs claros de processamento.

## Referências
- docs/README.md (Microserviços, Tópicos Kafka)
- docs/ARQUITETURA.md
