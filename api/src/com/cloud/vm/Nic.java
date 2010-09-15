/**
 *  Copyright (C) 2010 Cloud.com, Inc.  All rights reserved.
 * 
 * This software is licensed under the GNU General Public License v3 or later.  
 * 
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.cloud.vm;

import com.cloud.network.Network.Mode;
import com.cloud.resource.Resource;


/**
 * Nic represents one nic on the VM.
 */
public interface Nic extends Resource {

    String getIp4Address();
    
    String getMacAddress();
    
    /**
     * @return network profile id that this 
     */
    long getNetworkProfileId();
    
    /**
     * @return the vm instance id that this nic belongs to.
     */
    long getInstanceId();
    
    int getDeviceId();
    
    Mode getMode();
}
