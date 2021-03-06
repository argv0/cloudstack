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
package com.cloud.network.vpn;

import java.util.List;

import com.cloud.api.commands.ListRemoteAccessVpnsCmd;
import com.cloud.api.commands.ListVpnUsersCmd;
import com.cloud.exception.NetworkRuleConflictException;
import com.cloud.exception.ResourceUnavailableException;
import com.cloud.network.RemoteAccessVpn;
import com.cloud.network.VpnUser;

public interface RemoteAccessVpnService {

    RemoteAccessVpn createRemoteAccessVpn(long vpnServerAddressId, String ipRange, boolean openFirewall) throws NetworkRuleConflictException;
    void destroyRemoteAccessVpn(long vpnServerAddressId) throws ResourceUnavailableException;
    RemoteAccessVpn startRemoteAccessVpn(long vpnServerAddressId, boolean openFirewall) throws ResourceUnavailableException;

    VpnUser addVpnUser(long vpnOwnerId, String userName, String password);
    boolean removeVpnUser(long vpnOwnerId, String userName);
    List<? extends VpnUser> listVpnUsers(long vpnOwnerId, String userName);
    boolean applyVpnUsers(long vpnOwnerId);
    
    List<? extends RemoteAccessVpn> searchForRemoteAccessVpns(ListRemoteAccessVpnsCmd cmd);
    List<? extends VpnUser> searchForVpnUsers(ListVpnUsersCmd cmd);
    
    List<? extends RemoteAccessVpn> listRemoteAccessVpns(long networkId);
    
    RemoteAccessVpn getRemoteAccessVpn(long vpnId);

}
