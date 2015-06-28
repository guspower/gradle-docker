# gradle-docker
Plugin for managing [Docker](https://www.docker.com/ "Docker website") containers and images from [Gradle](http://gradle.org/ "Gradle website") build scripts.

# Apply Plugin To Gradle
## 1. Add Plugin Dependencies
*gradle-docker* releases are currently hosted by those nice folks at [Bintray](https://bintray.com/ "Bintray website"). 

    buildscript {
        repositories {
            jcenter()
            maven {
                url  "http://dl.bintray.com/guspower/maven"
            }
        }
        dependencies {
            classpath "com.energizedwork:gradle-docker:0.2-20150628145538"
        }
    }

## 2. Apply Plugin

    apply plugin: 'gradle-docker'
    
## 3. Configure Docker Host

    docker {
        host {
            url = 'http://localhost:2375'
        }
        
        ...
    }
    
## 3a. Use TLS (Optional)

//TODO

## 4. Test Connectivity

     ./gradlew containers
    
## 5. Configure Docker Containers

     docker {
         ...
         
         containers {
             container {
                 name    = 'web'
                 before  = 'functionalTest'
                 after   = 'testCleanup'
                 image   = 'busybox:latest'
                 command = ['/bin/sh', '-c', '"while true; do sleep 1; done"']
             }
         }
     }

## 6. Take It For A Test Drive

    ./gradlew tasks --all

# Tested With

<table cellpadding=6 rules=none frame=box>
    <thead>
        <tr>
            <th>OS</th>
            <th>JVM</th>
            <th>Docker</th>
            <th>Gradle</th>
            <th>Status</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Linux 4.0.5-gentoo</td>
            <td>oracle-jdk-bin-1.8.0.45</td>
            <td>1.4.1, build 5bc2ff8</td>
            <td>2.3</td>
            <td>PASS</td>
        </tr>
    </tbody>
</table>

# Licence
gradle-docker is licensed under the [GNU GPLv2](https://www.gnu.org/licenses/gpl-2.0.html "GNU GPLv2").
