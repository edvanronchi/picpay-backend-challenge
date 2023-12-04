
# Documentação da API

## Sobre o projeto

Esta API fornece funcionalidades para criação de usuarios e transferências de dinheiro entre dois tipos de usuários: usuários regulares e lojistas. Ambos os tipos de usuário possuem uma carteira com dinheiro, e transferências podem ser feitas entre eles.

## Técnologias utilizadas
- Java 17 
- Maven 3.9.2
- Spring Boot 3.1.3

## Docker
Um arquivo docker-compose foi desenvolvido para operar em conjunto com o banco de dados Postgres. A aplicação está configurada para ser executada na porta 8090, enquanto o banco de dados estará acessível na porta 5432. Antes de iniciar os contêineres, certifique-se de fazer o build do projeto.
```sh
docker-compose up -d
```

## Roadmap
### Fases Concluídas
- [x] Iniciar o Projeto
- [x] Criar Migrations
- [x] Configurar o Ambiente de Desenvolvimento com Docker
- [x] Criar Testes Unitários
- [x] Documentar a API

### Próximas Etapas
- [ ] Implementar Autenticação
- [ ] Implementar Documentação com Swagger
- [ ] Desacoplar Funcionalidades em Microserviços
- [ ] Implementar Ferramentas para Monitoramento e Observabilidade
