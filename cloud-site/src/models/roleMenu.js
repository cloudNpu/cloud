import { menuList } from "../services/rolesearch";

export default {
  namespace: "roleMenu",

  state: {
    menu: []
  },

  effects: {
    *menu(_, { call, put }) {
      const response = yield call(menuList);

      yield put({
        type: "menuList",
        payload: Array.isArray(response) ? response : []
      });
    }
  },

  reducers: {
    menuList(state, action) {
      return {
        ...state,
        menu: action.payload
      };
    }
  }
};
