-- Create the database (run this in PostgreSQL CLI or pgAdmin)
CREATE DATABASE simpleBD;

-- Connect to the database
\c simpleBD;

-- Create the Animal table if it doesn't exist
CREATE TABLE IF NOT EXISTS Animal (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(100) NOT NULL,
    age INT
);

-- Insert sample data
INSERT INTO Animal (name, species, age) VALUES
('Sparrow', 'Bird', 2),
('Parrot', 'Bird', 4),
('Dog', 'Mammal', 3),
('Snake', 'Reptile', 6);

-- Update an animal's age
UPDATE Animal SET age = 5 WHERE name = 'Parrot';

-- Delete an animal (example: removing 'Dog')
DELETE FROM Animal WHERE name = 'Dog';

-- Select all animals
SELECT * FROM Animal;
