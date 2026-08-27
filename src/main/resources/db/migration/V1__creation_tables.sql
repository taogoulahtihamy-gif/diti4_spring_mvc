CREATE TABLE type_produits (
   id BIGSERIAL PRIMARY KEY,
   libelle VARCHAR(255) NOT NULL
);

CREATE TABLE products (
  id BIGSERIAL PRIMARY KEY,
  libelle VARCHAR(255) NOT NULL,
  prix DOUBLE PRECISION NOT NULL,
  type_produit BIGINT NOT NULL,
  CONSTRAINT fk_product_type
      FOREIGN KEY (type_produit)
          REFERENCES type_produits(id)
);