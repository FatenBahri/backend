package tn.enis.testService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.enis.dao.AdminRepository;
import tn.enis.entities.Admin;
import tn.enis.service.AdminService;
import tn.enis.service.jwtUsingCreation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


	@ExtendWith(MockitoExtension.class)
	public class AdminServiceTest {
	    @InjectMocks
	    private AdminService adminService;
	    @Mock
	    private AdminRepository adminrep;
	    @Mock
	    private jwtUsingCreation jwtusing;
	    @Mock
	    private PasswordEncoder passwordEncoder;
	    @Mock
	    private AuthenticationManager authenticationManager;
	    @Mock
	    private Authentication successfulAuthentication; 

	    private  String VALID_USERNAME = "testAdmin";
	    private  String VALID_PASSWORD = "password123";
	    private  String INVALID_USERNAME = "wrongAdmin";
	    private  String GENERATED_TOKEN = "mocked.jwt.token";

	    @Test
	    void testVerifLogin_Success() {
	        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
	                .thenReturn(successfulAuthentication);
	        when(successfulAuthentication.getName()).thenReturn(VALID_USERNAME);
	        when(jwtusing.genererToken(VALID_USERNAME)).thenReturn(GENERATED_TOKEN);

	        String token = adminService.verifLogin(VALID_USERNAME, VALID_PASSWORD);
	        assertNotNull(token);
	        assertEquals(GENERATED_TOKEN, token);
	        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
	        verify(jwtusing, times(1)).genererToken(VALID_USERNAME);
	    }

	    @Test
	    void testVerifLogin_Failure_InvalidCredentials() {
	        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
	                .thenThrow(new BadCredentialsException("Bad credentials"));

	        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
	            adminService.verifLogin(INVALID_USERNAME, VALID_PASSWORD);
	        });
	        assertTrue(thrown.getMessage().contains("Invalid username or password"));

	        verify(jwtusing, never()).genererToken(anyString());
	    }
	    
	    @Test
	    void testCreateNewAdmin_Success() {
	        String rawPassword = "adminPassword";
	        String encodedPassword = "encoded$password";
	        
	        Admin adminToSave = Admin.builder().username(VALID_USERNAME).password(encodedPassword).build();
	        Admin savedAdmin = Admin.builder().id(1L).username(VALID_USERNAME).password(encodedPassword).build();

	        // Mock PasswordEncoder
	        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
	        
	        // Mock AdminRepository save()
	        when(adminrep.save(any(Admin.class))).thenReturn(savedAdmin);

	        // 2. Execute
	        Admin result = adminService.createNewAdmin(VALID_USERNAME, rawPassword);

	        // 3. Verify
	        assertNotNull(result);
	        assertEquals(1L, result.getId());
	        assertEquals(VALID_USERNAME, result.getUsername());
	        
	        // Ensure the password stored is the encoded one
	        assertEquals(encodedPassword, result.getPassword()); 
	        
	        // Verify the encoder and repository were called
	        verify(passwordEncoder, times(1)).encode(rawPassword);
	        verify(adminrep, times(1)).save(argThat(a -> a.getPassword().equals(encodedPassword)));
	    
	}
}
