package com.example.loan.arrears.integration.router;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;

import java.io.File;

@MessageEndpoint
public class FileRouter {

    @Router(inputChannel = "fileInputChannel")
    public String routeFileToJob(File aFile){
        if(aFile.getName().matches("loan.csv")){
          //  aFile.renameTo(new File(fileProcessingDir + "/" + aFile.getName() + String.valueOf(System.currentTimeMillis())));
            return "loanJobChannel";
        }

     //   aFile.renameTo(new File(fileProcessingDir + "/" + aFile.getName() + String.valueOf(System.currentTimeMillis())));
        return "loanPartJobChannel";
    }
}
