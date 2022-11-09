drop database if exists womensShop;

create database if not exists womensShop;

use womensShop;

CREATE TABLE produit(
    num INT AUTO_INCREMENT,
    nom VARCHAR(50),
    prix DOUBLE,
    nbExemplaires INT,
    PRIMARY KEY (num)
);

CREATE TABLE vetement(
    num INT NOT NULL ,
    taille INT,
    FOREIGN KEY (num) REFERENCES produit(num)
);

CREATE TABLE chaussure(
    num INT NOT NULL,
    pointure INT,
    FOREIGN KEY (num) REFERENCES produit(num)
);

CREATE TABLE accessoire(
    num INT NOT NULL,
    FOREIGN KEY (num) REFERENCES produit(num)
);

CREATE TABLE INFO(
    id INT NOT NULL,
    cost DOUBLE NOT NULL,
    recette DOUBLE NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO INFO VALUES (0, 0, 0);
