package se331.rest.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_participants")  // Specify the table name
public class EventParticipantDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id") // Foreign key to participant
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "event_id") // Foreign key to event
    private Event event;

    @Column(name = "participant_name")
    private String participantName;

    @Column(name = "event_title")
    private String eventTitle;
}
