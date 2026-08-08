package uz.uniSport.uni_sport.domain.pe;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.UUID;

/**
 * Jismoniy tarbiya (JT) darsini (PE Class) ifodalovchi Entity klassi.
 * Masalan: 'Umumiy jismoniy tayyorgarlik', 'Suzish asoslari'.
 */
@Getter
@Setter
@Entity
@Table(name = "pe_classes")
public class PEClass extends BaseEntity {

    /**
     * JT darsining yagona identifikatori (ID).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Darsni o'tadigan o'qituvchi (User - PE_FACULTY rolidagi foydalanuvchi).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    /**
     * JT darsining nomi.
     */
    @Column(nullable = false, length = 255)
    private String name;
}
