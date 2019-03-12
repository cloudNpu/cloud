import { parse } from "url";

const currentUser = {
  key: "001",
  username: "张三",
  dept: "技术部",
  mobile: 13366668888,
  avatar: "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png",
  officeTel: "6666-4796521",
  new_passward: "",
  new_passward_again: ""
};
//加入fetch
function postCenter(req, res, u, b) {
  let url = u;
  if (!url || Object.prototype.toString.call(url) !== "[object String]") {
    url = req.url; // eslint-disable-line
  }

  const body = (b && b.body) || req.body;
  const {
    method,
    username,
    dept,
    mobile,
    officeTel,
    key,
    avatar,
    new_passward,
    new_passward_again
  } = body;

  switch (method) {
    case "update":
      if (currentUser.key === item.key) {
        Object.assign(item, {
          key,
          username,
          dept,
          mobile,
          officeTel,
          avatar,
          new_passward,
          new_passward_again
        });
        return item;
      }
      return item;
      break;
  }
}

export default {
  "POST /api/center": postCenter
};
