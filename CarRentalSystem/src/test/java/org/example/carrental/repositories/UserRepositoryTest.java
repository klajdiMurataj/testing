//Eden Pajp
package org.example.carrental.repositories;

import org.example.carrental.model.common.Credential;
import org.example.carrental.model.enums.Role;
import org.example.carrental.model.users.Account;
import org.example.carrental.storage.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository repo;

    @BeforeEach
    void setUp() {
        repo = new UserRepository();

        // CRITICAL: wipe persisted data so tests don't fail due to leftover users.dat
        repo.clear();
    }

    @AfterEach
    void tearDown() {
        // Extra safety: keep the repo clean for future test runs
        repo.clear();
    }

    @Test
    void add_thenFindByUsername_returnsUser() {
        Account user = makeUser("U1", "john", "john@doe.com");

        repo.add(user);

        Optional<Account> found = repo.findByUsername("john");
        assertTrue(found.isPresent());
        assertEquals("U1", found.get().getId());
        assertEquals("john@doe.com", found.get().getEmail());
    }

    @Test
    void existsByUsername_and_existsByEmail_workCorrectly() {
        Account user = makeUser("U2", "alice", "alice@doe.com");
        repo.add(user);

        // existing
        assertTrue(repo.existsByUsername("alice"));
        assertTrue(repo.existsByEmail("alice@doe.com"));

        // non-existing
        assertFalse(repo.existsByUsername("notThere"));
        assertFalse(repo.existsByEmail("notThere@doe.com"));
    }

    @Test
    void remove_existingId_removesUser() {
        Account user = makeUser("U3", "bob", "bob@doe.com");
        repo.add(user);

        assertTrue(repo.remove("U3"));
        assertTrue(repo.findById("U3").isEmpty());
        assertFalse(repo.existsByUsername("bob"));
    }

    // helper: Account is abstract but has no abstract methods -> anonymous subclass is fine
    private Account makeUser(String id, String username, String email) {
        return new Account(
                id,
                "Test",
                "User",
                email,
                "123",
                new Credential(username, new byte[]{1, 2, 3}, new byte[]{9, 8, 7}),
                Role.END_USER
        ) {};
    }
}

