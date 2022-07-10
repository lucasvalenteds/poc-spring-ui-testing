package com.example.search;

import com.example.link.LinkRepository;
import com.example.link.LinkSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    private final LinkRepository linkRepository;

    public SearchController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping
    public String renderSearchPage(@ModelAttribute Search search, Model model) {
        model.addAttribute("firstSearch", true);
        model.addAttribute("links", List.of());

        LOGGER.info("Rendering index page (model={})", model);
        return "index";
    }

    @PostMapping
    public String submitSearchQuery(@ModelAttribute Search search,
                                    Model model) {
        final var links = linkRepository.findAll(
                Specification.where(Specification.anyOf(List.of(
                        LinkSpecification.titleLike(search.getText()),
                        LinkSpecification.descriptionLike(search.getText())))));

        model.addAttribute("firstSearch", false);
        model.addAttribute("links", links);

        LOGGER.info("Rendering search page (model={})", model);
        return "index";
    }
}
