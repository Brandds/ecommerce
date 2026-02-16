# Demanda 04 — Padrões de Resiliência

## Contexto
O projeto prevê padrões de resiliência: Retry, DLQ e Idempotência.

## Objetivo
Adicionar resiliência aos consumidores Kafka.

## Escopo mínimo
- Retry para falhas transitórias.
- DLQ para mensagens que excederem o limite de retry.
- Garantir idempotência no processamento de eventos.

## Referências
- docs/README.md (Resiliência)
- docs/SETUP.md (Troubleshooting)
