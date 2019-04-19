import { queryService } from "@/services/service";

export default {
  namespace: "service",

  state: {
    ServiceData: []
  },

  effects: {
    *fetch(_, { call, put }) {
      console.log(localStorage.getItem("currentUser"));
      // console.log(localStorage.getItem("antd-pro-authority"));
      const response = yield (yield call(queryService)).json();
      yield put({
        type: "show",
        payload: {
          ServiceData: response
        }
      });
      console.log(response);
    }
  },

  reducers: {
    show(state, { payload }) {
      return {
        ...state,
        ...payload
      };
    }
  }
};
