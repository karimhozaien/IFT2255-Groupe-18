#!/bin/bash

# Vérifier si Docker est en cours d'exécution
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker n'est pas en cours d'exécution. Veuillez démarrer Docker Desktop."
    exit 1
fi

# Créer le dossier data s'il n'existe pas
mkdir -p data

# Reconstruire l'image si demandé avec --build
if [[ "$1" == "--build" ]]; then
    echo "🏗️  Construction de l'image Docker..."
    docker build -t maville .
    if [ $? -ne 0 ]; then
        echo "❌ Échec de la construction de l'image"
        exit 1
    fi
fi

echo "🚀 Lancement de MaVille..."
docker run -it -p 8080:8080 -v "$(pwd)/data:/app/data" maville
