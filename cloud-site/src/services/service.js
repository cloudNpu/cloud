import { stringify } from "qs";
import request from "@/utils/request";
export async function queryService() {
  /* var a = localStorage.getItem("antd-pro-authority");
  for (var i = 1; i < a.length; i++) {
    a = a.replace('"', "");
  }*/
  var a = JSON.parse(sessionStorage.getItem("user")).username;
  return request(`/api/userApps/user/${a}`);
}
