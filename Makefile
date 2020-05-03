all: build
build:
	javac -cp libraries/slf4j-api-1.7.9.jar:libraries/slf4j-jdk14-1.7.9.jar:libraries/junit-4.13.jar:libraries/amqp-client-5.9.0.jar:libraries/gson-2.8.6.jar src/*.java src/ring/*.java src/routing/*.java src/utils/*.java src/utils/MQ/*.java
clean:
	rm -f src/*.class src/ring/*.class src/routing/*.class src/utils/*.class src/utils/MQ/*.class
run:
	java -cp libraries/slf4j-api-1.7.9.jar:libraries/slf4j-jdk14-1.7.9.jar:libraries/junit-4.13.jar:libraries/amqp-client-5.9.0.jar:libraries/gson-2.8.6.jar:src/ Main matrix.txt