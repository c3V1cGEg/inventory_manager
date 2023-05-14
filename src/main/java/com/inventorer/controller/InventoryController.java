package com.inventorer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorer.InventoryFormTypes;
import com.inventorer.TagType;
import com.inventorer.model.Inventory;
import com.inventorer.model.InventoryListRepository;
import com.inventorer.model.InventoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
  InventoryFormTypes inventoryFormTypes;
  InventoryRepository inventoryRepository;
  InventoryListRepository inventoryListRepository;

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

  @GetMapping("/add")
  public ModelAndView add(@RequestParam(required = false, defaultValue = "general") String type) {
    Map<String, Object> variables = new HashMap<>();
    variables.put("type", type);
    variables.put("inventory", new InventoryDTO());
    variables.put("pageId", "add");
    return new ModelAndView("index", variables);
  }

  @GetMapping("/list")
  public ModelAndView list(@RequestParam(defaultValue = "0") int page) {
    Pageable of = PageRequest.of(page, 5);
    Page<Inventory> all = inventoryListRepository.findAll(of);
    Map<String, Object> variables = new HashMap<>();
    variables.put("inventoryList", all);
    variables.put("pageId", "list");
    return new ModelAndView("index", variables);
  }

  @PostMapping("/save")
  public ModelAndView save(InventoryDTO reqDTO) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(reqDTO.getJsonData());

    Inventory inventory = new Inventory(reqDTO.id, reqDTO.tagId, reqDTO.tagType, reqDTO.shortDescription, reqDTO.description, reqDTO.type, json);
    inventoryRepository.save(inventory);

    return new ModelAndView("redirect:/inventory/list");
  }
}