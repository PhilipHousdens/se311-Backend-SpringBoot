package se331.rest.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import jakarta.servlet.ServletException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class CloudStorageHelper {
    private static Storage storage = null;
    static {
        InputStream serviceAccount = null;
        try {
            serviceAccount = new ClassPathResource("src/main/resources/compo-lab-work-firebase-adminsdk-uazot-0a7dc28892.json").getInputStream();
            storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount)).setProjectId("gs://compo-lab-work.appspot.com").build().getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String uploadFile(MultipartFile filePart, final String bucketName) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dtString = sdf.format(new Date());
        final String fileName = dtString + "-" + filePart.getOriginalFilename();
        InputStream is = filePart.getInputStream();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] readBuf = new byte[4096];
        while (is.available() > 0) {
            int bytesRead = is.read(readBuf);
            os.write(readBuf, 0, bytesRead);
        }
        // Convert ByteArrayOutputStream into byte[]
        BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, fileName)
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                .setContentType(filePart.getContentType())
                .build(),
                os.toByteArray());
        // Return the public download link
        return blobInfo.getMediaLink();
    }

    public String getImageUrl (MultipartFile file, final String bucket) throws IOException, ServletException {
        final String fileName = file.getOriginalFilename();
        // Check extension of file
        if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
            final String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            String [] allowedExt = {"jpg", "jpeg", "png", "gif"};
            for (String s : allowedExt) {
                if(extension.equalsIgnoreCase(s)) {
                    return uploadFile(file, bucket);
                }
            }
            throw new ServletException("Image extension not supported");
        }
        return null;
    }
}
