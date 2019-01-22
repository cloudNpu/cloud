import request from "@/utils/request";
export async function addDept(params) {
    console.log(params);
    console.log(params.dept);
  return request('/api/depts', {
    method: "POST",
    body: params.dept
  });
}
export async function deleteDept(params) {
  return request('/api/depts/:id' + params.dept[0].key, {
    method: "DELETE"
  });
}
