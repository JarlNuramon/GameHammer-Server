package de.eggheads.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Files;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import de.eggheads.test.service.HuffmanCodingService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HuffmanTest {

	@Autowired
	HuffmanCodingService codingService;
	private static final String CODEABLE_STRING = "ipsum.txt";
	private Map<Character, ArrayList<Boolean>> m;

	@Before
	public void setUp() {
		m = codingService.createTree(CODEABLE_STRING);
	}

	@Test
	public void huffman_creates_tree() {
		assertTrue(m.size() > 0);
	}

	@Test
	public void huffman_encode_size() {
		String test = Files.contentOf(new File("ipsum.txt"), StandardCharsets.US_ASCII);
		log.info("Size uncompressed={}", test.getBytes(StandardCharsets.US_ASCII).length);
		Boolean[] encoded = codingService.encode(m, test);
		String s = "";
		char c = 0;
		for (int i = 0; i < encoded.length; i++) {
			if (i % 8 == 0) {
				s += c;
				c = 0;
			}
			if (encoded[i])
				c |= 1 << i;
		}
		if (encoded.length % 8 != 0)
			s += 's';
		log.info("Size compressed={}", s.getBytes(StandardCharsets.US_ASCII).length);
		assertTrue(true);
	}

}
