# 🩸 DoadorHubAPI - API de Dados Estatísticos

DoadorHubAPI é uma **API desenvolvida com Spring Boot** para processar e armazenar dados estatísticos de doadores de sangue.  
A API recebe informações de candidatos, realiza o tratamento dos dados e permite consultas eficientes, garantindo **desempenho e escalabilidade**.

---

## 🚀 Tecnologias Utilizadas
- **Spring Boot** – Framework principal da aplicação  
- **Spring Data JPA** – Persistência no MySQL  
- **Spring Cache com Redis** – Otimização de consultas  
- **Spring Async** – Processamento assíncrono  
- **Docker & Docker Compose** – Orquestração dos serviços  
- **Maven** – Gerenciamento de dependências  

---

## 🔥 Como Rodar o Projeto

### 1 Pré-requisitos
Antes de começar, instale:
- [Java 17+](https://adoptium.net/)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/)

### 2️ Configuração do Banco de Dados
A aplicação utiliza **MySQL** para persistência e **Redis** para cache. Ambos são executados via **Docker Compose**.

#### 🔹 Criar e subir os containers do MySQL e Redis
```sh
mvn clean package -DskipTests
docker-compose up -d
```
---

## 🌍 Rotas da API
A API possui **duas principais rotas**:

### 📥 1. Enviar Dados para Processamento
- **Rota:** `POST /api/data/{deviceId}`
- **Descrição:** Recebe um JSON contendo dados estatísticos para processamento assíncrono.

📌 **Exemplo de Requisição:**
```sh
curl -X POST "http://localhost:8080/api/data/12345" \
     -H "Content-Type: application/json" \
     -d '{
          "totalCandidatesPerState": { "SP": 320, "RJ": 180 },
          "imcByAgeRange": [ { "range": {"min_age": 0, "max_age": 10}, "averageImc": 15.2 } ],
          "obesityPercentage": { "Male": {"total": 600, "obese": 180, "percentageObese": 30.0} },
          "averageAgeByBloodType": { "A+": 36.5 },
          "donorsPerBloodType": { "A+": {"donorsCount": 120} }
     }'
```

---

### 📤 2. Buscar Dados Processados
- **Rota:** `GET /api/processed-data/{deviceId}`
- **Descrição:** Retorna os dados já processados para um `deviceId` específico.

📌 **Exemplo de Requisição:**
```sh
curl -X GET "http://localhost:8080/api/processed-data/12345"
```

📌 **Exemplo de Resposta:**
```json
{
  "totalCandidatesPerState": { "SP": 320, "RJ": 180 },
  "imcByAgeRange": [ { "range": { "min_age": 0, "max_age": 10 }, "averageImc": 15.2 } ],
  "obesityPercentage": { "Male": { "total": 600, "obese": 180, "percentageObese": 30.0 } },
  "averageAgeByBloodType": { "A+": 36.5 },
  "donorsPerBloodType": { "A+": { "donorsCount": 120 } }
}
```
