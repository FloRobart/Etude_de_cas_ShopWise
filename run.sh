#!/bin/sh

# Ce script permet de lancer/mettre à jour la version dockerisée de la documentation
# Usage :
#   - Pour lancer la documentation : ./run.sh
#   - Pour mettre à jour la documentation : ./run.sh

docker compose -f docker-compose.yml up -d --force-recreate --build
