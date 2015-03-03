package com.artreset.image.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.artreset.image.exception.ImageNotFoundException;
import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
public interface ImageService {
    
    public List<Image> list();
    
    public Image create(Image image);
    
    public Image get(Long id) throws ImageNotFoundException;
    
    public Image delete(Image image) throws ImageNotFoundException;
    
    public Image deleteById(Long id) throws ImageNotFoundException;
    
    public Image create(MultipartFile file);
    
    public File makeThumbnailFile(File file) throws IOException;

}
