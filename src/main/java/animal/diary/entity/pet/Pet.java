package animal.diary.entity.pet;

import animal.diary.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Neutered neutered;

    private String species;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Health health;

    @ElementCollection(targetClass = Disease.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "pet_diseases", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "disease")
    private List<Disease> diseases;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
