package de.eggheads.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.eggheads.test.strategies.ValidDeltaStrategies;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class DemoApplicationTests {

	@Autowired
	ValidDeltaStrategies strategy;

	@Before
	public void setUp() {

	}

	@Test
	public void huffman_creates_tree() {

	}

}
