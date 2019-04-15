import { queryMenus } from "@/services/menu";
export default {
  namespace: "menu",

  state: {
    menus: [],
    isLoading: false
  },
  effects: {
    *fetchMenus(_, { call, put }) {
      yield put({
        type: "changeLoading",
        payload: true
      });
      const response = yield (yield call(queryMenus)).json();
      //   console.log(response);
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
        menus: action.payload
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
