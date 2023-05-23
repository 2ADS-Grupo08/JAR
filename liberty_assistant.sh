#!/bin/bash

echo " _     _ _               _            ____       "      
echo "| |   (_) |__   ___ _ __| |_ _   _   / ___|___   "  
echo "| |   | | '_ \ / _ \ '__| __| | | | | |   / _ \  " 
echo "| |___| | |_) |  __/ |  | |_| |_| | | |__| (_) | "
echo "|_____|_|_.__/ \___|_|   \__|\__, |  \____\___/  " 
echo "                             |___/               "


echo -e "[Liberty-assistant]: Olá, serei seu assistente para instalação do Java, docker e MYSQL!!!"
sleep 3
echo -e "[Liberty-assistant]: Irei atualizar os pacotes do sistema. Só um momento!"
sudo apt update && sudo apt upgrade –y
echo -e "[Liberty-assistant]: Pacotes atualizados!"
clear
sleep 2
echo -e "[Liberty-assistant]: Verificando se você possui o Java instalado..."
echo -e "\n"

sleep 3
java -version >/dev/null 2>&1

if [ $? -eq 0 ]
	then
		echo -e "[Liberty-assistant]: Você já tem o java instalado!!!"
		echo -e "    "
		sleep 1

		echo -e "[Liberty-assistant]: E essa é a sua atual versão:"
		java -version
	else
		echo -e"[Liberty-assistant]:  Opa! Não identifiquei nenhuma versão do Java instalado, mas sem problemas, irei resolver isso agora!"
		echo -e "[Liberty-assistant]:  Confirme para se realmente deseja instalar o Java (S/N)?"
	read inst
		if [ \"$inst\" == \"S\" ]
			then
				echo -e "[Liberty-assistant]:  Ok! Você escolheu instalar o Java"

				if [ $VERSAO -e 17 ]
					then
						echo -e "[Liberty-assistant]: Preparando para instalar a versão 17 do Java. Confirme a instalação quando solicitado"
						sudo apt install openjdk-17-jdk openjdk-17-jre-y openjdk-17 openjdk-17-jdk-headless;
						clear
						echo -e "[Liberty-assistant]: Java instalado com sucesso!"
                				echo -e "    "

					else
						echo -e "[Liberty-assistant]: A versão atual do Java é menor que 17. Deseja atualizá-la (S/N)?"
						read atualizar

						if [ \"$atualizar\" == \"S\" ]
							then
								echo -e "[Liberty-assistant]: Atualizando Java para a versão 17. Confirme a instalação quando solicitado."
								sudo apt install openjdk-17-jdk openjdk-17-jre-y openjdk-17 openjdk-17-jdk-headless;
								clear
								echo -e "[Liberty-assistant]: Java atualizado com sucesso!"
							else
							echo -e "[Liberty-assistant]: A versão atual do Java não atende aos requisitos mínimos. Atualize o Java para a versão 17 ou superior para continuar."
						fi
				fi
		fi
fi


sleep 2
echo -e "    "
echo -e "    "

echo -e "[Liberty-assistant]: Verificando se você possui o Docker instalado..."
sleep 2
docker --version >/dev/null 2>&1

if [ $? -eq 0 ]
	then
                echo -e "    "
                echo -e "    "
		echo -e "[Liberty-assistant]: Você já tem o docker instalado!!!"
                echo -e "    "
		sleep 1
		sudo systemctl start docker
		sudo systemctl enable docker

		echo -e "[Liberty-assistant]: E essa é a versão atual do docker:"
		docker --version
	else
		echo -e"[Liberty-assistant]:  Opa! Não identifiquei nenhuma versão do Docker instalado, mas sem problemas, irei resolver isso agora!"
		echo -e "[Liberty-assistant]:  Confirme para se realmente deseja instalar o Java (S/N)?"
	read inst
		if [ \"$inst\" == \"S\" ]
			then
				echo -e "[Liberty-assistant]:  Ok! Você escolheu instalar o Docker"
				echo -e "[Liberty-assistant]:  Realizando download!"
				sleep 2
				sudo apt install docker.io -y				
				echo -e "\n"
				echo -e "[Liberty-assistant]:  Atualizando! Quase lá."
				sleep 2
				echo -e "\n"
				sudo apt update && sudo apt upgrade –y
		                echo -e "    "
		                echo -e "    "
				echo -e "[Liberty-assistant]: Obrigado pela paciência! Aproveite nossas ferramentas!"
		fi
fi
echo -e "    "
echo -e "    "
echo -e "[Liberty-assistant]: Verificando se você possui o MySQL instalado..."
echo -e "    "

sleep 2
sudo docker images | grep mysql >/dev/null 2>&1

if [ $? -eq 0 ]
        then
                echo -e "[Liberty-assistant]: Você já tem o MySQL instalado!!!"
		echo -e "    "

 		sleep 1
        else
                echo -e"[Liberty-assistant]: Opa! Não identifiquei nenhuma versão do MySQL instalado, mas sem problemas, irei resolver isso agora!"
                echo -e "[Liberty-assistant]: Confirme para se realmente deseja instalar o MySQL (S/N)?"
        read inst
                if [ \"$inst\" == \"S\" ]
                        then
                                echo -e "[Liberty-assistant]: Ok! Você escolheu instalar o MySQL"
                                echo -e "[Liberty-assistant]: Realizando download!"
                                sleep 2
				sudo docker pull mysql:5.7
                                echo -e "\n"
                                echo -e "[Liberty-assistant]: Atualizando! Quase lá."
                                sleep 2
                                echo -e "\n"
                                sudo apt update && sudo apt upgrade –y
                                echo -e "[Liberty-assistant]: Obrigado pela paciência! Aproveite nossas ferramentas!"
                fi
fi
	sleep 3
	echo -e "[Liberty-assistant]: Agora que fora realizado a instalação de todas as ferramentas, iremos criar o ambiente!"
	echo -e "[Liberty-assistant]: Vamos criar uma imagem do banco de dados MYSQL."
        echo -e "    "
        echo -e "    "


if [ $( sudo docker ps -a | grep liberty-co | wc -l ) -gt 0 ];
	then
		echo -e "[Liberty-assistant]: Você já possuí o banco na sua máquina! Vamos inicializar esse container"
		sleep 2
				sudo docker start liberty-co >/dev/null 2>&1
		sleep 2
		echo -e "[Libert-assistant]: Inicializado container Liberty!"

	else
		echo -e "[Liberty-assistant]: Não encontramos nenhum banco do mysql na sua máquina, vamos resolver isso!"
		sleep 2
		sudo docker run -d -p 3306:3306 --name liberty-co -e "MYSQL_DATABASE=liberty-co" -e "MYSQL_ROOT_PASSWORD=#Gfgrupo8" mysql:5.7
		sleep 10
		sudo docker exec -it liberty-co mysql -u root -p#Gfgrupo8 -e "USE liberty-co;
CREATE TABLE  IF NOT EXISTS Instituicao (
	idInstituicao INT PRIMARY KEY AUTO_INCREMENT,
    razaoSoc VARCHAR(120),
    cnpj VARCHAR(14),
    email VARCHAR(100),
    cep VARCHAR(8),
    numero INT,
    complemento VARCHAR(10),
    token VARCHAR(6),
    senha VARCHAR(45)
);
CREATE TABLE IF NOT EXISTS Gestor (
	idGestor INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45),
    ultimoNome VARCHAR (45),
    cargo VARCHAR(25),
    email VARCHAR(45),
    senha VARCHAR(45),
    fkInstituicao INT, FOREIGN KEY (fkInstituicao) REFERENCES Instituicao(idInstituicao)
);

CREATE TABLE IF NOT EXISTS Telefone (
	idTelefone INT PRIMARY KEY AUTO_INCREMENT,
    numTelefone CHAR(14),
    numCelular CHAR(15),
    senha VARCHAR(45),
    fkInstituicao INT, FOREIGN KEY (fkInstituicao) REFERENCES Instituicao(idInstituicao),
    fkGestor INT, FOREIGN KEY (fkGestor) REFERENCES Gestor(idGestor)
);
CREATE TABLE IF NOT EXISTS Maquina (
	idMaquina INT PRIMARY KEY AUTO_INCREMENT,
    hostName VARCHAR(45),
    nomeArq VARCHAR(45),
    ultimoNomeArq VARCHAR(45),
    SO VARCHAR(45),
    status BOOLEAN,	
    fkGestor INT, FOREIGN KEY (fkGestor) REFERENCES Gestor(idGestor)
);
CREATE TABLE IF NOT EXISTS Processo (
	idProcesso INT PRIMARY KEY AUTO_INCREMENT,
    nomeProcesso VARCHAR(45),
    fkGestor INT, FOREIGN KEY (fkGestor) REFERENCES Gestor(idGestor),
    fkMaquina INT, FOREIGN KEY (fkMaquina) REFERENCES Maquina(idMaquina)
);
CREATE TABLE IF NOT EXISTS Componente (
	idComponente INT PRIMARY KEY AUTO_INCREMENT,
	nomeComponente VARCHAR(45),
	total DECIMAL(6,2),
	modelo VARCHAR(100),
	fkMaquina INT
);
CREATE TABLE IF NOT EXISTS Log (
	idLog INT PRIMARY KEY AUTO_INCREMENT,
	momentoCaptura VARCHAR(100),
	emUso DECIMAL(6,2),
	fkComponente INT,
	fkMaquina INT
);"
	fi

        echo -e "    "
        echo -e "    "
echo -e "[Liberty-assistant]: Agora foram criados os bancos em sua máquina, o monitoramento será iniciado após a instalação de nossa aplicação! Peço que aguarde a instalação!"
        echo -e "    "
        echo -e "    "
sleep 3

wget https://github.com/2ADS-Grupo08/JAR/raw/main/captura-componentes-liberty-co/target/captura-componentes-liberty-co-1.0-SNAPSHOT-jar-with-dependencies.jar

        echo -e "    "
        echo -e "    "

echo -e "[Liberty-assistant]: As dependencias foram baixados, iremos executar e peço que não encerre sua máquina"

java -jar captura-componentes-liberty-co-1.0-SNAPSHOT-jar-with-dependencies.jar

echo -e "[Liberty-assistant]: Obrigado por instalar nossa solução, peço que não encerre a aba de monitoramento! A Liberty Company agradece.


