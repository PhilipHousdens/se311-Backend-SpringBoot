package se331.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.rest.entity.ParticipantDTO;
import se331.rest.service.ParticipantService;
import se331.rest.util.LabMapper;

import java.util.List;

// Controller for class Participant
@RestController
@RequiredArgsConstructor
public class ParticipantController {
    final ParticipantService participantService;
    @GetMapping("/participants")
    ResponseEntity<?> getParticipants() {
        List<ParticipantDTO> participantDTOs = LabMapper.INSTANCE.getParticipantDTO(participantService.getAllParticipants());
        return ResponseEntity.ok(participantDTOs);
    }
}
