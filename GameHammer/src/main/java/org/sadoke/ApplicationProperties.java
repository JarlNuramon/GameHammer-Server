package org.sadoke;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
@Validated
public class ApplicationProperties {

	@NotBlank
	private String salt;
	@NotBlank
	private String secret;

}
