package com.coding4people.mosquitoreport.api.controllers;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.coding4people.mosquitoreport.api.buckets.PictureBucket;
import com.coding4people.mosquitoreport.api.models.Picture;
import com.coding4people.mosquitoreport.api.repositories.PictureRepository;

@Path("/picture")
public class PictureController {
    @Inject
    PictureRepository pictureRepository;
    @Inject
    PictureBucket pictureBucket;

    @POST
    @Consumes({ MediaType.MULTIPART_FORM_DATA })
    @Produces("application/json;charset=UTF-8")
    @Path("/focus/{focusguid}")
    public Object post(@PathParam("focusguid") String focusguid, @FormDataParam("file") InputStream fileInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDisposition) {
        Picture picture = new Picture();
        picture.setGuid(UUID.randomUUID().toString());
        picture.setFilename(fileDisposition.getFileName());
        picture.setFocusGuid(focusguid);
        picture.setCreateAt(Long.toString(new Date().getTime()));
        
        pictureBucket.put(picture.getPath(), fileInputStream, fileDisposition.getSize());
        pictureRepository.save(picture);

        return picture;
    }
}