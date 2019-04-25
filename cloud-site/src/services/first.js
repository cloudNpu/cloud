import request from "@/utils/request";
import { stringify } from "qs";

export async function queryMenu(params) {
  return request(`/api/menus`);
}

export async function addMenu(params) {
  // console.log(params);
  return request("/api/menus", {
    method: "POST",
    body: {
      ...params
    }
  });
}

export async function updateMenu(params) {
  //console.log(params);
  return request(`/api/menus?id=${params.id}`, {
    method: "PUT",
    body: {
      ...params
    }
  });
}

export async function deleteMenu(params) {
  //console.log(params);
  return request(`/api/menus?id=${params.id}`, {
    method: "DELETE",
    body: {
      ...params
    }
  });
}
