# IES_107658

## Notebook IES lab01

De uma forma geral, este lab aborda as práticas básicas para configurar um ambiente de desenvolvimento que facilite o desenvolvimento cooperativo, principalmente para projetos empresariais Java. Desta forma, esteremos a seguir 3 conceitos:
1. usar uma ferramenta de criação de projeto bem como gerir automaticamente dependências;
2. colaborar em projetos de código usando o git pra gerir o código-fonte (SCM - Source Code Management);
3. aplicar a tecnologia container, neste caso o Docker, para acelarar e reutilizar implantações.

### Maven

O Maven é um gestor de dependências que permite criar projetos baseados no conceito Project Object Model (POM).
Qunado criado um projeto, cria-se também o ficheiro pom.xml onde são descritos os dados do projeto como groupId, artifactId, version, properties e onde são descritas as dependências do porjeto. Este ficheito xml vai permitir que as dependências sejam instaladas remotamente. As dependências podem ser, também, outros projetos existentes. Baixa bibliotecas Java e plug-ins dinamicamente de um ou mais repositórios.

O groupId identifica de forma única o projeto entre todos os porjetos, devendo começar por um domain name controlado pelo utilizador como com.ies.app ou org.apache.maven.
O artifactId é o nome do ficheiro jar sem versão.

-Uma das ferramentas mais similar com Maven é a ferramenta Ant.

Para instalar o Maven temos de ter a linguagem Java instalada e devemos verificar a sua versão:
    java -version

E, procedendo à instalação do Maven executamos o comando:
    sudo apt install maven


Nome do diretório 	Propósito
Project Home 	    Contém o pom.xml e todos os subdiretórios.
src/main/java 	    Contém o código-fonte Java entregável para o projeto.
src/test/java 	    Contém as classes de teste para o projeto.

Criar um projeto Maven novo:

    §mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

Assim, criando este projeto, a estrutura é algo deste tipo

    my-app
    |-- pom.xml
    `-- src
        |-- main
        |   `-- java
        |       `-- com
        |           `-- mycompany
        |               `-- app
        |                   `-- App.java
        `-- test
            `-- java
                `-- com
                    `-- mycompany
                        `-- app
                            `-- AppTest.java

Para fazer build do projeto usa-se o comando
    mvn package
e, em caso de sucesso, a resposta será do tipo
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time:  2.953 s
    [INFO] Finished at: 2023-09-22T13:05:10+01:00
    [INFO] ------------------------------------------------------------------------

Depois de compilado podemos dar run e testar o projeto,
    mvn exec:java -Dexec.mainClass="com.mycompany.app.App" -D exec.cleanupDaemonThreads=false #adapt to match your own package and class name  



### API request - lab01_2

No exercício 2 é pedido para criar uma aplicação que invoque a weather forecast API disponível do IPMA.

    You may use any HTTP client, such as the browser or the curl utility. For example, to get a 5-day forecast for Aveiro (which has the internal id=1010500):
    $ curl http://api.ipma.pt/open-data/forecast/meteorology/cities/daily/1010500.json | json_pp

Este comando imrpime a informação json disponível nesse URL.

Foi necessário adicionar novas dependências no ficheiro pom.xml, nomeadamente Retrofit e Gson.
    <dependency>
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>retrofit</artifactId>
      <version>2.9.0</version>
    </dependency>  
    <dependency>  
      <groupId>com.squareup.retrofit2</groupId>
      <artifactId>converter-gson</artifactId>
      <version>2.9.0</version>
    </dependency>

A última alínea exige que o programa aceite argumentos, já vimos como se corre um projeto maven sem argumentos, para fazer com que sejam introduzidos argumentos no comando este deve mudar para
    mvn exec:java -Dexec.mainClass="weather.WeatherStarter" -D exec.cleanupDaemonThreads=false Dexec.args="1010500 'argument separated with space' 'another one'"
, este comando introduziu 3 argumentos.



### Source Code Management com git - lab01_3

Este comandos são os mais usados e dos mais importantes ou úteis no uso do git.

Criar novo repositório

git init

Verificar estado dos arquivos/diretórios

git status

Adicionar um arquivo em específico

git add meu_arquivo.txt

Adicionar um diretório em específico

git add meu_diretorio

Adicionar todos os arquivos/diretórios

git add .	

Adicionar um arquivo que esta listado no .gitignore (geral ou do repositório)

git add -f arquivo_no_gitignore.txt

Commit de um arquivo

git commit meu_arquivo.txt

Commit de vários arquivos

git commit meu_arquivo.txt meu_outro_arquivo.txt

Commit com mensagem

git commit meuarquivo.txt -m "minha mensagem de commit"

Remover arquivo

git rm meu_arquivo.txt

Remover diretório

git rm -r diretorio

Exibir histórico

git log

Exibir histórico com diff das duas últimas alterações

git log -p -2

Exibir resumo do histórico (hash completa, autor, data, comentário e qtde de alterações (+/-))

git log --stat

Exemplo ilustrativo

cd project_folder # move to the root of the working folder to be imported
git init # initialize a local git repo in this folder
git remote add origin <REMOTE_URL> #must adapt the url for your repo
git add. # mark all existing changes in this root to be commited
git commit -m "Initial project setup for exercise 1_3" #create the
commit snapshot locally
git push -u origin main #uploads the local commit to the shared repo

Adicionar ficheiro .gitignore
Este ficheiro é colocado na raíz do repositório e serve para ignorar todos os ficheiros que não são importantes, ou seja, ficheiros que não vão ser commited

Neste exercício foi simulada a existência de outro colaborador, para isso foi criada uma nova pasta com o nome 'location2', noutro diretório do computador.

    |
    `-- Desktop
            `--location2
        |-- IES
            |--IES_97484
                        `--location1

    git clone git@github.com:mariaabr/IES_107658.git

O comando acima foi usado para obter os ficheiros no novo local.
Nesta nova localização foi criado um logger, sendo as operações executadas escritas no terminal e num ficheiro, 'logs.log'.
Foi usada a biblioteca auxiliar Log4j2.

Links usados para criar os ficheiros:

    https://www.baeldung.com/java-logging-intro
    https://howtodoinjava.com/log4j2/log4j2-xml-configuration-example/

Exemplo do ficheiro

    ```<Configuration status="info">
        <Appenders>
            <Console name="sout" target="SYSTEM_OUT">
                <PatternLayout pattern="[%p] %d{HH:mm:ss} %m%n \n"/>
            </Console>
            <File name="file" fileName="logs.log" append="true">
                <PatternLayout pattern="[%p] %d{HH:mm:ss} %m%n \n">
                </PatternLayout>
            </File>
        </Appenders>
        <Loggers>
            <Root level="info">
                <AppenderRef ref="sout"/>
                <AppenderRef ref="file"/>
            </Root>
        </Loggers>
    </Configuration>```

Para dar commit a partir desta nova localização (simulando a existência de mais do que um colaborador para o projeto) foram usados os comandos descritos no início.

Para que seja possível ver todas as mensagens enviadas em cada commit pode ser usado o comando seguinte

    git log --reverse --oneline  

Serão listadas todas as mensagens, de todos os colaboradores do projeto.



### Introdução ao Docker - lab01_4

https://www.docker.com/
O docker é um conjunto de serviços que usam virtualização de nível de sistema operacional para entregar software em pacotes chamados containers. Os containers são isolados uns dos outros e agrupam os seus próprios softwares, bibliotecas e arquivos de configuração.

As imagens (docker images) são com que um template (uma classe OOP) que permite iniciar um container. Cada imagem é definida por um Dockerfile, um arquivo de configuração que contém todos os comandos que um utilizador precisa executar para modelar a imagem.

Começar por instalar o docker engine, disponível em https://docs.docker.com/engine/install/. Após a instalação, para uma melhor interação pode-se executar o docker sem ser necessário usar permissões, isto é, sem sudo - https://docs.docker.com/engine/install/linux-postinstall/.

Tutorial e Getting Started: https://docs.docker.com/get-started/ Foi seguido o tutorial e os ficheiros encontram-se neste diretório.

Foi instalada também a Portainer app, disponível em https://www.portainer.io/, que é uma web application e facilita o controlo dos containers.

Alguns comandos docker:
Ver os containers que estão a correr no momento

docker ps

Criar e começar um container

docker run

Remover um container

docker rm

Ver a lista de imagens

docker images

Criar uma nova imagem a partir do dockerfile

docker build

Fazer download de uma imagem de um repositório

docker pull

No final do exercício foi seguido o tutorial de docker compose, disponível em https://docs.docker.com/compose/gettingstarted/
Este tutorial usa a framework Flask e a base de dados NoSQL Redis
Está disponível em composetest

O docker compose permite definir e correr multi-container Docker applications. É usado um ficheiro yml para configurar os serviços.

Usar o docker compose consiste em 3 processos:

    Define your app’s environment with a Dockerfile so it can be reproduced anywhere.

    Define the services that make up your app in docker-compose.yml so they can be run together in an isolated environment.

    Run docker compose up and the Docker compose command starts and runs your entire app. You can alternatively run docker-compose up using the docker-compose binary.

Exemplo de um ficheiro docker-compose.yml

    version: "3.9"  # optional since v1.27.0
    services:
    web:
        build: .
        ports:
        - "5000:5000"
        volumes:
        - .:/code
        - logvolume01:/var/log
        links:
        - redis
    redis:
        image: redis
    volumes:
    logvolume01: {}



### Wrapping-up e conceitos de integração - lab01_5

Foram criados dois projetos, o projeto 2 é constituído pelas classes de suporto do exercício 2 e o porjeto 1 é constituído pela main que é dependente das outras classes.

Esta dependência deve ser adicionada ao ficheiro pom.xml do projeto 1, anycityforecast.
    ```<dependency>
      <groupId>com.ies</groupId>
      <artifactId>ipmaapiclient</artifactId>
      <version>1.0-SNAPSHOT</version>
    </dependency>```
    
Sendo o ipmaapiclient o projeto 2.