import request from "@/utils/request";
import { stringify } from "qs";

export async function queryRole(params) {
  return request(`/api/roles`);
}

export async function addRole(params) {
  return request("/api/roles", {
    method: "POST",
    body: {
      ...params
    }
  });
}

export async function updateRole(params) {
  return request(`/api/roles?id=${params.id}`, {
    method: "PUT",
    body: {
      ...params
      // method: "update"
    }
  });
}

export async function deleteRole(params) {
  return request("/api/roles/id", {
    method: "POST",
    body: {
      ...params
      //  method: "delete"
    }
  });
}

export async function menuList(params) {
  return request(
    `/api/roleMenus`,
    {
      method: "POST",
      body: {
        ...params
      }
    }
    /*  , {
        method: "GET",
        body: params
      }*/
  );
}
