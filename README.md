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
            classpath "com.energizedwork:gradle-docker:0.2"
        }
    }

## 2. Apply Plugin

    apply plugin: 'gradle-docker'
    
## 3. Configure Docker Host Settings

    docker {
        host {
            url = 'http://localhost:2375'
        }
        
        ...
    }
    
## 3a. Configure Docker Host To Serve Over HTTP/HTTPS
Modify your docker host configuration to listen over TCP, e.g:

    DOCKER_OPTS="-H localhost:2375"
    
Verify your configuration with curl:

    curl http://localhost:2375/version
    
## 3b. Use TLS (Optional)
Modify your docker host configuration to use TLS:

    echo DOCKER_OPTS="-H localhost:2376 --tlsverify=true --tlscacert=/etc/ssl/ca.pem --tlscert=/etc/ssl/server-cert.pem --tlskey=/etc/ssl/server-key.pem" 

Verify your configuration with docker:

    docker --tlsverify --tlscacert=/etc/ssl/ca.pem --tlscert=/etc/ssl/client-cert.pem --tlskey=/etc/ssl/client-key.pem -H=localhost:2376 version

Verify your configuration with openssl:

    printf 'GET /version HTTP/1.1\r\n\r\n' | openssl s_client -connect localhost:2376 -CAfile ca.pem -cert client-cert.pem -key client-key.pem
    
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
