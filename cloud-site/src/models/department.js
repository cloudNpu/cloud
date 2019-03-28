import { addDept, deleteDept } from "@/services/department";
import { message } from "antd";

export default {
  namespace: "department",

  state: {
    step: {
      payAccount: "ant-design@alipay.com",
      receiverAccount: "test@example.com",
      receiverName: "Alex",
      amount: "500"
    }
  },

  effects: {
    *save({ payload }, { call, put }) {
      // yield call(addDept, payload)
      const response = yield call(addDept, payload);

      console.log(response);
      //console.log( response);

      if (response.status === 200) {
        message.success("提交成功");
      } else message.success("提交失败");
    },

    *delete({ payload }, { call }) {
      //yield call(deleteDept, payload);
      const response = yield call(deleteDept, payload);
      //console.log(response);
      //localStorage.clear();
      const { msg } = JSON.parse(response);
      /*  console.log(msg);*/
      if (response.status === 204) {
        message.success("删除成功");
      } else message.success(msg);
    }
  }
};
