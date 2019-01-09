import { routerRedux } from "dva/router";
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
    /*  *submitDepartmentEdit({ payload }, { call, put }) {
            yield call(fakeDepartmentEdit, payload);
            message.success('提交成功');
        },*/

    *save({ payload }, { call, put }) {
      const response = yield call(addDept, payload);
      if (response.status == 200) {
        message.success("提交成功");
      } else message.success("提交失败");
    },

    *delete({ payload }, { call, put }) {
      const response = yield call(deleteDept, payload);
      /* console.log(response);*/
      const { msg } = JSON.parse(response);
      /* console.log(msg);*/
      if (response.status == 204) {
        message.success("删除成功");
      } else message.success(msg);
    }
  }
  /*reducers: {
        saveDepartmentEditData(state, { payload }) {
            return {
                ...state,
                step: {
                    ...state.step,
                    ...payload,
                },
            };
        },
    },*/
};
