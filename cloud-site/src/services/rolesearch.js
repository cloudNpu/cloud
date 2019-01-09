import request from "@/utils/request";
import { stringify } from "qs";

export async function queryRole(params) {
  return request(`/api/role?${stringify(params)}`);
}

export async function deleteRole(params) {
  return request("/api/role", {
    method: "POST",
    body: {
      ...params,
      method: "delete"
    }
  });
}

export async function addRole(params) {
  return request("/api/role", {
    method: "POST",
    body: {
      ...params,
      method: "post"
    }
  });
}

export async function updateRole(params) {
  return request("/api/role", {
    method: "POST",
    body: {
      ...params,
      method: "update"
    }
  });
}