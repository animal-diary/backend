package animal.diary.entity.pet;

import animal.diary.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Long id;

    @NotNull
    @Getter
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Getter
    private Type type;

    @Enumerated(EnumType.STRING)
    @Getter
    @NotNull
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Neutered neutered;

    @NotNull
    private String species;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Health health;

    @ElementCollection(targetClass = Disease.class)
    @Enumerated(EnumType.STRING)
    @Getter
    @CollectionTable(name = "pet_diseases", joinColumns = @JoinColumn(name = "pet_id"))
    @Column(name = "disease")
    private List<Disease> diseases = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    public void setDiseases(List<Disease> diseaseList) {
        this.diseases = diseaseList;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
