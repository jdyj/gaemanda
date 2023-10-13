package com.seoultech.gaemanda.image;

import com.seoultech.gaemanda.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Image extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalFilename;

  private String storeFileName;


  public Image(String originalFilename, String storeFileName) {
    this.originalFilename = originalFilename;
    this.storeFileName = storeFileName;
  }

  public String getResizeFileName(int width, int height) {
    int pos = this.storeFileName.lastIndexOf(".");
    String ext = extractExt(this.storeFileName);
    return this.storeFileName.substring(0, pos) + "_" + width + "x" + height + "." + ext;
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }

  protected Image() {
  }
}

