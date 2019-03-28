import { stringify } from "qs";
import request from "@/utils/request";

/*export async function queryAppList(params) {
    return request(`/api/applist?${stringify(params)}`);
}*/
export async function queryAppList(params) {
    return request("/api/apps/instanceInfoId");
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
    console.log(params);
    return request(`/api/cloud/apps/${params.app}`, {
        method: "POST",
        body: {
            ...params,
        }
    });
}
/*export async function addFound(params) {
  return request("/api/found", {
    method: "POST",
    body: {
      ...params,
      method: "post"
    }
  });
}*/
export async function updateAppList(params) {
    return request("/api/applist", {
        method: "POST",
        body: {
            ...params,
            method: "update"
        }
    });
}

export async function changeAppList(params) {
    return request("/api/applist", {
        method: "POST",
        body: {
            ...params,
            method: "change"
        }
    });
}

