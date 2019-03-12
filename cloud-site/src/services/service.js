import { stringify } from "qs";
import request from "@/utils/request";
export async function queryService() {
  return request("/api/service");
  console.log("111");
}
