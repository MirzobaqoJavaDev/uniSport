package uz.uniSport.uni_sport.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uniSport.uni_sport.domain.auth.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Tizim foydalanuvchilarini (User) boshqarish uchun Repository.
 * JpaRepository<User, Long> — DB ichki id (Long) asosida ishlaydi.
 * findByUuid — frontend UUID orqali qidirish uchun.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Foydalanuvchini UUID bo'yicha qidirish (API so'rovlarida ishlatiladi).
     * @param uuid foydalanuvchi tashqi identifikatori
     * @return topilgan foydalanuvchi
     */
    Optional<User> findByUuid(UUID uuid);

    /**
     * Foydalanuvchini email bo'yicha qidirish (login qilish uchun).
     * @param email foydalanuvchi elektron pochtasi
     * @return topilgan foydalanuvchi
     */
    Optional<User> findByEmail(String email);

    /**
     * Email tizimda mavjudligini tekshirish (ro'yxatdan o'tishda).
     * @param email foydalanuvchi elektron pochtasi
     * @return mavjud bo'lsa true, aks holda false
     */
    boolean existsByEmail(String email);
}
