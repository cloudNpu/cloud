import { stringify } from "qs";
import request from "@/utils/request";

export async function queryAppList(params) {
  return request("/api/apps");
}
export async function removeAppList(params) {
  const str = params.map(item => item).join(",");
  return request(`/api/instanceInfoIds?instanceInfoId=${str}`, {
    method: "DELETE"
  });
}

export async function addAppList(params) {
  console.log(params);
  console.log(params.instance.app);
  return request(`/api/cloud/apps/${params.instance.app}`, {
    method: "POST",
    body: {
      ...params
    }
  });
}
/////////////////////////////////
export async function appSearch(params) {
  return request(`/api/apps/appName?appName=${params.appName}`);
}
export async function idSearch(params) {
  console.log(params);
  return request(`/api/apps/instanceInfoId?instanceInfoId=${params.id}`);
}
export async function visibleSearch(params) {
  return request(`/api/apps/visible?visible=${params.visible}`);
}
export async function portSearch(params) {
  return request(`/api/apps/port?port=${params.port}`);
}
export async function ipAddrSearch(params) {
  return request(`/api/apps/ipAddr?ipAddr=${params.ipAddr}`);
}
//////////////////////////////
export async function updateAppList(params) {
  return request(
    `/api/cloud/apps/${params.instance.app}/${
      params.instance.instanceId
    }/update`,
    {
      method: "PUT",
      body: {
        ...params
      }
    }
  );
}

export async function changeAppList(params) {
  console.log(params);
  return request(
    `/api/apps/appName?appName=${params.appName}&isPublished=${params.visible}`,
    {
      method: "PUT",
      body: {}
    }
  );
}
