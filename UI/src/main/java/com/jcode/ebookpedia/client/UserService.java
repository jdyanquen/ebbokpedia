package com.jcode.ebookpedia.client;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.jcode.ebookpedia.client.dto.UserDto;

public interface UserService extends RestService {
	
    @GET
    public void findAll(@HeaderParam("Authorization") String token, MethodCallback<List<UserDto>> callback);

}
