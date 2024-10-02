package se331.rest.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String name;
    String telNo;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<EventParticipantDTO> eventHistories = new ArrayList<>();
}
