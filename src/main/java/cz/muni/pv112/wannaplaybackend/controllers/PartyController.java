package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.models.Party;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@RestController
@Slf4j
@RequestMapping("party")
public class PartyController {

	@GetMapping("")
	public List<Party> allGroups() {
		return new ArrayList<>();
	}
}
