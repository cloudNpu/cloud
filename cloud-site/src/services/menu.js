import request from "@/utils/request";

export async function queryMenus(params) {
  // console.log(params);
  return request(`/api/menus`);
}
