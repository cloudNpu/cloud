import request from "@/utils/request";
export  async function menuList() {
    return request('/api/roleMenus', {
        method: "POST",
       // body: params
    });
}