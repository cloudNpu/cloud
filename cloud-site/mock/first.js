import { parse } from "url";
// mock roleSearchDataSource
let menuDataSource = [];
for (let i = 0; i < 46; i += 1) {
  menuDataSource.push({
    key: i,
    name: `菜单 ${i}`,
    nameItem: `菜单项 ${i}`,
    icon: "menu-fold",
    urll: "",
    comurl: "",
    router: ""
  });
}
function getMenu(req, res, u) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }
  const params = parse(url, true).query;
  let dataSource = menuDataSource;
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
function postMenu(req, res, u, b) {
  //console.log("postMenu");
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }
  const body = (b && b.body) || req.body;
  const { method, name, nameItem, icon, urll, comurl, router, key } = body;
  switch (method) {
    /* eslint no-case-declarations:0 */
    case "delete":
      menuDataSource = menuDataSource.filter(
        item => key.indexOf(item.key) === -1
      );
      break;
    case "post":
      const i = Math.ceil(Math.random() * 10000);
      menuDataSource.unshift({
        key: i,
        name,
        nameItem,
        icon,
        urll,
        comurl,
        router
      });
      break;
    case "update":
      menuDataSource = menuDataSource.map(item => {
        if (item.key === key) {
          Object.assign(item, { name, nameItem, icon, urll, comurl, router });
          return item;
        }
        return item;
      });
      break;
    default:
      break;
  }
  const result = {
    list: menuDataSource,
    pagination: {
      total: menuDataSource.length
    }
  };
  return res.json(result);
}
export default {
  "GET /api/menus": getMenu,
  "POST /api/menus": postMenu
};
