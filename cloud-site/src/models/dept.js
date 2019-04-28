import { queryDept } from "@/services/dept";
export default {
  namespace: "dept",

  state: {
    depts: [],
    isLoading: false
  },
  effects: {
    *fetch(_, { call, put }) {
      yield put({
        type: "changeLoading",
        payload: true
      });
      const response = yield (yield call(queryDept)).json();
      yield put({
        type: "setMenus",
        payload: response
      });
      yield put({
        type: "changeLoading",
        payload: false
      });
    }
  },
  reducers: {
    setMenus(state, action) {
      return {
        ...state,
        depts: action.payload
      };
    },
    changeLoading(state, action) {
      return {
        ...state,
        isLoading: action.payload
      };
    }
  }
};
