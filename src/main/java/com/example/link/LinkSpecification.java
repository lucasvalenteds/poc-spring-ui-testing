package com.example.link;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public final class LinkSpecification {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkSpecification.class);

    private LinkSpecification() {
    }

    public static Specification<Link> titleLike(String string) {
        LOGGER.info("Selecting title like (string={})", string);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), "%" + string + "%");
    }

    public static Specification<Link> descriptionLike(String string) {
        LOGGER.info("Selecting description like (string={})", string);
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("description"), "%" + string + "%");
    }
}
