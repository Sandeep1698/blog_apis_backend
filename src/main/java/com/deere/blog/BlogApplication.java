package com.deere.blog;

import com.deere.blog.config.BlogLiterals;
import com.deere.blog.entity.Role;
import com.deere.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("com.deere.blog.repository")
public class BlogApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired(required=true)
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("ran1234"));

		try {
			roleRepository.saveAll(List.of(new Role(BlogLiterals.ADMIN_USER,"ADMIN"),new Role(BlogLiterals.NORMAL_USER,"USER")));
		}
		catch (Exception e){
			throw new RuntimeException("No roles Found !!");
		}
	}
}
