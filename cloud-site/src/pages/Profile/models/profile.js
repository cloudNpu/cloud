import { queryBasicProfile, queryAdvancedProfile } from "@/services/api";

export default {
  namespace: "profile",

  state: {
    basicGoods: [],
    advancedOperation1: [],
    advancedOperation2: [],
    advancedOperation3: []
  },

  effects: {
    *fetchBasic(_, { call, put }) {
      console.log(call);
      const response = yield (yield call(queryBasicProfile)).json();
      yield put({
        type: "show",
        payload: response
      });
      console.log(response);
    },
    *fetchAdvanced(_, { call, put }) {
      const response = yield (yield call(queryAdvancedProfile)).json();
      yield put({
        type: "show",
        payload: response
      });
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
