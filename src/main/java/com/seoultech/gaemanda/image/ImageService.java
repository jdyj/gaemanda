package com.seoultech.gaemanda.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

  private final ImageRepository imageRepository;

  @Value("{file.dir}")
  private String fileDir;

  public String getFullPath(String fileName) {
    return fileDir + fileName;
  }

  public List<Image> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
    List<Name> collect = new ArrayList<>();
    File folder = new File(fileDir);
    if (!folder.exists()) {
      folder.mkdir();
    }
    for (MultipartFile multipartFile : multipartFiles) {
      if (!multipartFile.isEmpty()) {
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));
        Name name = new Name(originalFilename, storeFilename);
        collect.add(name);
      }
    }

    List<Image> images = collect.stream()
        .map(
            (name) -> new Image(name.getOriginalFilename(), name.getStoreFileName()))
        .collect(Collectors.toList());
    imageRepository.saveAll(images);

    return images;
  }

  public Image storeFile(MultipartFile multipartFile) {
    File folder = new File(fileDir);
    if (!folder.exists()) {
      folder.mkdir();
    }
    String originalFilename = multipartFile.getOriginalFilename();
    String storeFilename = createStoreFileName(originalFilename);
    try {
      multipartFile.transferTo(new File(getFullPath(storeFilename)));
    } catch (IOException e) {
      log.error("파일 저장 에러 -> {}", e.getCause().toString());
    }
    Image image = new Image(originalFilename, storeFilename);
    return imageRepository.save(image);
  }

  public void resizeFile(String fileName, int width, int height) throws IOException {
    File image = new File(getFullPath(fileName));

    BufferedImage src = ImageIO.read(image);
    BufferedImage read = Scalr.resize(src, width);

    FileOutputStream out = new FileOutputStream(getFullPath(renameFile(fileName, width, height)));
    ImageIO.write(read, extractExt(fileName), out);
    out.close();
  }

  public Image findDefaultProfileImage() {
    return imageRepository.findById(1L).get();
  }


  public Image findDefaultFolderImage() {
    return imageRepository.findById(2L).get();
  }

  private String createStoreFileName(String originalFilename) {
    String ext = extractExt(originalFilename);
    String uuid = UUID.randomUUID().toString();
    return uuid + "." + ext;
  }

  private String extractExt(String originalFilename) {
    int pos = originalFilename.lastIndexOf(".");
    return originalFilename.substring(pos + 1);
  }

  private String renameFile(String fileName, int width, int height) {
    int pos = fileName.lastIndexOf(".");
    String ext = extractExt(fileName);
    return fileName.substring(0, pos) + "_" + width + "x" + height + "." + ext;
  }

  @Data
  @AllArgsConstructor
  private static class Name {

    private String originalFilename;
    private String storeFileName;
  }

}
