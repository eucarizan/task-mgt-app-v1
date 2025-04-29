package dev.nj.tms.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AppUserTests {

    @Test
    public void createAppUserSetsEmailAndPasswordTest() {
        AppUser appUser = new AppUser("user1@mail.com", "user1pasS!");
        assertEquals("user1@mail.com", appUser.getEmail());
        assertEquals("user1pasS!", appUser.getPassword());
    }

    @Test
    public void newAppUserStoresEmailAndPasswordCorrectlyTest() {
        AppUser appUser = new AppUser("user2@mail.com", "user2pasS!");
        assertEquals("user2@mail.com", appUser.getEmail());
        assertEquals("user2pasS!", appUser.getPassword());
    }

    @Test
    public void emailCannotBeNullTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new AppUser(null, "user1pasS!"));
    }

    @Test
    public void emailInvalidFormatTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new AppUser("user1@mail", "user1Pass!"));
    }

    @Test
    public void passwordCannotBeNullTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new AppUser("user1@mail.com", null));
    }

    @Test
    public void passwordMinimumLengthTest() {
        assertThrows(IllegalArgumentException.class, () ->
                new AppUser("user1@mail.com", "pass"));
    }
}
