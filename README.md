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
