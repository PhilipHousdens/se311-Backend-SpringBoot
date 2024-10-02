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
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String title;
    String description;
    String location;
    String date;
    String time;
    boolean petAllowed;
    String category;

    @ManyToOne
    private Organizer organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<EventParticipantDTO> participants = new ArrayList<>();
}
