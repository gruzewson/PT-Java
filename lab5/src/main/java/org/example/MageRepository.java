package org.example;

import java.util.Collection;
import java.util.Optional;

public class MageRepository {
    private Collection<Mage> collection;

    public MageRepository(Collection<Mage> collection) {
        this.collection = collection;
    }

    public Optional<Mage> find(String name) {
        for (Mage mage : collection) {
            if (mage.getName().equals(name)) {
                return Optional.of(mage);
            }
        }
        return Optional.empty();
    }

    public void delete(String name) {
        Optional<Mage> mageOptional = find(name);
        if (mageOptional.isPresent()) {
            collection.remove(mageOptional.get());
        } else {
            throw new IllegalArgumentException("not found");
        }
    }

    public void save(Mage mage) {
        if (find(mage.getName()).isPresent()) {
            throw new IllegalArgumentException("bad request");
        }
        collection.add(mage);
    }
}
