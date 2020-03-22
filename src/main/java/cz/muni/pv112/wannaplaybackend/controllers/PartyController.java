package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.dto.CreatePartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.service.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@RestController
@Slf4j
@RequestMapping("party")
public class PartyController {

	private final PartyService partyService;

	@Autowired
	public PartyController(PartyService partyService) {
		this.partyService = partyService;
	}

	@GetMapping("{id}")
	public PartyDTO findById(@PathVariable Long id) {
		log.debug("FindById party called: {}", id);

		return partyService.findById(id);
	}

	@PutMapping("")
	public long create(@RequestBody CreatePartyDTO createPartyDTO) {
		log.debug("Create party called: {}", createPartyDTO);

		long id = partyService.createParty(createPartyDTO);

		log.info("Created party with id: {}", id);

		return id;
	}
}
