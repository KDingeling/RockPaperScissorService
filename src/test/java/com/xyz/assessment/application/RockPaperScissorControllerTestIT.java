package com.xyz.assessment.application;

import java.net.URL;

import com.xyz.assessment.domain.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.xyz.assessment.domain.Turn;
import com.xyz.assessment.objectset.ObjectSetHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=8181" })
public class RockPaperScissorControllerTestIT {

	private Gson gson = new Gson();

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}
	
	@Test
	public void doIntegrationTest() {
		testCreateGame();
		testCreateTurn();
		testQueryStatus();
	}

	public void testCreateGame() {
		
		// Test V1
		
		ResponseEntity<String> responseV1 = template.getForEntity(base + "v1/games?rounds=3", String.class);
		Game responseGameV1 = gson.fromJson(responseV1.getBody(), Game.class);
		Game expectedGameV1 = new Game(0, 3, ObjectSetHolder.objectSetA);
		Assert.assertEquals(responseGameV1, expectedGameV1);
		
		// Test v2
		
		ResponseEntity<String> responseV2 = template.getForEntity(base + "v2/games?rounds=3", String.class);
		Game responseGameV2 = gson.fromJson(responseV2.getBody(), Game.class);
		Game expectedGameV2 = new Game(1, 3, ObjectSetHolder.objectSetB);
		Assert.assertEquals(responseGameV2, expectedGameV2);
	}
	
	public void testCreateTurn() {
		
		// Test V1
		
		template.getForEntity(base + "v1/games/0?object=rock", String.class);
		template.getForEntity(base + "v1/games/0?object=paper", String.class);
		ResponseEntity<String> responseV1_3 = template.getForEntity(base + "v1/games/0?object=rock", String.class);
		
		Turn responseTurnV1 = gson.fromJson(responseV1_3.getBody(), Turn.class);
		Turn expectedTurnV1 = new Turn(ObjectSetHolder.ROCK, responseTurnV1.getBotObject(), responseTurnV1.getResult());
		Assert.assertEquals(expectedTurnV1, responseTurnV1);
		
		// Test V2
		
		template.getForEntity(base + "v2/games/1?object=rock", String.class);
		template.getForEntity(base + "v2/games/1?object=well", String.class);
		ResponseEntity<String> responseV2 = template.getForEntity(base + "v2/games/1?object=scissor", String.class);
		
		Turn responseTurnV2 = gson.fromJson(responseV2.getBody(), Turn.class);
		Turn expectedTurnV2 = new Turn(ObjectSetHolder.SCISSOR, responseTurnV2.getBotObject(), responseTurnV2.getResult());
		Assert.assertEquals(expectedTurnV2, responseTurnV2);

	}
	

	public void testQueryStatus() {
		
		// Test V1
		
		ResponseEntity<String> responseV1 = template.getForEntity(base + "v1/games/0/status", String.class);
		Game responseGameV1 = gson.fromJson(responseV1.getBody(), Game.class);
		Game expectedGameV1 = new Game(0, 3, ObjectSetHolder.objectSetA);
		expectedGameV1.setCurrentTurn(3);
		Assert.assertEquals(responseGameV1.getCurrentTurn(), expectedGameV1.getCurrentTurn());
		Assert.assertTrue(responseGameV1.equalsWithoutRandomFields(expectedGameV1));
		
		// Test V2
		
		ResponseEntity<String> responseV2 = template.getForEntity(base + "v2/games/1/status", String.class);
		Game responseGameV2 = gson.fromJson(responseV2.getBody(), Game.class);
		Game expectedGameV2 = new Game(1, 3, ObjectSetHolder.objectSetB);
		expectedGameV2.setCurrentTurn(3);
		Assert.assertTrue(responseGameV2.equalsWithoutRandomFields(expectedGameV2));
	}
}
