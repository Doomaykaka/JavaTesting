# Installing and using plugin from git and etc

## Installing

To use the plugin you need to download three files (example folder):

- build.gradle
- downloadScript.gradle
- Makefile

Then these files must be placed in the application directory.
You must first save your build.gradle file in a different directory.
Example of a directory for placing scripts:

```bash
MyProject/app
```

You need to transfer the contents of your build.gradle script to the new one
with saving block data:

```groovy
buildscript {		
	repositories {
		flatDir { dirs 'build/tmp' }
		mavenCentral()
	}
    
	dependencies {
			//Plugin dependencies
			classpath "de.undercouch:gradle-download-task:3.4.3"
			classpath "org.eclipse.persistence:org.eclipse.persistence.moxy:3.0.0"
			classpath 'jakarta.xml.bind:jakarta.xml.bind-api:3.0.0'
			//Plugin
			classpath 'doomaykacheckstylecriticplugin:parseReport:1.0.0'
    }	
}

//...Application plugins

apply plugin: 'pluginPackage.pluginTask'

//...User script
```

Then you need to start installing the plugin.
To do this you need to run the command:

```bash
make install
```

To start you need to run the command:

```bash
make taskName
```

After cleaning the project build, the plugin will need to be reinstalled.

To get help on the Make file, run the command:

```bash
make help
```