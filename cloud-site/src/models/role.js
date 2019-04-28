import { queryRole } from "@/services/rolesearch";
export default {
  namespace: "role",

  state: {
    roles: [],
    isLoading: false
  },
  effects: {
    *fetch(_, { call, put }) {
      yield put({
        type: "changeLoading",
        payload: true
      });
      const response = yield (yield call(queryRole)).json();
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
        roles: action.payload
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
