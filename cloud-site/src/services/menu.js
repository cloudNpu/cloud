import request from "@/utils/request";
export async function addMenu(params) {
    console.log(params);
    console.log(params.menu);
    return request('/api/menus', {
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