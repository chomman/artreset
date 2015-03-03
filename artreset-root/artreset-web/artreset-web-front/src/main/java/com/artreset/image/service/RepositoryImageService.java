/**
 * 
 */
package com.artreset.image.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.artreset.config.PropertyPlaceholderConfig;
import com.artreset.image.exception.ImageNotFoundException;
import com.artreset.image.repository.ImageRepository;
import com.artreset.model.Image;

/**
 * @author Taehyun Jung
 *
 */
@Service
@Import(PropertyPlaceholderConfig.class)
public class RepositoryImageService implements ImageService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryImageService.class);
	
	private final ImageRepository repository;
	
    @Value("${file.upload.directory}")
    private String fileUploadDirectory;
	
	@Autowired
	public RepositoryImageService(ImageRepository repository) {
		this.repository = repository;
	}
	

	@Override
	public List<Image> list() {
		LOGGER.debug("Image list");
		return repository.findAll();
	}
	
	@Transactional
	@Override
	public Image create(Image image) {
		LOGGER.debug("Add Image entry with information: {}", image);
		repository.save(image);
		return image;
	}

	@Override
	public Image get(Long id) throws ImageNotFoundException {
		LOGGER.debug("Find by id: {}", id);
		
		Image found = repository.findOne(id);
		LOGGER.debug("Found Image entry: {}", found);
		
		if(found == null){
			throw new ImageNotFoundException("ID {}번 이미지를 찾을 수 없습니다.");
		}
		
		return found;
	}

	@Override
	public Image delete(Image image) throws ImageNotFoundException {
		LOGGER.debug("Deleting image {}", image);
		
		repository.delete(image);
		
		return image;
	}
	
	@Override
	public Image deleteById(Long id) throws ImageNotFoundException {
		LOGGER.debug("Deleting image {}", id);
		
		Image deleted = repository.findOne(id);
		LOGGER.debug("Found Image entry: {}", deleted);
		
		if(deleted == null){
			throw new ImageNotFoundException("ID {}번 이미지를 찾을 수 없습니다.");
		}
		
		repository.delete(deleted);
		
		return deleted;
	}


	@Transactional
	@Override
	public Image create(MultipartFile file) {
		Image image = null;
		
        LOGGER.debug("Uploading {}", file.getOriginalFilename());
        String newFilenameBase = UUID.randomUUID().toString();
        String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFilename = newFilenameBase + originalFileExtension;
        String storageDirectory = fileUploadDirectory;
        String contentType = file.getContentType();
        
        File newFile = new File(storageDirectory + "/" + newFilename);
        File thumbnailFile = null;
        try {
            file.transferTo(newFile);
            
            thumbnailFile = makeThumbnailFile(newFile);
            
            image = Image.getBuilder(file.getOriginalFilename())
            		.newFilename(newFilename)
            		.contentType(contentType)
            		.size(file.getSize())
            		.thumbnailFilename(thumbnailFile != null ? thumbnailFile.getName() : null)
            		.thumbnailSize(thumbnailFile.length())
            		.build();
            
            image = create(image);
            
            image.setUrl("/image/picture/"+image.getId());
            image.setThumbnailUrl("/image/thumbnail/"+image.getId());
            image.setDeleteUrl("/image/delete/"+image.getId());
            image.setDeleteType("DELETE");
            
        } catch(IOException e) {
            LOGGER.error("Could not upload file "+file.getOriginalFilename(), e);
        } finally {
        	thumbnailFile = null;
        }
        
		return image;
	}


	@Override
	public File makeThumbnailFile(File file) {
		File thumbnailFile = null;
		try {
			String newFilenameBase = UUID.randomUUID().toString();
			//String originalFileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String originalFileExtension = file.getName().substring(file.getName().lastIndexOf("."));
			String storageDirectory = fileUploadDirectory + "/thumbnails";
			
			BufferedImage thumbnail = Scalr.resize(ImageIO.read(file), 290);
			
			String thumbnailFilename = newFilenameBase + "-thumbnail.png";
			thumbnailFile = new File(storageDirectory + "/" + thumbnailFilename);
			ImageIO.write(thumbnail, "png", thumbnailFile);
		} catch (Throwable th) {
			th.printStackTrace();
			thumbnailFile = null;
		}
		
		return thumbnailFile;
	}

}
