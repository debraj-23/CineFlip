package com.moviebookingapp.Service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


import com.moviebookingapp.Entity.LoginTable;
import com.moviebookingapp.Repo.LoginRepo;
class LoginServiceMockTest {
	@Test
	public void testForSaveUser() {
		LoginRepo RepositoryMock=mock(LoginRepo.class);
		LoginTable loginCredentials=new LoginTable();
		loginCredentials.setLoginId("Vinoragul");
		loginCredentials.setPassword("Vino@1234");
		when(RepositoryMock.save(any())).thenReturn(loginCredentials);
		LoginService LoginService=new LoginService(RepositoryMock);
		LoginTable returnedLoginTable=LoginService.save(loginCredentials);
		assertEquals(loginCredentials.getLoginId(),returnedLoginTable.getLoginId());
	}
	 @Test
	    public void testForFetchAllUserIds() {
	    	LoginRepo RepositoryMock=mock(LoginRepo.class);
	    	LoginTable loginCredentials=new LoginTable();
	    	loginCredentials.setLoginId("Vinoragul");
	    	loginCredentials.setPassword("Vino@1234");
	    	List<LoginTable> userList=new ArrayList<>();
	    	userList.add(loginCredentials);
	    	when(RepositoryMock.findAll()).thenReturn(userList);
	        LoginService loginService=new LoginService(RepositoryMock);
	    	List<LoginTable> returnedAdminList=loginService.getAllIds();
	    	assertEquals(1,returnedAdminList.size());
	    }

}
