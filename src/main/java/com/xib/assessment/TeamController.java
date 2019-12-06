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
import java.util.ArrayList;
import javax.validation.Valid;

@RestController
public class TeamController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private AgentRepository agentRepository;

	/* Retrieve all teams / Returns a list of teams in the database */
	@GetMapping("/teams")
	public List<Team> getAllTeams() {
		return teamRepository.findAll();
	}

	/* Get a team by ID/ Returns a detail view of the specified team */
	@GetMapping("/team/{id}")
	public ResponseEntity<Team> getTeamById(@PathVariable(value = "id") Long teamId) throws Exception {
		Team team = teamRepository.findById(teamId)
				.orElseThrow(() -> new Exception("Team not found for this id " + teamId));
		return ResponseEntity.ok().body(team);
	}

	/* Create a team/ Creates a new team with the specified details */
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

	/*
	 * An api endpoint that will return a list of all empty teams (i.e teams with no
	 * agents or managers)
	 */
	@GetMapping("/emptyteams")
	public List<Team> getEmptyTeams() {

		List<Team> emptyTeams = new ArrayList<Team>();
		
		List<Team> agentTeams = new ArrayList<Team>();
        
		List<Team> teams = teamRepository.findAll();
		List<Agent> agents = agentRepository.findAll();
		
		for(int i = 0; i< teams.size(); i++)
		{
			
			for(int c = 0; c< agents.size(); c++ )
			{
				
				if(teams.get(i).getId() == agents.get(c).getTeam().getId() )
				{
					agentTeams.add(agents.get(c).getTeam());
				}
			}
			
		}
		
		/*Post team count implementation*/
		
	
		for(int i = 0; i< teams.size(); i++)
		{
			int countTeams = 0;
			int indexAt = -1;
			for(int c = 0; c< agentTeams.size(); c++ )
			{
				
				if(teams.get(i).getId() == agentTeams.get(c).getId() )
				{
				
					countTeams++;
					indexAt = i;
					
				}else {
					indexAt = i;
					
				}
			}
	
			
			if(countTeams == 0)
			{
				emptyTeams.add(teams.get(indexAt));
				
			}
			
		}

		return emptyTeams;
	}

}
