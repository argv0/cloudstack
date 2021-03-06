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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cloud.api.ApiConstants;
import com.cloud.api.BaseListCmd;
import com.cloud.api.IdentityMapper;
import com.cloud.api.Implementation;
import com.cloud.api.Parameter;
import com.cloud.api.response.ListResponse;
import com.cloud.host.Host;
import com.cloud.network.NetworkUsageManager;
import com.cloud.server.ManagementService;
import com.cloud.server.api.response.ExternalFirewallResponse;
import com.cloud.server.api.response.TrafficMonitorResponse;
import com.cloud.utils.component.ComponentLocator;

@Implementation(description="List traffic monitor Hosts.", responseObject = ExternalFirewallResponse.class)
public class ListTrafficMonitorsCmd extends BaseListCmd {
	public static final Logger s_logger = Logger.getLogger(ListServiceOfferingsCmd.class.getName());
    private static final String s_name = "listtrafficmonitorsresponse";

    /////////////////////////////////////////////////////
    //////////////// API parameters /////////////////////
    /////////////////////////////////////////////////////

    @IdentityMapper(entityTableName="data_center")
    @Parameter(name=ApiConstants.ZONE_ID, type=CommandType.LONG, required = true, description="zone Id")
    private long zoneId;


    /////////////////////////////////////////////////////
    /////////////////// Accessors ///////////////////////
    /////////////////////////////////////////////////////

    public long getZoneId() {
        return zoneId;
    }

    /////////////////////////////////////////////////////
    /////////////// API Implementation///////////////////
    /////////////////////////////////////////////////////

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public void execute(){
        ComponentLocator locator = ComponentLocator.getLocator(ManagementService.Name);
        NetworkUsageManager networkUsageMgr = locator.getManager(NetworkUsageManager.class);
    	List<? extends Host> trafficMonitors = networkUsageMgr.listTrafficMonitors(this);

        ListResponse<TrafficMonitorResponse> listResponse = new ListResponse<TrafficMonitorResponse>();
        List<TrafficMonitorResponse> responses = new ArrayList<TrafficMonitorResponse>();
        for (Host trafficMonitor : trafficMonitors) {
            TrafficMonitorResponse response = networkUsageMgr.getApiResponse(trafficMonitor);
        	response.setObjectName("trafficmonitor");
        	response.setResponseName(getCommandName());
        	responses.add(response);
        }

        listResponse.setResponses(responses);
        listResponse.setResponseName(getCommandName());
        this.setResponseObject(listResponse);
    }
}
