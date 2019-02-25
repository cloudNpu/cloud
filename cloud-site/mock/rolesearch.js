import { parse } from "url";
// mock roleSearchDataSource
let roleSearchDataSource = [];
for (let i = 0; i < 46; i += 1) {
  roleSearchDataSource.push({
    key: i,
    name: `用户管理员 ${i}`,
    roleAuth: "用户管理",
    desc: "用于管理用户信息"
  });
}
function getRole(req, res, u) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }
  const params = parse(url, true).query;
  let dataSource = roleSearchDataSource;
  if (params.name) {
    dataSource = dataSource.filter(data => data.name.indexOf(params.name) > -1);
  }
  let pageSize = 10;
  if (params.pageSize) {
    pageSize = params.pageSize * 1;
  }
  const result = {
    list: dataSource,
    pagination: {
      total: dataSource.length,
      pageSize,
      current: parseInt(params.currentPage, 10) || 1
    }
  };
   //console.log(result);
  return res.json(result);
}
function postRole(req, res, u, b) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }
  const body = (b && b.body) || req.body;
  const { method, name, desc, roleAuth, key } = body;
  switch (method) {
    /* eslint no-case-declarations:0 */
    case "delete":
      roleSearchDataSource = roleSearchDataSource.filter(
        item => key.indexOf(item.key) === -1
      );
      break;
    case "post":
      const i = Math.ceil(Math.random() * 10000);
      roleSearchDataSource.unshift({
        key: i,
        name,
        roleAuth,
        desc
      });
      break;
    case "update":
      roleSearchDataSource = roleSearchDataSource.map(item => {
        if (item.key === key) {
          Object.assign(item, { desc, name, roleAuth });
          return item;
        }
        return item;
      });
      break;
    default:
      break;
  }
  const result = {
    list: roleSearchDataSource,
    pagination: {
      total: roleSearchDataSource.length
    }
  };
  return res.json(result);
}
export default {
  "GET /api/role": getRole,
  "POST /api/role": postRole
};
