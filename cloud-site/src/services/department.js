import request from "@/utils/request";
export async function addDept(params) {
  //console.log(params);
  /* return request('/api/depts', {*/
  return request("/depts", {
    method: "POST",
    body: params
  });
}
export async function deleteDept(params) {
  return request(`/depts/id` + params.dept[0].key, {
    method: "DELETE"
  });
}
