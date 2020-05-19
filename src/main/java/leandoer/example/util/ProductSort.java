package leandoer.example.util;

import leandoer.example.entity.Product;

import java.util.Comparator;

public enum ProductSort {
    EXPENSIVE {
        @Override
        public Comparator<Product> getSort() {
            return Comparator.comparing(Product::getPrice).reversed();
        }
    }, CHEAP {
        @Override
        public Comparator<Product> getSort() {
            return Comparator.comparing(Product::getPrice);
        }
    }, DEFAULT {
        @Override
        public Comparator<Product> getSort() {
            return Comparator.comparing(Product::getTotalScore).reversed();
        }
    };


    public abstract Comparator<Product> getSort();
}
