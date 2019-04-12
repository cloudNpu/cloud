import request from "@/utils/request";
import { stringify } from "qs";

export async function queryDept(params) {
  return request(`/api/depts`);
}
export async function addDept(params) {
  return request(`/api/depts`, {
    method: "POST",
    body: {
      ...params
    }
  });
}

export async function updateDept(params) {
  return request(`/api/depts/${params.id}`, {
    method: "PUT",
    body: {
      ...params
      // method: "update"
    }
  });
}

export async function deleteDept(params) {
  return request(`/api/depts`, {
    method: "DELETE",
    body: {
      ...params
    }
  });
}
