package com.inventorer;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class InventoryFormTypes {
  Map<String, JsonObject> formTypes;

  List<String> formTypesList;

  public InventoryFormTypes() throws Exception {
    String[] list = ResourceUtils.getFile("classpath:templates/fragments/sections/forms").list();
    formTypesList = Arrays.stream(list)
        .map(s -> s.substring(0, s.lastIndexOf(".")))
        .sorted()
        .toList();
  }

  public Map<String, JsonObject> getFormTypes() {
    return formTypes;
  }

  public List<String> getFormTypesList() {
    return formTypesList;
  }
}