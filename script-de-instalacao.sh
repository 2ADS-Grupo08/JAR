#!/bin/bash
echo "Liberty Co - Este é o processo de instalação do java na sua máquina"
echo "Verificando se o java já está instalado..."
java -version 
if [ $? = 0 ]; 
        then 
                echo “java instalado!” 
        else 
                echo “java não instalado”
	        echo "Para o nosso sistema funcionar precisamos instalar o java"	
                echo “gostaria de instalar o java? [s/n]” 
                read get 
        if [ \“$get\” == \“s\” ]; 
        then 
                sudo apt install openjdk-17-jre -y
        fi 
fi 
