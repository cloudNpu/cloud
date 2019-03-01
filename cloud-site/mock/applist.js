import { parse } from "url";
import { Table } from "antd";

// mock tableListDataSource
let tableListDataSource = [];
for (let i = 0; i < 46; i += 1) {
  tableListDataSource.push({
    key: i,
    // disabled: i % 6 === 0,
    href: "https://ant.design",
    app: `App ${i}`,
    userid: "Tzbobby",
    ipAddr: "10.0.0.0",
    port: "8080",
    instanceId: `S${i}`,
    appGroupName: `G${i}`,
    count: Math.floor(Math.random() * 1000),
    status: Math.floor(Math.random() * 10) % 2,
    visible: Math.floor(Math.random() * 10) % 2
  });
}

function getAppList(req, res, u) {
  let url = u;
  console.log(u);
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url;
  }

  const params = parse(url, true).query;

  let dataSource = tableListDataSource;

  if (params.visible) {
    const visible = params.visible.split(",");
    let filterDataSource = [];
    visible.forEach(s => {
      filterDataSource = filterDataSource.concat(
        dataSource.filter(
          data => parseInt(data.visible, 10) === parseInt(s[0], 10)
        )
      );
    });
    dataSource = filterDataSource;
  }

  if (params.app) {
    dataSource = dataSource.filter(data => data.app.indexOf(params.app) > -1);
  }
  if (params.instanceId) {
    dataSource = dataSource.filter(
      data => data.instanceId.indexOf(params.instanceId) > -1
    );
  }
  if (params.ipAddr) {
    dataSource = dataSource.filter(
      data => data.ipAddr.indexOf(params.ipAddr) > -1
    );
  }
  if (params.port) {
    dataSource = dataSource.filter(data => data.port.indexOf(params.port) > -1);
  }

  let pageSize = 5;
  if (params.pageSize) {
    pageSize = params.pageSize * 1;
  }

  const result = {
    list: dataSource,
    pagination: {
      total: dataSource.length,
      pageSize,
      current: parseInt(params.currentPage, 5) || 1
    }
  };

  return res.json(result);
}

function postAppList(req, res, u, b) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }

  const body = (b && b.body) || req.body;
  const {
    method,
    app,
    instanceId,
    ipAddr,
    port,
    userid,
    status,
    appGroupName,
    visible,
    txt,
    key
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
        app,
        instanceId,
        ipAddr,
        port,
        userid,
        status,
        appGroupName,
        visible,
        txt,
        count: Math.floor(Math.random() * 1000)
      });
      break;
    case "update":
      tableListDataSource = tableListDataSource.map(item => {
        if (item.key === key) {
          Object.assign(item, {
            app,
            instanceId,
            ipAddr,
            port,
            userid,
            status,
            appGroupName
          });
          return item;
        }
        return item;
      });
      break;
    case "change":
      tableListDataSource = tableListDataSource.map(item => {
        if (item.key === key) {
          Object.assign(item, {
            visible
          });
          return item;
        }
        return item;
      });
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
  "GET /api/applist": getAppList,
  "POST /api/applist": postAppList
};
