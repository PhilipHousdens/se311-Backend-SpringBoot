package se331.rest.entity;

import jakarta.persistence.*;
import lombok.*;

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
    String category;
    String date;
    String description;
    String location;
    String time;
    String title;
    Boolean petAllowed;

    @ManyToOne
    private Organizer organizer;

    @ManyToMany(mappedBy = "eventHistory")
    List<Participant> participants;
}
