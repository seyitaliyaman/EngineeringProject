package com.safestagram.ws.service;


import com.safestagram.ws.exception.SafestagramServiceException;
import com.safestagram.ws.exception.ErrorCode;
import com.safestagram.ws.exception.FileReadException;
import com.safestagram.ws.model.request.UploadPostRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ArchiveService {

    @Value("${bowtie.store.photo.path}")
    private String photoPath;

    private String currentArchiveFileName = "";

    private String currentArchiveStore;



    private void createArchiveFolderFromPath(String archivePath){
        File directory = new File(archivePath);
        if(!directory.exists())
            directory.mkdirs();
    }


    public boolean storeProfilePhoto(MultipartFile photo){

        String archiveFileName = photo.getOriginalFilename();
        currentArchiveStore = this.photoPath;
        File currentArchiveFolder = new File(currentArchiveStore);
        if(!currentArchiveFolder.exists()){
            createArchiveFolderFromPath(currentArchiveStore);
        }
        String archiveFilenameFull = currentArchiveStore + File.separator + archiveFileName;
        File targetFile = new File(archiveFilenameFull);
        try {
            if(!targetFile.exists()){
                FileUtils.touch(targetFile);
                FileUtils.writeByteArrayToFile(targetFile,photo.getBytes());
                this.currentArchiveFileName = targetFile.getCanonicalPath();
                return true;
            }else{
                return false;
            }
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
    }



    public boolean storePost(UploadPostRequest postRequest){

        currentArchiveStore = this.photoPath;

        File currentArchiveFolder = new File(currentArchiveStore);

        if(!currentArchiveFolder.exists()){
            createArchiveFolderFromPath(currentArchiveStore);
        }

        String archiveFilenameFull = currentArchiveStore + File.separator + postRequest.getPost().getOriginalFilename();

        File targetFile = new File(archiveFilenameFull);

        try {
            if(!targetFile.exists()){
                FileUtils.touch(targetFile);
                FileUtils.writeByteArrayToFile(targetFile,postRequest.getPost().getBytes());
                this.currentArchiveFileName = targetFile.getCanonicalPath();
                return true;
            }else{
                return false;
            }
        }catch (IOException ex){
            ex.printStackTrace();
            return false;
        }

    }

    public String getCurrrentArchiveFileName(){
        return this.currentArchiveFileName;
    }

    public String getPath(){return this.photoPath;}

    public byte[] getData(String path){
        try {
            return Files.readAllBytes(new File(path).toPath());
        } catch (IOException e) {
            throw new FileReadException();
        }
    }

    public boolean removeFile(String path){
        try {
            FileUtils.forceDelete(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Resource getPostAsResource(String filename, String path){
        try {
            Path filePath = Paths.get(path).resolve(filename).normalize();
            Resource resource = null;
            resource = new UrlResource(filePath.toUri());
            if(resource.exists())
                return resource;
            else
                throw new SafestagramServiceException(ErrorCode.POST_NOT_FOUND);
        } catch (MalformedURLException e) {
            throw new SafestagramServiceException(ErrorCode.POST_NOT_FOUND);
        }

    }
}
