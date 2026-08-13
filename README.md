# ShopWise

## Table des matières

- [ShopWise](#shopwise)
    - [Table des matières](#table-des-matières)
    - [Description](#description)
    - [Workflow GIT](#workflow-git)

## Description

ShopWise est une application web de digitalisation des commerces de proximité.

## Workflow GIT

- **main** : Branche principale contenant le code stable et prêt pour la production.
    - Lors de la fusion de code sur main, des actions se lance pour créer une release et un build de l'image de production puis la publie sur GitHub Container Registry (GHCR).
- **dev** : Branche de développement où les nouvelles fonctionnalités sont intégrées et testées avant d'être fusionnées dans la branche principale.
- **us/** : Branches de fonctionnalités (User Stories) créées à partir de la branche dev. Chaque branche us/ est dédiée à une fonctionnalité spécifique et est fusionnée dans dev une fois terminée et testée.
