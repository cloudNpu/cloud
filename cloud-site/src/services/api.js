import { stringify } from "qs";
import request from "@/utils/request";

export async function queryProjectNotice() {
  return request("/api/project/notice");
}

export async function queryActivities() {
  return request("/api/activities");
}

export async function queryRule(params) {
  return request(`/api/rule?${stringify(params)}`);
}

export async function removeRule(params) {
  return request("/api/rule", {
    method: "POST",
    body: {
      ...params,
      method: "delete"
    }
  });
}

export async function addRule(params) {
  return request("/api/rule", {
    method: "POST",
    body: {
      ...params,
      method: "post"
    }
  });
}

export async function updateRule(params) {
  return request("/api/rule", {
    method: "POST",
    body: {
      ...params,
      method: "update"
    }
  });
}

export async function fakeSubmitForm(params) {
  return request("/api/forms", {
    method: "POST",
    body: params
  });
}

export async function fakeChartData() {
  return request("/api/fake_chart_data");
}

export async function queryTags() {
  return request("/api/tags");
}

export async function queryBasicProfile() {
  return request("/api/profile/basic");
}

export async function queryAdvancedProfile() {
  return request("/api/profile/advanced");
}

export async function queryFakeList(params) {
  return request(`/api/fake_list?${stringify(params)}`);
}

export async function removeFakeList(params) {
  const { count = 5, ...restParams } = params;
  return request(`/api/fake_list?count=${count}`, {
    method: "POST",
    body: {
      ...restParams,
      method: "delete"
    }
  });
}

export async function addFakeList(params) {
  const { count = 5, ...restParams } = params;
  return request(`/api/fake_list?count=${count}`, {
    method: "POST",
    body: {
      ...restParams,
      method: "post"
    }
  });
}

export async function updateFakeList(params) {
  const { count = 5, ...restParams } = params;
  return request(`/api/fake_list?count=${count}`, {
    method: "POST",
    body: {
      ...restParams,
      method: "update"
    }
  });
}

export async function fakeAccountLogin(params) {
  return request("/api/auth/login", {
    //   return request("/api/login/account", {
    method: "POST",
    body: params
  });
}

export async function fakeRegister(params) {
  return request("/api/register", {
    method: "POST",
    body: params
  });
}

export async function queryNotices() {
  return request("/api/notices");
}

export async function getFakeCaptcha(mobile) {
  return request(`/api/captcha?mobile=${mobile}`);
}
//
export async function queryAppList(params) {
  return request(`/api/applist?${stringify(params)}`);
}

export async function removeAppList(params) {
  return request("/api/applist", {
    method: "POST",
    body: {
      ...params,
      method: "delete"
    }
  });
}

export async function addAppList(params) {
  //console.log(params);
  //console.log(`/api/cloud/apps/${params.app}`);
  /// "/api/applist"
  //${params.app}
  return request(`/api/cloud/apps/APPLICATION0`, {
    method: "POST",
    body: {
      ...params,
      /* "instance":{
            "instanceId": "i-00000000",
            "app": "APPLICATION0",
            "appGroupName": "APPLICATION0GROUP",
            "ipAddr": "192.168.0.5",
            "sid": "na",
            "homePageUrl": "http://instance0.application0.com:8080/homepage",
            "statusPageUrl": "http://instance0.application0.com:8080/status",
            "healthCheckUrl": "http://instance0.application0.com:8080/healthcheck",
            "secureHealthCheckUrl": "https://instance0.application0.com:8081/healthcheck",
            "vipAddress": "application0:8080",
            "secureVipAddress": "application0:8081",
            "countryId": 1,
            "dataCenterInfo": {
                "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
                "name": "MyOwn"
            },

            "inputparm":"int,int",
            "outputparm":"int",
            "hostName": "instance0.application0.com",
            "status": "UP",
            "leaseInfo": {
                "renewalIntervalInSecs": 300,
                "durationInSecs": 60,
                "registrationTimestamp": "0000-00-00 00:00:00",
                "lastRenewalTimestamp": "0000-00-00 00:00:00",
                "evictionTimestamp": "0000-00-00 00:00:00",
                "serviceUpTimestamp": "0000-00-00 00:00:00"
            },
            "isCoordinatingDiscoveryServer": true,
            "metadata": {
                "appKey0": "0"
            },
            "lastUpdatedTimestamp": "0000-00-00 00:00:00",
            "lastDirtyTimestamp": "0000-00-00 00:00:00",
            "actionType": "ADDED",
            "asgName": "application0ASG",
            "overriddenStatus": "UNKNOWN"
        }  ,*/
      method: "post"
    }
    /*{"instance":{
                "instanceId": "i-00000000",
                "app": "APPLICATION0",
                "appGroupName": "APPLICATION0GROUP",
                "ipAddr": "192.168.0.5",
                "sid": "na",
                "homePageUrl": "http://instance0.application0.com:8080/homepage",
                "statusPageUrl": "http://instance0.application0.com:8080/status",
                "healthCheckUrl": "http://instance0.application0.com:8080/healthcheck",
                "secureHealthCheckUrl": "https://instance0.application0.com:8081/healthcheck",
                "vipAddress": "application0:8080",
                "secureVipAddress": "application0:8081",
                "countryId": 1,
                "dataCenterInfo": {
                    "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
                    "name": "MyOwn"
                },

                "inputparm":"int,int",
                "outputparm":"int",
                "hostName": "instance0.application0.com",
                "status": "UP",
                "leaseInfo": {
                    "renewalIntervalInSecs": 300,
                    "durationInSecs": 60,
                    "registrationTimestamp": "0000-00-00 00:00:00",
                    "lastRenewalTimestamp": "0000-00-00 00:00:00",
                    "evictionTimestamp": "0000-00-00 00:00:00",
                    "serviceUpTimestamp": "0000-00-00 00:00:00"
                },
                "isCoordinatingDiscoveryServer": true,
                "metadata": {
                    "appKey0": "0"
                },
                "lastUpdatedTimestamp": "0000-00-00 00:00:00",
                "lastDirtyTimestamp": "0000-00-00 00:00:00",
                "actionType": "ADDED",
                "asgName": "application0ASG",
                "overriddenStatus": "UNKNOWN"
            }}*/
  });
}

export async function updateAppList(params) {
  return request("/api/applist", {
    method: "POST",
    body: {
      ...params,
      method: "update"
    }
  });
}
//
export async function changeAppList(params) {
  return request("/api/applist", {
    method: "POST",
    body: {
      ...params,
      method: "change"
    }
  });
}

export async function updateCenter(params) {
  return request("/api/center", {
    method: "POST",
    body: {
      ...params,
      method: "update"
    }
  });
}
export async function queryService() {
  return request("/api/service");
}
