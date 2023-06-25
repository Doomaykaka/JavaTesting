PROJECT_NAME = OnlineTestingService
PROJECT_VERSION = 1.0.2
JAR_PATH = build/libs

.PHONY: about help run build clean

help:
	@echo "make about: printing information about app"
	@echo "make help: printing information about available commands"
	@echo "make run: application launch"
	@echo "make build: building an application"
	@echo "make clean: deleting an existing application build"
about:
	@echo "Web application for passing tests"
run:
	java -Dserver.port=8090 -jar $(JAR_PATH)/$(PROJECT_NAME)-$(PROJECT_VERSION).war > log.txt
build:
	gradle build -Pver=$(PROJECT_VERSION)
clean:
	gradle clean
