import { queryMenuFs } from "@/services/menuF";
export default {
  namespace: "menuF",

  state: {
    menuFs: [],
    isLoading: false
  },
  effects: {
    *fetchMenuFs(_, { call, put }) {
      yield put({
        type: "changeLoading",
        payload: true
      });
      const response = yield (yield call(queryMenuFs)).json();
      // console.log(response);
      yield put({
        type: "setMenuFs",
        payload: response
      });
      yield put({
        type: "changeLoading",
        payload: false
      });
    }
  },
  reducers: {
    setMenuFs(state, action) {
      return {
        ...state,
        menuFs: action.payload
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
