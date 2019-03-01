import { parse } from "url";

// mock tableListDataSource
let tableListDataSource = [];
for (let i = 0; i < 46; i += 1) {
  tableListDataSource.push({
    key: i,
    username: `张${i}号`,
    dept: Math.floor(Math.random() * 10) % 6,
    sex: Math.floor(Math.random() * 10) % 2,
    birthday: `1990-01-${Math.floor(i / 2) + 1}`,
    mobile: "XXX-XXXX-XXXX",
    officeTel: "XXXX-XXXXXXX",
    role: "普通用户",
    my_service: ""
  });
}
function getFound(req, res, u) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }

  const params = parse(url, true).query;

  let dataSource = tableListDataSource;

  //过滤掉不符合条件的数据
  if (params.dept) {
    const dept = params.dept.split(",");
    let filterDataSource = [];
    dept.forEach(s => {
      filterDataSource = filterDataSource.concat(
        dataSource.filter(
          data => parseInt(data.dept, 10) === parseInt(s[0], 10)
        )
      );
    });
    dataSource = filterDataSource;
  }

  if (params.username) {
    dataSource = dataSource.filter(
      data => data.username.indexOf(params.username) > -1
    );
  }
  if (params.mobile) {
    dataSource = dataSource.filter(
      data => data.mobile.indexOf(params.mobile) > -1
    );
  }
  if (params.officeTel) {
    dataSource = dataSource.filter(
      data => data.officeTel.indexOf(params.officeTel) > -1
    );
  }
  if (params.birthday) {
    dataSource = dataSource.filter(
      data => data.birthday.indexOf(params.birthday) > -1
    );
  }
  if (params.role) {
    dataSource = dataSource.filter(data => data.role.indexOf(params.role) > -1);
  }
  if (params.sex) {
    const sex = params.sex.split(",");
    let filterDataSource = [];
    sex.forEach(s => {
      filterDataSource = filterDataSource.concat(
        dataSource.filter(data => parseInt(data.sex, 10) === parseInt(s[0], 10))
      );
    });
    dataSource = filterDataSource;
  }
  let pageSize = 7;
  if (params.pageSize) {
    pageSize = params.pageSize * 1;
  }

  const result = {
    list: dataSource,
    pagination: {
      total: dataSource.length,
      pageSize,
      current: parseInt(params.currentPage, 7) || 1
    }
  };
  return res.json(result);
}

function postFound(req, res, u, b) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }

  const body = (b && b.body) || req.body;
  const {
    method,
    username,
    dept,
    sex,
    birthday,
    mobile,
    officeTel,
    role,
    key,
    my_service
  } = body;

  switch (method) {
    /* eslint no-case-declarations:0 */
    case "delete":
      tableListDataSource = tableListDataSource.filter(
        item => key.indexOf(item.key) === -1
      );
      break;
    case "post":
      const i = Math.ceil(Math.random() * 10000);
      tableListDataSource.unshift({
        key: i,
        username,
        dept,
        sex,
        birthday,
        mobile,
        officeTel,
        role
      });
      break;
    case "update":
      tableListDataSource = tableListDataSource.map(item => {
        if (item.key === key) {
          Object.assign(item, {
            username,
            dept,
            sex,
            birthday,
            mobile,
            officeTel,
            role
          });
          return item;
        }
        return item;
      });
      break;
    case "add_user_app":
      /* tableListDataSource = tableListDataSource.map(item => {
                 if (item.key === key) {
                     Object.assign(item.service, {
                        service
                     });
                     return item.service;
                 }
                 return item.service;
             });*/
      console.log("receive successfully");
      break;
    case "add_user_role":
      console.log("receive data successfully");
      break;
    default:
      break;
  }

  const result = {
    list: tableListDataSource,
    pagination: {
      total: tableListDataSource.length
    }
  };

  return res.json(result);
}

export default {
  "GET /api/found": getFound,
  "POST /api/found": postFound
};
