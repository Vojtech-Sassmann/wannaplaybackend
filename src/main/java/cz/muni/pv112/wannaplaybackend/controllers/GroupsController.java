package cz.muni.pv112.wannaplaybackend.controllers;

import cz.muni.pv112.wannaplaybackend.models.Group;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vojtech Sassmann <vojtech.sassmann@gmail.com>
 */
@RestController
@RequestMapping("groups")
public class GroupsController {
	@RequestMapping("")
	public List<Group> allGroups() {
		return new ArrayList<>();
	}
}
