.PHONY: run

run:
	@echo "Running..../gradlew dockerClean"
	@./gradlew dockerClean
	@echo "Running..../gradlew docker"
	@./gradlew docker
	@echo "Running... docker-compose up -d"
	@docker-compose up -d
