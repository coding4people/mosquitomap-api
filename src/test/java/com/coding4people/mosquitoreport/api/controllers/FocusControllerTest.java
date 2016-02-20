package com.coding4people.mosquitoreport.api.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.mockito.Mock;

import com.coding4people.mosquitoreport.api.WithServer;
import com.coding4people.mosquitoreport.api.controllers.FocusController.FocusPostInput;
import com.coding4people.mosquitoreport.api.models.Focus;
import com.coding4people.mosquitoreport.api.repositories.FocusRepository;

public class FocusControllerTest extends WithServer {
    @Mock
    private FocusRepository focusRepository;
    
    @Override
    protected ResourceConfig configure() {
        return super.configure().register(FocusController.class);
    }

    @Test
    public void testGetIt() {
        FocusPostInput data = new FocusPostInput();
        data.setLatitude("01234");
        data.setLongitude("56789");
        
        Response response = target().path("/focus").request()
                .post(Entity.json(data));
        
        assertEquals(200, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getHeaderString("Content-type"));
        
        verify(focusRepository).save(any(Focus.class));
        Focus focus = response.readEntity(Focus.class);
        
        assertNotNull(focus.getGuid());
        assertEquals("01234", focus.getLatitude());
        assertEquals("56789", focus.getLongitude());
        assertNotNull(focus.getCreateAt());
    }
}
