import request from "@/utils/request";
import { stringify } from "qs";

export async function queryRole(params) {
  return request(`/api/roles?${stringify(params)}`);
}

export async function addRole(params) {
  return request("/api/roles", {
    method: "POST",
    body: {
      ...params,
      method: "post"
    }
  });
}

export async function deleteRole(params) {
  return request("/api/roles/id", {
    method: "POST",
    body: {
      ...params,
      method: "delete"
    }
  });
}

export async function updateRole(params) {
  return request("/api/roles/id", {
    method: "POST",
    body: {
      ...params,
      method: "update"
    }
  });
}

export async function menuList() {
  return request("/api/menus", {
    method: "GET"
    //body: params
  });
}
