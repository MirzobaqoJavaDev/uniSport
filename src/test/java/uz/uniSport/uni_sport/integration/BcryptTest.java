package uz.uniSport.uni_sport.integration;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class BcryptTest {
    @Test
    void printHash() {
        System.out.println("HASH: " + new BCryptPasswordEncoder().encode("Password123!"));
    }
}
