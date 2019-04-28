import request from "@/utils/request";
import { stringify } from "qs";

export async function queryDept(params) {
  return request(`/api/depts`);
}
