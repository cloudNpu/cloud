import request from "@/utils/request";
export async function addMenuItem(params) {
    console.log(params);
    console.log(params.menuItem);
    return request('/api/menusItem', {
        method: "POST",
        body: params
    });
}
/*
export async function deleteDept(params) {
    return request('/api/depts/:id' + params.dept[0].key, {
        method: "DELETE"
    });
}
*/