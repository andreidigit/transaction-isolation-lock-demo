# Transaction Isolation Lock
Изоляция транзакций и блокировка ресурсов PostgreSQL.

Нагрузочное тестирование Spring приложения Gatling.

Kafka и мониторинг в Grafana.

### 01 init
Install Docker [link](https://www.docker.com/)
Docker: PostgreSQL. JPA.

### 02 Isolation, Lock

Read Committed(default), Repeatable Read, Serializable. Optimistic Lock, Pessimistic Lock. 
Gatling: 500(ops/s) x 2s tests.

Gatling download [link](https://gatling.io/open-source/) and  
Quick start [link](https://gatling.io/docs/gatling/tutorials/quickstart/)

Download archive bundle and copy it to c:/Gatling folder.

Put a java scenario files to the path - c:/Gatling/user-files/simulations

c:/Gatling/bin/gatling.bat
Start the script and select option "1" then select a script file.

### 03 Prometheus, Micrometer
Prometheus [link](https://prometheus.io/docs/introduction/overview/#architecture)

metrics ```http://localhost:8084/actuator/prometheus```

Grafana support [link](https://prometheus.io/docs/visualization/grafana/)

Configuration -> Data sources -> Select "Prometheus"

```URL: http://host.docker.internal:9090``` 

```histogram_quantile(0.95,sum(rate(http_server_requests_seconds_bucket{ status!~"5..", uri="/api/add-repeatable-read"}[1m])) by(le))```

```http_server_requests_seconds{ uri="/api/add-repeatable-read", quantile="0.95"}```

