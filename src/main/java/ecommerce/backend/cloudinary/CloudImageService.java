package ecommerce.backend.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudImageService {

    public Map<?,?> uploadImage(MultipartFile file) throws IOException;
}
