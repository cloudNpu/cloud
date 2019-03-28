import { stringify } from "qs";
import request from "@/utils/request";
export async function queryFound(params) {
    console.log(params);
    return request(`/api/users?${stringify(params)}`);
}

export async function removeFound(params) {
    return request("/api/users", {
        method: "DELETE",
        body: {
            ...params,
            // method: "delete"
        }
    });
}

export async function addFound(params) {
    return request("/api/users", {
        method: "POST",
        body: {
            ...params,
        }
    });
}

export async function updateFound(params) {
    return request("/api/users/id", {
        method: "PUT",
        body: {
            ...params,
            //method: "update"
        }
    });
}
export async function Add_user_app(params) {
    return request("/api/userApps", {
        method: "POST",
        body: {
            ...params,
            method: "add_user_app"
        }
    });
}
export async function Add_user_role(params) {
    return request("/api/users/roles", {
        method: "POST",
        body: {
            ...params,
          //  method: "add_user_role"
        }
    });
}
