package com.xib.assessment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import javax.validation.Valid;

@RestController
public class TeamController {

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private AgentRepository agentRepository;

	/* Retrieve all teams */
	@GetMapping("/teams")
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	/* Get a team by ID */
	@GetMapping("/team/{id}")
	public ResponseEntity<Team> getTeamById(@PathVariable(value = "id") Long teamId) throws Exception {
		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new Exception("Team not found for this id " + teamId));
		return ResponseEntity.ok().body(team);
	}

	/* Create a team */
	@PostMapping("/team")
	public Team createTeam(@Valid @RequestBody Team team) {
		return teamRepository.save(team);
	}

	/* Assigns an agent to the specified team */
	@PutMapping("/team/{id}/agent")
	public ResponseEntity<Agent> assignAgent(@PathVariable(value = "id") Long teamID,
			@Valid @RequestBody Agent agentDetails) throws Exception {
		Team team = teamRepository.findById(teamID).orElseThrow(() -> new Exception("ID not found :: " + teamID));

		agentDetails.setTeam(team);
		final Agent updatedAgent = agentRepository.save(agentDetails);
		return ResponseEntity.ok(updatedAgent);
	}



}
