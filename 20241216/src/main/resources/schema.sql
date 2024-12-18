CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- schema.sql
CREATE TABLE IF NOT EXISTS CUSTOMER (
                                        id UUID NOT NULL DEFAULT uuid_generate_v4(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
    );