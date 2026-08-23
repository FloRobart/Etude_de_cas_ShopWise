# ShopWise

## Table des matières

- [ShopWise](#shopwise)
  - [Table des matières](#table-des-matières)
  - [Description](#description)
  - [Workflow GIT](#workflow-git)
  - [Launching the application](#launching-the-application)
    - [In production](#in-production)
    - [For the developpement](#for-the-developpement)
  - [Insert test data](#insert-test-data)
  - [Stop the application](#stop-the-application)

## Description

ShopWise est une application web de digitalisation des commerces de proximité.

## Workflow GIT

- **main** : Branche principale contenant le code stable et prêt pour la production.
    - Lors de la fusion de code sur main, des actions se lance pour créer une release et un build de l'image de production puis la publie sur GitHub Container Registry (GHCR).
- **dev** : Branche de développement où les nouvelles fonctionnalités sont intégrées et testées avant d'être fusionnées dans la branche principale.
- **us/** : Branches de fonctionnalités (User Stories) créées à partir de la branche dev. Chaque branche us/ est dédiée à une fonctionnalité spécifique et est fusionnée dans dev une fois terminée et testée.

## Launching the application

### In production

- In empty repository
- Copy file `.env.example` from Github in file `.env` on your local machine.
- Copy file `docker-compose.yml` from Github in file `docker-compose.yml` on your local machine.
- Copy file `run.sh` from Github in file `run.sh` on your local machine.
- Make the script executable.

    ```sh
    chmod +x run.sh
    ```

- execute `run.sh` to pull the production image and launch the application.

### For the developpement

- Clone repository

    ```sh
    git clone https://github.com/FloRobart/Etude_de_cas_ShopWise.git
    ```

- Copy file `.env.example` in file `.env`.

    ```sh
    cp .env.example .env
    ```

- execute docker compose command to build and launch the application.

    ```sh
    docker compose -f docker-compose.prod.test.yml up -d --force-recreate --build
    ```

## Insert test data

- Clone repository

    ```sh
    git clone https://github.com/FloRobart/Etude_de_cas_ShopWise.git
    ```

- execute `insert_data.sh <container_name>` to insert test data into the database. The script will execute the SQL commands from `data.sql` file inside the specified container.

- Client with password :
    - email : `albert.einstein@gmail.com`
    - password : `123456`

## Stop the application

- execute `stop.sh` to stop the application and remove the Docker container.

    ```sh
    ./stop.sh
    ```
