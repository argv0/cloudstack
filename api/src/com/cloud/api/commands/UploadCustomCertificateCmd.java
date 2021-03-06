// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.api.commands;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseAsyncCmd;
import com.cloud.api.BaseCmd;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.ServerApiException;
import com.cloud.api.response.CustomCertificateResponse;
import com.cloud.event.EventTypes;
import com.cloud.user.Account;

@Implementation(responseObject=CustomCertificateResponse.class, description="Uploads custom certificate")
public class UploadCustomCertificateCmd extends BaseAsyncCmd {
    public static final Logger s_logger = Logger.getLogger(UploadCustomCertificateCmd.class.getName());

    private static final String s_name = "uploadcustomcertificateresponse";

    @Parameter(name=ApiConstants.CERTIFICATE,type=CommandType.STRING,required=true,description="the custom cert to be uploaded", length=65535)
    private String certificate;
    
    @Parameter(name=ApiConstants.ID,type=CommandType.INTEGER,required=false,description="the custom cert id in the chain")
    private Integer index;
    
    @Parameter(name=ApiConstants.NAME,type=CommandType.STRING,required=false,description="the alias of the certificate")
    private String alias;

    @Parameter(name=ApiConstants.PRIVATE_KEY,type=CommandType.STRING,required=false,description="the private key for the certificate", length=65535)
    private String privateKey;

    @Parameter(name=ApiConstants.DOMAIN_SUFFIX,type=CommandType.STRING,required=true,description="DNS domain suffix that the certificate is granted for")
    private String domainSuffix;
    
    public String getCertificate() {
        return certificate;
    }
    
    public String getPrivateKey() {
    	return privateKey;
    }
    
    public String getDomainSuffix() {
    	return domainSuffix;
    }
    
    public Integer getCertIndex() {
    	return index;
    }
    
    public String getAlias() {
    	return alias;
    }

    @Override
    public String getEventType() {
        return EventTypes.EVENT_UPLOAD_CUSTOM_CERTIFICATE;
    }

    @Override
    public String getEventDescription() {
        return  ("Uploading custom certificate to the db, and applying it to all the cpvms in the system");
    }
    
    @Override
    public String getCommandName() {
        return s_name;
    }
    
    public static String getResultObjectName() {
    	return "certificate";
    }

    @Override
    public long getEntityOwnerId() {
        return Account.ACCOUNT_ID_SYSTEM; // no account info given, parent this command to SYSTEM so ERROR events are tracked
    }
    
    @Override
    public void execute(){
        String result = _mgr.uploadCertificate(this);
        if (result != null) {
            CustomCertificateResponse response = new CustomCertificateResponse();
            response.setResponseName(getCommandName());
            response.setResultMessage(result);
            response.setObjectName("customcertificate");
            this.setResponseObject(response);
        } else {
            throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to upload custom certificate");
        }
    }

}
