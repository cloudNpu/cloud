import request from "@/utils/request";
export async function addMenu(params) {
    return request('/api/menus', {
        method: "POST",
        body: params
    });
}
