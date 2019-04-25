import request from "@/utils/request";
import { stringify } from "qs";

export async function getDynamicmenu() {
  return request(`/api/menus/${JSON.parse(sessionStorage.getItem("user")).id}`);
}
