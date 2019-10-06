package it.gabrieletondi.telldontaskkata.repository;

import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.useCase.UnknownProductException;

public interface ProductCatalog {
    Product getByName(String name) throws UnknownProductException;
}
