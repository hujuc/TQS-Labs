CREATE TABLE IF NOT EXISTS student (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    age INTEGER NOT NULL,
    email VARCHAR(255)
);

-- Inserir alguns dados de exemplo
INSERT INTO student (name, age, email) VALUES ('Ana Silva', 21, 'ana.silva@example.com');
INSERT INTO student (name, age, email) VALUES ('Carlos Santos', 24, 'carlos.santos@example.com');
INSERT INTO student (name, age, email) VALUES ('Mariana Oliveira', 19, 'mariana.oliveira@example.com');
INSERT INTO student (name, age, email) VALUES ('Pedro Costa', 22, 'pedro.costa@example.com');
INSERT INTO student (name, age, email) VALUES ('Sofia Martins', 25, 'sofia.martins@example.com');