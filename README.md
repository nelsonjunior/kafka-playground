# kafka-playground

Inicar Zookeper
bin/zookeeper-server-start.sh config/zookeeper.properties

Iniciar Kafka

bin/kafka-server-start.sh config/server.properties 

Criar topic

bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic LOJA_NOVOPEDIDO

Criar producer no console

bin/kafka-console-producer.sh --broker-list localhost:9092 --topic LOJA_NOVOPEDIDO

Criar consumer no console

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic LOJA_NOVOPEDIDO

Para ler do inicio adicionar --from-beginning

Alterar topic

bin/kafka-topics.sh --alter --zookeeper localhost:2181 --topic ECOMMERCE_SEND_EMAIL --partitions 3

Descreve situação grupos
bin/kafka-consumer-groups.sh --all-groups --bootstrap-server localhost:9092 --describe