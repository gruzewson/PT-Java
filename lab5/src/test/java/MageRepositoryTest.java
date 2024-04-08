import org.example.Mage;
import org.example.MageRepository;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MageRepositoryTest {
    MageRepository repository;

    @BeforeEach
    void setup() {
        Mage mage1 = new Mage("kempix", 12);
        Mage mage2 = new Mage("blaku", 13);
        Mage mage3 = new Mage("dagguh", 14);
        Mage mage4 = new Mage("kwidzix", 15);

        Collection<Mage> collection = new ArrayList<>();
        collection.add(mage1);
        collection.add(mage2);
        collection.add(mage3);
        collection.add(mage4);

        repository = new MageRepository(collection);
    }

    @Test
    @DisplayName("Test to delete existing mage")
    void DeletingExistingMage() {
        assertTrue(repository.find("kempix").isPresent());
        repository.delete("kempix");

        assertFalse(repository.find("kempix").isPresent());
    }

    @Test
    @DisplayName("Test to try deleting non-existing mage")
    void DeletingNonExistingMage()
    {
        try{
            repository.delete("gandalf");
        }
        catch(IllegalArgumentException e)
        {
            assertEquals("not found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test to find existing mage")
    void FindingExistingMage() {
        assertTrue(repository.find("blaku").isPresent());

    }

    @Test
    @DisplayName("Test to try finding non-existing mage")
    void FindingNonExistingMage()
    {
        try{
            repository.find("gandalf");
        }
        catch(IllegalArgumentException e)
        {
            assertEquals("not found", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test to save new mage")
    void SavingNewMage()
    {
        Mage mage = new Mage("gandalf", 16);
        repository.save(mage);
        assertTrue(repository.find("gandalf").isPresent());
    }

    @Test
    @DisplayName("Test to try saving an existing mage")
    void SavingExistingMage()
    {
        try{
            Mage mage = new Mage("kwidzix", 15);
            repository.save(mage);
        }
        catch(IllegalArgumentException e)
        {
            assertEquals("bad request", e.getMessage());
        }
    }
}
