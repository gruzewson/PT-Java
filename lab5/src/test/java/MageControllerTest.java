import org.example.Mage;
import org.example.MageController;
import org.example.MageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MageControllerTest {

    MageRepository repository;
    MageController controller;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(MageRepository.class);
        controller = new MageController(repository);
    }

    @Test
    @DisplayName("Test to delete existing mage")
    void DeletingExistingMage() {
        when(repository.find("kempix")).thenReturn(Optional.of(new Mage("kempix", 12)));
        assertEquals("done", controller.delete("kempix"));
    }

    @Test
    @DisplayName("Test to try deleting non-existing mage")
    void DeletingNonExistingMage() {
        doThrow(new IllegalArgumentException("not found")).when(repository).delete("gandalf");
        String result = controller.delete("gandalf");

        assertEquals("not found", result);
    }

    @Test
    @DisplayName("Test to find existing mage")
    void FindingExistingMage() {
        when(repository.find("blaku")).thenReturn(Optional.of(new Mage("blaku", 13)));
        String result = controller.find("blaku");
        assertEquals("done", result);
    }

    @Test
    @DisplayName("Test to try finding non-existing mage")
    void FindingNonExistingMage() {
        when(repository.find("gandalf")).thenReturn(Optional.empty());
        String result = controller.find("gandalf");

        assertEquals("not found", result);
    }

    @Test
    @DisplayName("Test to save new mage")
    void SavingNewMage() {
        when(repository.find("gandalf")).thenReturn(Optional.empty());
        Mage mage = new Mage("gandalf", 16);
        String result = controller.save(mage);

        assertEquals("done", result);
    }

    @Test
    @DisplayName("Test to try saving an existing mage")
    void SavingExistingMage() {
        Mage mage = new Mage("kempix", 11);
        doThrow(new IllegalArgumentException("bad request")).when(repository).save(mage);
        String result = controller.save(mage);

        assertEquals("bad request", result);
    }
}
