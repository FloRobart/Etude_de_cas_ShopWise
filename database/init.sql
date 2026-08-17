CREATE TABLE clients (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE commercants (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nom VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE rendezvous (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    client_id INTEGER NOT NULL,
    commercant_id INTEGER NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    service_type TEXT,
    status ENUM('scheduled', 'completed', 'canceled') DEFAULT 'scheduled',
    fidelity_points INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (commercant_id) REFERENCES commercants(id)
);
