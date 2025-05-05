package com.deere.blog;

import com.deere.blog.config.BlogLiterals;
import com.deere.blog.entity.Role;
import com.deere.blog.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BlogApplicationTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private BlogApplication blogApplication;

	@Test
	void testContextLoads() {
		assertNotNull(blogApplication);
	}

	@Test
	void testModelMapperBean() {
		ModelMapper modelMapper = blogApplication.modelMapper();
		assertNotNull(modelMapper);
	}

	@Test
	void testRun_SuccessfulRoleSave() throws Exception {
		when(passwordEncoder.encode("ran1234")).thenReturn("encodedPassword");
		when(roleRepository.saveAll(anyList())).thenReturn(List.of(
				new Role(BlogLiterals.ADMIN_USER, "ADMIN"),
				new Role(BlogLiterals.NORMAL_USER, "USER")
		));

		blogApplication.run();

		verify(passwordEncoder).encode("ran1234");
		verify(roleRepository).saveAll(anyList());
	}

	@Test
	void testRun_RoleSaveThrowsException() throws Exception{
		when(passwordEncoder.encode("ran1234")).thenReturn("encodedPassword");
		when(roleRepository.saveAll(anyList())).thenThrow(new RuntimeException("No roles Found !!"));

		RuntimeException exception = assertThrows(RuntimeException.class, () -> blogApplication.run());
		assertEquals("No roles Found !!", exception.getMessage());

		verify(passwordEncoder).encode("ran1234");
		verify(roleRepository).saveAll(anyList());
	}

	@Test
	void testMain() {
		try (MockedStatic<SpringApplication> mockedSpringApplication = Mockito.mockStatic(SpringApplication.class)) {
			mockedSpringApplication.when(() -> SpringApplication.run(BlogApplication.class, new String[]{})).thenReturn(null);
			BlogApplication.main(new String[]{});
			mockedSpringApplication.verify(() -> SpringApplication.run(BlogApplication.class, new String[]{}));
		}
	}
}
