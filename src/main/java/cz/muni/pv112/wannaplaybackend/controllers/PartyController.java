package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.dto.CreatePartyDTO;
import cz.muni.pv112.wannaplaybackend.dto.PartyDTO;
import cz.muni.pv112.wannaplaybackend.security.Principal;
import cz.muni.pv112.wannaplaybackend.service.PartyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cz.muni.pv112.wannaplaybackend.security.SecurityInterceptor.PRINCIPAL_ATTR;

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
	public long create(@RequestBody CreatePartyDTO createPartyDTO, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
		log.debug("Create party called: {}", createPartyDTO);

		long id = partyService.createParty(createPartyDTO, principal);

		log.info("Created party with id: {}", id);

		return id;
	}

	@PostMapping("{id}/join")
	public void joinParty(@PathVariable("id") Long id, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
		log.debug("Join party called on id: {}", id);

		partyService.joinParty(id, principal.getId());
	}

	@PostMapping("{id}/leave")
	public void leaveParty(@PathVariable("id") Long id, @RequestAttribute(PRINCIPAL_ATTR) Principal principal) {
		log.debug("Leave party called with id: {}", id);

		partyService.leaveParty(id, principal.getId());
	}
}
