import request from "@/utils/request";

export async function query() {
  return request("/api/users");
}

export async function queryCurrent() {
  // return request("/auth/currentUser");
  return request("/api/currentUser");
}
