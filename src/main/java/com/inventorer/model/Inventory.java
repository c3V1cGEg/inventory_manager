package com.inventorer.model;

import com.inventorer.util.JsonConverter;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Inventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String tagId;
  String tagType;
  String shortDescription;
  String description;
  String type;
  @CreatedDate
  @Column(nullable = false, updatable = false)
  Date insertedAt;
  @LastModifiedDate
  Date updatedAt;
  @Column( columnDefinition = "json" )
  @Convert(converter = JsonConverter.class)
  Map<String, Object> dataJson;

  public Inventory() {
  }

  public Inventory(Long id, String tagId, String tagType, String shortDescription, String description, String type, Map<String, Object> dataJson) {
    this.id = id;
    this.tagId = tagId;
    this.tagType = tagType;
    this.shortDescription = shortDescription;
    this.description = description;
    this.type = type;
    this.dataJson = dataJson;
  }

  public Long getId() {
    return id;
  }

  public String getTagId() {
    return tagId;
  }

  public String getTagType() {
    return tagType;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public String getType() {
    return type;
  }

  public Map<String, Object> getDataJson() {
    return dataJson;
  }
}