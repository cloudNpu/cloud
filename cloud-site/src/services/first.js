import request from "@/utils/request";
import { stringify } from "qs";

export async function queryMenu(params) {
    return request(`/api/menus?${stringify(params)}`);
}

export async function deleteMenu(params) {
    console.log(params);
    return request("/api/menus", {
        method: "POST",
        body: {
            ...params,
            method: "delete"
        }
    });
}

export async function addMenu(params) {
    return request("/api/menus", {
        method: "POST",
        body: {
            ...params,
            method: "post"
        }
    });
}

export async function updateMenu(params) {
    console.log(params);
    return request("/api/menus", {
        method: "POST",
        body: {
            ...params,
            method: "update"
        }
    });
}
