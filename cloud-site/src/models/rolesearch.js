import {
  addRole,
  deleteRole,
  queryRole,
  updateRole,
  menuList
} from "@/services/rolesearch";

export default {
  namespace: "rolesearch",

  state: {
    data: {
      list: [],
      pagination: {}
    },
    menu: []
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryRole, payload);

      yield put({
        type: "save",
        payload: response
      });
    },
    *add({ payload, callback }, { call, put }) {
      const response = yield call(addRole, payload);
      console.log(response);
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *delete({ payload, callback }, { call, put }) {
      const response = yield call(deleteRole, payload);
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *update({ payload, callback }, { call, put }) {
      const response = yield call(updateRole, payload);
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *menu(_, { call, put }) {
      const response = yield call(menuList);

      yield put({
        type: "menuList",
        payload: Array.isArray(response) ? response : []
      });
    }
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload
      };
    },
    menuList(state, action) {
      return {
        ...state,
        menu: action.payload
      };
    }
  }
};
