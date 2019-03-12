import { queryService } from "@/services/api";

export default {
  namespace: "service",

  state: {},

  effects: {
    *fetch(_, { call, put }) {
      console.log(call);
      const response = yield (yield call(queryService)).json();
      yield put({
        type: "show",
        payload: response
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
