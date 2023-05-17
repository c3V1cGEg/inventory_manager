package com.inventorer.controller;

import com.inventorer.InventoryFormTypes;
import com.inventorer.TagType;
import com.inventorer.model.Inventory;
import com.inventorer.model.InventoryListRepository;
import com.inventorer.model.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
  public static final int PAGE_SIZE = 5;
  InventoryFormTypes inventoryFormTypes;
  InventoryRepository inventoryRepository;
  InventoryListRepository inventoryListRepository;
  UriComponents listUrl = UriComponentsBuilder.fromUriString("/inventory/list").build();
  UriComponents listSearchUrl = UriComponentsBuilder.fromUriString("/inventory/search?phrase={phrase}").build();
  Map<String, String> oppositeDirection = Map.of("ASC", "DESC", "DESC", "ASC");

  public InventoryController(InventoryFormTypes inventoryFormTypes, InventoryRepository inventoryRepository, InventoryListRepository inventoryListRepository) {
    this.inventoryFormTypes = inventoryFormTypes;
    this.inventoryRepository = inventoryRepository;
    this.inventoryListRepository = inventoryListRepository;
  }

  @ModelAttribute(name = "formTypes")
  public List<String> formTypes() {
    return inventoryFormTypes.getFormTypesList();
  }

  @ModelAttribute(name = "tagTypes")
  public List<String> tagTypes() {
    return Arrays.stream(TagType.values()).map(TagType::name).toList();
  }

  @ModelAttribute(name = "opDir")
  Map<String, String> opDir() {
    return oppositeDirection;
  }

  @GetMapping("/add")
  public ModelAndView add(@RequestParam(required = false, defaultValue = "general") String type) {
    InventoryDTO inventory = new InventoryDTO();
    inventory.setType(type);
    return new ModelAndView("index", Map.of("inventory", inventory, "pageId", "add"));
  }

  @GetMapping("/edit")
  public ModelAndView edit(@RequestParam Long id) {
    Optional<Inventory> inventory = inventoryRepository.findById(id);
    return new ModelAndView("index", Map.of("inventory", inventory.get(), "pageId", "add"));
  }

  @GetMapping("/search")
  public ModelAndView search(@RequestParam(required = false, defaultValue = "") String phrase,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "short_description") String sort,
                             @RequestParam(defaultValue = "ASC") Direction dir) {
    //https://stackoverflow.com/questions/59422883/spring-boot-custom-validation-in-request-params
    Page<Inventory> all = inventoryListRepository.searchInventory(phrase, PageRequest.of(page, PAGE_SIZE, Sort.by(dir, sort)));
    String url = listSearchUrl.expand(Map.of("phrase", phrase)).toUriString();
    return new ModelAndView("index", Map.of("inventoryList", all, "pageId", "list", "phrase", phrase, "pageUrl", url));
  }

  @GetMapping("/list")
  public ModelAndView list(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "short_description") String sort,
                           @RequestParam(defaultValue = "ASC") Direction dir) {
    Page<Inventory> all = inventoryListRepository.getAll(PageRequest.of(page, PAGE_SIZE, Sort.by(dir, sort)));
    String url = listUrl.toUriString();
    return new ModelAndView("index", Map.of("inventoryList", all, "pageId", "list", "pageUrl", url));
  }

  @PostMapping("/save")
  public ModelAndView save(InventoryDTO reqDTO) {
    Inventory inventory = new Inventory(reqDTO.id, reqDTO.tagId, reqDTO.tagType, reqDTO.shortDescription, reqDTO.description, reqDTO.type, reqDTO.getJsonData());
    inventoryRepository.save(inventory);

    return new ModelAndView("redirect:/inventory/list");
  }
}