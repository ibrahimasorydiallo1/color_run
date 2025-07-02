CREATE TABLE IF NOT EXISTS course (
      id IDENTITY PRIMARY KEY,
      nom VARCHAR(255),
    description VARCHAR(255),
    date TIMESTAMP,
    lieu VARCHAR(255),
    nombreMaxParticipants INT,
    prix FLOAT
    );

CREATE TABLE IF NOT EXISTS utilisateur (
   id          BIGINT AUTO_INCREMENT PRIMARY KEY,
   prenom      VARCHAR(60)  NOT NULL,
    nom         VARCHAR(60)  NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL
    );


