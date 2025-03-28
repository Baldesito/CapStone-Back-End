package data

-- Inserisci un utente
INSERT INTO utenti (id, username, password, email)
VALUES (1, 'testuser', 'password', 'test@example.com');

-- Inserisci un volo
INSERT INTO voli (id, compagnia, origine, destinazione, data_partenza, data_arrivo, prezzo)
VALUES (1, 'CompagniaTest', 'Origine', 'Destinazione', '2025-03-04 10:00:00', '2025-03-04 12:00:00', 100.00);e', 'Destinazione', '2025-03-04T10:00:00', '2025-03-04T12:00:00', 100.00);