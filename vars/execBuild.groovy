def call(String command) {
	if (command == "gradle") {
		pipeline {
 		   agent {
			 node "master" 
		  }
    		   stages {
        		stage ("Checkout") {
            			steps {
                			script {
                    				cleanWs()
                    				checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/FamidhaThurab/GradleProject.git']]])
               				 }
           			 }
       			 }
        		stage ("Build") {
            			steps {
                			script {
                   				 sh label: '', script: '''#!/bin/bash -ex
                       					 ./gradlew build
                       					 ./gradlew run
                   				 '''
               				 }
           			 }
       			 }
   		 }
	}	
}
}
