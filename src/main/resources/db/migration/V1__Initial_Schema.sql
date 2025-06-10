-- Initial database schema for Product Management Application

-- Product table
CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    handle VARCHAR(255),
    body_html TEXT,
    published_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    vendor VARCHAR(255),
    product_type VARCHAR(255)
);

-- Product tags table
CREATE TABLE IF NOT EXISTS product_tags (
    product_id BIGINT REFERENCES product(id),
    tag VARCHAR(255),
    PRIMARY KEY (product_id, tag)
);

-- Option table
CREATE TABLE IF NOT EXISTS option (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    position INT,
    product_id BIGINT REFERENCES product(id)
);

-- Option values table
CREATE TABLE IF NOT EXISTS option_values (
    option_id BIGINT REFERENCES option(id),
    value VARCHAR(255),
    PRIMARY KEY (option_id, value)
);

-- Featured Image table
CREATE TABLE IF NOT EXISTS featured_image (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT,
    position INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    alt VARCHAR(255),
    width INT,
    height INT,
    src VARCHAR(255)
);

-- Featured Image variant IDs table
CREATE TABLE IF NOT EXISTS featured_image_variant_ids (
    featured_image_id BIGINT REFERENCES featured_image(id),
    variant_id BIGINT,
    PRIMARY KEY (featured_image_id, variant_id)
);

-- Variant table
CREATE TABLE IF NOT EXISTS variant (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    option1 VARCHAR(255),
    option2 VARCHAR(255),
    option3 VARCHAR(255),
    sku VARCHAR(255),
    requires_shipping BOOLEAN,
    taxable BOOLEAN,
    featured_image_id BIGINT REFERENCES featured_image(id),
    available BOOLEAN,
    price VARCHAR(255),
    grams INT,
    compare_at_price VARCHAR(255),
    position INT,
    product_id BIGINT REFERENCES product(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Image table
CREATE TABLE IF NOT EXISTS image (
    id BIGSERIAL PRIMARY KEY,
    created_at TIMESTAMP,
    position INT,
    updated_at TIMESTAMP,
    product_id BIGINT REFERENCES product(id),
    src VARCHAR(255),
    width INT,
    height INT
);

-- Image variant IDs table
CREATE TABLE IF NOT EXISTS image_variant_ids (
    image_id BIGINT REFERENCES image(id),
    variant_id BIGINT,
    PRIMARY KEY (image_id, variant_id)
);