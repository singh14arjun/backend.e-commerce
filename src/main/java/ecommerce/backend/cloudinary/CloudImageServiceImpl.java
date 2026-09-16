package ecommerce.backend.cloudinary;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudImageServiceImpl implements CloudImageService {

    private final Cloudinary cloudinary;

    @Override
    public Map<?, ?> uploadImage(MultipartFile file) throws IOException {
        try{
            Map<?,?> data=cloudinary.uploader().upload(file.getBytes(),Map.of());
            return data;
        }
        catch(IOException e){
            e.printStackTrace();

            throw new RuntimeException("Failed to upload image"+e.getMessage());
        }
    }
}
