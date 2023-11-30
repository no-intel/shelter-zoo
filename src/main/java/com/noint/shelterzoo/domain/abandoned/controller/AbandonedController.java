package com.noint.shelterzoo.domain.abandoned.controller;

import com.github.pagehelper.PageInfo;
import com.noint.shelterzoo.domain.abandoned.dto.req.AbandonedListRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptReservationRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.req.AdoptUpdateRequestDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedDetailResponseDTO;
import com.noint.shelterzoo.domain.abandoned.dto.res.AbandonedListResponseDTO;
import com.noint.shelterzoo.domain.abandoned.service.AbandonedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class AbandonedController {
    private final AbandonedService abandonedService;
    private final HttpSession session;

    @GetMapping("/pets")
    public ResponseEntity<PageInfo<AbandonedListResponseDTO>> getAbandonedList(@ModelAttribute AbandonedListRequestDTO request) {
        long userSeq = (long) session.getAttribute("userSeq");
        return new ResponseEntity<>(abandonedService.getAbandonedList(userSeq, request), HttpStatus.OK);
    }

    @GetMapping("/pets/{petSeq}")
    public ResponseEntity<AbandonedDetailResponseDTO> abandonedPetDetail(@PathVariable long petSeq) {
        return new ResponseEntity<>(abandonedService.abandonedPetDetail(petSeq), HttpStatus.OK);
    }

    @PostMapping("/adopt/pets/{petSeq}")
    public ResponseEntity<Void> adoptPetForReservation(@PathVariable long petSeq, @RequestBody AdoptReservationRequestDTO request) {
        request.setPetSeq(petSeq);
        long userSeq = (long) session.getAttribute("userSeq");
        abandonedService.adoptPetForReservation(userSeq, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/adopt/pets/{petSeq}/{status}")
    public ResponseEntity<Void> adoptPetUpdate(@PathVariable long petSeq, @PathVariable String status) {
        long userSeq = (long) session.getAttribute("userSeq");
        AdoptUpdateRequestDTO request = new AdoptUpdateRequestDTO();
        request.setState(status);
        request.setPetSeq(petSeq);
        abandonedService.adoptPetUpdate(userSeq, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pets/{petSeq}/pin")
    public ResponseEntity<Void> pinUp(@PathVariable long petSeq) {
        long userSeq = (long) session.getAttribute("userSeq");
        abandonedService.pinUp(userSeq, petSeq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/pets/{petSeq}/pin")
    public ResponseEntity<Void> pinUpDel(@PathVariable long petSeq) {
        long userSeq = (long) session.getAttribute("userSeq");
        abandonedService.pinUpDel(userSeq, petSeq);
        return ResponseEntity.noContent().build();
    }
}
