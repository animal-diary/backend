package animal.diary.entity;

import animal.diary.entity.pet.Pet;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String nickname;

    @Getter  
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();
}
