.PHONY: about help run build clean install

help:
	@echo "make about: printing information about app"
	@echo "make help: printing information about available commands"
	@echo "make run: application launch"
	@echo "make build: building an application"
	@echo "make clean: deleting an existing application build"
	@echo "make install: installing the plugin"
	@echo "make checkApp: launching the plugin"
about:
	@echo "Gradle plugin for assessing code quality based on CheckStyle reports"
run:
	gradle run
build:
	gradle build
clean:
	gradle clean
install:
	mkdir -p tmp
	mkdir -p build/tmp
	mv build.gradle tmp
	cat downloadScript.gradle > build.gradle
	gradle build > log.txt
	rm build.gradle
	mv tmp/build.gradle .
	rm -r tmp
	gradle build > log.txt
checkApp:
	gradle parseReport
	