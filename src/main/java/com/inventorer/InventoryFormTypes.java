package com.inventorer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InventoryFormTypes {
  Map<String, JsonObject> formTypes;

  List<String> formTypesList;

  public InventoryFormTypes() throws Exception {
    String[] list = ResourceUtils.getFile("classpath:forms").list();

    formTypes = Arrays.stream(list).collect(Collectors.toMap(file -> file.substring(0, file.indexOf('.')), file -> {
      JsonObject jsonElement = null;
      try {
        File absoluteFile = ResourceUtils.getFile("classpath:forms/" + file).getAbsoluteFile();
        String s = Files.readString(absoluteFile.toPath());
        jsonElement = (JsonObject) JsonParser.parseString(s);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      return jsonElement;
    }));

    List<String> s = new ArrayList<>(formTypes.keySet());
    Collections.sort(s);
    formTypesList = s;
  }

  public Map<String, JsonObject> getFormTypes() {
    return formTypes;
  }

  public List<String> getFormTypesList() {
    return formTypesList;
  }
}