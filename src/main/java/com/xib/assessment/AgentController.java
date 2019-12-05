package com.xib.assessment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
public class AgentController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private AgentRepository agentRepository;

	/* Retrieve all agents */
	@GetMapping("/agents")
	public List<Agent> getAllAgents() {
		return agentRepository.findAll();
	}

	/* Get a Agent by ID */
	@GetMapping("/agent/{id}")
	public ResponseEntity<Agent> getAgentById(@PathVariable(value = "id") Long agentID) throws Exception {
		Agent agent = agentRepository.findById(agentID)
				.orElseThrow(() -> new Exception("Agent not found for this id " + agentID));
		return ResponseEntity.ok().body(agent);
	}

	/* Create an Agent */
	@PostMapping("/agent")
	public Agent createAgent(@Valid @RequestBody Agent agent) {
		return agentRepository.save(agent);
	}

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


}
