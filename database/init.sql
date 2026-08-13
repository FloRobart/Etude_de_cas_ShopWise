CREATE TABLE clients (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
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
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (commercant_id) REFERENCES commercants(id)
);

CREATE TABLE fidelisation (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    client_id INTEGER NOT NULL,
    commercant_id INTEGER NOT NULL,
    points INTEGER DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (commercant_id) REFERENCES commercants(id)
);

CREATE TABLE transactions (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    client_id INTEGER NOT NULL,
    commercant_id INTEGER NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES clients(id),
    FOREIGN KEY (commercant_id) REFERENCES commercants(id)
);

CREATE TABLE products (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    commercant_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (commercant_id) REFERENCES commercants(id)
);
