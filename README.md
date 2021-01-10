# De zoo
Dit is de edge microservice voor het OPO Advanced Programming Topics.

Met dit project kun je de verschillende dieren, het personeel en de verblijven van dieren opvragen en gebruiken.

Hieronder vindt u de links naar de andere microservices:
- [Dieren](https://github.com/Ferwardo/dezoo-dieren)
  
- [Personeel](https://github.com/Ferwardo/dezoo-personeel)
  
- [Verblijven](https://github.com/Ferwardo/dezoo-verblijven)

- [Front end](https://github.com/Ferwardo/dezoo-front)

## Diagram microservices
In onderstaand diagram vindt u de architectuur gebruikt in ons project.

![Diagram microservices](images/diagram-microservices-dezoo.svg)

## Overzicht van de functionaliteiten in swagger

### Dieren
![Alle Dieren functionaliteiten](images/animals/allOperations.png)
#### Get
![Alle dieren](images/animals/getAll.png)
![Dier via dierID](images/animals/getSingleAnimal.png)
![Alle gewervelden](images/animals/getVertebrates.png)

#### Post
![Nieuw dier aanmaken](images/animals/addAnimal.png)

#### Put
![Een dier updaten](images/animals/updateAnimal.png)

#### Delete
![Een dier verwijderen](images/animals/deleteAnimal.png)

### Personeel
![Alle Personeelsfunctionaliteiten](images/personnel/allOperations.png)
#### Get
![Alle personeelsleden](images/personnel/getAll.png)
![Personeelslid via personeelsID](images/personnel/getByID.png)

#### Post
![Nieuw personeelslid aanmaken](images/personnel/addPersonnel.png)

#### Put
![Een personeelslid updaten](images/personnel/updatePersonnel.png)

#### Delete
![Een personeelslid verwijderen](images/personnel/deletePersonnel.png)

### Verblijven
![Alle verblijfs functionaliteiten](images/residences/allOperations.png)
#### Get
![Verblijf via verblijfID](images/residences/getByResidenceID.png)
![Verblijf via personeelsID](images/residences/getByPersonnelID.png)
![Verblijf via dierID](images/residences/getByAnimalID.png)

#### Post
![Nieuw personeelslid aanmaken](images/residences/addResidence.png)

#### Put
![Een personeelslid updaten](images/residences/updateResidence.png)

#### Delete
![Een personeelslid verwijderen](images/residences/deleteResidence.png)
