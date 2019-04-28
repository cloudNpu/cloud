import request from "@/utils/request";

export async function query() {
  return request("/api/users");
}

export async function queryCurrent() {
  return request("/api/currentUser");
}

export async function updateUser(params) {
  return request("/api/center", {
    method: "POST",
    body: {
      ...params,
      method: "update"
    }
  });
}
export async function updateCenter(params) {
  return request(`/api/users/${params.id}`, {
    method: "PUT",
    body: {
      ...params
    }
  });
}
