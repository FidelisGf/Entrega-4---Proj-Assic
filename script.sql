CREATE DATABASE IF NOT EXISTS consultas;
USE consultas;


CREATE TABLE usuarios (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          cpf VARCHAR(11) NOT NULL UNIQUE,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          telefone VARCHAR(15),
                          INDEX (cpf),
                          INDEX (email)
);


CREATE TABLE enderecos (
                           id_endereco INT AUTO_INCREMENT PRIMARY KEY,
                           estado VARCHAR(50) NOT NULL,
                           cidade VARCHAR(100) NOT NULL,
                           rua VARCHAR(255) NOT NULL,
                           numero_residencia INT NOT NULL,
                           cep VARCHAR(10) NOT NULL,
                           id_usuario INT NOT NULL,
                           FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE,
                           INDEX (cep),
                           INDEX (id_usuario)
);


CREATE TABLE consultas (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           data_hora DATETIME NOT NULL,
                           id_paciente INT NOT NULL,
                           status VARCHAR(20) DEFAULT 'Pendente',
                           FOREIGN KEY (id_paciente) REFERENCES usuarios(id) ON DELETE CASCADE,
                           INDEX (data_hora),
                           INDEX (id_paciente)
);


CREATE TABLE auditorias (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            operacao VARCHAR(50) NOT NULL,
                            data_operacao DATETIME DEFAULT CURRENT_TIMESTAMP,
                            tabela VARCHAR(50) NOT NULL
);


DELIMITER $$
CREATE TRIGGER trg_consultas_status_default
    BEFORE INSERT ON consultas
    FOR EACH ROW
BEGIN
    SET NEW.status = 'Pendente';
END$$
    DELIMITER ;


DELIMITER $$
    CREATE TRIGGER trg_auditorias_usuarios_insert
        AFTER INSERT ON usuarios
        FOR EACH ROW
    BEGIN
        INSERT INTO auditorias (operacao, tabela) VALUES ('INSERT', 'usuarios');
        END$$
        DELIMITER ;

DELIMITER $$
        CREATE TRIGGER trg_auditorias_usuarios_update
            AFTER UPDATE ON usuarios
            FOR EACH ROW
        BEGIN
            INSERT INTO auditorias (operacao, tabela) VALUES ('UPDATE', 'usuarios');
            END$$
            DELIMITER ;

DELIMITER $$
            CREATE TRIGGER trg_auditorias_usuarios_delete
                AFTER DELETE ON usuarios
                FOR EACH ROW
            BEGIN
                INSERT INTO auditorias (operacao, tabela) VALUES ('DELETE', 'usuarios');
                END$$
                DELIMITER ;


DELIMITER $$
                CREATE TRIGGER trg_auditorias_enderecos_insert
                    AFTER INSERT ON enderecos
                    FOR EACH ROW
                BEGIN
                    INSERT INTO auditorias (operacao, tabela) VALUES ('INSERT', 'enderecos');
                    END$$
                    DELIMITER ;


DELIMITER $$
                    CREATE TRIGGER trg_auditorias_enderecos_update
                        AFTER UPDATE ON enderecos
                        FOR EACH ROW
                    BEGIN
                        INSERT INTO auditorias (operacao, tabela) VALUES ('UPDATE', 'enderecos');
                        END$$
                        DELIMITER ;


DELIMITER $$
                        CREATE TRIGGER trg_auditorias_enderecos_delete
                            AFTER DELETE ON enderecos
                            FOR EACH ROW
                        BEGIN
                            INSERT INTO auditorias (operacao, tabela) VALUES ('DELETE', 'enderecos');
                            END$$
                            DELIMITER ;





DELIMITER $$
        CREATE TRIGGER trg_auditorias_consultas_insert
        AFTER INSERT ON consultas
        FOR EACH ROW
        BEGIN
            INSERT INTO auditorias (operacao, tabela) VALUES ('INSERT', 'consultas');
            END$$
DELIMITER ;


DELIMITER $$
                                CREATE TRIGGER trg_auditorias_consultas_update
                                    AFTER UPDATE ON consultas
                                    FOR EACH ROW
                                BEGIN
                                    INSERT INTO auditorias (operacao, tabela) VALUES ('UPDATE', 'consultas');
                                    END$$
                                    DELIMITER ;


DELIMITER $$
                                    CREATE TRIGGER trg_auditorias_consultas_delete
                                        AFTER DELETE ON consultas
                                        FOR EACH ROW
                                    BEGIN
                                        INSERT INTO auditorias (operacao, tabela) VALUES ('DELETE', 'consultas');
                                        END$$
                                        DELIMITER ;



CREATE VIEW agenda_pacientes AS
    SELECT
        a.id AS id_consulta,
        u.nome AS paciente_nome,
        u.cpf AS paciente_cpf,
        a.id_paciente,
        a.data_hora,
        a.status
        FROM consultas a
        JOIN usuarios u ON a.id_paciente = u.id;


-- Consulta parametrizada na view:


    SELECT *
    FROM agenda_pacientes
    WHERE DATE(data_hora) = CURRENT_DATE();



    SELECT *
    FROM agenda_pacientes
    WHERE YEARWEEK(data_hora, 1) = YEARWEEK(CURRENT_DATE(), 1);



    SELECT *
    FROM agenda_pacientes
    WHERE YEAR(data_hora) = YEAR(CURRENT_DATE()) AND MONTH(data_hora) = MONTH(CURRENT_DATE());