import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerateHashes {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String[] passwords = {"User123!", "Coach123!", "Desk123!", "Facility123!", "Admin123!", "Observer123!"};
        String[] usernames = {"test.user", "test.coach", "test.desk", "test.facility.admin", "test.sport.director", "test.ministry.observer"};
        
        for (int i = 0; i < passwords.length; i++) {
            System.out.println(usernames[i] + ": " + encoder.encode(passwords[i]));
        }
    }
}
