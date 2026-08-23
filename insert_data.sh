#!/bin/sh

# Vérifie qu'il y a un argument passé au script
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <container_name>"
    exit 1
fi

# Exécution du script SQL dans le conteneur
docker exec -i "$1" psql -U shopwise_user -d shopwise < ./database/data.sql
