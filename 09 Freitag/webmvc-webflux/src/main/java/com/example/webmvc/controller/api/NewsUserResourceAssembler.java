package com.example.webmvc.controller.api;

import com.example.webmvc.model.NewsUser;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class NewsUserResourceAssembler
        extends ResourceAssemblerSupport<NewsUser, NewsUserResource> {
    public NewsUserResourceAssembler() {
        super(UserApiController.class, NewsUserResource.class);
    }

    @Override
    protected NewsUserResource instantiateResource(NewsUser u) {
        return new NewsUserResource(
                u.getFirstname(),
                u.getLastname(),
                u.getBirthday(),
                u.getUsername());
    }

    @Override
    public NewsUserResource toResource(NewsUser user) {
        return createResourceWithId(user.getUsername(), user);
    }
}
