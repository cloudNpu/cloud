import { stringify } from "qs";
import request from "@/utils/request";

export async function queryAnalysis(params) {
  return request("/api/cloud/status");
}
