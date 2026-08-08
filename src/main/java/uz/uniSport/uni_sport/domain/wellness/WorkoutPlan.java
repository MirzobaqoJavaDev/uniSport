package uz.uniSport.uni_sport.domain.wellness;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.uniSport.uni_sport.domain.auth.User;
import uz.uniSport.uni_sport.domain.common.BaseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Foydalanuvchi uchun mo'ljallangan mashg'ulot rejasini (Workout Plan) ifodalovchi Entity.
 */
@Getter
@Setter
@Entity
@Table(name = "workout_plans")
public class WorkoutPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Masalan: 'BEGINNER', 'INTERMEDIATE', 'ADVANCED'
     */
    @Column(name = "difficulty_level", length = 50)
    private String difficultyLevel;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "workout_plan_exercises",
            joinColumns = @JoinColumn(name = "workout_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private Set<Exercise> exercises = new HashSet<>();
}
