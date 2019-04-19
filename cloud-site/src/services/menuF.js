import request from "@/utils/request";

export async function queryMenuFs(params) {
  // console.log(params);
  return request(`/api/menus`);
}
