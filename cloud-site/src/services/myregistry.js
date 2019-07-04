import { stringify } from "qs";
import request from "@/utils/request";
export async function queryMyRegistry() {
    var userId = JSON.parse(sessionStorage.getItem("user")).id;
    return request(`/api/apps/myapps?userId=${userId}`);
}
