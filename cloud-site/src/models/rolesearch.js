import {
  addRole,
  deleteRole,
  queryRole,
  updateRole
} from "@/services/rolesearch";

export default {
  namespace: "rolesearch",

  state: {
    data: {
      list: [],
      pagination: {}
    }
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield (yield call(queryRole, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
    },
    *add({ payload, callback }, { call, put }) {
      const response = yield (yield call(addRole, payload)).json();
      //console.log(response);
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *delete({ payload, callback }, { call, put }) {
      const response = yield (yield call(deleteRole, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    },
    *update({ payload, callback }, { call, put }) {
      const response = yield (yield call(updateRole, payload)).json();
      yield put({
        type: "save",
        payload: response
      });
      if (callback) callback();
    }
  },

  reducers: {
    save(state, action) {
      return {
        ...state,
        data: action.payload
      };
    }
  }
};
